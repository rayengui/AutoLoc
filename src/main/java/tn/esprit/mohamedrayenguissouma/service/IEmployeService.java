package tn.esprit.mohamedrayenguissouma.service;

import tn.esprit.mohamedrayenguissouma.domain.Employe;

import java.util.List;

public interface IEmployeService {

    List<Employe> findAll();

    Employe findById(Long idEmploye);

    Employe create(Employe employe);

    Employe update(Employe employe);

    void deleteById(Long idEmploye);
}
