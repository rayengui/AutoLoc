package tn.esprit.mohamedrayenguissouma.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.mohamedrayenguissouma.domain.Client;
import tn.esprit.mohamedrayenguissouma.service.IClientService;

import java.util.List;

@Tag(name = "Client", description = "Gestion des clients")
@RestController
@RequestMapping("/client")
@RequiredArgsConstructor // injection de dépendances : clientService est final
public class ClientRestController {

    private final IClientService clientService;

    @Operation(summary = "findAll", description = "Récupérer la liste complète des clients")
    @GetMapping("/retrieve-all-clients")
    public List<Client> findAll() {
        return clientService.findAll();
    }

    @Operation(summary = "findById", description = "Récupérer un(e) client par son identifiant")
    @GetMapping("/retrieve-client/{idClient}")
    public Client findById(@PathVariable Long idClient) {
        return clientService.findById(idClient);
    }

    @Operation(summary = "create", description = "Ajouter un(e) client")
    @PostMapping("/add-client")
    public Client create(@RequestBody Client client) {
        return clientService.create(client);
    }

    @Operation(summary = "update", description = "Modifier un(e) client existant(e)")
    @PutMapping("/modify-client")
    public Client update(@RequestBody Client client) {
        return clientService.update(client);
    }

    @Operation(summary = "deleteById", description = "Supprimer un(e) client par son identifiant")
    @DeleteMapping("/remove-client/{idClient}")
    public void deleteById(@PathVariable Long idClient) {
        clientService.deleteById(idClient);
    }
}
