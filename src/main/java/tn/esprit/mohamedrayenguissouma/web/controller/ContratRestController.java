package tn.esprit.mohamedrayenguissouma.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Contrat;
import tn.esprit.mohamedrayenguissouma.service.IContratService;

import java.util.List;

@Tag(name = "Contrat", description = "Gestion des contrats de location")
@RestController
@RequestMapping("/contrat")
@RequiredArgsConstructor // injection de dépendances : contratService est final
public class ContratRestController {

    private final IContratService contratService;

    @Operation(summary = "findAll", description = "Récupérer la liste complète des contrats de location")
    @GetMapping("/retrieve-all-contrats")
    public List<Contrat> findAll() {
        return contratService.findAll();
    }

    @Operation(summary = "findById", description = "Récupérer un(e) contrat par son identifiant")
    @GetMapping("/retrieve-contrat/{idContrat}")
    public Contrat findById(@PathVariable Long idContrat) {
        return contratService.findById(idContrat);
    }

    @Operation(summary = "create", description = "Ajouter un(e) contrat")
    @PostMapping("/add-contrat")
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }

    @Operation(summary = "update", description = "Modifier un(e) contrat existant(e)")
    @PutMapping("/modify-contrat")
    public Contrat update(@RequestBody Contrat contrat) {
        return contratService.update(contrat);
    }

    @Operation(summary = "deleteById", description = "Supprimer un(e) contrat par son identifiant")
    @DeleteMapping("/remove-contrat/{idContrat}")
    public void deleteById(@PathVariable Long idContrat) {
        contratService.deleteById(idContrat);
    }
}
