package com.projet.controller;

import com.projet.model.Foret;
import com.projet.model.Grille;
import com.projet.model.Joueur;
import com.projet.model.Soldat;
import com.projet.model.Ville;
import com.projet.persistence.*;
import com.projet.utils.button.ButtonStrategy;
import com.projet.utils.button.actionMove.MoveDown;
import com.projet.utils.button.actionMove.MoveLeft;
import com.projet.utils.button.actionMove.MoveRight;
import com.projet.utils.button.actionMove.MoveUp;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ActionsController {
    @PersistenceContext
    private GrilleDAO grilleDAO;
    @PersistenceContext
    private JoueurDAO joueurDAO;
    @PersistenceContext
    private VilleDAO villeDAO;

    public ActionsController() {
        setGrilleDAO(new GrilleDAO());
        setJoueurDAO(new JoueurDAO());
        setVilleDAO(new VilleDAO());
    }

    public void creerGrille(HttpServletRequest request, HttpServletResponse response) {
        int lignes = 8;
        int colonnes = 8;
        HttpSession session = request.getSession(false);
        Grille grille = new Grille(lignes, colonnes);
        Joueur joueur = (Joueur) session.getAttribute("joueur");
        joueur.setTurn(true);

        // Sauvegarder le joueur actuel s'il n'est pas déjà sauvegardé
        if (joueur.getId() == null) {
            try (JoueurDAO joueurDAO = new JoueurDAO()) {
                joueurDAO.creerJoueur(joueur);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        joueur.setNbVilles(0);
        joueur.setNbSoldats(0);
        joueur.setPointsProduction(10);

        System.out.println("Nombre de soldats initialisé: " + joueur.getNbSoldats());
        System.out.println("Nombre de villes initialisé: " + joueur.getNbVilles());
        System.out.println("Score actuel: " + joueur.getScore());

        // Ajouter le premier soldat avec le joueur actuel comme propriétaire
        grille.ajouterSoldat(0, 1, joueur);
        grille.addJoueur(joueur);
        System.out.println("Soldat ajouté, nombre de soldats: " + joueur.getNbSoldats());
        System.out.println("Score actuel: " + joueur.getScore());

        // ajout d'une foret
        grille.ajouterForet(1, 3, 3);

        // ajout d'une ville
        try (JoueurDAO joueurDAO = new JoueurDAO()) {
            Joueur joueur2 = joueurDAO.trouverJoueurParNom("hadjuse3");
            grille.ajouterVille(0, 2, joueur2);  // Ville du joueur 2
            // ajout d'un soldat
            grille.ajouterSoldat(2, 4, joueur2);
            grille.addJoueur(joueur2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        grille.ajouterVille(0, 0, joueur); // Ville du joueur 1

        // Sauvegarder la grille

        // Initialisation du score du joueur
        joueur.updateScore();

        // Envoi des attributs à la vue
        request.setAttribute("joueur", joueur);
        try (GrilleDAO grilleDAO = new GrilleDAO()) {
            //grilleDAO.creerGrille(grille);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("grille", grille);
        grille.getJoueurs().forEach(System.out::println);
        // Mise à jour du joueur
        try (JoueurDAO joueurDAO = new JoueurDAO(); GrilleDAO grilleDAO = new GrilleDAO()) {
            grilleDAO.enregistrerGrille(grille);
            joueurDAO.mettreAJourJoueur(joueur);
        } catch (Exception e) {
            e.printStackTrace();
        } // Mise à jour du joueur
        //grilleDAO.enregistrerGrille(grille);
        // Log pour vérifier que la grille a été créée
        System.out.println("Grille créée avec succès : " + grille);
    }

    public void afficherGrille(HttpServletRequest request, HttpServletResponse response) {
        String grilleId = request.getParameter("id");
        if (grilleId == null) {
            request.setAttribute("message", "ID de la grille manquant.");
            return;
        }

        try (GrilleDAO grilleDAO = new GrilleDAO();
             JoueurDAO joueurDAO = new JoueurDAO();
             SoldatDAO soldatDAO = new SoldatDAO())
        {
            Grille grille = grilleDAO.trouverGrilleParId(Long.parseLong(grilleId));

            // Récupérer le joueur (depuis la session) :
            HttpSession session = request.getSession(false);
            Joueur sessionPlayer = (Joueur) session.getAttribute("joueur");

            if (grille == null || sessionPlayer == null) {
                request.setAttribute("message", "Grille ou joueur non trouvé.");
            } else {
                // 1) Recharger le joueur depuis la base (pour rafraîchir turn et autres champs)
                Joueur joueurMisAJour = joueurDAO.trouverJoueurParId(sessionPlayer.getId());

                // 2) Mettre ce joueurMisAJour dans la session
                session.setAttribute("joueur", joueurMisAJour);

                // 3) Facultatif : récupérer les soldats
                List<Soldat> soldats = soldatDAO.trouverSoldatsParJoueurId(joueurMisAJour.getId().longValue());
                joueurMisAJour.setSoldats(soldats);

                // 4) Préparer les attributs pour la JSP
                request.setAttribute("grille", grille);
                request.setAttribute("joueur", joueurMisAJour);
                request.setAttribute("soldats", soldats);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deplacerSoldat(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String grilleId = request.getParameter("grilleId");
        int xSource = Integer.parseInt(request.getParameter("xSource"));
        int ySource = Integer.parseInt(request.getParameter("ySource"));
        String direction = request.getParameter("direction");

        try (GrilleDAO grilleDAO = new GrilleDAO(); JoueurDAO joueurDAO = new JoueurDAO(); SoldatDAO soldatDAO = new SoldatDAO(); VilleDAO villeDAO = new VilleDAO()) {
            Grille grille = grilleDAO.trouverGrilleParId(Long.parseLong(grilleId));
            Joueur joueur = (Joueur) request.getSession().getAttribute("joueur");

            ButtonStrategy strategy = getButtonStrategy(direction, grilleDAO);
            if (strategy == null) {
                request.setAttribute("message", "Direction invalide.");
                return;
            }

            // Exécution de la stratégie de déplacement
            strategy.action(grille, xSource, ySource);

            // Mise à jour des informations du joueur, des soldats et des villes
            joueur = joueurDAO.trouverJoueurParId(joueur.getId());
            List<Soldat> soldats = soldatDAO.trouverSoldatsParJoueurId(joueur.getId().longValue());
            List<Ville> villes = villeDAO.trouverVillesParJoueurId(joueur.getId().longValue());
            joueur.setSoldats(soldats);
            joueur.setVilles(villes);

            // Mise à jour des attributs de la requête
            updateRequestAttributes(request, grille, joueur, soldats, villes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateRequestAttributes(HttpServletRequest request, Grille grille, Joueur joueur, List<Soldat> soldats, List<Ville> villes) {
        boolean[][] adjacentToSoldat = new boolean[grille.getLignes()][grille.getColonnes()];
        for (int i = 0; i < grille.getLignes(); i++) {
            for (int j = 0; j < grille.getColonnes(); j++) {
                adjacentToSoldat[i][j] = grille.isAdjacentToType(i, j, "SOLDATOCCUPE");
            }
        }

        request.setAttribute("grille", grille);
        request.setAttribute("adjacentToSoldat", adjacentToSoldat);
        request.setAttribute("joueur", joueur);
        request.setAttribute("soldats", soldats);
        request.setAttribute("villes", villes);
    }

    private ButtonStrategy getButtonStrategy(String direction, GrilleDAO grilleDAO) {
        switch (direction) {
            case "up":
                return new MoveUp(grilleDAO);
            case "down":
                return new MoveDown(grilleDAO);
            case "left":
                return new MoveLeft(grilleDAO);
            case "right":
                return new MoveRight(grilleDAO);
            default:
                return null;
        }
    }



    public void collecterResources(HttpServletRequest request, HttpServletResponse response) {
        try {
            String grilleId = getParameter(request, "grilleId");
            int xSource = Integer.parseInt(getParameter(request, "xSource"));
            int ySource = Integer.parseInt(getParameter(request, "ySource"));

            try (GrilleDAO grilleDAO = new GrilleDAO(); JoueurDAO joueurDAO = new JoueurDAO(); ForetDAO foretDAO = DAOFactory.getForetDAO()) {
                Grille grille = grilleDAO.trouverGrilleParId(Long.parseLong(grilleId));
                Joueur joueur = (Joueur) request.getSession().getAttribute("joueur");

                collecterRessourcesDeLaForet(grille, xSource, ySource, joueur, foretDAO);

                mettreAJourJoueur(joueur);
                grilleDAO.enregistrerGrille(grille);
                request.setAttribute("grille", grille);
                request.setAttribute("joueur", joueur);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guerirSoldat(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long soldatId = obtenirSoldatId(request);
            Joueur joueur = obtenirJoueur(request);

            Soldat soldatToHeal = trouverSoldat(soldatId, joueur);
            if (soldatToHeal != null) {
                soignerSoldat(soldatToHeal);
                mettreAJourSoldat(soldatToHeal);
                mettreAJourJoueur(joueur);
            }

            recupererGrille(request);
            stockerJoueurDansRequest(request, joueur);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Long obtenirSoldatId(HttpServletRequest request) {
        String soldatIdParam = request.getParameter("soldatId");
        if (soldatIdParam == null || soldatIdParam.isEmpty()) {
            throw new IllegalArgumentException("Le paramètre soldatId est manquant ou vide.");
        }
        return Long.parseLong(soldatIdParam);
    }

    private Joueur obtenirJoueur(HttpServletRequest request) {
        return (Joueur) request.getSession().getAttribute("joueur");
    }

    private Soldat trouverSoldat(Long soldatId, Joueur joueur) throws SQLException {
        try (SoldatDAO soldatDAO = new SoldatDAO()) {
            Soldat soldat = soldatDAO.trouverSoldatParId(soldatId);
            if (soldat != null && !soldat.getProprietaire().getId().equals(joueur.getId())) {
                return null;
            }
            return soldat;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void soignerSoldat(Soldat soldat) {
        soldat.soigner();
    }

    private void mettreAJourSoldat(Soldat soldat) throws SQLException {
        try (SoldatDAO soldatDAO = new SoldatDAO()) {
            soldatDAO.mettreAJourSoldat(soldat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void mettreAJourJoueur(Joueur joueur) throws SQLException {
        try (JoueurDAO joueurDAO = new JoueurDAO()) {
            joueurDAO.mettreAJourJoueur(joueur);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void recupererGrille(HttpServletRequest request) throws SQLException {
        String grilleId = request.getParameter("grilleId");
        if (grilleId != null && !grilleId.isEmpty()) {
            try (GrilleDAO grilleDAO = new GrilleDAO()) {
                Grille grille = grilleDAO.trouverGrilleParId(Long.parseLong(grilleId));
                request.setAttribute("grille", grille);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void stockerJoueurDansRequest(HttpServletRequest request, Joueur joueur) {
        request.setAttribute("joueur", joueur);
    }

    public void occuperVille(HttpServletRequest request, HttpServletResponse response) {
        // Récupération des paramètres de la requête
        String grilleId = request.getParameter("grilleId");
        int xSource = Integer.parseInt(request.getParameter("xSource"));
        int ySource = Integer.parseInt(request.getParameter("ySource"));
        Grille grille = getGrilleDAO().trouverGrilleParId(Long.parseLong(grilleId));
        Joueur joueur = (Joueur) request.getSession().getAttribute("joueur");
        Ville ville = grille.getTuile(xSource, ySource).getVille();
        System.out.println("Ville occupée avec succès");
        // Attaque de la ville
        ville.subirAttaque(3); // Attaque de la ville par le soldat factice
        if (ville.getPointsDefense() <= 0) {
            // TD enlever la propriété a l'autre joueur
            ville.setProprietaire(joueur); // La ville est conquise
            joueur.addNbVilles(1); // Ajout d'une ville au joueur
            System.out.println("Ville capturée avec succès ");
            ville.setPointsDefense(12); // Réinitialisation des points de défense
            joueur.updateScore();
        }
        System.out.println("Ville attaquée avec succès il reste: "+ville.getPointsDefense());
        joueurDAO.mettreAJourJoueur(joueur);
        villeDAO.mettreAJourVille(ville);
        request.setAttribute("grille", grille);
        request.setAttribute("joueur", joueur);
    }

    public void setGrilleDAO(GrilleDAO grilleDAO) {
        this.grilleDAO = grilleDAO;
    }

    public GrilleDAO getGrilleDAO() {
        return grilleDAO;
    }

    public void setJoueurDAO(JoueurDAO joueurDAO) {
        this.joueurDAO = joueurDAO;
    }

    public JoueurDAO getJoueurDAO() {
        return joueurDAO;
    }

    public void setVilleDAO(VilleDAO villeDAO) {
        this.villeDAO = villeDAO;
    }

    public VilleDAO getVilleDAO() {
        return villeDAO;
    }

    private void forwardToFrontController(HttpServletRequest request, HttpServletResponse response, String action) throws IOException {
        request.setAttribute("action", action);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/FrontController");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new IOException("Erreur lors de la redirection vers FrontController", e);
        }
    }

    private String getParameter(HttpServletRequest request, String paramName) {
        return request.getParameter(paramName);
    }

    private void collecterRessourcesDeLaForet(Grille grille, int xSource, int ySource, Joueur joueur, ForetDAO foretDAO) {
        Foret foret = grille.getTuile(xSource, ySource).getForet();
        int ptGagner = foret.fourrager();
        joueur.ajouterPointsProduction(ptGagner);

        // Mettre à jour la forêt dans la base de données
        foretDAO.mettreAJourForet(foret);
        System.out.println("Ressources collectées avec succès " + joueur.getPointsProduction());
    }

    public void creerSoldat(HttpServletRequest request, HttpServletResponse response) {
        try {
            String grilleId = getParameter(request, "grilleId");
            int xSource = Integer.parseInt(getParameter(request, "xSource"));
            int ySource = Integer.parseInt(getParameter(request, "ySource"));

            try (GrilleDAO grilleDAO = new GrilleDAO(); JoueurDAO joueurDAO = new JoueurDAO(); SoldatDAO soldatDAO = new SoldatDAO()) {
                Grille grille = grilleDAO.trouverGrilleParId(Long.parseLong(grilleId));
                Joueur joueur = (Joueur) request.getSession().getAttribute("joueur");

                grille.ajouterSoldat(xSource + 1, ySource, joueur);
                soldatDAO.creerSoldat(grille.getTuile(xSource + 1, ySource).getSoldat());

                joueur.retirerPointsProduction(10);
                joueur.updateScore();
                joueurDAO.mettreAJourJoueur(joueur);
                grilleDAO.enregistrerGrille(grille);
                Joueur joueur1 = joueurDAO.trouverJoueurParId(joueur.getId());
                System.out.println(joueur1.isTurn());
                request.setAttribute("grille", grille);
                request.setAttribute("joueur", joueur);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void passerTour(HttpServletRequest request, HttpServletResponse response) {
        try (GrilleDAO grilleDAO1 = new GrilleDAO();
             JoueurDAO joueurDAO = new JoueurDAO();
             SoldatDAO soldatDAO = new SoldatDAO())
        {
            // 1) Récupérer la grille via l'ID passé en paramètre
            Long grilleId = Long.valueOf(request.getParameter("grilleId"));
            Grille grille = grilleDAO1.trouverGrilleParId(grilleId);

            // (Optionnel) Enregistrer la grille si vous le souhaitez vraiment ici
            grilleDAO1.enregistrerGrille(grille);

            // 2) Récupérer le joueur actuel depuis la session
            Joueur currentPlayer = (Joueur) request.getSession(false).getAttribute("joueur");
            if (currentPlayer == null) {
                throw new RuntimeException("Joueur en session introuvable.");
            }

            System.out.println("Joueur actuel : " + currentPlayer.getNom()
                    + " (ID=" + currentPlayer.getId() + ")");

            // 3) Mettre turn=false pour le joueur actuel en base
            currentPlayer.setTurn(false);
            joueurDAO.mettreAJourJoueur(currentPlayer);

            // 4) Récupérer la liste des joueurs dans la grille
            List<Joueur> joueurs = grille.getJoueurs();
            // ex. [ Joueur#1, Joueur#2, ... ]

            // 5) Trouver l'index du currentPlayer (par ID) dans cette liste
            int currentIndex = -1;
            Integer currentPlayerId = currentPlayer.getId();
            for (int i = 0; i < joueurs.size(); i++) {
                if (joueurs.get(i).getId().equals(currentPlayerId)) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex == -1) {
                throw new RuntimeException("Impossible de trouver le joueur ID="
                        + currentPlayerId + " dans la liste !");
            }

            // 6) Calculer l’index du prochain joueur
            int nextIndex = (currentIndex + 1) % joueurs.size();

            // 7) Récupérer le prochain joueur via son ID en base (pour être sûr de l’entité)
            Joueur nextPlayer = joueurDAO.trouverJoueurParId(joueurs.get(nextIndex).getId());
            if (nextPlayer == null) {
                throw new RuntimeException("Impossible de trouver le prochain joueur (index="
                        + nextIndex + ") en base !");
            }

            // 8) Mettre turn=true pour le prochain joueur
            nextPlayer.setTurn(true);
            joueurDAO.mettreAJourJoueur(nextPlayer);

            // 9) Mettre à jour l'état des soldats du prochain joueur pour qu'ils soient jouables
            List<Soldat> soldats = soldatDAO.trouverSoldatsParJoueurId(nextPlayer.getId().longValue());
            for (Soldat soldat : soldats) {
                soldat.setAJouer(false);
                soldatDAO.mettreAJourSoldat(soldat);
            }

            // Logs de débogage
            System.out.println("Index du joueur actuel : " + currentIndex);
            System.out.println("Index du prochain joueur : " + nextIndex);
            System.out.println("** Joueur actuel (ID=" + currentPlayerId + ") => turn=false");
            System.out.println("** Joueur suivant (ID=" + nextPlayer.getId() + ") => turn=true");

            // 10) Préparer les attributs pour la JSP
            request.getSession(false).setAttribute("joueur", currentPlayer);
            request.setAttribute("joueur", currentPlayer);
            request.setAttribute("grille", grille);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}