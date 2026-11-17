# Couche Service — AutoLoc

Atelier 4 — Inversion de contrôle, injection de dépendances et couche Service.

## A. Modes d'injection comparés

Expérience de l'étape 2 sur `ContratServiceImpl` (injection par attribut, puis retour au constructeur).

| Critère | Constructeur (`final` + `@RequiredArgsConstructor`) | Attribut (`@Autowired private`) |
|---|---|---|
| Champ `final` possible | Oui | Non |
| Dépendance visible dans la signature de la classe | Oui, paramètre du constructeur | Non, cachée dans un champ privé |
| Utilisable avec `new` hors conteneur sans dépendance `null` | Oui : impossible de créer l'objet sans son repository | Non : le champ reste `null` hors de Spring |
| Signalé par SonarQube for IDE | Non | Oui — java:S6813 « Remove this field injection and use constructor injection instead » |

L'application démarre dans les deux cas : la différence porte sur la conception et la testabilité.
Convention retenue : injection par constructeur pour tous les services.

## B. Services

Tous les services sont des beans `@Service`, singletons sans état, injectés par constructeur
(champ `final` + `@RequiredArgsConstructor`), et dépendent d'une interface de repository.

| Service | Dépendances injectées | Mode | Justification / règle de gestion |
|---|---|---|---|
| ContratServiceImpl | IContratRepository | Constructeur | Montant total négatif refusé ; la suppression d'un contrat supprime ses paiements par cascade. |
| AgenceServiceImpl | IAgenceRepository | Constructeur | Nom et ville obligatoires. |
| EmployeServiceImpl | IEmployeRepository | Constructeur | Nom et prénom obligatoires. |
| VehiculeServiceImpl | IVehiculeRepository | Constructeur | Tarif journalier absent ou négatif refusé. |
| EquipementServiceImpl | IEquipementRepository | Constructeur | Libellé obligatoire (le modèle n'a pas d'attribut quantité). |
| ClientServiceImpl | IClientRepository | Constructeur | Nom et e-mail obligatoires ; type `PARTICULIER` par défaut (Atelier 6). |
| ReservationServiceImpl | IReservationRepository | Constructeur | Date de fin antérieure à la date de début refusée. |
| MaintenanceServiceImpl | IMaintenanceRepository | Constructeur | Date de fin antérieure à la date de début refusée (le modèle n'a pas d'attribut coût). |
| PaiementServiceImpl | IPaiementRepository | Constructeur | Lecture seule (`findById`, `findAll`) : un paiement ne se crée, ne se modifie et ne se supprime qu'à travers son contrat. |

Dans chaque service CRUD : `create` refuse un identifiant non `null`, `findById` lève
`ResourceNotFoundException`, `update` charge l'entité existante puis recopie les attributs
modifiables, `deleteById` vérifie l'existence avant de supprimer.

## C. Messages d'erreur du conteneur

Les trois erreurs ont été reproduites sur le projet puis le code a été restauré.

**Message 1 — `required a bean of type 'IContratService' that could not be found`**
(`NoSuchBeanDefinitionException`). Reproduit en retirant `@Service` de `ContratServiceImpl`.
Causes probables : annotation `@Service` oubliée, ou classe placée hors du package racine
`tn.esprit.mohamedrayenguissouma` et de ses sous-packages, donc jamais scannée.
Correction : remettre `@Service` et vérifier le package.

**Message 2 — `required a single bean, but 2 were found`** (`NoUniqueBeanDefinitionException`).
Reproduit en ajoutant une seconde implémentation `ContratServiceBisImpl` de `IContratService`.
Deux corrections : poser `@Primary` **sur la classe** de l'implémentation à utiliser par défaut ;
ou poser `@Qualifier("contratServiceImpl")` **sur le paramètre du constructeur** du client pour
choisir explicitement le bean (nom par défaut = nom de la classe, première lettre en minuscule).
Avec Lombok, le `@Qualifier` doit être recopié sur le paramètre généré (`lombok.config` :
`lombok.copyableAnnotations += org.springframework.beans.factory.annotation.Qualifier`) ou le
constructeur écrit à la main.

**Message 3 — `The dependencies of some of the beans in the application context form a cycle`**
(`BeanCurrentlyInCreationException`). Reproduit avec `ClientServiceImpl` → `IReservationService`
et `ReservationServiceImpl` → `IClientService`. Avec l'injection par constructeur, chaque bean
exige l'autre déjà construit pour pouvoir être créé : aucun ordre de création n'est possible, et
Spring interdit par défaut les références circulaires. Piste : extraire la responsabilité commune
dans un troisième service, ou faire dépendre l'un des deux services du repository de l'autre
plutôt que du service.

## D. Anomalies SonarQube for IDE

| Anomalie | Règle / explication | Correction apportée |
|---|---|---|
| `Remove this field injection and use constructor injection instead` (ContratServiceImpl, expérience étape 2) | java:S6813 — l'injection par attribut cache la dépendance, interdit `final` et laisse un champ `null` hors conteneur. | Retour à `private final IContratRepository` + `@RequiredArgsConstructor`. |
| `Remove this unused import 'java.util.Optional'` (ContratServiceImpl) | java:S1128 — import inutilisé. | Import supprimé. |
| `This block of commented-out lines of code should be removed` (ContratServiceImpl, commentaire au-dessus de `verifier`) | java:S125 — ce commentaire de fin de ligne, avec sa parenthèse, ressemblait assez à une instruction Java pour être pris pour du code mis en commentaire. | Commentaires des règles de gestion réécrits en Javadoc (`/** Regle de gestion : … */`) dans les huit services. |

Expérience S112 : `RuntimeException` à la place de `ResourceNotFoundException` dans
`findById` n'a **pas** été signalée, car l'exception est construite dans le *supplier* de
`orElseThrow` et non levée par un `throw` direct, seul cas couvert par java:S112. L'exception
métier reste préférable : elle nomme la ressource et l'identifiant, et pourra être traduite en
réponse HTTP 404 à l'Atelier 5.

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?** Il n'est écrit nulle part dans
   notre code. Le conteneur Spring (`ApplicationContext`, créé par `SpringApplication.run`) détecte
   la classe grâce à `@Service` lors du scan, puis appelle lui-même son constructeur en lui passant
   le proxy de `IContratRepository`.
2. **Pourquoi un contrôleur dépend-il de `IContratService` et non de `ContratServiceImpl` ?** Pour
   dépendre d'une abstraction : on peut changer d'implémentation, en ajouter une autre
   (`@Primary`/`@Qualifier`) ou fournir une doublure de test sans modifier le contrôleur (principe
   d'inversion des dépendances).
3. **Pourquoi un service singleton doit-il rester sans état ?** Une seule instance est partagée par
   tous les threads qui traitent les requêtes. Exemple de bug (Atelier 7, mission 1) : un attribut
   `dernierMontant` dans le service de tarification ; deux clients simultanés écrivent le même
   attribut et l'un voit le prix de l'autre.
4. **Pourquoi `update` charge-t-il l'entité existante avant `save` ?** Passer l'objet reçu à `save`
   fusionnerait toutes ses colonnes, y compris celles non envoyées (mises à `null`) ; avec
   `orphanRemoval`, une liste de paiements vide dans l'objet reçu supprimerait les paiements en base.
   Charger puis recopier les seuls attributs modifiables protège l'identifiant et les associations.
5. **`@Component` / `@Bean`, `@Primary` / `@Qualifier`, `IPaiementService` :** `@Component` se pose
   sur une classe que l'on écrit et qui est détectée par le scan ; `@Bean` se pose sur une méthode
   d'une classe `@Configuration` pour déclarer un objet que l'on ne peut pas annoter (bibliothèque,
   classe du JDK). `@Primary` désigne, sur la classe, le candidat par défaut ; `@Qualifier`
   choisit explicitement un bean par son nom, au point d'injection. `IPaiementService` n'expose que
   la lecture car le paiement est une composition du contrat : on le crée ou on le retire en
   modifiant le contrat, dont la cascade ALL et l'orphanRemoval répercutent l'opération.
