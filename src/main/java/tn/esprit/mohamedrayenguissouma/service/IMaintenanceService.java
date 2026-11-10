package tn.esprit.mohamedrayenguissouma.service;

import tn.esprit.mohamedrayenguissouma.domain.Maintenance;

import java.util.List;

public interface IMaintenanceService {

    List<Maintenance> findAll();

    Maintenance findById(Long idMaintenance);

    Maintenance create(Maintenance maintenance);

    Maintenance update(Maintenance maintenance);

    void deleteById(Long idMaintenance);
}
