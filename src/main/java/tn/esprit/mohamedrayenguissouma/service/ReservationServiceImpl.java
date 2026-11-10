package tn.esprit.mohamedrayenguissouma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.mohamedrayenguissouma.domain.Reservation;
import tn.esprit.mohamedrayenguissouma.repository.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor // injection par constructeur de l'attribut final reservationRepository
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation findById(Long idReservation) {
        return reservationRepository.findById(idReservation).orElse(null);
    }

    @Override
    public Reservation create(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation update(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteById(Long idReservation) {
        reservationRepository.deleteById(idReservation);
    }
}
