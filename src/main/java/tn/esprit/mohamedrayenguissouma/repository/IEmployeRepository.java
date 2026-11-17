package tn.esprit.mohamedrayenguissouma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedrayenguissouma.domain.Employe;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}
