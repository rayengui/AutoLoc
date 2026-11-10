package tn.esprit.mohamedrayenguissouma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedrayenguissouma.domain.Equipement;
import tn.esprit.mohamedrayenguissouma.repository.EquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // injection par constructeur de l'attribut final equipementRepository
public class EquipementServiceImpl implements IEquipementService {

    private final EquipementRepository equipementRepository;

    @Override
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement findById(Long idEquipement) {
        return equipementRepository.findById(idEquipement).orElse(null);
    }

    @Override
    public Equipement create(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement update(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public void deleteById(Long idEquipement) {
        equipementRepository.deleteById(idEquipement);
    }
}
