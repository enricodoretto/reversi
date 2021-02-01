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

    SUGGEST(((gameManager, is, os) -> {
        gameManager.suggestMoves((Collection<Coordinate>) is.readObject());
    })),
    ILLEGAL(((gameManager, is, os) -> {
        gameManager.illegalMove((String) is.readObject());
    })),
    GET_MOVE(((gameManager, is, os) -> {
        os.writeObject(gameManager.getMoveFromPlayer());
    })),
    UPDATE(((gameManager, is, os) -> {
        gameManager.updateGame((Game) is.readObject());
    })),
    START(((gameManager, is, os) -> {
        gameManager.startTurn((Player) is.readObject());
    })),
    SKIP(((gameManager, is, os) -> {
        gameManager.skipTurn();
    })),
    INITIALIZE(((gameManager, is, os) -> {
        gameManager.initialize((Game) is.readObject());
    })),
    WINNER(((gameManager, is, os) -> {
        gameManager.showWinner((Player) is.readObject());
    }));

    private final TriConsumer<GameManager, ObjectInputStream, ObjectOutputStream> consumer;

    CommunicationProtocol(TriConsumer<GameManager, ObjectInputStream, ObjectOutputStream> consumer) {
        this.consumer = consumer;
    }

    public void performAction(GameManager gameManager, ObjectInputStream is, ObjectOutputStream os) throws IOException, ClassNotFoundException {
        consumer.accept(gameManager, is, os);
    }
}