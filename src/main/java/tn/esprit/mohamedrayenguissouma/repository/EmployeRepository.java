package tn.esprit.mohamedrayenguissouma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedrayenguissouma.domain.Employe;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
}
