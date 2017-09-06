package org.lentulus.traveller.StarmakerRelational.model;

import java.io.Serializable;

/**
 * Created by User on 8/29/2017.
 */
public class Population implements Serializable {
    public Long speciesKey;
    public Integer techLevel;

    public Population(Long species, int startTech) {
        this.speciesKey = species;
        this.techLevel = startTech;
    }
}
