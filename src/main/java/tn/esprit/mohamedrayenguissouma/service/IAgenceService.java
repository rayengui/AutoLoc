package tn.esprit.mohamedrayenguissouma.service;

import tn.esprit.mohamedrayenguissouma.domain.Agence;

import java.util.List;

public interface IAgenceService {

    List<Agence> findAll();

    Agence findById(Long idAgence);

    Agence create(Agence agence);

    Agence update(Agence agence);

    void deleteById(Long idAgence);
}
