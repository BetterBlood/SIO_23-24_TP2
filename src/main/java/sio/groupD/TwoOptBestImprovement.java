/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspImprovementHeuristic;
import sio.tsp.TspTour;

/**
 * Class implementing the 2-Opt Swap Best Improvement algorithm
 */
public final class TwoOptBestImprovement implements TspImprovementHeuristic {

    /**
     * <p>Computes a tour for the travelling salesman problem by improving the given tour.</p>
     *
     * <p>No guarantee is given as to the optimality of the resulting tour.</p>
     *
     * @param tspTour Existing
     *
     * @return Solution found by the heuristic
     * @throws NullPointerException if {@code tour} is null
     */
    @Override
    public TspTour computeTour(TspTour tspTour) throws NullPointerException {
        if (tspTour == null) {
            throw new NullPointerException();
        }

        int[] tour = tspTour.tour();
        int nbCities = tspTour.data().getNumberOfCities();
        long length = tspTour.length();
        TspUtils tspUtils = new TspUtils(tspTour.data());

        boolean hasSwapped;
        do {
            hasSwapped = false;
            int[] bestSwap = new int[2];
            int bestDistanceGained = 0;
            tspUtils.setTour(tour);

            for (int i = 1; i < nbCities; i++) {
                // We only iterate until i as to avoid the cases:
                // - (j,i) is equivalent to (i,j)
                // - (i,i) causes errors
                for (int j = 0; j < i; j++) {
                    if (tspUtils.isSwapUseless(i, j)) {
                        continue;
                    }

                    // Calculate the distance to gain from a swap (i,j).
                    // If it is a better improving swap than current best, we save it.
                    int distanceGained = tspUtils.getGainedDistance(i, j);
                    if (distanceGained <= bestDistanceGained) {
                        continue;
                    }

                    hasSwapped = true;
                    bestSwap[0] = i;
                    bestSwap[1] = j;
                    bestDistanceGained = distanceGained;
                }
            }

            if (hasSwapped) {
                tour = tspUtils.swap(bestSwap[0], bestSwap[1]);
                length -= bestDistanceGained;
            }
        } while (hasSwapped);

        return new TspTour(tspTour.data(), tour, length);
    }
}
