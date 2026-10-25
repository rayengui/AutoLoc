package tn.esprit.mohamedrayenguissouma.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Vehicule;
import tn.esprit.mohamedrayenguissouma.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/vehicule")
@RequiredArgsConstructor // injection de dépendances : vehiculeService est final
public class VehiculeRestController {

    private final IVehiculeService vehiculeService;

    @PostMapping("/add-vehicule")
    public Vehicule addVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.addVehicule(vehicule);
    }

    @GetMapping("/retrieve-all-vehicules")
    public List<Vehicule> retrieveAllVehicules() {
        return vehiculeService.retrieveAllVehicules();
    }

    @GetMapping("/retrieve-vehicule/{idVehicule}")
    public Vehicule retrieveVehicule(@PathVariable Long idVehicule) {
        return vehiculeService.retrieveVehicule(idVehicule);
    }

    @PutMapping("/modify-vehicule")
    public Vehicule modifyVehicule(@RequestBody Vehicule vehicule) {
        return vehiculeService.updateVehicule(vehicule);
    }

    @DeleteMapping("/remove-vehicule/{idVehicule}")
    public void removeVehicule(@PathVariable Long idVehicule) {
        vehiculeService.deleteVehicule(idVehicule);
    }
}
