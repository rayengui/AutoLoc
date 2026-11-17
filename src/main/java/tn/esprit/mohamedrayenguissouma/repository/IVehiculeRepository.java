package tn.esprit.mohamedrayenguissouma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedrayenguissouma.domain.Vehicule;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {
}
