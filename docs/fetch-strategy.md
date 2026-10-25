# Stratégie de chargement (fetch) et de cascade — AutoLoc

Atelier 2 — Associations JPA : Cascade et Fetch.

## Règles générales

- **Côté propriétaire** : le côté `@ManyToOne` (ou le `@OneToOne` qui porte la clé étrangère). C'est lui qui crée la colonne en base.
- **Côté inverse** : la collection `@OneToMany` / `@ManyToMany`, déclarée avec `mappedBy`. Elle ne crée ni colonne ni table.
- **Fetch** : `LAZY` partout. `@ManyToOne` et `@OneToOne` sont `EAGER` par défaut, on force donc `LAZY` pour ne pas charger toute la chaîne d'objets liés. `@OneToMany` et `@ManyToMany` sont déjà `LAZY` par défaut.
- **Cascade** : uniquement quand l'enfant n'a pas de sens sans son parent (composition) ou quand on veut enregistrer le parent et ses nouveaux enfants en une seule opération. Jamais de cascade `REMOVE` vers une entité qui doit survivre à son parent.

## Tableau des associations

| Association | Annotations | Côté propriétaire (colonne FK) | Fetch | Cascade | Justification |
|---|---|---|---|---|---|
| Contrat → Paiement | `@OneToMany` / `@ManyToOne` | Paiement (`id_contrat`) | LAZY | ALL + `orphanRemoval` | Un paiement n'existe que rattaché à son contrat (composition) : supprimer le contrat supprime ses paiements, et retirer un paiement de la liste le supprime en base. |
| Agence → Vehicule | `@OneToMany` / `@ManyToOne` | Vehicule (`id_agence`) | LAZY | Aucune | Un véhicule survit à son agence (il peut être transféré) : supprimer une agence ne doit pas supprimer sa flotte. |
| Agence → Employe | `@OneToMany` / `@ManyToOne` | Employe (`id_agence`) | LAZY | Aucune | Un employé a sa propre existence (mutation possible vers une autre agence) ; on le gère via son propre repository. |
| Vehicule ↔ Equipement | `@ManyToMany` | Vehicule (`@JoinTable vehicule_equipement`) | LAZY | Aucune | Les équipements (GPS, siège bébé…) sont partagés entre plusieurs véhicules : supprimer un véhicule ne doit pas supprimer l'équipement. `Set` pour éviter les doublons dans la table de jointure. |
| Client → Reservation | `@OneToMany` / `@ManyToOne` | Reservation (`id_client`) | LAZY | PERSIST | Enregistrer un client avec ses nouvelles réservations en une seule opération. Pas de REMOVE : l'historique des réservations est conservé. |
| Reservation → Vehicule | `@ManyToOne` (+ `@OneToMany` côté Vehicule) | Reservation (`id_vehicule`) | LAZY | Aucune côté Reservation ; PERSIST côté Vehicule | Supprimer ou modifier une réservation ne doit jamais toucher au véhicule. |
| Reservation ↔ Contrat | `@OneToOne` | Contrat (`id_reservation`, unique) | LAZY | ALL côté Reservation | Le contrat est la concrétisation d'une réservation : il suit son cycle de vie (création et suppression avec elle). |
| Vehicule → Maintenance | `@OneToMany` / `@ManyToOne` | Maintenance (`id_vehicule`) | LAZY | PERSIST | Enregistrer un véhicule avec ses nouvelles maintenances en une fois. Pas de REMOVE : l'historique d'entretien est une donnée de traçabilité. |

## LAZY ou EAGER ?

| Fetch | Comportement | Conséquence |
|---|---|---|
| **LAZY** (retenu) | Les entités liées ne sont chargées qu'au premier accès (`getPaiements()`, `getAgence()`…). | Chaque lecture reste légère. Un accès hors transaction lève une `LazyInitializationException`, à traiter par une requête dédiée (Atelier 7). |
| EAGER | Les entités liées sont chargées systématiquement, même si on ne les utilise pas. | Plus simple à utiliser, mais alourdit chaque lecture et peut charger une grande partie de la base. |

## Points techniques

- Pas de `@Data` ni de `@ToString` sur les entités : le `toString()` / `hashCode()` généré parcourrait les deux côtés d'une association bidirectionnelle et provoquerait un `StackOverflowError`. Lombok ciblé : `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`.
- `@JsonIgnore` sur chaque côté inverse (`mappedBy`) pour éviter une récursion infinie lors de la sérialisation JSON par le RestController.
- `@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})` sur les entités ciblées par un `LAZY` : Hibernate renvoie alors un proxy, dont ces champs techniques ne doivent pas être sérialisés.
