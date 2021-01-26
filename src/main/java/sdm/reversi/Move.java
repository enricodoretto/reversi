package sdm.reversi;

import java.util.Objects;
import java.util.Set;

public class Move {

    private final Coordinate diskPosition;
    private final Set<Coordinate> coordinatesOfDisksToFlip;

    public Move(Coordinate diskPosition, Set<Coordinate> coordinatesOfDisksToFlip) {
        this.diskPosition = diskPosition;
        this.coordinatesOfDisksToFlip = coordinatesOfDisksToFlip;
    }

    public Coordinate getCoordinate() {
        return diskPosition;
    }

    public Set<Coordinate> getCoordinatesOfDisksToFlip() {
        return coordinatesOfDisksToFlip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return Objects.equals(diskPosition, move.diskPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diskPosition);
    }
}
