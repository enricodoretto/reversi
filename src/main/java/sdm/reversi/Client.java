package sdm.reversi;

import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.GameManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static void main(String[] args) throws UnknownHostException {
        GameManager gameManager = new CLIManager();
        String playerName = "Client Player";
        connectAndPlay(playerName, gameManager, InetAddress.getLocalHost(), 10000);
    }

    public static void connectAndPlay(String playerName, GameManager gameManager, InetAddress ip, int port) {
        try (Socket socket = new Socket(ip, port);
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
