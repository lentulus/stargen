package org.lentulus.traveller.StarmakerRelational;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.lentulus.traveller.StarmakerRelational.builders.*;
import org.lentulus.traveller.StarmakerRelational.model.Location;
import org.lentulus.traveller.StarmakerRelational.model.PathElement;
import org.lentulus.traveller.StarmakerRelational.model.SystemPrimeData;
import org.lentulus.traveller.StarmakerRelational.model.support.StarSystems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;

@SpringBootApplication

@Slf4j
public class start implements CommandLineRunner {

    private StarSystems starSystems;

    @Autowired
    private PathMaker pathMaker;

    public static void main(String[] args) {
        SpringApplication.run(start.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Options ops = new Options();
        ops.addOption(new Option("v",false,"Version"));
        ops.addOption(new Option("save",true,"File name"));



        StarSystems starSystems = generateStarSystems();
       // starSystems.makeLinks();
        List<Location> startEndStars = starSystems.getGardens(0, 0, 0);
        List<Location> stopEndStars = starSystems.getGardens(19, 9, 9);

        Optional<PathElement> start = pathMaker.path(starSystems, startEndStars.get(0), stopEndStars.get(0));
    }

    private StarSystems generateStarSystems() {
        StarSystems starSystems = new StarSystems();
        starSystems.setBounds(200d, 100d, 100d, 10);
        LocationSource mainLocationSource = new UniformLocationSource(200d, 100d, 100d, .14d, .5d);
        mainLocationSource.generate(starSystems);

        LocationSource clusterLocationSource = new ClusterLocationSource(10d, 100, .5d);
        clusterLocationSource.setDisplace(150d, 50d, 50d);
        clusterLocationSource.generate(starSystems);

        log.info("Generated {} stars ", starSystems.size());
        starSystems.makeWorlds(new WorldBuilder());

        log.info("** BARREN:{}", starSystems.countType(SystemPrimeData.SystemType.BARREN));
        log.info("** HOSTILE:{}", starSystems.countType(SystemPrimeData.SystemType.HOSTILE));
        log.info("** MARGINAL:{}", starSystems.countType(SystemPrimeData.SystemType.MARGINAL));
        log.info("** GARDEN:{}", starSystems.countType(SystemPrimeData.SystemType.GARDEN));
        log.info("== N by TL");
        for(int i= 0; i < 13; i++){
            log.info("== TL{} count: {}", i, starSystems.countLifeByTech(i) );
        }

        return starSystems;
    }
}
