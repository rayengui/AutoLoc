package tn.esprit.mohamedrayenguissouma.service;

import tn.esprit.mohamedrayenguissouma.domain.Client;

import java.util.List;

public interface IClientService {

    List<Client> findAll();

    Client findById(Long idClient);

    Client create(Client client);

    Client update(Client client);

    void deleteById(Long idClient);
}
