package com.projet.utils.button.actionMove;

import com.projet.model.Grille;
import com.projet.model.Soldat;
import com.projet.model.Tuile;
import com.projet.model.TypeTuile;
import com.projet.persistence.GrilleDAO;
import jakarta.persistence.EntityManager;

public class MoveMethod {

    static void MoveMethod(Grille grille, int xSource, int ySource, int xDest, int yDest, GrilleDAO grilleDAO) {
        // Validation des limites de la grille
        if (!isValidCoordinate(grille, xSource, ySource) || !isValidCoordinate(grille, xDest, yDest)) {
            System.out.println("Déplacement impossible : coordonnées hors limites.");
            return;
        }

        try (EntityManager em = grilleDAO.getEmf().createEntityManager()) {
            em.getTransaction().begin();

            Tuile source = grille.getTuile(xSource, ySource);
            Tuile destination = grille.getTuile(xDest, yDest);

            // Vérifier que les entités sont attachées
            if (!em.contains(source)) {
                source = em.merge(source);
            }
            if (!em.contains(destination)) {
                destination = em.merge(destination);
            }

            // Vérifier la présence d'un soldat sur la tuile source
            Soldat soldat = source.getSoldat();
            if (soldat == null) {
                System.out.println("Aucun soldat sur la tuile source.");
                em.getTransaction().rollback();
                return;
            }

            // Mise à jour des relations
            source.setSoldat(null);
            source.setType(TypeTuile.VIDE);

            destination.setSoldat(soldat);
            destination.setType(TypeTuile.SOLDATOCCUPE);

            // Mise à jour des coordonnées du soldat
            soldat.setX(xDest);
            soldat.setY(yDest);

            // Persiste les modifications
            grilleDAO.enregistrerGrille(grille);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode utilitaire pour vérifier les limites de la grille
    private static boolean isValidCoordinate(Grille grille, int x, int y) {
        return x >= 0 && x < grille.getLignes() && y >= 0 && y < grille.getColonnes();
    }

}