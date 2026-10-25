package tn.esprit.mohamedrayenguissouma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedrayenguissouma.domain.Maintenance;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {
}
