package com.projet.controller;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint("/ws/game/{playerId}")
public class GameWebSocket {

    // On stocke toutes les sessions ouvertes, associées à l'ID du joueur
    // (En production, préférez un stockage plus sophistiqué ou un gestionnaire de sessions)
    private static final ConcurrentMap<String, Session> sessionsByPlayerId = new ConcurrentHashMap<>();

    /**
     * Méthode appelée quand un client (navigateur) se connecte au WebSocket
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("playerId") String playerId) {
        sessionsByPlayerId.put(playerId, session);
        System.out.println("WebSocket ouvert pour le joueur " + playerId);
    }

    /**
     * Méthode appelée quand on reçoit un message depuis le client
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("playerId") String playerId) {
        System.out.println("Message reçu de " + playerId + " : " + message);
        // Selon votre besoin, vous pouvez gérer ce message (par ex. un chat, etc.)
    }

    /**
     * Méthode appelée quand la connexion WebSocket est fermée
     */
    @OnClose
    public void onClose(Session session, @PathParam("playerId") String playerId) {
        sessionsByPlayerId.remove(playerId);
        System.out.println("WebSocket fermé pour le joueur " + playerId);
    }

    /**
     * Méthode appelée si erreur
     */
    @OnError
    public void onError(Session session, Throwable throwable, @PathParam("playerId") String playerId) {
        System.out.println("Erreur sur le WebSocket du joueur " + playerId);
        throwable.printStackTrace();
    }

    /**
     * Méthode statique pour "pousser" un message à un joueur.
     * @param playerId identifiant du joueur.
     * @param message message à envoyer.
     */
    public static void sendMessageToPlayer(String playerId, String message) {
        Session session = sessionsByPlayerId.get(playerId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
