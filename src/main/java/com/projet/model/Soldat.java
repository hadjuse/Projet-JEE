package com.projet.model;

public class Soldat {
    private int x;
    private int y;
    private int pointsDefense;
    private boolean blesse;
    private int coutProduction;
    private boolean aJouer;

    public Soldat(int x, int y, int pointsDefense, int coutProduction) {
        this.x = x;
        this.y = y;
        this.pointsDefense = pointsDefense;
        this.blesse = false;
        this.coutProduction = coutProduction;
        this.aJouer = false;
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

    // Méthodes
    public void soigner() {
        if (blesse) {
            this.blesse = false;
            this.pointsDefense += 10; // Règle : augmenter la défense lors de la guérison
        }
    }

    public void action(Grille grille, int x, int y) {
        Tuile tuile = grille.getTuile(x, y);
        switch (tuile.getType()) {
            case VILLE:
                // Logique pour capturer une ville
                break;

            case FORET:
                int ptGagner = ((Foret) tuile).fourrager(); // Caster tuile en Foret pour appeler fourrager()
                this.aJouer = true;
                System.out.println("Action : Fourrager et gagner " + ptGagner + " points de production.");
                break;

            case MONTAGNE:
                // Aucun déplacement ou autre action possible
                break;

            case SOLDATOCCUPE:
                // Logique pour attaquer un soldat
                break;

            default:
                // Tuile vide
                break;
        }
    }
}
