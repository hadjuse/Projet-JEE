package com.projet.utils.button;

import com.projet.model.Joueur;
import com.projet.persistence.JoueurDAO;
import com.projet.utils.button.actionMove.MoveDown;

public class Main {
    public static void main(String[] args) {
        //ButtonMove buttonMove = new ButtonMove(new MoveDown(), "Move down");
        //buttonMove.getButtonActionStrategy().action(, , );
        //buttonMove.setButtonStrategy(new MoveLeft());
        //buttonMove.getButtonActionStrategy().action(, , );
        JoueurDAO dao = new JoueurDAO(); // Assure-toi que ça charge la même config
        Joueur j = dao.trouverJoueurParId(1); // un ID qui existe vraiment en base
        j.setPointProduction(3);
        /*
        System.out.println("Avant : turn=" + j.isTurn());
        j.setTurn(!j.isTurn()); // On inverse la valeur pour tester
         */
        dao.mettreAJourJoueur(j);



        Joueur reloaded = dao.trouverJoueurParId(1);
        System.out.println("Après : pointProduction=" + reloaded.getPointProduction());
        System.out.println("Après : turn=" + reloaded.isTurn());
    }
}
