package com.projet.model;
import jakarta.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Joueur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int nbSoldats;

    @Column(nullable = false)
    private int nbVilles;

    @Column(nullable = false)
    private int nbForets;

    @Column(nullable = false)
    private int nbTuiles;

    @Column(nullable = false)
    private int pointProduction;

    @Column(nullable = false)
    private String password;

    @Transient
    private int x;

    @Transient
    private int y;
    @Column(name = "turn")
    private boolean turn;
    @ManyToOne
    private Grille grille;
    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Transient
    private List<Soldat> soldats=new ArrayList<>();
    @Transient
    private List<Ville> villes=new ArrayList<>();
    // autres champs et méthodes

    public List<Soldat> getSoldats() {
        return soldats;
    }
    public int getPointsProduction() {
        return pointProduction;
    }

    public void setPointsProduction(int pointProduction) {
        this.pointProduction = pointProduction;
    }

    public Integer getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNom() {
        return this.nom;
    }

    public int getScore() {
        return this.score;
    }

    public int getNbSoldats() {
        return this.nbSoldats;
    }

    public int getNbVilles() {
        return this.nbVilles;
    }

    public int getNbForets() {
        return this.nbForets;
    }

    public int getNbTuiles() {
        return this.nbTuiles;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNbSoldats(int nbSoldats) {
        this.nbSoldats = nbSoldats;
    }

    public void setNbVilles(int nbVilles) {
        this.nbVilles = nbVilles;
    }

    public void setNbForets(int nbForets) {
        this.nbForets = nbForets;
    }

    public void setNbTuiles(int nbTuiles) {
        this.nbTuiles = nbTuiles;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void addNbSoldats(int nbSoldats) {
        this.nbSoldats += nbSoldats;
    }

    public void addNbVilles(int nbVilles) {
        this.nbVilles += nbVilles;
    }

    public void addNbForets(int nbForets) {
        this.nbForets += nbForets;
    }

    public void addNbTuiles(int nbTuiles) {
        this.nbTuiles += nbTuiles;
    }

    public void removeScore(int score) {
        this.score -= score;
    }

    public void removeNbSoldats(int nbSoldats) {
        this.nbSoldats -= nbSoldats;
    }

    public void removeNbVilles(int nb) {
        this.nbVilles -= nb;
    }

    public String getPassword() {
        return password;
    }

    public void updateScore() {
        setScore(getNbVilles() * 10 + getNbSoldats() * 3);
        System.out.println("Nombre de soldats: " + getNbSoldats());
        System.out.println("Nombre de villes: " + getNbVilles());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        // On compare par l'ID s'il n'est pas nul
        return Objects.equals(this.id, joueur.id);
    }

    @Override
    public int hashCode() {
        // On peut se baser sur l'id
        return (id == null) ? 0 : id.hashCode();
    }

    public void ajouterPointsProduction(int productionParTour) {
        pointProduction += productionParTour;
    }

    public void retirerPointsProduction(int productionParTour) {
        pointProduction -= productionParTour;
    }

    public void setSoldats(List<Soldat> soldats) {
        this.soldats = soldats;
    }

    public Grille getGrille() {
        return grille;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean isTurn() {
        return turn;
    }

    public List<Ville> getVilles() {
        return villes;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }
}
