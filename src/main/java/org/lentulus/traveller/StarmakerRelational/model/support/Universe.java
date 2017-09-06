package org.lentulus.traveller.StarmakerRelational.model.support;

import lombok.Getter;

/**
 * Created by User on 8/30/2017.
 */
public class Universe {
    @Getter
    static private double maxDistanceCentiparsecs = 1000;

    @Getter
    static private int sectorSize = 10;

    @Getter
    static private int barrenPercent = 60;

    @Getter
    static private int hostilePercent = 20;

    @Getter
    static private int marginalPercent = 15;

    @Getter
    static private int sapientPercent = 25;
}
