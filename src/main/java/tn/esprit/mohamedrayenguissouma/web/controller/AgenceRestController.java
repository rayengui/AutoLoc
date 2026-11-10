package tn.esprit.mohamedrayenguissouma.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Agence;
import tn.esprit.mohamedrayenguissouma.service.IAgenceService;

import java.util.List;

@Tag(name = "Agence", description = "Gestion des agences")
@RestController
@RequestMapping("/agence")
@RequiredArgsConstructor // injection de dépendances : agenceService est final
public class AgenceRestController {

    private final IAgenceService agenceService;

    @Operation(summary = "findAll", description = "Récupérer la liste complète des agences")
    @GetMapping("/retrieve-all-agences")
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    @Operation(summary = "findById", description = "Récupérer un(e) agence par son identifiant")
    @GetMapping("/retrieve-agence/{idAgence}")
    public Agence findById(@PathVariable Long idAgence) {
        return agenceService.findById(idAgence);
    }

    @Operation(summary = "create", description = "Ajouter un(e) agence")
    @PostMapping("/add-agence")
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @Operation(summary = "update", description = "Modifier un(e) agence existant(e)")
    @PutMapping("/modify-agence")
    public Agence update(@RequestBody Agence agence) {
        return agenceService.update(agence);
    }

    @Operation(summary = "deleteById", description = "Supprimer un(e) agence par son identifiant")
    @DeleteMapping("/remove-agence/{idAgence}")
    public void deleteById(@PathVariable Long idAgence) {
        agenceService.deleteById(idAgence);
    }
}
