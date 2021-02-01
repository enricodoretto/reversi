package sdm.reversi.manager;

import sdm.reversi.Player;
import sdm.reversi.game.Game;

public interface NotificationsManager {

    void initialize(Game game);
    void startTurn(Player currentPlayer);
    void skipTurn();
    void updateGame(Game game);
    void showWinner(Player player);
}
