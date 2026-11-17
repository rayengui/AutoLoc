# Couche Repository — AutoLoc

Atelier 3 — Spring Data JPA : interfaces repository et opérations CRUD.

## Choix de l'interface

Les neuf repositories sont dans le package `repository`, préfixés `I`, et étendent tous
`JpaRepository<Entité, Long>`. Au démarrage, Spring Data affiche
`Found 9 JPA repository interfaces` : aucune classe d'implémentation n'est écrite, le proxy
(`SimpleJpaRepository`) est généré à l'exécution, et `@Repository` est inutile.

| Interface | Étend | Justification |
|---|---|---|
| IContratRepository | JpaRepository<Contrat, Long> | CRUD complet, `findAll` renvoie une `List`, `saveAndFlush` disponible. Les suppressions passent par le contexte de persistance : cascade ALL et orphanRemoval vers les paiements s'appliquent. |
| IPaiementRepository | JpaRepository<Paiement, Long> | Utilisé en lecture : un paiement se crée ou se retire via son contrat (composition). |
| IAgenceRepository | JpaRepository<Agence, Long> | CRUD, listes, tri par ville ou par nom. |
| IEmployeRepository | JpaRepository<Employe, Long> | CRUD et listes ; pagination utile pour une grande agence. |
| IVehiculeRepository | JpaRepository<Vehicule, Long> | CRUD, tri (par tarif, par marque) et pagination du catalogue. |
| IEquipementRepository | JpaRepository<Equipement, Long> | CRUD simple sur une table de référence. |
| IClientRepository | JpaRepository<Client, Long> | CRUD, listes et pagination de la clientèle. |
| IReservationRepository | JpaRepository<Reservation, Long> | CRUD, tri par date de début, pagination de l'historique. |
| IMaintenanceRepository | JpaRepository<Maintenance, Long> | CRUD et historique d'entretien trié par date. |

`JpaRepository` est retenue partout pour l'homogénéité : elle réunit `ListCrudRepository`
(CRUD avec des `List` au lieu d'`Iterable`) et `ListPagingAndSortingRepository` (tri, pagination),
ce qu'il faudrait sinon déclarer séparément depuis Spring Data 3.

### Points d'attention

- `save` avec un identifiant `null` déclenche un `persist` (INSERT) ; avec un identifiant renseigné,
  un `merge` (SELECT puis UPDATE). Toujours utiliser l'objet renvoyé.
- `deleteById` ne lève aucune exception si l'identifiant n'existe pas (Spring Data 3).
- `deleteAllInBatch` / `deleteAllByIdInBatch` contournent le contexte de persistance : ni cascade ni
  orphanRemoval. À ne pas utiliser sur `Contrat` (paiements orphelins ou violation de clé étrangère).

## Anomalies SonarQube for IDE

Analyse réalisée avec SonarQube for IDE 13.2 dans IntelliJ IDEA (« Analyze All Files with
SonarQube for IDE »). Les entités et les repositories étaient déjà propres (Lombok ciblé, sans
`@Data`). Les anomalies ci-dessous ont été détectées par l'outil, puis corrigées.

| Anomalie SonarQube for IDE | Règle / explication | Correction apportée |
|---|---|---|
| `Remove this unused import 'java.util.Optional'` (IContratRepository) | java:S1128 — un import inutilisé alourdit le fichier et masque les vraies dépendances. | Import supprimé. |
| `This block of commented-out lines of code should be removed` (IContratRepository) | java:S125 — du code mis en commentaire est du code mort : il n'est ni compilé ni testé, et devient vite faux. | Ligne commentée supprimée ; une méthode dérivée sera ajoutée quand un besoin réel existera. |
| `Replace this persistent entity with a simple POJO or DTO object` (AgenceRestController, `create` et `update`) | java:S4684 — recevoir une entité JPA directement dans `@RequestBody` expose au *mass assignment* : le client peut renseigner des champs qu'il ne devrait pas (identifiant, associations). | Relevée, non corrigée à ce stade : la correction (DTO d'entrée) relève de la couche web et de la validation (Atelier 5 / Chapitre 10). |
