package tn.esprit.mohamedrayenguissouma.service;

import tn.esprit.mohamedrayenguissouma.domain.Paiement;

import java.util.List;

/**
 * Paiement en lecture seule : un paiement n'existe que dans son contrat (composition).
 * Sa creation, sa modification et sa suppression passent par le Contrat.
 */
public interface IPaiementService {

    Paiement findById(Long id);

    List<Paiement> findAll();
}
