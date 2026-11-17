package tn.esprit.mohamedrayenguissouma.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.mohamedrayenguissouma.domain.Contrat;
import tn.esprit.mohamedrayenguissouma.domain.ModePaiement;
import tn.esprit.mohamedrayenguissouma.domain.Paiement;
import tn.esprit.mohamedrayenguissouma.domain.Reservation;
import tn.esprit.mohamedrayenguissouma.repository.IContratRepository;
import tn.esprit.mohamedrayenguissouma.repository.IReservationRepository;
import tn.esprit.mohamedrayenguissouma.service.location.legacy.SmsGateway;
import tn.esprit.mohamedrayenguissouma.service.location.legacy.SmtpClient;

/**
 * Confirmation des reservations.
 * Ecrit en urgence par le stagiaire. NE PAS MODIFIER : ca marche.
 *
 * Fichier fourni pour l'Atelier 6. Adaptez uniquement les noms des
 * getters/setters si votre modele (Atelier 2) utilise d'autres noms
 * (ex. getIdReservation() au lieu de getId()).
 *
 * Adaptations au modele AutoLoc (marquees "adapte") : packages,
 * getIdXxx() au lieu de getId(), et conversions double/BigDecimal
 * (tarifJournalier, montant et montantTotal sont des BigDecimal dans le modele).
 */
@Service
public class LocationManager {

    @Autowired private IReservationRepository reservationRepository;
    @Autowired private IContratRepository contratRepository;

    public Contrat confirmerReservation(Long reservationId, String modePaiement,
                                        String codePromo, String canal) {

        Reservation r = reservationRepository.findById(reservationId).orElse(null);
        if (r == null) throw new RuntimeException("Reservation introuvable");

        // ---------- 1. disponibilite ----------
        for (Reservation x : reservationRepository.findAll()) {
            if (x.getVehicule().getIdVehicule().equals(r.getVehicule().getIdVehicule()) // adapte : getId()
                    && !x.getIdReservation().equals(r.getIdReservation())                // adapte : getId()
                    && !(r.getDateFin().isBefore(x.getDateDebut())
                         || r.getDateDebut().isAfter(x.getDateFin()))) {
                throw new RuntimeException("Vehicule indisponible");
            }
        }

        // ---------- 2. tarif de base ----------
        long j = ChronoUnit.DAYS.between(r.getDateDebut(), r.getDateFin()) + 1;
        double m = j * r.getVehicule().getTarifJournalier().doubleValue(); // adapte : BigDecimal -> double

        // ---------- 3. remises ----------
        String t = r.getClient().getType();
        if (t.equals("PARTICULIER")) {
            if (j >= 7) m = m * 0.90;
        } else if (t.equals("ENTREPRISE")) {
            m = m * 0.85;
        } else if (t.equals("VIP")) {
            m = m * 0.80;
        }
        if (codePromo != null && codePromo.equals("BIENVENUE")) {
            m = m - 20;
        }

        // ---------- 4. paiement ----------
        Paiement p = new Paiement();
        p.setDatePaiement(LocalDate.now());
        if (modePaiement.equals("CARTE")) {
            System.out.println("Debit de la carte bancaire...");
            m = m * 1.01; // 1 % de frais
            p.setModePaiement(ModePaiement.CARTE);
        } else if (modePaiement.equals("VIREMENT")) {
            System.out.println("Attente du virement...");
            p.setModePaiement(ModePaiement.VIREMENT);
        } else if (modePaiement.equals("ESPECES")) {
            System.out.println("Encaissement en especes a l'agence");
            p.setModePaiement(ModePaiement.ESPECES);
        } else {
            throw new RuntimeException("Mode de paiement inconnu : " + modePaiement);
        }
        p.setMontant(BigDecimal.valueOf(m)); // adapte : double -> BigDecimal

        // ---------- 5. contrat ----------
        Contrat c = new Contrat();
        c.setDateSignature(LocalDate.now());
        c.setMontantTotal(BigDecimal.valueOf(m)); // adapte : double -> BigDecimal
        c.setValide(true);
        c.getPaiements().add(p);
        p.setContrat(c);
        Contrat res = contratRepository.save(c);

        // ---------- 6. notification ----------
        if (canal.equals("EMAIL")) {
            SmtpClient smtp = new SmtpClient("smtp.autoloc.tn", 25);
            smtp.send(r.getClient().getEmail(), "Contrat " + res.getIdContrat() + " confirme"); // adapte : getId()
        } else if (canal.equals("SMS")) {
            SmsGateway sms = new SmsGateway("https://sms.autoloc.tn");
            sms.send(r.getClient().getTelephone(), "Contrat " + res.getIdContrat() + " confirme"); // adapte : getId()
        }

        return res;
    }
}
