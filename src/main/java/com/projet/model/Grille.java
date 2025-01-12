package com.projet.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Grille {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int lignes;
    private int colonnes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grille", fetch = FetchType.EAGER)
    private List<Tuile> tuiles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grille", fetch = FetchType.EAGER)
    private List<Soldat> soldats;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grille", fetch = FetchType.EAGER)
    private List<Ville> villes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grille", fetch = FetchType.EAGER)
    private List<Joueur> joueurs = new ArrayList<>();
    public Grille(){
    }
    /**
     * Initialisation de la grille avec des types aléatoires
     */
    public Grille(int lignes, int colonnes){
        this.lignes=lignes;
        this.colonnes=colonnes;
        this.tuiles=new ArrayList<>();

        Random random = new Random();
/*
        for(int i=0; i<lignes; i++){
            for(int j=0; j<colonnes; j++){
                TypeTuile type = TypeTuile.values()[random.nextInt(TypeTuile.values().length)];
                Tuile tuile = new Tuile(i,j,type);
                tuile.setGrille(this);
                getTuiles().add(tuile);
            }
        }

 */
        for(int i=0; i<lignes; i++){
            for(int j=0; j<colonnes; j++){
                TypeTuile type = TypeTuile.VIDE;
                Tuile tuile = new Tuile(i,j,type);
                tuile.setGrille(this);
                getTuiles().add(tuile);
            }
        }
    }

    /**
     * Méthode pour récupérer une tuile par ses coordonnées.
     * Si les coordonnées sont hors intervalles, on retourne null
     * */
    public Tuile getTuile(int xLigne, int yColonne){
        return tuiles.stream()
                .filter(t->t.getX()==xLigne && t.getY()==yColonne)
                .findFirst()
                .orElse(null);
    }
    public void ajouterSoldat(int x, int y, Joueur joueur) {
        Tuile tuile = getTuile(x, y);
        if (tuile != null && tuile.getType() == TypeTuile.VIDE) {
            Soldat soldat = new Soldat(x, y, 10, joueur); // Exemple de coût de production
            tuile.setSoldat(soldat);
            tuile.setType(TypeTuile.SOLDATOCCUPE);
            soldat.setGrille(this);
            tuile.getSoldat().setBlesse(true);
            joueur.getSoldats().add(soldat);
        }
    }

    public void ajouterVille(int x, int y, Joueur joueur) {
        Tuile tuile = getTuile(x, y);
        if (tuile != null && tuile.getType() == TypeTuile.VIDE) {
            Ville ville = new Ville(x, y, 3, joueur);
            tuile.setVille(ville);
            tuile.setType(TypeTuile.VILLE);
            ville.setGrille(this);
            joueur.getVilles().add(ville);
        }
    }

    public void ajouterForet(int x, int y, int ressourcesProduction) {
        Tuile tuile = getTuile(x, y);
        if (tuile != null && tuile.getType() == TypeTuile.VIDE) {
            Foret foret = new Foret(x, y, ressourcesProduction);
            tuile.setForet(foret);
            tuile.setType(TypeTuile.FORET);
        }
    }
    public boolean isAdjacentToType(int x, int y, String type) {
        // Vérifier les tuiles adjacentes (haut, bas, gauche, droite)
        if (x > 0 && getTuile(x - 1, y).getType().name().equals(type)) {
            return true;
        }
        if (x < lignes - 1 && getTuile(x + 1, y).getType().name().equals(type)) {
            return true;
        }
        if (y > 0 && getTuile(x, y - 1).getType().name().equals(type)) {
            return true;
        }
        if (y < colonnes - 1 && getTuile(x, y + 1).getType().name().equals(type)) {
            return true;
        }
        return false;
    }

    public int getLignes(){
        return lignes;
    }

    public int getColonnes(){
        return colonnes;
    }

    public List<Tuile> getTuiles(){
        return tuiles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setColonnes(int colonnes) {
        this.colonnes = colonnes;
    }

    public void setLignes(int lignes) {
        this.lignes = lignes;
    }

    public void setTuiles(List<Tuile> tuiles) {
        this.tuiles = tuiles;
    }

    public List<Soldat> getSoldats() {
        return soldats;
    }

    public void setSoldats(List<Soldat> soldats) {
        this.soldats = soldats;
    }

    public List<Ville> getVilles() {
        return villes;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Joueur> joueurs) {
        this.joueurs = joueurs;
    }
    public void addJoueur(Joueur joueur) {
        joueurs.add(joueur);
        joueur.setGrille(this);
    }

    public void removeJoueur(Joueur joueur) {
        joueurs.remove(joueur);
        joueur.setGrille(null);
    }

}
