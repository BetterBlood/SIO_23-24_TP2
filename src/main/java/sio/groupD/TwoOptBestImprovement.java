/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspImprovementHeuristic;
import sio.tsp.TspTour;

public final class TwoOptBestImprovement implements TspImprovementHeuristic {

    @Override
    public TspTour computeTour(TspTour tspTour) {
        int[] tour = tspTour.tour();
        int nbCities = tspTour.data().getNumberOfCities();
        long length = tspTour.length();
        Utils utils = new Utils(tspTour);

        boolean hasSwapped;
        do {
            hasSwapped = false;
            int[] bestSwap = new int[2];
            int bestDistanceGained = Integer.MAX_VALUE;
            utils.setTour(tour);

            // TODO refactor
            for (int i = 0; i < nbCities; i++) {
                // We only iterate until i as to avoid the cases:
                // (j,i) is equivalent to (i,j), which is uselesss
                // (i,i) is useless
                for (int j = 0; j < i; j++) {
                    // (i, j = i + 1 ) is useless as it changes the direction of the turn and nothing else
                    if (tour[j] == tour[(i + 1) % nbCities]) {
                        continue;
                    }

                    // Calculate the distance to gain from a swap (i,j). If it is a better improving swap than current best, we save it.
                    int distanceGained = utils.getGainedDistance(i, j);
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
                tour = utils.swapAllBetween(bestSwap[0], bestSwap[1]);

                // Decrements length based on difference between previousDistance and newDistance
                length -= bestDistanceGained;
            }
        } while (hasSwapped);

        return new TspTour(tspTour.data(), tour, length);
    }
}
