package com.projet.controller;

import com.projet.model.Foret;
import com.projet.model.Grille;
import com.projet.model.Joueur;
import com.projet.model.Soldat;
import com.projet.model.Ville;
import com.projet.persistence.DAOFactory;
import com.projet.persistence.ForetDAO;
import com.projet.persistence.GrilleDAO;
import com.projet.persistence.JoueurDAO;
import com.projet.persistence.VilleDAO;
import com.projet.utils.button.ButtonStrategy;
import com.projet.utils.button.actionMove.MoveDown;
import com.projet.utils.button.actionMove.MoveLeft;
import com.projet.utils.button.actionMove.MoveRight;
import com.projet.utils.button.actionMove.MoveUp;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.DataInput;
import java.io.IOException;

public class ActionsController {
    private GrilleDAO grilleDAO;
    private JoueurDAO joueurDAO;
    private VilleDAO villeDAO;


    public ActionsController() {
        setGrilleDAO(new GrilleDAO());
        setJoueurDAO(new JoueurDAO());
        setVilleDao(new VilleDAO());
    }

    public void creerGrille(HttpServletRequest request, HttpServletResponse response) {
        int lignes = 8;
        int colonnes = 8;
        HttpSession session = request.getSession(false);
        Grille grille = new Grille(lignes, colonnes);
        Joueur joueur = (Joueur) session.getAttribute("joueur");
      
        try (JoueurDAO joueurDAO = new JoueurDAO()) {
            Joueur joueur2 = joueurDAO.trouverJoueurParNom("hadjuse3");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        System.out.println("Nombre de soldats initialisé: " + joueur.getNbSoldats());
        System.out.println("Nombre de villes initialisé: " + joueur.getNbVilles());
        System.out.println("Score actuel: " + joueur.getScore());

        // Ajouter le premier soldat avec le joueur actuel comme propriétaire
        grille.ajouterSoldat(0, 1, joueur);
        System.out.println("Soldat ajouté, nombre de soldats: " + joueur.getNbSoldats());
        System.out.println("Score actuel: " + joueur.getScore());


        // ajout d'une foret
        grille.ajouterForet(1, 3, 3);

        // ajout d'une ville
        grille.ajouterVille(0, 0, joueur); // Ville du joueur 1
        grille.ajouterVille(0, 2, joueur2);  // Ville du joueur 2

        // Sauvegarder la grille

        // Initialisation du score du joueur
        joueur.updateScore();

        // Envoi des attributs à la vue
        request.setAttribute("joueur", joueur);
        try (GrilleDAO grilleDAO = new GrilleDAO()) {
            grilleDAO.creerGrille(grille);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("grille", grille);

        // Mise à jour du joueur
        joueurDAO.mettreAJourJoueur(joueur);

        // Log pour vérifier que la grille a été créée
        System.out.println("Grille créée avec succès : " + grille);
    }

    public void afficherGrille(HttpServletRequest request, HttpServletResponse response) {
        String grilleId = request.getParameter("id");
        if (grilleId == null) {
            request.setAttribute("message", "ID de la grille manquant.");
            return;
        }

        // Récupérer la grille depuis la base de données
        try (GrilleDAO grilleDAO = new GrilleDAO()) {
            Grille grille = grilleDAO.trouverGrilleParId(Long.parseLong(grilleId));

            if (grille == null) {
                request.setAttribute("message", "Grille non trouvée.");
            } else {
                request.setAttribute("grille", grille);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deplacerSoldat(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String grilleId = request.getParameter("grilleId");
        Long grilleIdLong = Long.parseLong(grilleId);
        int xSource = Integer.parseInt(request.getParameter("xSource"));
        int ySource = Integer.parseInt(request.getParameter("ySource"));
        String direction = request.getParameter("direction");
        System.out.printf("Grille ID: %s, xSource: %d, ySource: %d, direction: %s\n", grilleId, xSource, ySource, direction);

        try (GrilleDAO grilleDAO = new GrilleDAO()) {
            Grille grille = grilleDAO.trouverGrilleParId(grilleIdLong);
            Joueur joueur = (Joueur) request.getSession().getAttribute("joueur");
            ButtonStrategy strategy;

            switch (direction) {
                case "up":
                    strategy = new MoveUp(grilleDAO);
                    break;
                case "down":
                    strategy = new MoveDown(grilleDAO);
                    break;
                case "left":
                    strategy = new MoveLeft(grilleDAO);
                    break;
                case "right":
                    strategy = new MoveRight(grilleDAO);
                    break;
                default:
                    request.setAttribute("message", "Direction invalide.");
                    return;
            }
            System.out.printf("Grille ID: %s, xSource: %d, ySource: %d, direction: %s\n", grilleId, xSource, ySource, direction);
            strategy.action(grille, xSource, ySource);
            boolean[][] adjacentToSoldat = new boolean[grille.getLignes()][grille.getColonnes()];
            for (int i = 0; i < grille.getLignes(); i++) {
                for (int j = 0; j < grille.getColonnes(); j++) {
                    adjacentToSoldat[i][j] = grille.isAdjacentToType(i, j, "SOLDATOCCUPE");
                }
            }

            request.setAttribute("grille", grille);
            request.setAttribute("adjacentToSoldat", adjacentToSoldat);
            //forwardToFrontController(request, response, "deplacerSoldat");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void setVilleDao(VilleDAO villeDAO) {
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

    public void collecterResources(HttpServletRequest request, HttpServletResponse response) {
        try {
            String grilleId = getParameter(request, "grilleId");
            int xSource = Integer.parseInt(getParameter(request, "xSource"));
            int ySource = Integer.parseInt(getParameter(request, "ySource"));

            try (GrilleDAO grilleDAO = new GrilleDAO(); JoueurDAO joueurDAO = new JoueurDAO(); ForetDAO foretDAO = DAOFactory.getForetDAO()) {
                Grille grille = grilleDAO.trouverGrilleParId(Long.parseLong(grilleId));
                Joueur joueur = (Joueur) request.getSession().getAttribute("joueur");

                collecterRessourcesDeLaForet(grille, xSource, ySource, joueur, foretDAO);

                joueurDAO.mettreAJourJoueur(joueur);
                request.setAttribute("grille", grille);
                request.setAttribute("joueur", joueur);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}