package tn.esprit.mohamedrayenguissouma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.mohamedrayenguissouma.domain.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
