package org.lentulus.traveller.StarmakerRelational.model;

import lombok.Getter;

import java.util.TreeSet;

/**
 * Created by User on 8/31/2017.
 */
@Getter
public class PathElement implements Comparable<PathElement> {
    private Location thisElement;
    private Double distanceTravelled;


    public PathElement(Location thisElement, double distanceTravelled) {
        this.thisElement = thisElement;
        this.distanceTravelled = distanceTravelled;
    }

    @Override
    public int compareTo(PathElement o) {
        return distanceTravelled.compareTo(o.distanceTravelled);
    }
}
