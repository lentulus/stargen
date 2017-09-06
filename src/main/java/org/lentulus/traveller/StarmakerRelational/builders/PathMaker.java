package org.lentulus.traveller.StarmakerRelational.builders;

import lombok.extern.slf4j.Slf4j;
import org.lentulus.traveller.StarmakerRelational.model.Location;
import org.lentulus.traveller.StarmakerRelational.model.PathElement;
import org.lentulus.traveller.StarmakerRelational.model.support.Sector;
import org.lentulus.traveller.StarmakerRelational.model.support.StarSystems;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by User on 8/31/2017.
 */
@Slf4j
@Component
public class PathMaker {


    public Optional<PathElement> path(StarSystems starSystems, Location start, Location destination) {
        log.info("Pathing over {} parsecs", start.distanceTo(destination) / 100);

        AStarClass pather = new AStarClass();

        pather.aStarSearch(starSystems, start, destination, 300);
        pather.logPath(start, destination);

        return Optional.empty();
    }






}
