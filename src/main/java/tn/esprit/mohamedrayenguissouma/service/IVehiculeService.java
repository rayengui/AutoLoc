package tn.esprit.mohamedrayenguissouma.service;

import tn.esprit.mohamedrayenguissouma.domain.Vehicule;

import java.util.List;

public interface IVehiculeService {

    Vehicule addVehicule(Vehicule vehicule);

    Vehicule updateVehicule(Vehicule vehicule);

    void deleteVehicule(Long idVehicule);

    Vehicule retrieveVehicule(Long idVehicule);

    List<Vehicule> retrieveAllVehicules();
}
