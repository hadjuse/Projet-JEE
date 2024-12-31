package com.projet.model;

public class Soldat extends Tuile{
    private int pointsDefense;
    private boolean blesse;
    private int coutProduction;
    private boolean aJouer;
    private final Joueur proprietaire;

    public Soldat(int x, int y, Joueur proprietaire) {
        super(x,y,TypeTuile.SOLDATOCCUPE);
        this.pointsDefense = 6;
        this.blesse = false;
        this.coutProduction = 12;
        this.aJouer = false;
        this.proprietaire = proprietaire;
    }

    // Getters et Setters
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

    public void setProprietaire(Joueur proprietaire) {}

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
        pointsDefense -= pointsAttaque;
        if (pointsDefense <= 0) {
            pointsDefense = 0;
            return true; // La ville est capturée
        }
        return false; // La ville résiste encore
    }

    public void actionDeplacement(Grille grille, int x, int y) {
        Tuile tuile = grille.getTuile(x, y);
        switch (tuile.getType()) {
            case VILLE, VILLESOLDAT:
                if (((Ville) tuile).subirAttaque(attaquer())) {
                    this.setX(x);
                    this.setY(y);
                    tuile.setProprietaire(proprietaire);
                    tuile.setType(TypeTuile.VILLESOLDAT);
            };
                this.aJouer = true;
                break;

            case FORET, VIDE:
                this.setX(x);
                this.setY(y);
                this.setAJouer(true);
                break;

            case MONTAGNE:
                System.out.println("Déplacement impossible");
                break;

            case SOLDATOCCUPE, FORETSOLDAT:
                if ((((Soldat) tuile).subirAttaque(attaquer()))) {
                    this.setX(x);
                    this.setY(y);
                    tuile.setProprietaire(proprietaire);
                };
                this.aJouer = true;
                break;

            default:
                // null
                break;
        }
    }

    public void actionStatique(Grille grille, int x, int y, String action) {
        Tuile tuile = grille.getTuile(x, y);
        switch (action) {
            case "GUERISON":
                this.soigner();
                this.aJouer = true;
                break;

            case "FORET":
                if (tuile.getType().equals(TypeTuile.FORETSOLDAT)) {
                    int ptGagner = ((Foret) tuile).fourrager(); // Caster tuile en Foret pour appeler fourrager()
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
}
