package tn.esprit.mohamedrayenguissouma.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Maintenance;
import tn.esprit.mohamedrayenguissouma.service.IMaintenanceService;

import java.util.List;

@Tag(name = "Maintenance", description = "Gestion des maintenances")
@RestController
@RequestMapping("/maintenance")
@RequiredArgsConstructor // injection de dépendances : maintenanceService est final
public class MaintenanceRestController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "findAll", description = "Récupérer la liste complète des maintenances")
    @GetMapping("/retrieve-all-maintenances")
    public List<Maintenance> findAll() {
        return maintenanceService.findAll();
    }

    @Operation(summary = "findById", description = "Récupérer un(e) maintenance par son identifiant")
    @GetMapping("/retrieve-maintenance/{idMaintenance}")
    public Maintenance findById(@PathVariable Long idMaintenance) {
        return maintenanceService.findById(idMaintenance);
    }

    @Operation(summary = "create", description = "Ajouter un(e) maintenance")
    @PostMapping("/add-maintenance")
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return maintenanceService.create(maintenance);
    }

    @Operation(summary = "update", description = "Modifier un(e) maintenance existant(e)")
    @PutMapping("/modify-maintenance")
    public Maintenance update(@RequestBody Maintenance maintenance) {
        return maintenanceService.update(maintenance);
    }

    @Operation(summary = "deleteById", description = "Supprimer un(e) maintenance par son identifiant")
    @DeleteMapping("/remove-maintenance/{idMaintenance}")
    public void deleteById(@PathVariable Long idMaintenance) {
        maintenanceService.deleteById(idMaintenance);
    }
}
