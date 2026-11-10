package tn.esprit.mohamedrayenguissouma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedrayenguissouma.domain.Maintenance;
import tn.esprit.mohamedrayenguissouma.repository.MaintenanceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // injection par constructeur de l'attribut final maintenanceRepository
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance findById(Long idMaintenance) {
        return maintenanceRepository.findById(idMaintenance).orElse(null);
    }

    @Override
    public Maintenance create(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance update(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public void deleteById(Long idMaintenance) {
        maintenanceRepository.deleteById(idMaintenance);
    }
}
