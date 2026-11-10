package tn.esprit.mohamedrayenguissouma.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Paiement;
import tn.esprit.mohamedrayenguissouma.service.IPaiementService;

import java.util.List;

@Tag(name = "Paiement", description = "Gestion des paiements")
@RestController
@RequestMapping("/paiement")
@RequiredArgsConstructor // injection de dépendances : paiementService est final
public class PaiementRestController {

    private final IPaiementService paiementService;

    @Operation(summary = "findAll", description = "Récupérer la liste complète des paiements")
    @GetMapping("/retrieve-all-paiements")
    public List<Paiement> findAll() {
        return paiementService.findAll();
    }

    @Operation(summary = "findById", description = "Récupérer un(e) paiement par son identifiant")
    @GetMapping("/retrieve-paiement/{idPaiement}")
    public Paiement findById(@PathVariable Long idPaiement) {
        return paiementService.findById(idPaiement);
    }

    @Operation(summary = "create", description = "Ajouter un(e) paiement")
    @PostMapping("/add-paiement")
    public Paiement create(@RequestBody Paiement paiement) {
        return paiementService.create(paiement);
    }

    @Operation(summary = "update", description = "Modifier un(e) paiement existant(e)")
    @PutMapping("/modify-paiement")
    public Paiement update(@RequestBody Paiement paiement) {
        return paiementService.update(paiement);
    }

    @Operation(summary = "deleteById", description = "Supprimer un(e) paiement par son identifiant")
    @DeleteMapping("/remove-paiement/{idPaiement}")
    public void deleteById(@PathVariable Long idPaiement) {
        paiementService.deleteById(idPaiement);
    }
}
