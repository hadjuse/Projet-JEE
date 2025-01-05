package com.projet.model;

import java.util.List;

public class TurnByTurn {
    private List<Joueur> joueurs;
    private int currentPlayerIndex;

    public TurnByTurn(List<Joueur> joueurs){
        this.joueurs = joueurs;
        this.currentPlayerIndex = 0;
    }
    public void passTurn(){
        currentPlayerIndex = (currentPlayerIndex + 1) % joueurs.size();
    }

    public Joueur getCurentPlayer(){
        return joueurs.get(currentPlayerIndex);
    }
}
