package org.lentulus.traveller.StarmakerRelational.builders;

import lombok.extern.slf4j.Slf4j;
import org.lentulus.traveller.StarmakerRelational.model.Location;
import org.lentulus.traveller.StarmakerRelational.model.PathElement;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by User on 9/3/2017.
 */
@Slf4j
public class AStarClass {

/* NOTE about types: in the main article, in the Python code I just
 * use numbers for costs, heuristics, and priorities. In the C++ code
 * I use a typedef for this, because you might want int or double or
 * another type. In this C# code I use double for costs, heuristics,
 * and priorities. You can use an int if you know your values are
 * always integers, and you can use a smaller size number if you know
 * the values are always small. */


//        public Dictionary<Location, Location> cameFrom
//                = new Dictionary<Location, Location>();

        private Map<Location, Location> cameFrom = new HashMap<>();

//        public Dictionary<Location, double> costSoFar
//                = new Dictionary<Location, double>();

        private Map<Location, Double> costSoFar = new HashMap<>();

        // Note: a generic version of A* would abstract over Location and
        // also Heuristic
        static public double Heuristic(Location a, Location b)
        {
            return a.distanceTo(b);
        }

        public void logPath(Location start, Location destination){
            Location current = destination;
            int elements = 0;
            do {
                log.info("index {}, key {} {}, travelled {}",elements++, current.getKey(), current.getSectorCoordinates(), costSoFar.get(current));
                current = cameFrom.get(current);

            } while(!start.equals(current));
        }

        public boolean aStarSearch(WeightedGraph graph, Location start, Location goal, double maxJump)
        {
            long startTime = System.currentTimeMillis();
            log.info("Starting search");
            PriorityQueue<PathElement> frontier = new PriorityQueue<>();
            frontier.add(new PathElement(start, 0d));

            cameFrom.put(start,start);
            costSoFar.put(start, 0d);

            boolean foundDestination =false;
            while (frontier.size() > 0)
            {
                Location current = frontier.poll().getThisElement();
//                log.info("Frontier has {} elements looking at {} {}, goal is {} {} at {} centiparsecs",
//                        frontier.size(),
//                        current.getKey(),current.getSectorCoordinates(),
//                        goal.getKey(),current.getSectorCoordinates(),
//                        current.distanceTo(goal));
                if (current.equals(goal))
                {
                    log.info("Found destination");
                    foundDestination = true;
                    break;
                }

                for (Location next: graph.getNeighbors(current, maxJump))
                {

                    double newCost = costSoFar.get(current)
                            + graph.Cost(current, next);
                    if (!costSoFar.containsKey(next)
                            || newCost < costSoFar.get(next))
                    {
                        costSoFar.put(next, newCost);
                        double priority = newCost + Heuristic(next, goal);
                        frontier.add(new PathElement(next, priority));
                        cameFrom.put(next, current);
                    }
                }


            }
            long endTime = System.currentTimeMillis();
            log.info("*** Found:{} searching {} nodes in {} milliseconds",foundDestination, cameFrom.size(), endTime - startTime);
            return foundDestination;
        }

}

