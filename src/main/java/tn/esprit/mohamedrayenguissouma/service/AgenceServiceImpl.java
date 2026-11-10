package tn.esprit.mohamedrayenguissouma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedrayenguissouma.domain.Agence;
import tn.esprit.mohamedrayenguissouma.repository.AgenceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // injection par constructeur de l'attribut final agenceRepository
public class AgenceServiceImpl implements IAgenceService {

    private final AgenceRepository agenceRepository;

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence findById(Long idAgence) {
        return agenceRepository.findById(idAgence).orElse(null);
    }

    @Override
    public Agence create(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public Agence update(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public void deleteById(Long idAgence) {
        agenceRepository.deleteById(idAgence);
    }
}
