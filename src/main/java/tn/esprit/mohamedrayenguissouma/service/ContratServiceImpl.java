package tn.esprit.mohamedrayenguissouma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedrayenguissouma.domain.Contrat;
import tn.esprit.mohamedrayenguissouma.repository.ContratRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // injection par constructeur de l'attribut final contratRepository
public class ContratServiceImpl implements IContratService {

    private final ContratRepository contratRepository;

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat findById(Long idContrat) {
        return contratRepository.findById(idContrat).orElse(null);
    }

    @Override
    public Contrat create(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat update(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public void deleteById(Long idContrat) {
        contratRepository.deleteById(idContrat);
    }
}
