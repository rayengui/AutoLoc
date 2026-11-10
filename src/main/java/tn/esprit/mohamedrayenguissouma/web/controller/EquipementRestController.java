package tn.esprit.mohamedrayenguissouma.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Equipement;
import tn.esprit.mohamedrayenguissouma.service.IEquipementService;

import java.util.List;

@Tag(name = "Equipement", description = "Gestion des équipements")
@RestController
@RequestMapping("/equipement")
@RequiredArgsConstructor // injection de dépendances : equipementService est final
public class EquipementRestController {

    private final IEquipementService equipementService;

    @Operation(summary = "findAll", description = "Récupérer la liste complète des équipements")
    @GetMapping("/retrieve-all-equipements")
    public List<Equipement> findAll() {
        return equipementService.findAll();
    }

    @Operation(summary = "findById", description = "Récupérer un(e) equipement par son identifiant")
    @GetMapping("/retrieve-equipement/{idEquipement}")
    public Equipement findById(@PathVariable Long idEquipement) {
        return equipementService.findById(idEquipement);
    }

    @Operation(summary = "create", description = "Ajouter un(e) equipement")
    @PostMapping("/add-equipement")
    public Equipement create(@RequestBody Equipement equipement) {
        return equipementService.create(equipement);
    }

    @Operation(summary = "update", description = "Modifier un(e) equipement existant(e)")
    @PutMapping("/modify-equipement")
    public Equipement update(@RequestBody Equipement equipement) {
        return equipementService.update(equipement);
    }

    @Operation(summary = "deleteById", description = "Supprimer un(e) equipement par son identifiant")
    @DeleteMapping("/remove-equipement/{idEquipement}")
    public void deleteById(@PathVariable Long idEquipement) {
        equipementService.deleteById(idEquipement);
    }
}
