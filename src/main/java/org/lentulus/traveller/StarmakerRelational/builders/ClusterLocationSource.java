package org.lentulus.traveller.StarmakerRelational.builders;

import org.lentulus.traveller.StarmakerRelational.model.Location;
import org.lentulus.traveller.StarmakerRelational.model.support.StarSystems;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by User on 8/28/2017.
 */
public class ClusterLocationSource implements LocationSource {
    private double radius;
    private double displaceX;
    private double displaceY;
    private double displaceZ;
    private int generateCount;
    private double countUncertainty;

    public ClusterLocationSource(double radius, int generateCount, double countUncertainty) {
        this.radius = radius;
        this.generateCount = generateCount;
        this.countUncertainty = countUncertainty;
    }

    @Override
    public void generate(StarSystems starSystems) {
        long allGen = generateCount + (long) ((double) generateCount * ThreadLocalRandom.current().nextDouble(0, countUncertainty));
        while (allGen > 0) {

            if (starSystems.addLocation(genOneLocation())) {
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

    private Location genOneLocation() {
        double randRad = ThreadLocalRandom.current().nextGaussian();
        randRad *= radius;
        double randRadSqure = randRad * randRad;

        final double aSquare = ThreadLocalRandom.current().nextDouble(randRadSqure);
        randRadSqure -= aSquare;
        final double bSquare = ThreadLocalRandom.current().nextDouble(randRadSqure);
        final double cSquare = randRadSqure - bSquare;
        List<Double> randoms = new ArrayList<>();
        randoms.add(aSquare);
        randoms.add(bSquare);
        randoms.add(cSquare);
        final double x = displaceX + scramble(randoms);
        final double y = displaceY + scramble(randoms);
        final double z = displaceZ + scramble(randoms);

        return new Location(x, y, z);
    }

    private double scramble(List<Double> randoms) {
        final double sign = (ThreadLocalRandom.current().nextInt(0, 1) == 0) ? -1d : 1d;

        if (randoms.size() == 1) {
            return sign * randoms.get(0);
        } else {
            final int index = ThreadLocalRandom.current().nextInt(0, randoms.size() - 1);
            final double retval = randoms.get(index) * sign;
            randoms.remove(index);
            return retval;
        }
    }
}
