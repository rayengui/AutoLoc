package tn.esprit.mohamedrayenguissouma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedrayenguissouma.domain.Paiement;
import tn.esprit.mohamedrayenguissouma.repository.PaiementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // injection par constructeur de l'attribut final paiementRepository
public class PaiementServiceImpl implements IPaiementService {

    private final PaiementRepository paiementRepository;

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement findById(Long idPaiement) {
        return paiementRepository.findById(idPaiement).orElse(null);
    }

    @Override
    public Paiement create(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement update(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public void deleteById(Long idPaiement) {
        paiementRepository.deleteById(idPaiement);
    }
}
