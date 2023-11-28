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
    private record TspMetaData(String name, int optimalLength) { }

    private static final int NS_2_MS = 1000000;
    private static final TspConstructiveHeuristic[] CONSTRUCTIVE_ALGORITHMS = {new RandomTour(), new NearestNeighbor(), new DoubleEndsNearestNeighbor()};
    private static final TspImprovementHeuristic[] IMPROVEMENT_ALGORITHMS = {new TwoOptFirstImprovement(), new TwoOptBestImprovement()};

    private static final TspMetaData[] METADATA = {
            new TspMetaData("att532", 86729),
            new TspMetaData("rat575", 6773),
            new TspMetaData("rl1889", 316536),
            new TspMetaData("u574", 36905),
            new TspMetaData("u1817", 57201),
            new TspMetaData("vm1748", 336556),
            new TspMetaData("carre", 396),
    };

    public static void main(String[] args) {
        // TODO
        //  - Implémentation des classes TwoOptBestImprovement et TwoOptFirstImprovement ;
        //  - Documentation abondante des classes comprenant :
        //    - la javadoc, avec auteurs et description des implémentations ;
        //    - des commentaires sur les différentes parties de vos algorithmes.

        TspData data = null;
        try {
            data = TspData.fromFile("data/" + METADATA[6].name + ".dat");
            System.out.println("computing " + METADATA[6].name);
        } catch (Exception e) {
            System.out.println("error reading file : " + e);
        }

        TspTour tour = CONSTRUCTIVE_ALGORITHMS[0].computeTour(data, 0);
        System.out.println("length before is " + tour.length());
        tour = IMPROVEMENT_ALGORITHMS[0].computeTour(tour);
        System.out.println("length after is " + tour.length());
    }
}