package tn.esprit.mohamedrayenguissouma.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Vehicule;
import tn.esprit.mohamedrayenguissouma.service.IVehiculeService;

import java.util.List;

@Tag(name = "Vehicule", description = "Gestion des véhicules de la flotte")
@RestController
@RequestMapping("/vehicule")
@RequiredArgsConstructor // injection de dépendances : vehiculeService est final
public class VehiculeRestController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "create", description = "Ajouter un véhicule")
    @PostMapping("/add-vehicule")
    public Vehicule addVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.addVehicule(vehicule);
    }

    @Operation(summary = "findAll", description = "Récupérer la liste complète des véhicules")
    @GetMapping("/retrieve-all-vehicules")
    public List<Vehicule> retrieveAllVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    @Operation(summary = "findById", description = "Récupérer un véhicule par son identifiant")
    @GetMapping("/retrieve-vehicule/{idVehicule}")
    public Vehicule retrieveVehicule(@PathVariable Long idVehicule) {
        return vehiculeService.retrieveVehicule(idVehicule);
    }

    @Operation(summary = "update", description = "Modifier un véhicule existant")
    @PutMapping("/modify-vehicule")
    public Vehicule modifyVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.updateVehicule(vehicule);
    }

    @Operation(summary = "deleteById", description = "Supprimer un véhicule par son identifiant")
    @DeleteMapping("/remove-vehicule/{idVehicule}")
    public void removeVehicule(@PathVariable Long idVehicule) {
        vehiculeService.deleteVehicule(idVehicule);
    }
}
