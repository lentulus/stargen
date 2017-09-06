package org.lentulus.traveller.StarmakerRelational.model;

import lombok.Getter;

/**
 * Created by User on 8/31/2017.
 */
@Getter
public class PathLink implements Comparable {
    private Double distance;
    private Location to;

    @Override
    public int compareTo(Object o) {
        return distance.compareTo(((PathLink) o).distance);
    }
}
