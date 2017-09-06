package org.lentulus.traveller.StarmakerRelational.builders;

import org.lentulus.traveller.StarmakerRelational.model.Location;

import java.util.Collection;

/**
 * Created by User on 9/3/2017.
 */
public interface WeightedGraph {
        double Cost(Location a, Location b);
        Collection<Location> getNeighbors(Location id, double maxJump);
}
