package tn.esprit.mohamedrayenguissouma.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Reservation;
import tn.esprit.mohamedrayenguissouma.service.IReservationService;

import java.util.List;

@Tag(name = "Reservation", description = "Gestion des réservations")
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor // injection de dépendances : reservationService est final
public class ReservationRestController {

    private final IReservationService reservationService;

    @Operation(summary = "findAll", description = "Récupérer la liste complète des réservations")
    @GetMapping("/retrieve-all-reservations")
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }

    @Operation(summary = "findById", description = "Récupérer un(e) reservation par son identifiant")
    @GetMapping("/retrieve-reservation/{idReservation}")
    public Reservation findById(@PathVariable Long idReservation) {
        return reservationService.findById(idReservation);
    }

    @Operation(summary = "create", description = "Ajouter un(e) reservation")
    @PostMapping("/add-reservation")
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @Operation(summary = "update", description = "Modifier un(e) reservation existant(e)")
    @PutMapping("/modify-reservation")
    public Reservation update(@RequestBody Reservation reservation) {
        return reservationService.update(reservation);
    }

    @Operation(summary = "deleteById", description = "Supprimer un(e) reservation par son identifiant")
    @DeleteMapping("/remove-reservation/{idReservation}")
    public void deleteById(@PathVariable Long idReservation) {
        reservationService.deleteById(idReservation);
    }
}
