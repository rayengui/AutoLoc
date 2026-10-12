package tn.esprit.mohamedrayenguissouma.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tn.esprit.mohamedrayenguissouma.domain.CategorieVehicule;
import tn.esprit.mohamedrayenguissouma.domain.StatutVehicule;
import tn.esprit.mohamedrayenguissouma.domain.Vehicule;
import tn.esprit.mohamedrayenguissouma.repository.VehiculeRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final VehiculeRepository vehiculeRepository;

    @Override
    public void run(String... args) {
        if (vehiculeRepository.count() > 0) {
            return;
        }
        vehiculeRepository.saveAll(List.of(
                new Vehicule(null, "123 TU 4567", "Renault", "Clio", CategorieVehicule.CITADINE,
                        new BigDecimal("80.00"), StatutVehicule.DISPONIBLE),
                new Vehicule(null, "234 TU 5678", "Peugeot", "508", CategorieVehicule.BERLINE,
                        new BigDecimal("150.00"), StatutVehicule.DISPONIBLE),
                new Vehicule(null, "345 TU 6789", "Kia", "Sportage", CategorieVehicule.SUV,
                        new BigDecimal("200.00"), StatutVehicule.MAINTENANCE)
        ));
        log.info("Véhicules de démonstration insérés : {}", vehiculeRepository.count());
    }
}
