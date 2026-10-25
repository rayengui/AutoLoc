package tn.esprit.mohamedrayenguissouma.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import tn.esprit.mohamedrayenguissouma.domain.Agence;
import tn.esprit.mohamedrayenguissouma.domain.CategorieVehicule;
import tn.esprit.mohamedrayenguissouma.domain.StatutVehicule;
import tn.esprit.mohamedrayenguissouma.domain.Vehicule;
import tn.esprit.mohamedrayenguissouma.repository.AgenceRepository;
import tn.esprit.mohamedrayenguissouma.repository.VehiculeRepository;

import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AgenceRepository agenceRepository;
    private final VehiculeRepository vehiculeRepository;

    @Override
    public void run(String... args) {
        if (vehiculeRepository.count() > 0) {
            return;
        }
        Agence agence = new Agence();
        agence.setNom("AutoLoc Tunis Centre");
        agence.setVille("Tunis");
        agence.setAdresse("Avenue Habib Bourguiba");
        agence.setTelephone("71 000 000");
        agence = agenceRepository.save(agence);

        vehiculeRepository.saveAll(List.of(
                vehicule(agence, "123 TU 4567", "Renault", "Clio", CategorieVehicule.CITADINE,
                        "80.00", StatutVehicule.DISPONIBLE),
                vehicule(agence, "234 TU 5678", "Peugeot", "508", CategorieVehicule.BERLINE,
                        "150.00", StatutVehicule.DISPONIBLE),
                vehicule(agence, "345 TU 6789", "Kia", "Sportage", CategorieVehicule.SUV,
                        "200.00", StatutVehicule.MAINTENANCE)
        ));
        log.info("Véhicules de démonstration insérés : {}", vehiculeRepository.count());
    }

    private static Vehicule vehicule(Agence agence, String immatriculation, String marque, String modele,
                                     CategorieVehicule categorie, String tarif, StatutVehicule statut) {
        Vehicule v = new Vehicule();
        v.setAgence(agence);
        v.setImmatriculation(immatriculation);
        v.setMarque(marque);
        v.setModele(modele);
        v.setCategorie(categorie);
        v.setTarifJournalier(new BigDecimal(tarif));
        v.setStatut(statut);
        return v;
    }
}
