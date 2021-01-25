package sdm.reversi;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDisk {

    @ParameterizedTest
    @EnumSource(Disk.Color.class)
    public void hasOriginalSideUpWithNoMoves(Disk.Color color){
        Disk disk = new Disk(color);
        assertEquals(color, disk.getSideUp());
    }

    @ParameterizedTest
    @CsvSource({"BLACK, WHITE", "WHITE, BLACK"})
    public void changesColorWhenFlipped(Disk.Color initialColor, Disk.Color flippedColor){
        Disk disk = new Disk(initialColor);
        disk.flip();
        assertEquals(flippedColor, disk.getSideUp());
    }

    @ParameterizedTest
    @CsvSource({"BLACK, B", "WHITE, W"})
    public void isRepresentedAsInitialLetterOfItsSideUpColor(Disk.Color color, String representation){
        Disk disk = new Disk(color);
        assertEquals(representation, disk.toString());
    }

}
