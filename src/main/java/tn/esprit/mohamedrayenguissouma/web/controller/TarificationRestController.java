package tn.esprit.mohamedrayenguissouma.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Reservation;
import tn.esprit.mohamedrayenguissouma.service.IReservationService;
import tn.esprit.mohamedrayenguissouma.service.tarification.DernierPrixCalcule;
import tn.esprit.mohamedrayenguissouma.service.tarification.ITarificationService;

@Tag(name = "Tarification", description = "Atelier 7 - Mission 1 : calcul du prix d'une réservation")
@RestController
@RequestMapping("/tarification")
@RequiredArgsConstructor // injection par constructeur (attributs final)
public class TarificationRestController {

    private final IReservationService reservationService;
    private final ITarificationService tarificationService;   // singleton sans etat
    private final DernierPrixCalcule dernierPrixCalcule;      // proxy vers le bean de la requete en cours

    public record PrixResponse(Long idReservation, double montant, Double dernierPrixAffiche, String thread) {
    }

    @Operation(summary = "calculerMontant",
            description = "Calcule le prix d'une réservation (remise client + code promo éventuel)")
    @GetMapping("/{idReservation}")
    public PrixResponse calculer(@PathVariable Long idReservation,
                                 @RequestParam(required = false) String codePromo) {
        Reservation reservation = reservationService.findById(idReservation);
        double montant = tarificationService.calculerMontant(reservation, codePromo);
        dernierPrixCalcule.setMontant(montant); // "dernier prix" propre a CETTE requete
        return new PrixResponse(idReservation, montant, dernierPrixCalcule.getMontant(),
                Thread.currentThread().getName());
    }
}
