package com.projet.utils.button.actionMove;

import com.projet.model.Grille;
import com.projet.persistence.GrilleDAO;
import com.projet.utils.button.ButtonStrategy;

public class MoveRight implements ButtonStrategy {
    private GrilleDAO grilleDAO;
    public MoveRight(GrilleDAO grilleDAO){
        setGrilleDAO(grilleDAO);
    }
    @Override
    public void action(Grille grille, int xSource, int ySource) {
        getXDestAndYdest setXdestAndYdest = getGetXDestAndYdest(xSource, ySource);
        moveSoldier(grille, xSource, ySource, setXdestAndYdest.xDest(), setXdestAndYdest.yDest());
    }

    private getXDestAndYdest getGetXDestAndYdest(int xSource, int ySource) {
        System.out.println("Move right !");
        int xDest = xSource;
        int yDest = ySource + 1;
        getXDestAndYdest setXdestAndYdest = new getXDestAndYdest(xDest, yDest);
        return setXdestAndYdest;
    }

    private record getXDestAndYdest(int xDest, int yDest) {
    }

    private void moveSoldier(Grille grille, int xSource, int ySource, int xDest, int yDest) {
        MoveMethod.move(grille, xSource, ySource, xDest, yDest, grilleDAO);
    }

    public GrilleDAO getGrilleDAO() {
        return grilleDAO;
    }

    public void setGrilleDAO(GrilleDAO grilleDAO) {
        this.grilleDAO = grilleDAO;
    }
}