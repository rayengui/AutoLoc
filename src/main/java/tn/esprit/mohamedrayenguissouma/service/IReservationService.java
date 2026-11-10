package tn.esprit.mohamedrayenguissouma.service;

import tn.esprit.mohamedrayenguissouma.domain.Reservation;

import java.util.List;

public interface IReservationService {

    List<Reservation> findAll();

    Reservation findById(Long idReservation);

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    void deleteById(Long idReservation);
}
