package org.lentulus.traveller.StarmakerRelational.builders;

import org.lentulus.traveller.StarmakerRelational.model.Location;
import org.lentulus.traveller.StarmakerRelational.model.LocationUtils;
import org.lentulus.traveller.StarmakerRelational.model.support.StarSystems;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by User on 8/6/2017.
 */
public class UniformLocationSource implements LocationSource {
    private double maxX;
    private double maxY;
    private double maxZ;

    private double displaceX = 0;
    private double displaceY = 0;
    private double displaceZ = 0;

    private long generateCount;

    private double countUncertainty;

    public UniformLocationSource(double maxX, double maxY, double maxZ, double density, double countUncertainty) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        double volume = maxX * maxY * maxZ;

        this.generateCount = (long) (volume * density);

        this.countUncertainty = countUncertainty;
    }

    @Override
    public void generate(StarSystems starSystems) {
        long allGen = generateCount + (long) ((double) generateCount * ThreadLocalRandom.current().nextDouble(0, countUncertainty));
        while (allGen > 0) {
            if (starSystems.addLocation(new Location(LocationUtils.randAxis(maxX) + displaceX,
                    LocationUtils.randAxis(maxY) + displaceY,
                    LocationUtils.randAxis(maxZ) + displaceZ))) {
                allGen--;
            }
        }
    }

    @Override
    public void setDisplace(double displaceX, double displaceY, double displaceZ) {
        this.displaceZ = displaceZ;
        this.displaceY = displaceY;
        this.displaceX = displaceX;
    }
}
