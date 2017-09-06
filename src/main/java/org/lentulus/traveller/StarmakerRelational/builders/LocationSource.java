package org.lentulus.traveller.StarmakerRelational.builders;

import org.lentulus.traveller.StarmakerRelational.model.support.StarSystems;

/**
 * Created by User on 8/27/2017.
 */
public interface LocationSource {
    void generate(StarSystems starSystems);

    void setDisplace(double displaceX, double displaceY, double displaceZ);
}
