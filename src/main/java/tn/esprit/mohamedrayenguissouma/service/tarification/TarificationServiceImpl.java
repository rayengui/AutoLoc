package tn.esprit.mohamedrayenguissouma.service.tarification;

import org.springframework.stereotype.Service;
import tn.esprit.mohamedrayenguissouma.domain.Reservation;

import java.time.temporal.ChronoUnit;

/**
 * Atelier 7 - Mission 1 : correction n°1, service SANS ETAT.
 *
 * Spring cree une seule instance de ce bean (scope singleton par defaut),
 * partagee par tous les threads qui traitent les requetes HTTP.
 * Il ne doit donc avoir aucun attribut modifiable : le montant est une
 * variable locale, stockee dans la pile du thread qui fait le calcul,
 * donc propre a chaque appel.
 */
@Service
public class TarificationServiceImpl implements ITarificationService {

    @Override
    public double calculerMontant(Reservation reservation, String codePromo) {
        long jours = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin()) + 1;
        double montant = jours * reservation.getVehicule().getTarifJournalier().doubleValue();

        montant = appliquerRemiseClient(montant, jours, reservation.getClient().getType());
        if ("BIENVENUE".equals(codePromo)) {
            montant = montant - 20;
        }
        return montant;
    }

    private double appliquerRemiseClient(double montant, long jours, String typeClient) {
        if ("ENTREPRISE".equals(typeClient)) {
            return montant * 0.85;
        }
        if ("VIP".equals(typeClient)) {
            return montant * 0.80;
        }
        // PARTICULIER : -10 % a partir de 7 jours
        return jours >= 7 ? montant * 0.90 : montant;
    }
}
