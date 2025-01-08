package com.projet.utils.button.actionMove;

import com.projet.model.Grille;
import com.projet.model.Soldat;
import com.projet.model.Tuile;
import com.projet.model.TypeTuile;
import com.projet.persistence.DAOFactory;
import com.projet.persistence.GrilleDAO;
import com.projet.persistence.TuileDAO;
import jakarta.persistence.EntityManager;

public class MoveMethod {

    public static void move(Grille grille, int xSource, int ySource, int xDest, int yDest, GrilleDAO grilleDAO) {
        if (grille == null) {
            System.out.println("Déplacement impossible : la grille est nulle.");
            return;
        }

        // Validation des limites de la grille
        if (!isValidCoordinate(grille, xSource, ySource) || !isValidCoordinate(grille, xDest, yDest)) {
            System.out.println("Déplacement impossible : coordonnées hors limites.");
            return;
        }

        TuileDAO tuileDAO = DAOFactory.getTuileDAO();

        try (EntityManager em = grilleDAO.getEmf().createEntityManager()) {
            em.getTransaction().begin();

            Tuile source = tuileDAO.trouverTuileParId(grille.getTuile(xSource, ySource).getId());
            Soldat soldat = source.getSoldat();
            if (soldat == null) {
                System.out.println("Aucun soldat sur la tuile source.");
                em.getTransaction().rollback();
                return;
            }
            source.getGrille().getTuile(xSource, ySource).setType(TypeTuile.VIDE);
            soldat = em.merge(soldat);
            // Utiliser la méthode de déplacement du soldat
            if (!soldat.deplacer(grille, xDest, yDest)) {
                em.getTransaction().rollback();
                return;
            }

            Tuile destination = grille.getTuile(xDest, yDest);
            destination.setSoldat(soldat);
            source.setSoldat(null);

            tuileDAO.mettreAJourTuile(source);
            tuileDAO.mettreAJourTuile(destination);
            grilleDAO.enregistrerGrille(grille);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode utilitaire pour vérifier les limites de la grille
    private static boolean isValidCoordinate(Grille grille, int x, int y) {
        return (x >= 0 && x < grille.getLignes() && y >= 0 && y < grille.getColonnes());
    }
}