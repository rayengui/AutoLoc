package tn.esprit.mohamedrayenguissouma.service.tarification;

import tn.esprit.mohamedrayenguissouma.domain.Reservation;

public interface ITarificationService {

    double calculerMontant(Reservation reservation, String codePromo);
}
