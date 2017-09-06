package org.lentulus.traveller.StarmakerRelational.model.support;

/**
 * Created by User on 8/31/2017.
 */

import org.lentulus.traveller.StarmakerRelational.builders.WeightedGraph;
import org.lentulus.traveller.StarmakerRelational.builders.WorldBuilder;
import org.lentulus.traveller.StarmakerRelational.model.Location;
import org.lentulus.traveller.StarmakerRelational.model.LocationUtils;
import org.lentulus.traveller.StarmakerRelational.model.SystemPrimeData;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * Created by User on 8/6/2017.
 */

public class StarSystems implements WeightedGraph, Serializable {

    private double x, y, z; // bounds
    private double cellMax; // bounds for cubical cell
    private SectorArray sectorArray;
    private Map<Long, Location> locationMap = new TreeMap<>();

    public Sector getSector(int x, int y, int z) {
        return sectorArray.getSector(x, y, z);
    }

    public boolean addLocation(Location l) {
        if (sectorArray.isOutside(l)) {
            return false;
        }
        if (!locationMap.containsKey(l.getKey())) {
            if (sectorArray.add(l)) {
                locationMap.put(l.getKey(), l);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    public double size() {
        return locationMap.size();
    }

    public void setBounds(double x, double y, double z, double cellMax) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.cellMax = cellMax;
        sectorArray = new SectorArray((int) (x / Universe.getSectorSize()),
                (int) (y / Universe.getSectorSize()),
                (int) (z / Universe.getSectorSize()));
    }


    public void makeWorlds(WorldBuilder worldBuilder) {
        locationMap.forEach((key, location) -> {
            location.setSystemPrimeData(worldBuilder.makeSystemPrimeData(location));
        });
    }

    public int countType(SystemPrimeData.SystemType systemType) {
        int count = 0;

        Collection<Location> locations = locationMap.values();
        for (Location l : locations) {
            if (l.getSystemPrimeData().isType(systemType)) {
                count++;
            }
        }

        return count;
    }

    public List<Location> getGardens(int x, int y, int z) {
        return sectorArray.getSector(x, y, z).getLocationsWithLife();
    }

    public Sector getSector(Location current) {
        int x = LocationUtils.toSector(current.getX());
        int y = LocationUtils.toSector(current.getY());
        int z = LocationUtils.toSector(current.getZ());

        return sectorArray.getSector(x, y, z);
    }

    public Collection<Sector> getAdjacentSectors(Sector sector) {
        Collection<Sector> adjacentSectors = new ArrayList<>();
        int currentX = sector.getArrayX();
        int currentY = sector.getArrayY();
        int currentZ = sector.getArrayZ();
        for (int dx = -1; dx < 2; dx++) {
            for (int dy = -1; dy < 2; dy++) {
                for (int dz = -1; dz < 2; dz++) {
                    if (dx == 0 && dy == 0 && dz == 0) {
                        continue;
                    }
                    if (sectorArray.inBounds(currentX + dx, currentY + dy, currentZ + dz)) {
                        adjacentSectors.add(sectorArray.getSector(currentX + dx, currentY + dy, currentZ + dz));
                    }
                }
            }
        }

        return adjacentSectors;
    }

    @Override
    public double Cost(Location a, Location b) {
        return a.distanceTo(b);
    }

    @Override
    public Collection<Location> getNeighbors(Location current, double maxJump) {
        Collection<Location> near = new ArrayList<>();
        Sector sector = this.getSector(current);
        near.addAll(sector.getNearIn(current, maxJump));
        Collection<Sector> adjacentSectors = this.getAdjacentSectors(sector);

        for (Sector s : adjacentSectors) {
            near.addAll(s.getNearIn(current, maxJump));
        }

        return near;
    }

    public int countLifeByTech(int i) {
        int count = 0;

        Collection<Location> locations = locationMap.values();
        for (Location l : locations) {
            if (l.getSystemPrimeData().getPopulation().isPresent()){
                if(l.getSystemPrimeData().getPopulation().get().techLevel == i){
                    count++;
                }
            }
        }

        return count;
    }
}
