package com.projet.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.projet.model.TurnByTurn;


import java.io.IOException;

@WebServlet("/passTurn")
public class PassTurnServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        TurnByTurn turn = (TurnByTurn)  session.getAttribute("tour");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if(turn !=null){
            turn.passTurn();
            response.getWriter().write("{\"success\": true}");
        }else{
            response.getWriter().write("{\"success\": false}");
        }

    }

}
