package org.lentulus.traveller.StarmakerRelational.model.support;

import lombok.extern.slf4j.Slf4j;
import org.lentulus.traveller.StarmakerRelational.model.Location;
import org.lentulus.traveller.StarmakerRelational.model.LocationUtils;

import java.io.Serializable;
import java.util.*;

/**
 * Created by User on 8/9/2017.
 */
@Slf4j
public class Sector implements Serializable {

    Map<Long, Location> locationsInSector;

    Optional<Integer> sectorX = Optional.empty();
    Optional<Integer> sectorY = Optional.empty();
    Optional<Integer> sectorZ = Optional.empty();


    public Sector() {
        this.locationsInSector = new TreeMap<>();
    }

    public void add(Location location) {
        locationsInSector.put(LocationUtils.encodeKey(location), location);
    }

    public List<Location> getLocationsWithLife() {
        List<Location> life = new ArrayList<>();
        for (Location l : locationsInSector.values()) {
            if (l.getSystemPrimeData().isGarden() || l.getSystemPrimeData().getPopulation().isPresent()) {
                life.add(l);
            }
        }

        return life;
    }

    public Collection<Location> getNearIn(Location current, double distance) {
        Collection<Location> nearInSector = new ArrayList<>();
        for (Location l : locationsInSector.values()) {
            if (!current.equals(l)) {
                if (current.distanceTo(l) < distance) {
                    nearInSector.add(l);
                }
            }
        }

        return nearInSector;
    }

    public int getArrayX() {
        if (!sectorX.isPresent()) {
            ArrayList<Location> locations = new ArrayList<Location>(locationsInSector.values());
            int x = LocationUtils.toSector(locations.get(0).getX());
            sectorX = Optional.of(new Integer(x));
        }
        return sectorX.get();
    }

    public int getArrayY() {
        if (!sectorY.isPresent()) {
            ArrayList<Location> locations = new ArrayList<Location>(locationsInSector.values());
            int y = LocationUtils.toSector(locations.get(0).getY());
            sectorY = Optional.of(new Integer(y));
        }
        return sectorY.get();
    }

    public int getArrayZ() {
        if (!sectorZ.isPresent()) {
            ArrayList<Location> locations = new ArrayList<Location>(locationsInSector.values());
            int z = LocationUtils.toSector(locations.get(0).getZ());
            sectorZ = Optional.of(new Integer(z));
        }
        return sectorZ.get();
    }
}
