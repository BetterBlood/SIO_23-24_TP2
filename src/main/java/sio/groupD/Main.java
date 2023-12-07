/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.*;

public final class Main {
    /**
     * Contains metadata for a TSP problem
     *
     * @param name          filename of a file containing the TSP data
     * @param optimalLength optimal length for the TSP tour
     */
    private record TspMetaData(String name, int optimalLength) {
    }

    /**
     * Contains information on a TSP problem computed
     *
     * @param averageTime   average time taken to compute the TSP tour
     * @param bestTime      shortest time taken to compute the TSP tour
     * @param worstTime     longest time taken to compute the TSP tour
     * @param averageLength average length computed for a TSP problem
     * @param bestLength    shortest length computed for a TSP problem
     * @param worstLength   longest length computed for a TSP problem
     */
    private record TspObservation(float averageTime, long bestTime,
                                  long worstTime, float averageLength,
                                  long bestLength, long worstLength) {
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
                    TspTour tour = CONSTRUCTIVE_ALGORITHMS[0].computeTour(data, 0);
                    if (CONSTRUCTIVE_ALGORITHMS[i].getClass() != RandomTour.class) {
                        tour = CONSTRUCTIVE_ALGORITHMS[i].computeTour(data, tour.tour()[k]);
                    }

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

                // Computes average/best/worst time/length
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
                    }
                    if (times[k] > worstTime) {
                        worstTime = times[k];
                    }
                    if (lengths[k] < bestLength) {
                        bestLength = lengths[k];
                    }
                    if (lengths[k] > worstLength) {
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

    private static void printDetailedObservations(String formatString, TspMetaData metaData, TspObservation[][] observations) {
        for (int i = 0; i < CONSTRUCTIVE_ALGORITHMS.length; i++) {
            for (int j = 0; j < IMPROVEMENT_ALGORITHMS.length; j++) {
                System.out.format(formatString,
                        metaData.name,
                        CONSTRUCTIVE_ALGORITHMS[i].getClass().getSimpleName(),
                        IMPROVEMENT_ALGORITHMS[j].getClass().getSimpleName(),
                        observations[i][j].averageTime / NS_2_MS,
                        (float) observations[i][j].bestTime / NS_2_MS,
                        (float) observations[i][j].worstTime / NS_2_MS,
                        observations[i][j].averageLength / metaData.optimalLength,
                        (float) observations[i][j].bestLength / metaData.optimalLength,
                        (float) observations[i][j].worstLength / metaData.optimalLength);
            }
        }
    }

    private static void printAggregatedObservations(String formatString, TspObservation[][][] observations) {
        for (int j = 0; j < IMPROVEMENT_ALGORITHMS.length; j++) {
            for (int i = 0; i < CONSTRUCTIVE_ALGORITHMS.length; i++) {
                // Computes the average per metadata
                float averageTime = 0;
                float bestTime = 0;
                float worstTime = 0;
                float averageLength = 0;
                float bestLength = 0;
                float worstLength = 0;
                for (int k = 0; k < METADATA.length; k++) {
                    averageTime += observations[k][i][j].averageTime;
                    bestTime += observations[k][i][j].bestTime;
                    worstTime += observations[k][i][j].worstTime;
                    averageLength += observations[k][i][j].averageLength / METADATA[k].optimalLength;
                    bestLength += (float) observations[k][i][j].bestLength / METADATA[k].optimalLength;
                    worstLength += (float) observations[k][i][j].worstLength / METADATA[k].optimalLength;
                }

                averageTime /= METADATA.length;
                bestTime /= METADATA.length;
                worstTime /= METADATA.length;
                averageLength /= METADATA.length;
                bestLength /= METADATA.length;
                worstLength /= METADATA.length;

                System.out.format(formatString,
                        CONSTRUCTIVE_ALGORITHMS[i].getClass().getSimpleName(),
                        IMPROVEMENT_ALGORITHMS[j].getClass().getSimpleName(),
                        averageTime / NS_2_MS,
                        bestTime / NS_2_MS,
                        worstTime / NS_2_MS,
                        averageLength,
                        bestLength,
                        worstLength);
            }
        }
    }

    public static void main(String[] args) {
        String formatString = "| %-7s | %-25s | %-22s | %13f | %10f | %11f | %13f | %10f | %11f |%n";
        String line = "+---------+---------------------------+------------------------+---------------+------------+-------------+---------------+------------+-------------+%n";
        System.out.format("| file    | constructiveAlgorithm     | improvementAlgorithm   |   averageTime |   bestTime |   worstTime | averageLength | bestLength | worstLength |%n");
        System.out.format(line);

        TspObservation[][][] observations = new TspObservation[METADATA.length][CONSTRUCTIVE_ALGORITHMS.length][IMPROVEMENT_ALGORITHMS.length];
        TspData data;
        for (int i = 0; i < METADATA.length; i++) {
            try {
                data = TspData.fromFile("data/" + METADATA[i].name + ".dat");
            } catch (Exception e) {
                System.out.println("error reading file : " + e);
                return;
            }

            observations[i] = computeObservations(data);
            printDetailedObservations(formatString, METADATA[i], observations[i]);
        }
        System.out.format(line);
        System.out.println();

        formatString = "| %-25s | %-22s | %13f | %10f | %11f | %13f | %10f | %11f |%n";
        line = "+---------------------------+------------------------+---------------+------------+-------------+---------------+------------+-------------+%n";
        System.out.format("| constructiveAlgorithm     | improvementAlgorithm   |   averageTime |   bestTime |   worstTime | averageLength | bestLength | worstLength |%n");
        System.out.format(line);
        printAggregatedObservations(formatString, observations);
        System.out.format(line);
        System.out.println();
    }
}