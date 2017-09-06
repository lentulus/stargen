package org.lentulus.traveller.StarmakerRelational.model;

import lombok.Getter;

import java.util.Objects;

/**
 * Created by User on 8/30/2017.
 */
@Getter
public class ClosePair {
    Location oneEnd;
    Location otherEnd;
    double distance;

    public ClosePair(Location oneEnd, Location otherEnd) {
        this.oneEnd = oneEnd;
        this.otherEnd = otherEnd;
        this.distance = oneEnd.distanceTo(otherEnd);
    }

    public Location goesTo(Location from) {
        if (this.oneEnd.equals(from)) {
            return otherEnd;
        } else {
            return oneEnd;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClosePair)) return false;
        ClosePair closePair = (ClosePair) o;
        boolean simple = Objects.equals(oneEnd, closePair.oneEnd) &&
                Objects.equals(otherEnd, closePair.otherEnd);

        boolean opposite = Objects.equals(oneEnd, closePair.otherEnd) &&
                Objects.equals(otherEnd, closePair.oneEnd);

        return simple || opposite;

    }

    @Override
    public int hashCode() {
        return Objects.hash(oneEnd, otherEnd);
    }
}
