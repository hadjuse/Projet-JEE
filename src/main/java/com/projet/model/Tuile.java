package com.projet.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type_tuile", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Tuile")
public class Tuile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int x;
    private int y;
    private TypeTuile type; // "ville", "montagne", "foret", "vide"
    @Transient
    private Joueur proprietaire;

    @ManyToOne
    private Grille grille;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "soldat_id", unique = true)
    private Soldat soldat;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "foret_id", unique = true)
    private Foret foret;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "ville_id", unique = true)
    private Ville ville;

    public Tuile(){
    }
    public Tuile(int x, int y, TypeTuile type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.proprietaire = null;
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

    public TypeTuile getType() {
        return type;
    }

    public void setType(TypeTuile type) {
        this.type = type;
    }

    public Joueur getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Joueur proprietaire) {
        this.proprietaire = proprietaire;
    }

    @Override
    public String toString(){
        return "Tuile{"+"x="+ x+", y="+ y+", type="+ type+'\''+'}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grille getGrille() {
        return grille;
    }

    public void setGrille(Grille grille) {
        this.grille = grille;
    }

    public Soldat getSoldat() {
        return soldat;
    }

    public void setSoldat(Soldat soldat) {
        this.soldat = soldat;
    }

    public Ville getVille() {
        return ville;
    }

    public void setVille(Ville ville) {
        this.ville = ville;
    }

    public Foret getForet() {
        return foret;
    }

    public void setForet(Foret foret) {
        this.foret = foret;
    }
}
