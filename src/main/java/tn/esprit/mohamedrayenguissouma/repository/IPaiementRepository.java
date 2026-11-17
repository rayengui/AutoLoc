package tn.esprit.mohamedrayenguissouma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedrayenguissouma.domain.Paiement;

public interface IPaiementRepository extends JpaRepository<Paiement, Long> {
}
