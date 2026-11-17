package tn.esprit.mohamedrayenguissouma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedrayenguissouma.domain.Reservation;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
}
