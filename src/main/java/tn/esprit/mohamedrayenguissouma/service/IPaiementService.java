package tn.esprit.mohamedrayenguissouma.service;

import tn.esprit.mohamedrayenguissouma.domain.Paiement;

import java.util.List;

public interface IPaiementService {

    List<Paiement> findAll();

    Paiement findById(Long idPaiement);

    Paiement create(Paiement paiement);

    Paiement update(Paiement paiement);

    void deleteById(Long idPaiement);
}
