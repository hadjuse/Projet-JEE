package com.projet.model;

import com.projet.persistence.SoldatDAO;
import jakarta.persistence.*;

// TODO instancier soldat quand on creer la map et qu'il soit accessible tout le temps
@Entity
public class Soldat{
    private int x;
    private int y;
    private int pointsDefense;
    private boolean blesse;
    private int coutProduction;
    private boolean aJouer;
    @ManyToOne
    private Joueur proprietaire;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Grille grille;

    // Constructeurs
    public Soldat(int x, int y, int coutProduction, Joueur proprietaire) {
        this.x = x;
        this.y = y;
        this.pointsDefense = 6;
        this.blesse = false;
        this.coutProduction = coutProduction;
        this.aJouer = false;
        this.proprietaire = proprietaire;
        this.proprietaire.addNbSoldats(1);
    }

    public Soldat(int x, int y, Joueur proprietaire) {
        this.x = x;
        this.y = y;
        this.pointsDefense = 6;
        this.blesse = false;
        this.coutProduction = 5;
        this.aJouer = false;
        this.proprietaire = proprietaire;
        this.proprietaire.addNbSoldats(1);
    }

    public Soldat() {

    }

    // Getters et Setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPointsDefense() {
        return pointsDefense;
    }

    public void setPointsDefense(int pointsDefense) {
        this.pointsDefense = pointsDefense;
    }

    public boolean isBlesse() {
        return blesse;
    }

    public void setBlesse(boolean blesse) {
        this.blesse = blesse;
    }

    public int getCoutProduction() {
        return coutProduction;
    }

    public void setCoutProduction(int coutProduction) {
        this.coutProduction = coutProduction;
    }

    public boolean isAJouer() { return aJouer; }

    public void setAJouer(boolean aJouer) {this.aJouer = aJouer; }

    public Joueur getProprietaire() { return proprietaire; }

    public void setProprietaire(Joueur proprietaire) {
        this.proprietaire = proprietaire;
        proprietaire.addNbSoldats(1);
    }

    // Méthodes
    public void soigner() {
        if (blesse) {
            this.blesse = false;
            this.pointsDefense += 4; // Règle : augmenter la défense lors de la guérison
        }
    }

    public int attaquer(){
        return (int) (Math.random() * 6) + 1; // Math.random() donne un nombre entre 1 et 6
    }

    public boolean subirAttaque(int pointsAttaque) {
        this.pointsDefense -= pointsAttaque;
        if (this.pointsDefense <= 0) {
            this.pointsDefense = 0;
            return true; // Le soldat est tué
        }
        return false; // Le soldat résiste encore
    }

    public void actionStatique(Grille grille, int x, int y, String action) {
        Tuile tuile = grille.getTuile(x, y);
        switch (action) {
            case "GUERISON":
                this.soigner();
                this.aJouer = true;
                break;

            case "FORET":
                if (tuile.getType().equals(TypeTuile.FORET)) {
                    int ptGagner = tuile.getForet().fourrager();
                    this.aJouer = true;
                    this.getProprietaire().ajouterPointsProduction(ptGagner);
                    System.out.println("Action : Fourrager et gagner " + ptGagner + " points de production.");
                    break;
                }

            case "PASSAGE":
                // Logique tour passe
                break;

            default:
                // null
                break;
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean deplacer(Grille grille, int xDest, int yDest) {
        Tuile source = grille.getTuile(this.x, this.y);
        Tuile destination = grille.getTuile(xDest, yDest);

        try (SoldatDAO soldatDAO = new SoldatDAO()) {
            switch (destination.getType()) {
                case VILLE:
                    return gererDeplacementVille(source, destination, soldatDAO);
                case VIDE:
                    return gererDeplacementVide(source, destination, xDest, yDest, soldatDAO);
                case FORET:
                case MONTAGNE:
                    return gererCollision(destination.getType());
                case SOLDATOCCUPE:
                    return gererDeplacementSoldatOccupe(source, destination, xDest, yDest, soldatDAO);
                default:
                    return gererCollision();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean gererDeplacementVille(Tuile source, Tuile destination, SoldatDAO soldatDAO) throws Exception {
        /*
        if (destination.getVille().subirAttaque(attaquer())) {
            mettreAJourRelations(source, destination);
            mettreAJourCoordonnees(destination.getX(), destination.getY());
            mettreAJourSoldat(soldatDAO);
            return true;
        }*/
        mettreAJourSoldat(soldatDAO);
        return false;
    }

    private boolean gererDeplacementVide(Tuile source, Tuile destination, int xDest, int yDest, SoldatDAO soldatDAO) throws Exception {
        mettreAJourRelations(source, destination);
        mettreAJourCoordonnees(xDest, yDest);
        mettreAJourSoldat(soldatDAO);
        return true;
    }

    private boolean gererCollision(TypeTuile type) {
        System.out.println("Collision détectée avec " + type);
        return false;
    }

    private boolean gererDeplacementSoldatOccupe(Tuile source, Tuile destination, int xDest, int yDest, SoldatDAO soldatDAO) throws Exception {
        if (destination.getSoldat().subirAttaque(2)) {
            mettreAJourRelations(source, destination);
            mettreAJourCoordonnees(xDest, yDest);
            mettreAJourSoldat(soldatDAO);
            mettreAJourSoldat(soldatDAO, destination.getSoldat());
            return true;
        }
        mettreAJourSoldat(soldatDAO);
        mettreAJourSoldat(soldatDAO, destination.getSoldat());
        return false;
    }

    private boolean gererCollision() {
        System.out.println("Collision détectée");
        return false;
    }

    private void mettreAJourRelations(Tuile source, Tuile destination) {
        source.setSoldat(null);
        source.setType(TypeTuile.VIDE);
        destination.setSoldat(this);
        destination.setType(TypeTuile.SOLDATOCCUPE);
    }

    private void mettreAJourCoordonnees(int xDest, int yDest) {
        this.x = xDest;
        this.y = yDest;
    }

    private void mettreAJourSoldat(SoldatDAO soldatDAO) throws Exception {
        this.aJouer = true;
        soldatDAO.mettreAJourSoldat(this);
    }

    private void mettreAJourSoldat(SoldatDAO soldatDAO, Soldat soldat) throws Exception {
        soldatDAO.mettreAJourSoldat(soldat);
    }

    public Grille getGrille() {
        return grille;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }
}
