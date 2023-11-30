/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class Main {
    /**
     * Record contenant les méta informations pour un problème TSP
     *
     * @param name          le nom d'un fichier contenant les données du problème
     * @param optimalLength la longueur optimale correspondant aux données du problème
     */
    private record TspMetaData(String name, int optimalLength) {
    }

    private static final int NS_2_MS = 1000000;
    private static final long SEED = 0x134B3BD;
    private static final int NB_TRIES = 10;
    private static final TspConstructiveHeuristic[] CONSTRUCTIVE_ALGORITHMS = {
            new RandomTour(SEED),
            //new NearestNeighbor(),
            //new DoubleEndsNearestNeighbor(),
    };
    private static final TspImprovementHeuristic[] IMPROVEMENT_ALGORITHMS = {
            //new TwoOptFirstImprovement(),
            new TwoOptBestImprovement(),
    };

    private static final TspMetaData[] METADATA = {
            new TspMetaData("att532", 86729),
            /*new TspMetaData("rat575", 6773),
            new TspMetaData("u574", 36905),
            /*
            new TspMetaData("rl1889", 316536),
            new TspMetaData("u1817", 57201),
            new TspMetaData("vm1748", 336556),
            */
            //new TspMetaData("carre", 396),
    };

    public static void main(String[] args) {
        // TODO
        //  - Implémentation des classes TwoOptBestImprovement;
        //  - Documentation abondante des classes comprenant :
        //    - la javadoc, avec auteurs et description des implémentations ;
        //    - des commentaires sur les différentes parties de vos algorithmes.

        TspData data = null;
        for (TspMetaData metaData : METADATA) {
            try {
                System.out.println("reading " + metaData.name);
                data = TspData.fromFile("data/" + metaData.name + ".dat");
            } catch (Exception e) {
                System.out.println("error reading file : " + e);
            }

            for (TspConstructiveHeuristic constructiveAlgo : CONSTRUCTIVE_ALGORITHMS) {
                for (TspImprovementHeuristic improvementAlgo : IMPROVEMENT_ALGORITHMS) {
                    for (int i = 0; i < NB_TRIES; ++i)
                    {
                        //System.out.println("constructing with " + constructiveAlgo.getClass().getSimpleName());
                        System.out.println("constructing with " + constructiveAlgo.getClass().getSimpleName() + " index " + i);
                        TspTour tour = constructiveAlgo.computeTour(data, i);
                        System.out.println("length is \t\t\t" + tour.length());
                        System.out.println("improving with " + improvementAlgo.getClass().getSimpleName());
                        tour = improvementAlgo.computeTour(tour);
                        System.out.println("length is \t\t\t" + tour.length());
                        System.out.println("length should be \t" + metaData.optimalLength);
                    }
                }
            }

            System.out.println();
        }
    }
}