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

        // TODO store as a Stack all bestSwap/bestDistanceGained and then pop them all ?
        // (except if one of them has been altered, meaning, its distanceGained no longer is equal to bestDistanceGained)

        boolean hasSwapped;
        do {
            hasSwapped = false;
            int[] bestSwap = new int[2];
            int bestDistanceGained = Integer.MAX_VALUE;
            utils.setTour(tour);

            for (int i = 1; i < nbCities; i++) {
                // We only iterate until i as to avoid the cases:
                // - (j,i) is equivalent to (i,j)
                // - (i,i) causes errors
                for (int j = 0; j < i; j++) {
                    if (utils.isSwapUseless(i, j)) {
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
                tour = utils.swap(bestSwap[0], bestSwap[1]);
                length -= bestDistanceGained;
            }
        } while (hasSwapped);

        return new TspTour(tspTour.data(), tour, length);
    }
}
