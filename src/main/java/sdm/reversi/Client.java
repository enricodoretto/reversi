package sdm.reversi;

import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.GameManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket(InetAddress.getLocalHost(), 10000);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ) {
            GameManager gameManager = new CLIManager();
            CommunicationProtocol communicationProtocol;
            while ((communicationProtocol = (CommunicationProtocol) objectInputStream.readObject()
            ) != null) {
                communicationProtocol.performAction(gameManager, objectInputStream, objectOutputStream);
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
