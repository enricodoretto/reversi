package sdm.reversi;

import sdm.reversi.game.Game;
import sdm.reversi.manager.GameManager;
import sdm.reversi.player.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;

public enum CommunicationProtocol implements Serializable {

    @SuppressWarnings("unchecked")
    SUGGEST(((gameManager, is, os) -> {
        try {
            gameManager.suggestMoves((Collection<Coordinate>) is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    })),
    ILLEGAL(((gameManager, is, os) -> {
        try {
            gameManager.illegalMove((String) is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    })),
    GET_MOVE(((gameManager, is, os) -> {
        try {
            os.writeObject(
                    gameManager.getMoveFromPlayer()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    })),
    UPDATE(((gameManager, is, os) -> {
        try {
            gameManager.updateGame((Game) is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    })),
    START(((gameManager, is, os) -> {
        try {
            gameManager.startTurn((Player) is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    })),
    SKIP(((gameManager, is, os) -> {
        gameManager.skipTurn();
    })),
    INITIALIZE(((gameManager, is, os) -> {
        try {
            gameManager.initialize((Game) is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    })),
    WINNER(((gameManager, is, os) -> {
        try {
            gameManager.showWinner((Player) is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }));

    private final TriConsumer<GameManager, ObjectInputStream, ObjectOutputStream> consumer;

    CommunicationProtocol(TriConsumer<GameManager, ObjectInputStream, ObjectOutputStream> consumer) {
        this.consumer = consumer;
    }

    public void act(GameManager gameManager, ObjectInputStream is, ObjectOutputStream os) {
        consumer.accept(gameManager, is, os);
    }
}