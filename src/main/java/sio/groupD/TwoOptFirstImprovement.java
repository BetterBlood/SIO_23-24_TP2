/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspImprovementHeuristic;
import sio.tsp.TspTour;

public final class TwoOptFirstImprovement implements TspImprovementHeuristic {
    private interface Utils {
        int getDistanceModulo(int i);

        int getDistanceModulo(int i, int j);

        int getDistance(int i, int j);
    }

    @Override
    public TspTour computeTour(TspTour tspTour) {
        int nbCities = tspTour.data().getNumberOfCities();
        int[] tour = tspTour.tour();
        long length = tspTour.length();

        Utils utils = new Utils() {
            public int getDistanceModulo(int i) {
                return tspTour.data().getDistance(tour[i], tour[(i + 1) % nbCities]);
            }

            public int getDistanceModulo(int i, int j) {
                return tspTour.data().getDistance(tour[i % nbCities], tour[j % nbCities]);
            }

            public int getDistance(int i, int j) {
                return tspTour.data().getDistance(tour[i], tour[j]);
            }
        };

        boolean hasSwapped;
        do {
            hasSwapped = false;
            for (int i = 0; i < nbCities; i++) {
                // We only iterate until i as to avoid the cases:
                // (j,i) is equivalent to (i,j), which is useless
                // (i,i) is useless
                for (int j = 0; j < i; j++) {
                    // (i, j = i + 1 ) is useless as it changes the direction of the turn and nothing else
                    if (tour[j] == tour[(i + 1) % nbCities]) {
                        continue;
                    }

                    int previousDistance = utils.getDistanceModulo(i) + utils.getDistanceModulo(j);
                    int newDistance = utils.getDistance(i, j) + utils.getDistanceModulo(i + 1, j + 1);
                    if (newDistance < previousDistance) {
                        hasSwapped = true;

                        // nbCities + i + j are the total of items to swap. if it is odd, the last iteration can be skipped.
                        //TODO improve algorithm by swapping the smaller part only ? (i, j) = (j, i) but one may be smaller (less swaps) thus faster
                        int totalSwap = (nbCities - i + j) / 2;
                        for (int k = 0; k < totalSwap; k++) {
                            int indexI = (k + i + 1) % nbCities;
                            int indexJ = (j - k + nbCities) % nbCities;
                            int tmp = tour[indexI];
                            tour[indexI] = tour[indexJ];
                            tour[indexJ] = tmp;
                        }

                        // Decrements length based on difference between previousDistance and newDistance
                        length -= previousDistance - newDistance;

                        //TODO break pre emptively from the two for (how ?) since the do while will be executed anyway afterwards ? not sure if better algorithmic wise
                    }
                }
            }
        } while (hasSwapped);

        return new TspTour(tspTour.data(), tour, length);
    }
}
