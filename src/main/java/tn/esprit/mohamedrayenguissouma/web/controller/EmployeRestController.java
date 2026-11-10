package tn.esprit.mohamedrayenguissouma.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Employe;
import tn.esprit.mohamedrayenguissouma.service.IEmployeService;

import java.util.List;

@Tag(name = "Employe", description = "Gestion des employés")
@RestController
@RequestMapping("/employe")
@RequiredArgsConstructor // injection de dépendances : employeService est final
public class EmployeRestController {

    private final IEmployeService employeService;

    @Operation(summary = "findAll", description = "Récupérer la liste complète des employés")
    @GetMapping("/retrieve-all-employes")
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    @Operation(summary = "findById", description = "Récupérer un(e) employe par son identifiant")
    @GetMapping("/retrieve-employe/{idEmploye}")
    public Employe findById(@PathVariable Long idEmploye) {
        return employeService.findById(idEmploye);
    }

    @Operation(summary = "create", description = "Ajouter un(e) employe")
    @PostMapping("/add-employe")
    public Employe create(@RequestBody Employe employe) {
        return employeService.create(employe);
    }

    @Operation(summary = "update", description = "Modifier un(e) employe existant(e)")
    @PutMapping("/modify-employe")
    public Employe update(@RequestBody Employe employe) {
        return employeService.update(employe);
    }

    @Operation(summary = "deleteById", description = "Supprimer un(e) employe par son identifiant")
    @DeleteMapping("/remove-employe/{idEmploye}")
    public void deleteById(@PathVariable Long idEmploye) {
        employeService.deleteById(idEmploye);
    }
}
