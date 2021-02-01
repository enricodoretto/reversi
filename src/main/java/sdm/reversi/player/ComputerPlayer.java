package sdm.reversi.player;

import sdm.reversi.Disk;
import sdm.reversi.manager.ComputerGameManager;

public class ComputerPlayer extends Player{

    public ComputerPlayer(Disk.Color color){
        super("CPU", color, new ComputerGameManager());
    }
}
