package sdm.reversi;

import sdm.reversi.manager.CLIManager;
import sdm.reversi.manager.GameManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        GameManager gameManager = new CLIManager();
        String playerName = "Client Player";
        try (Socket socket = new Socket(InetAddress.getLocalHost(), 10000);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream())
        ) {
            objectOutputStream.writeObject(playerName);
            CommunicationProtocol communicationProtocol;
            while ((communicationProtocol = (CommunicationProtocol) objectInputStream.readObject()
            ) != null) {
                communicationProtocol.performAction(gameManager, objectInputStream, objectOutputStream);
                if(communicationProtocol.equals(CommunicationProtocol.WINNER)) break;
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
