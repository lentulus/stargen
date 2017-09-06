package org.lentulus.traveller.StarmakerRelational.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by User on 8/26/2017.
 */
public class SystemPrimeData implements Serializable {
    @Getter
    private SystemType systemType;
    @Getter
    private Optional<Population> population;

    public SystemPrimeData(SystemType systemType) {
        this.systemType = systemType;
        this.population = Optional.empty();
    }

    public boolean isGarden() {
        return SystemType.GARDEN.equals(systemType);
    }

    public void initPopulation(Location l) {
        this.population = Optional.of(new Population(l.getKey(), randomStartTech()));
    }

    private int randomStartTech() {

        int tl = 0;
        do {
            double range = Math.abs(ThreadLocalRandom.current().nextGaussian());
            tl = (int) (range * 6d);
        } while (tl > 13);

        return tl;
    }

    public boolean isType(SystemType systemType) {
        return systemType.equals(this.systemType);
    }

    public enum SystemType {
        HOSTILE,
        BARREN,
        MARGINAL,
        GARDEN
    }


}
