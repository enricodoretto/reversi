package sdm.reversi.player;

import sdm.reversi.Disk;
import sdm.reversi.manager.RemoteGameManager;

import java.io.IOException;

public class RemotePlayer extends Player{
    public RemotePlayer(Disk.Color color) throws IOException {
        this(color, new RemoteGameManager());
    }

    private RemotePlayer(Disk.Color color, RemoteGameManager serverManager){
        super(serverManager.getName(), color, serverManager);
    }
}
