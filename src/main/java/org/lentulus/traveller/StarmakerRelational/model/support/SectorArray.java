package org.lentulus.traveller.StarmakerRelational.model.support;

import lombok.extern.slf4j.Slf4j;
import org.lentulus.traveller.StarmakerRelational.model.Location;
import org.lentulus.traveller.StarmakerRelational.model.LocationUtils;

import java.io.Serializable;

/**
 * Created by User on 8/19/2017.
 */
@Slf4j
public class SectorArray implements Serializable{
    int nx, ny, nz;
    private Sector[][][] cells;


    public SectorArray(int nx, int ny, int nz) {
        cells = new Sector[nx][][];
        for (int i = 0; i < nx; i++) {
            cells[i] = new Sector[ny][];
            for (int j = 0; j < ny; j++) {
                cells[i][j] = new Sector[nz];
                for (int k = 0; k < nz; k++) {
                    cells[i][j][k] = new Sector();
                }
            }
        }
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;

    }

    public boolean add(Location l) {
        Sector mySector = locationSector(l);
        if (mySector == null) {
            log.warn("null sector", l.getKey());
            return false;
        }
        mySector.add(l);
        return true;
    }

    private Sector locationSector(Location l) {
        final int x = LocationUtils.toSector(l.getX());
        final int y = LocationUtils.toSector(l.getY());
        final int z = LocationUtils.toSector(l.getZ());

        try {
            if (cells[x][y][z] == null) {
                cells[x][y][z] = new Sector();
            }
        } catch (Exception e) {
            return null;
        }
        return cells[x][y][z];
    }

    public boolean isOutside(Location l) {
        final int x = LocationUtils.toSector(l.getX());
        final int y = LocationUtils.toSector(l.getY());
        final int z = LocationUtils.toSector(l.getZ());

        return !inBounds(x, y, z);
    }


    public Sector getSector(int x, int y, int z) {
        return cells[x][y][z];
    }

    public boolean inBounds(int x, int y, int z) {
        if (x >= nx) return false;
        if (y >= ny) return false;
        if (z >= nz) return false;
        if (x < 0) return false;
        if (y < 0) return false;
        if (z < 0) return false;

        return true;
    }
}
