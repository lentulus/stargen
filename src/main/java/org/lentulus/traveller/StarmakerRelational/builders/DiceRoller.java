package org.lentulus.traveller.StarmakerRelational.builders;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by User on 8/29/2017.
 */
public class DiceRoller {
    static int D100() {
        return ThreadLocalRandom.current().nextInt(1, 100);
    }
}
