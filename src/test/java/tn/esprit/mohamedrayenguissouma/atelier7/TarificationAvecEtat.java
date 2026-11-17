package tn.esprit.mohamedrayenguissouma.atelier7;

import org.springframework.stereotype.Service;
import tn.esprit.mohamedrayenguissouma.domain.Reservation;
import tn.esprit.mohamedrayenguissouma.service.tarification.ITarificationService;
import tn.esprit.mohamedrayenguissouma.service.tarification.TarificationServiceImpl;

/**
 * Atelier 7 - Mission 1 : le code "AVANT", tel que le stagiaire l'avait ecrit.
 * Garde uniquement dans les tests pour reproduire le bug : un singleton Spring
 * avec un attribut modifiable partage par toutes les requetes.
 */
@Service
public class TarificationAvecEtat implements ITarificationService {

    private double dernierMontant;      // pour afficher « le dernier prix calcule »

    @Override
    public double calculerMontant(Reservation r, String codePromo) {
        dernierMontant = new TarificationServiceImpl().calculerMontant(r, codePromo);
        // ... quelques lignes de plus (simulees par une courte pause) ...
        pause(100);
        return dernierMontant;
    }

    private static void pause(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
