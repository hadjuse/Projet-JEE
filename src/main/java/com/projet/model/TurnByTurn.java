package com.projet.model;

import com.projet.persistence.JoueurDAO;
import java.util.List;

public class TurnByTurn {
    private Joueur currentPlayer;
    private List<Joueur> players;
    private int currentIndex; // Indice du joueur actuel dans la liste

    /**
     * Constructeur avec initialisation du premier joueur
     */
    public TurnByTurn(List<Joueur> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("La liste des joueurs ne peut pas être vide.");
        }
        this.players = players;
        this.currentIndex = 0; // Premier joueur
        this.currentPlayer = players.get(currentIndex);
        this.currentPlayer.setTurn(true); // Activer le tour pour le premier joueur
    }

    /**
     * Obtenir le joueur actuel
     */
    public Joueur getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Passer au joueur suivant
     */
    public void nextTurn() {
        if (players == null || players.isEmpty()) {
            throw new IllegalStateException("La liste des joueurs est vide.");
        }

        // Désactiver le tour pour le joueur actuel
        currentPlayer.setTurn(false);
        try (JoueurDAO joueurDAO = new JoueurDAO()) {
            joueurDAO.mettreAJourJoueur(currentPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Passer au joueur suivant (cyclique)
        currentIndex = (currentIndex + 1) % players.size();
        currentPlayer = players.get(currentIndex);

        // Activer le tour pour le nouveau joueur
        currentPlayer.setTurn(true);
        try (JoueurDAO joueurDAO = new JoueurDAO()) {
            joueurDAO.mettreAJourJoueur(currentPlayer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Tour passé au joueur suivant : " + currentPlayer.getNom());
    }

    /**
     * Obtenir la liste des joueurs
     */
    public List<Joueur> getPlayers() {
        return players;
    }

    /**
     * Définir une nouvelle liste de joueurs
     */
    public void setPlayers(List<Joueur> players) {
        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("La liste des joueurs ne peut pas être vide.");
        }
        this.players = players;
        this.currentIndex = 0;
        this.currentPlayer = players.get(currentIndex);
    }
}
