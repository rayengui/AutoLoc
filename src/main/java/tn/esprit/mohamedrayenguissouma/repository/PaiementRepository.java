package tn.esprit.mohamedrayenguissouma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedrayenguissouma.domain.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
