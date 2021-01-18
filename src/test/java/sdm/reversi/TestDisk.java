package sdm.reversi;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDisk {

    @Test
    public void hasWhiteSideUpAfterCreatedWhiteWithNoMoves(){
        Disk whiteDisk = new Disk(Disk.Color.WHITE);
        assertEquals(Disk.Color.WHITE, whiteDisk.getSideUp());
    }

    @Test
    public void hasBlackSideUpAfterCreatedBlackWithNoMoves(){
        Disk blackDisk = new Disk(Disk.Color.BLACK);
        assertEquals(Disk.Color.BLACK, blackDisk.getSideUp());
    }

}
