/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspImprovementHeuristic;
import sio.tsp.TspTour;

public final class TwoOptBestImprovement implements TspImprovementHeuristic {
    //TODO redundant : refactor
    private interface Utils {
        int getPreviousDistance(int i, int j);

        int getSwappedDistance(int i, int j);
    }

    @Override
    public TspTour computeTour(TspTour tspTour) {
        int[] tour = tspTour.tour();
        int nbCities = tspTour.data().getNumberOfCities();
        long length = tspTour.length();

        //TODO refactor
        Utils utils = new Utils() {
            private int getDistanceModulo(int i) {
                return tspTour.data().getDistance(tour[i], tour[(i + 1) % nbCities]);
            }

            private int getDistanceModulo(int i, int j) {
                return tspTour.data().getDistance(tour[i % nbCities], tour[j % nbCities]);
            }

            private int getDistance(int i, int j) {
                return tspTour.data().getDistance(tour[i], tour[j]);
            }

            public int getPreviousDistance(int i, int j) {
                return getDistanceModulo(i) + getDistanceModulo(j);
            }

            public int getSwappedDistance(int i, int j) {
                return getDistance(i, j) + getDistanceModulo(i + 1, j + 1);
            }
        };

        boolean hasSwapped;
        do {
            hasSwapped = false;
            int[] bestSwap = new int[2];
            int bestDistanceGained = Integer.MAX_VALUE;

            for (int i = 0; i < nbCities; i++) {
                // We only iterate until i as to avoid the cases:
                // (j,i) is equivalent to (i,j), which is uselesss
                // (i,i) is useless
                for (int j = 0; j < i; j++) {
                    // (i, j = i + 1 ) is useless as it changes the direction of the turn and nothing else
                    if (tour[j] == tour[(i + 1) % nbCities]) {
                        continue;
                    }

                    // Calculate the distance to gain from a swap (i,j). If it is a better swap, it is saved.
                    int distanceGained = utils.getPreviousDistance(i, j) - utils.getSwappedDistance(i, j);
                    if (distanceGained >= bestDistanceGained || distanceGained <= 0) {
                        continue;
                    }

                    hasSwapped = true;
                    bestSwap[0] = i;
                    bestSwap[1] = j;
                    bestDistanceGained = distanceGained;
                }
            }

            if (hasSwapped) {
                //TODO refactor
                int totalSwap = (nbCities - bestSwap[0] + bestSwap[1]) / 2;
                for (int k = 0; k < totalSwap; k++) {
                    int indexI = (k + bestSwap[0] + 1) % nbCities;
                    int indexJ = (bestSwap[1] - k + nbCities) % nbCities;
                    int tmp = tour[indexI];
                    tour[indexI] = tour[indexJ];
                    tour[indexJ] = tmp;
                }

                // Decrements length based on difference between previousDistance and newDistance
                length -= bestDistanceGained;
            }
        } while (hasSwapped);

        return new TspTour(tspTour.data(), tour, length);
    }
}
