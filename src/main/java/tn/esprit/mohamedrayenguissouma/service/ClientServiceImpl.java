package tn.esprit.mohamedrayenguissouma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedrayenguissouma.domain.Client;
import tn.esprit.mohamedrayenguissouma.repository.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // injection par constructeur de l'attribut final clientRepository
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long idClient) {
        return clientRepository.findById(idClient).orElse(null);
    }

    @Override
    public Client create(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(Long idClient) {
        clientRepository.deleteById(idClient);
    }
}
