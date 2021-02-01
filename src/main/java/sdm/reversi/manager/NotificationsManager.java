package sdm.reversi.manager;

import sdm.reversi.player.Player;
import sdm.reversi.game.Game;

public interface NotificationsManager {

    void initialize(Game game);
    void startTurn(Player currentPlayer);
    void skipTurn();
    void updateGame(Game game);
    void showWinner(Player player);

    default NotificationsManager compose(NotificationsManager other) {
        if (this.equals(other)) {
            return this;
        }
        NotificationsManager notificationsManager = this;
        return new NotificationsManager() {
            @Override
            public void updateGame(Game game) {
                notificationsManager.updateGame(game);
                other.updateGame(game);
            }

            @Override
            public void startTurn(Player currentPlayer) {
                notificationsManager.startTurn(currentPlayer);
                other.startTurn(currentPlayer);
            }

            @Override
            public void skipTurn() {
                notificationsManager.skipTurn();
                other.skipTurn();
            }

            @Override
            public void initialize(Game game) {
                notificationsManager.initialize(game);
                other.initialize(game);
            }

            @Override
            public void showWinner(Player player) {
                notificationsManager.showWinner(player);
                other.showWinner(player);
            }
        };
    }
}
