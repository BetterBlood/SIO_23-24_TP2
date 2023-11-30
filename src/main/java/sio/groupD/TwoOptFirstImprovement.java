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

            for (int i = 1; i < nbCities; i++) {
                // We only iterate until i as to avoid the cases:
                // - (j,i) is equivalent to (i,j)
                // - (i,i) causes errors
                for (int j = 0; j < i; j++) {
                    if (utils.isSwapUseless(i, j)) {
                        continue;
                    }

                    // Calculate the distance to gain from a swap (i,j). If it is an improving swap, we swap.
                    int distanceGained = utils.getGainedDistance(i, j);
                    if (distanceGained <= 0) {
                        continue;
                    }

                    hasSwapped = true;
                    tour = utils.swap(i, j);
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
