/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.*;

public final class Main {
    /**
     * Record contenant les méta informations pour un problème TSP
     *
     * @param name          le nom d'un fichier contenant les données du problème
     * @param optimalLength la longueur optimale correspondant aux données du problème
     */
    private record TspMetaData(String name, int optimalLength) {
    }

    private record TspObservation(float averageTime, long bestTime, long worstTime, float averageLength, long bestLength, long worstLength) {
    }

    private static final int NS_2_MS = 1000000;
    private static final long SEED = 0x134B3BD;
    private static final int NB_TRIES = 10;
    private static final TspConstructiveHeuristic[] CONSTRUCTIVE_ALGORITHMS = {
            //new RandomTour(SEED),
            new NearestNeighbor(),
            new DoubleEndsNearestNeighbor(),
    };
    private static final TspImprovementHeuristic[] IMPROVEMENT_ALGORITHMS = {
            new TwoOptFirstImprovement(),
            new TwoOptBestImprovement(),
    };

    private static final TspMetaData[] METADATA = {
            new TspMetaData("att532", 86729),
            new TspMetaData("rat575", 6773),
            new TspMetaData("u574", 36905),
            /*
            new TspMetaData("rl1889", 316536),
            new TspMetaData("u1817", 57201),
            new TspMetaData("vm1748", 336556),
            */
            new TspMetaData("carre", 396)
    };

    private static TspObservation[][] computeObservations(TspData data) {
        TspObservation[][] observations = new TspObservation[CONSTRUCTIVE_ALGORITHMS.length][IMPROVEMENT_ALGORITHMS.length];
        for (int i = 0; i < CONSTRUCTIVE_ALGORITHMS.length; i++) {
            for (int j = 0; j < IMPROVEMENT_ALGORITHMS.length; j++) {
                long[] times = new long[NB_TRIES];
                long[] lengths = new long[NB_TRIES];
                for (int k = 0; k < NB_TRIES; ++k) {
                    TspTour tour = CONSTRUCTIVE_ALGORITHMS[i].computeTour(data, k);
                    long start = System.nanoTime();
                    try {
                        tour = IMPROVEMENT_ALGORITHMS[j].computeTour(tour);
                    } catch (NullPointerException e) {
                        System.out.println("error improving file : " + e);
                    }

                    long end = System.nanoTime();
                    times[k] = end - start;
                    lengths[k] = tour.length();
                }

                float averageTime = 0;
                long bestTime = Long.MAX_VALUE;
                long worstTime = 0;
                float averageLength = 0;
                long bestLength = Long.MAX_VALUE;
                long worstLength = 0;
                for (int k = 0; k < NB_TRIES; k++) {
                    averageTime += times[k];
                    averageLength += lengths[k];

                    if (times[k] < bestTime) {
                        bestTime = times[k];
                    } else if (times[k] > worstTime) {
                        worstTime = times[k];
                    }

                    if (lengths[k] < bestLength) {
                        bestLength = lengths[k];
                    } else if (lengths[k] > worstLength) {
                        worstLength = lengths[k];
                    }
                }

                averageTime /= NB_TRIES;
                averageLength /= NB_TRIES;

                observations[i][j] = new TspObservation(averageTime, bestTime, worstTime, averageLength, bestLength, worstLength);
            }
        }
        return observations;
    }

    private static void printObservations(String formatString, String file, TspObservation[][] observations) {
        for (int i = 0; i < CONSTRUCTIVE_ALGORITHMS.length; i++) {
            for (int j = 0; j < IMPROVEMENT_ALGORITHMS.length; j++) {
                System.out.format(formatString,
                        file,
                        CONSTRUCTIVE_ALGORITHMS[i].getClass().getSimpleName(),
                        IMPROVEMENT_ALGORITHMS[j].getClass().getSimpleName(),
                        observations[i][j].averageTime / NS_2_MS,
                        observations[i][j].bestTime / NS_2_MS,
                        observations[i][j].worstTime / NS_2_MS,
                        observations[i][j].averageLength,
                        observations[i][j].bestLength,
                        observations[i][j].worstLength);
            }
        }
    }

    public static void main(String[] args) {
        String formatString = "| %-7s | %-25s | %-22s | %13f | %10d | %11d | %13f | %10d | %11d |%n";
        String line =       "+---------+---------------------------+------------------------+---------------+------------+-------------+---------------+------------+-------------+%n";
        System.out.format(  "| file    | constructiveAlgorithm     | improvementAlgorithm   |   averageTime |   bestTime |   worstTime | averageLength | bestLength | worstLength |%n");
        System.out.format(line);

        TspData data = null;
        for (TspMetaData metaData : METADATA) {
            try {
                data = TspData.fromFile("data/" + metaData.name + ".dat");
            } catch (Exception e) {
                System.out.println("error reading file : " + e);
            }

            TspObservation[][] observations = computeObservations(data);
            printObservations(formatString, metaData.name, observations);
        }

        System.out.format(line);
    }
}