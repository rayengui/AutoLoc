package tn.esprit.mohamedrayenguissouma.service.tarification;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Atelier 7 - Mission 1 : correction n°2, l'etat "dernier prix calcule"
 * est sorti du singleton et place dans un bean de portee REQUETE.
 *
 * Spring cree une instance par requete HTTP. Ceux qui l'injectent (des
 * singletons) recoivent en realite un proxy, qui redirige chaque appel vers
 * l'instance de la requete en cours : deux clients simultanes ne partagent
 * donc jamais la meme valeur.
 */
@Component
@RequestScope
@Getter
@Setter
public class DernierPrixCalcule {

    private Double montant;
}
