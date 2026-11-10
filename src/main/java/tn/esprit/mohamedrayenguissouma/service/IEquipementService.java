package tn.esprit.mohamedrayenguissouma.service;

import tn.esprit.mohamedrayenguissouma.domain.Equipement;

import java.util.List;

public interface IEquipementService {

    List<Equipement> findAll();

    Equipement findById(Long idEquipement);

    Equipement create(Equipement equipement);

    Equipement update(Equipement equipement);

    void deleteById(Long idEquipement);
}
