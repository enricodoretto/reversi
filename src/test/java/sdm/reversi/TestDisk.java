package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDisk {

    @ParameterizedTest
    @EnumSource(Disk.Color.class)
    public void hasOriginalSideUpWithNoMoves(Disk.Color color){
        Disk disk = new Disk(color);
        assertEquals(color, disk.getSideUp());
    }

}
