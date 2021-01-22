package sdm.reversi;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestBoard {

    @ParameterizedTest
    @CsvSource({"0A", "2Z", "10L"})
    public void checkingIfEmptyCellOutsideBoardIsNotPossible(String stringCoordinate){
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.isCellEmpty(Coordinate.parseCoordinate(stringCoordinate)));
    }

    @ParameterizedTest
    @CsvSource({"1A", "2A", "5C"})
    public void putDiskInEmptyCell(String stringCoordinate){
        Board board = new Board();
        Coordinate coordinate = Coordinate.parseCoordinate(stringCoordinate);
        Disk disk = new Disk(Disk.Color.BLACK);
        assertTrue(board.putDisk(disk, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"1A", "2A", "5C"})
    public void putDiskInEmptyCellDelegatingDiskCreation(String stringCoordinate){
        Board board = new Board();
        Coordinate coordinate = Coordinate.parseCoordinate(stringCoordinate);
        assertTrue(board.putDisk(Disk.Color.BLACK, coordinate));
    }


    @ParameterizedTest
    @CsvSource({"1A", "5F", "8H"})
    public void cantPutDiskInNonEmptyCell(String stringCoordinate){
        Board board = new Board();
        Disk disk = new Disk(Disk.Color.BLACK);
        Coordinate coordinate = Coordinate.parseCoordinate(stringCoordinate);
        board.putDisk(disk,coordinate);
        Disk duplicatedDisk = new Disk(Disk.Color.BLACK);
        assertFalse(board.putDisk(duplicatedDisk, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"1A", "5F", "8H"})
    public void cantPutDiskInNonEmptyCellDelegatingDiskCreation(String stringCoordinate){
        Board board = new Board();
        Coordinate coordinate = Coordinate.parseCoordinate(stringCoordinate);
        board.putDisk(Disk.Color.BLACK,coordinate);
        assertFalse(board.putDisk(Disk.Color.BLACK, coordinate));
    }

    @ParameterizedTest
    @CsvSource({"11C", "0A", "4I"})
    public void cantPutDiskInCellOutOfBoard(String stringCoordinate){
        Board board = new Board();
        Disk disk = new Disk(Disk.Color.BLACK);
        Coordinate coordinate = Coordinate.parseCoordinate(stringCoordinate);
        assertThrows(IllegalArgumentException.class, () -> board.putDisk(disk,coordinate));
    }

    @ParameterizedTest
    @CsvSource({"11C", "0A", "4I"})
    public void cantPutDiskInCellOutOfBoardDelegatingDiskCreation(String stringCoordinate){
        Board board = new Board();
        Coordinate coordinate = Coordinate.parseCoordinate(stringCoordinate);
        assertThrows(IllegalArgumentException.class, () -> board.putDisk(Disk.Color.BLACK,coordinate));
    }

    @ParameterizedTest
    @CsvSource({"4", "6", "12"})
    public void canBuildBoardOfDifferentSize(int boardSize){
        assertDoesNotThrow(() -> {Board board = new Board(boardSize);});
    }

    @ParameterizedTest
    @CsvSource({"5", "2", "30"})
    public void cantBuildBoardOfIllegalSize(int boardSize){
        assertThrows(IllegalArgumentException.class, () -> {Board board = new Board(boardSize);});
    }

    @Test
    public void emptyBoardRepresentedAsString(){
        String emptyBoard = "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------";
        Board board = new Board();
        assertEquals(emptyBoard, board.toString());
    }

    @Test
    public void boardWithWhiteDiskInCell1ARepresentedAsString(){
        String emptyBoard = "W-------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------";
        Board board = new Board();
        Disk whiteDisk = new Disk(Disk.Color.WHITE);
        board.putDisk(whiteDisk,Coordinate.parseCoordinate("1A"));
        assertEquals(emptyBoard, board.toString());
    }

    @Test
    public void boardWithWhiteDiskInCell1ARepresentedAsStringDelegatingDiskCreation(){
        String emptyBoard = "W-------\n" +
                "--------\n" +
                "--------\n" +
                "--------\n" +
                "--------\n" +
                "--------\n" +
                "--------\n" +
                "--------";
        Board board = new Board();
        board.putDisk(Disk.Color.WHITE,Coordinate.parseCoordinate("1A"));
        assertEquals(emptyBoard, board.toString());
    }

    @Test
    public void boardWithWhiteDiskInCell1AAndBlackDiskInCell8HRepresentedAsStringDelegatingDiskCreation(){
        String emptyBoard = "W-------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "--------\n" +
                            "-------B";
        Board board = new Board();
        board.putDisk(Disk.Color.WHITE,Coordinate.parseCoordinate("1A"));
        board.putDisk(Disk.Color.BLACK,Coordinate.parseCoordinate("8H"));
        assertEquals(emptyBoard, board.toString());
    }

    @Test
    public void boardWithWhiteDiskInCell1AAndBlackDiskInCell8HRepresentedAsString(){
        String emptyBoard = "W-------\n" +
                "--------\n" +
                "--------\n" +
                "--------\n" +
                "--------\n" +
                "--------\n" +
                "--------\n" +
                "-------B";
        Board board = new Board();
        Disk whiteDisk = new Disk(Disk.Color.WHITE);
        Disk blackDisk = new Disk(Disk.Color.BLACK);
        board.putDisk(whiteDisk,Coordinate.parseCoordinate("1A"));
        board.putDisk(blackDisk,Coordinate.parseCoordinate("8H"));
        assertEquals(emptyBoard, board.toString());
    }


}
