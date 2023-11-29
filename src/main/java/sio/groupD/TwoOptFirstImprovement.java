/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspImprovementHeuristic;
import sio.tsp.TspTour;

public final class TwoOptFirstImprovement implements TspImprovementHeuristic {

    @Override
    public TspTour computeTour(TspTour tspTour) {
        int nbCities = tspTour.data().getNumberOfCities();
        int[] tour = tspTour.tour();
        long length = tspTour.length();
        Utils utils = new Utils(tspTour);

        boolean hasSwapped;
        do {
            hasSwapped = false;
            utils.setTour(tour);

            for (int i = 0; i < nbCities; i++) {
                // We only iterate until i as to avoid the cases:
                // (j,i) is equivalent to (i,j), which is useless
                // (i,i) is useless
                for (int j = 0; j < i; j++) {
                    // (i, j = i + 1) is useless as it changes the direction of the turn and nothing else
                    if (tour[j] == tour[(i + 1) % nbCities]) {
                        continue;
                    }

                    // Calculate the distance to gain from a swap (i,j). If it is an improving swap, we swap.
                    int distanceGained = utils.getGainedDistance(i, j);
                    if (distanceGained <= 0) {
                        continue;
                    }

                    hasSwapped = true;

                    // nbCities + i + j are the total of items to swap. if it is odd, the last iteration can be skipped.
                    //TODO improve algorithm by swapping the smaller part only ? (i, j) = (j, i) but one may be smaller (less swaps) thus faster
                    tour = utils.swapAllBetween(i, j);

                    // Decrements length based on difference between previousDistance and newDistance
                    length -= distanceGained;

                    // Break out of the second for loop
                    break;
                }

                // Break out of the first for loop
                if (hasSwapped) {
                    break;
                }
            }
        } while (hasSwapped);

        return new TspTour(tspTour.data(), tour, length);
    }
}
