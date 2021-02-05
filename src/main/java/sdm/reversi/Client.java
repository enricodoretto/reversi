package sdm.reversi;

import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.GUIManager;
import sdm.reversi.manager.GameManager;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private static final int PORT_NUMBER = 10000;

    public static void main(String[] args) throws UnknownHostException {
        GameManager gameManager = new CLIManager();
        String playerName = "Client Player";
        connectAndPlay(playerName, gameManager, InetAddress.getLocalHost());
    }

    public static void connectAndPlay(String playerName, GameManager gameManager, InetAddress ip) {
        if(playerName.isEmpty()){
            throw new IllegalArgumentException("Player name can't be empty");
        }
        if (gameManager instanceof GUIManager) {
                SwingWorker<Void,Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        connectAndPlayWorker(playerName, gameManager, ip);
                        return null;
                    }
                };
                worker.execute();
        } else {
            connectAndPlayWorker(playerName, gameManager, ip);
        }
    }

    private static void connectAndPlayWorker(String playerName, GameManager gameManager, InetAddress ip){
        try (Socket socket = new Socket(ip, PORT_NUMBER);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            objectOutputStream.writeObject(playerName);
            CommunicationProtocol communicationProtocol;
            while ((communicationProtocol = (CommunicationProtocol) objectInputStream.readObject()
            ) != null) {
                communicationProtocol.performAction(gameManager, objectInputStream, objectOutputStream);
                if (communicationProtocol.equals(CommunicationProtocol.WINNER)) break;
            }
        } catch (IOException | ClassNotFoundException e) {
            gameManager.notifyError("connection failed");
        }
    }
}
