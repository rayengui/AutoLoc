package tn.esprit.mohamedrayenguissouma.atelier7;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tn.esprit.mohamedrayenguissouma.domain.Client;
import tn.esprit.mohamedrayenguissouma.domain.Reservation;
import tn.esprit.mohamedrayenguissouma.domain.Vehicule;
import tn.esprit.mohamedrayenguissouma.service.tarification.ITarificationService;
import tn.esprit.mohamedrayenguissouma.service.tarification.TarificationServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Atelier 7 - Mission 1 « Les prix qui se melangent ».
 * Tests unitaires sans base de donnees : ils montrent combien d'instances Spring
 * cree, puis ce qui arrive quand deux clients calculent un prix en meme temps.
 */
class Mission1PrixMelangesTest {

    // Client A : Clio a 80 €/jour, 3 jours, PARTICULIER -> 240 €
    private final Reservation reservationA = reservation("123 TU 4567", "80.00", "PARTICULIER", 15, 17);
    // Client B : Peugeot 508 a 150 €/jour, 5 jours, ENTREPRISE (-15 %) -> 637,5 €
    private final Reservation reservationB = reservation("234 TU 5678", "150.00", "ENTREPRISE", 15, 19);

    @Test
    void springCreeUneSeuleInstanceDuService() {
        try (var context = new AnnotationConfigApplicationContext(TarificationAvecEtat.class)) {
            Object pourClientA = context.getBean(ITarificationService.class);
            Object pourClientB = context.getBean(ITarificationService.class);

            System.out.println("[Singleton] bean demande par le client A : " + identite(pourClientA));
            System.out.println("[Singleton] bean demande par le client B : " + identite(pourClientB));
            String nomDuBean = context.getBeanNamesForType(ITarificationService.class)[0];
            boolean singleton = context.getBeanFactory().getBeanDefinition(nomDuBean).isSingleton();
            System.out.println("[Singleton] meme objet ? " + (pourClientA == pourClientB)
                    + " | scope singleton ? " + singleton);
            assertSame(pourClientA, pourClientB);
            assertTrue(singleton);
        }
    }

    @Test
    void avant_leSingletonAvecEtatMelangeLesPrix() throws Exception {
        double[] vus = deuxClientsEnMemeTemps(new TarificationAvecEtat());
        afficher("AVANT (attribut dernierMontant)", vus);
        assertEquals(637.5, vus[0], "le client A voit le prix du client B : bug reproduit");
    }

    @Test
    void apres_leSingletonSansEtatDonneLeBonPrixAChacun() throws Exception {
        double[] vus = deuxClientsEnMemeTemps(new TarificationServiceImpl());
        afficher("APRES (variable locale)", vus);
        assertEquals(240.0, vus[0]);
        assertEquals(637.5, vus[1]);
    }

    /** Le client A lance son calcul, le client B arrive 30 ms plus tard sur LA MEME instance. */
    private double[] deuxClientsEnMemeTemps(ITarificationService singleton) throws Exception {
        ExecutorService requetes = Executors.newFixedThreadPool(2);
        try {
            Future<Double> clientA = requetes.submit(() -> singleton.calculerMontant(reservationA, null));
            Thread.sleep(30);
            Future<Double> clientB = requetes.submit(() -> singleton.calculerMontant(reservationB, null));
            return new double[]{clientA.get(5, TimeUnit.SECONDS), clientB.get(5, TimeUnit.SECONDS)};
        } finally {
            requetes.shutdownNow();
        }
    }

    private static void afficher(String version, double[] vus) {
        System.out.printf("[%s] client A : attendu 240.0, affiche %.1f%s%n", version, vus[0],
                vus[0] == 240.0 ? "  OK" : "  <-- prix du client B !");
        System.out.printf("[%s] client B : attendu 637.5, affiche %.1f%s%n", version, vus[1],
                vus[1] == 637.5 ? "  OK" : "  <-- prix du client A !");
    }

    private static String identite(Object bean) {
        return bean.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(bean));
    }

    private static Reservation reservation(String immatriculation, String tarif, String typeClient,
                                           int jourDebut, int jourFin) {
        Vehicule v = new Vehicule();
        v.setImmatriculation(immatriculation);
        v.setTarifJournalier(new BigDecimal(tarif));
        Client c = new Client();
        c.setType(typeClient);
        Reservation r = new Reservation();
        r.setVehicule(v);
        r.setClient(c);
        r.setDateDebut(LocalDate.of(2026, 11, jourDebut));
        r.setDateFin(LocalDate.of(2026, 11, jourFin));
        return r;
    }
}
