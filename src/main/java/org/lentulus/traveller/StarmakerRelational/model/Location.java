package org.lentulus.traveller.StarmakerRelational.model;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by User on 8/5/2017.
 */

@Getter
public class Location implements Serializable{
    @Setter
    @Getter
    private SystemPrimeData systemPrimeData;

   // all in centiparsecs

    private long x;
    private long y;
    private long z;

    private long key;


    public Location(double x, double y, double z) {
        this.x = LocationUtils.toCentiparsecs(x);
        this.y = LocationUtils.toCentiparsecs(y);
        this.z = LocationUtils.toCentiparsecs(z);

        this.key = LocationUtils.encodeKey(this);

    }

    public Long getKey() {
        return key;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    public double distanceTo(Location location) {
        final double distanceX = this.x - location.getX();
        final double distanceY = this.y - location.getY();
        final double distanceZ = this.z - location.getZ();

        final double distSquare
                = distanceX * distanceX
                + distanceY * distanceY
                + distanceZ * distanceZ;

        return Math.sqrt(distSquare);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location location = (Location) o;
        return getKey() == location.getKey();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey());
    }

    public String getSectorCoordinates(){
        int x = LocationUtils.toSector(getX());
        int y = LocationUtils.toSector(getY());
        int z = LocationUtils.toSector(getZ());
        return String.format("S:[%d][%d][%d]",x,y,z);

    }
}
