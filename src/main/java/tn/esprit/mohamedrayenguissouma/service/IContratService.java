package tn.esprit.mohamedrayenguissouma.service;

import tn.esprit.mohamedrayenguissouma.domain.Contrat;

import java.util.List;

public interface IContratService {

    List<Contrat> findAll();

    Contrat findById(Long idContrat);

    Contrat create(Contrat contrat);

    Contrat update(Contrat contrat);

    void deleteById(Long idContrat);
}
