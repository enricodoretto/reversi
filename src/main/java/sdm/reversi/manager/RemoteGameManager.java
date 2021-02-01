package sdm.reversi.manager;

import sdm.reversi.CommunicationProtocol;
import sdm.reversi.Coordinate;
import sdm.reversi.game.Game;
import sdm.reversi.player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.HashSet;

public class RemoteGameManager implements GameManager {

    private static final int PORT_NUMBER = 10000;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    public RemoteGameManager() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            Socket socket = serverSocket.accept();
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
    }

    @Override
    public void updateGame(Game game) {
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(CommunicationProtocol.UPDATE);
            objectOutputStream.writeObject(game);
        } catch (IOException e) {
        }
    }

    @Override
    public void startTurn(Player currentPlayer) {
        try {
            objectOutputStream.writeObject(CommunicationProtocol.START);
            objectOutputStream.writeObject(currentPlayer);
        } catch (IOException e) {
        }
    }

    @Override
    public void skipTurn() {
        try {
            objectOutputStream.writeObject(CommunicationProtocol.SKIP);
        } catch (IOException e) {
        }
    }

    @Override
    public void showWinner(Player player) {
        try {
            objectOutputStream.writeObject(CommunicationProtocol.WINNER);
            objectOutputStream.writeObject(player);
        } catch (IOException e) {
        }
    }

    @Override
    public void suggestMoves(Collection<Coordinate> moves) {
        try {
            objectOutputStream.writeObject(CommunicationProtocol.SUGGEST);
            objectOutputStream.writeObject(new HashSet<>(moves));
        } catch (IOException e) {
        }
    }

    @Override
    public Coordinate getMoveFromPlayer() {
        try {
            objectOutputStream.writeObject(CommunicationProtocol.GET_MOVE);
            return (Coordinate) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public void initialize(Game game) {
        try {
            objectOutputStream.writeObject(CommunicationProtocol.INITIALIZE);
            objectOutputStream.writeObject(game);
        } catch (IOException e) {
        }
    }

    @Override
    public void illegalMove() {
        try {
            objectOutputStream.writeObject(CommunicationProtocol.ILLEGAL);
        } catch (IOException e) {
        }
    }

    public String getName() {
        try {
            return (String) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
