package org.lentulus.traveller.StarmakerRelational.builders;

import org.lentulus.traveller.StarmakerRelational.model.Location;
import org.lentulus.traveller.StarmakerRelational.model.SystemPrimeData;
import org.lentulus.traveller.StarmakerRelational.model.support.Universe;

/**
 * Created by User on 8/29/2017.
 */
public class WorldBuilder {


    public SystemPrimeData makeSystemPrimeData(Location l) {
        SystemPrimeData systemPrimeData;
        int roll = DiceRoller.D100();
        if (roll < Universe.getBarrenPercent()) {
            systemPrimeData = new SystemPrimeData(SystemPrimeData.SystemType.BARREN);
        } else if (roll < (Universe.getBarrenPercent() + Universe.getHostilePercent())) {
            systemPrimeData = new SystemPrimeData(SystemPrimeData.SystemType.HOSTILE);
        } else if (roll < (Universe.getBarrenPercent() + Universe.getHostilePercent() + Universe.getMarginalPercent())) {
            systemPrimeData = new SystemPrimeData(SystemPrimeData.SystemType.MARGINAL);
        } else {
            systemPrimeData = new SystemPrimeData(SystemPrimeData.SystemType.GARDEN);
        }

        if (systemPrimeData.isGarden()) {
            if (DiceRoller.D100() <= Universe.getSapientPercent()) {
                systemPrimeData.initPopulation(l);
            }
        }

        return systemPrimeData;
    }
}
