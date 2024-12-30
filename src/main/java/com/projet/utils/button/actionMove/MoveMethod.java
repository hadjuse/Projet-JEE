package com.projet.utils.button.actionMove;

import com.projet.model.Grille;
import com.projet.model.Tuile;
import com.projet.model.TypeTuile;
import com.projet.persistence.GrilleDAO;

public class MoveMethod {
    static void MoveMethod(Grille grille, int xSource, int ySource, int xDest, int yDest, GrilleDAO grilleDAO) {
        Tuile source = grille.getTuile(xSource, ySource);
        Tuile destination = grille.getTuile(xDest, yDest);
        System.out.printf("Source: %s, Destination: %s\n", source.getType(), destination.getType());

        if (source.getType().equals(TypeTuile.SOLDATOCCUPE) && destination.getType().equals(TypeTuile.VIDE)) {
            System.out.printf("Source: %s, Destination: %s\n", source.getType(), destination.getType());
            source.setType(TypeTuile.VIDE);
            destination.setType(TypeTuile.SOLDATOCCUPE);
            grilleDAO.enregistrerGrille(grille);
        }
    }
}
