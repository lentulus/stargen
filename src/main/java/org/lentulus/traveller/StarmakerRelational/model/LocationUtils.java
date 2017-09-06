package org.lentulus.traveller.StarmakerRelational.model;

import org.lentulus.traveller.StarmakerRelational.model.support.Sector;
import org.lentulus.traveller.StarmakerRelational.model.support.StarSystems;
import org.lentulus.traveller.StarmakerRelational.model.support.Universe;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by User on 8/19/2017.
 */
public class LocationUtils {
    public static long toCentiparsecs(double x) {
        return (long) (100d * x);
    }

    public static long encodeKey(Location location) {
        long key = location.getX();
        key = key * 100000L + location.getY();
        key = key * 100000L + location.getZ();
        return key;
    }

    public static double randAxis(double max) {
        return ThreadLocalRandom.current().nextDouble(0, max);
    }

    public static int toSector(long centiparsecs) {
        final long parsecs = centiparsecs / 100;
        final long sector = parsecs / Universe.getSectorSize();
        return (int) sector;
    }

    public Sector getLocationSector(StarSystems s, Location l) {
        int x = toSector(l.getX());
        int y = toSector(l.getY());
        int z = toSector(l.getZ());

        return s.getSector(x, y, z);
    }
}
