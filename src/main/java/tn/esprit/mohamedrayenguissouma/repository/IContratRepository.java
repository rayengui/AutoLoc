package tn.esprit.mohamedrayenguissouma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedrayenguissouma.domain.Contrat;

public interface IContratRepository extends JpaRepository<Contrat, Long> {
}
