/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspImprovementHeuristic;
import sio.tsp.TspTour;

/**
 * Class implementing the 2-Opt Swap First Improvement algorithm
 */
public final class TwoOptFirstImprovement implements TspImprovementHeuristic {

    /**
     * <p>Computes a tour for the travelling salesman problem by improving the given tour.</p>
     *
     * <p>No guarantee is given as to the optimality of the resulting tour.</p>
     *
     * @param tspTour Existing
     * @return Solution found by the heuristic
     * @throws NullPointerException if {@code tour} is null
     */
    @Override
    public TspTour computeTour(TspTour tspTour) throws NullPointerException {
        if (tspTour == null) {
            throw new NullPointerException();
        }

        int nbCities = tspTour.data().getNumberOfCities();
        int[] tour = tspTour.tour();
        long length = tspTour.length();
        TspUtils tspUtils = new TspUtils(tspTour.data());

        boolean hasSwapped;
        do {
            hasSwapped = false;
            tspUtils.setTour(tour);

            // Iteration starts at 2 as the cases of 0 and 1 do not enter the second for loop
            for (int i = 2; i < nbCities; ++i) {
                // We only iterate until i as to avoid the cases:
                // - (j,i) is equivalent to (i,j)
                // - (i,i) causes errors
                // Iteration ends at i - 1 as the swap (i = j + 1, i) is useless
                int nextI = (i + 1) % nbCities;
                int distanceI = tspUtils.getDistance(i, nextI);
                for (int j = 0; j < i - 1; ++j) {
                    // The case (i, j = i + 1) is useless thus skipped
                    if (nextI == j) {
                        continue;
                    }

                    // Compute the distance to gain from a swap (i,j). If it is an improving swap, we swap.
                    int distanceGained = distanceI + tspUtils.getDistance(j, j + 1) - tspUtils.getDistance(i, j) - tspUtils.getDistance(nextI, j + 1);
                    if (distanceGained <= 0) {
                        continue;
                    }

                    hasSwapped = true;
                    tour = tspUtils.swap(i, j);
                    length -= distanceGained;
                    distanceI = tspUtils.getDistance(i, nextI);
                }
            }
        } while (hasSwapped);

        return new TspTour(tspTour.data(), tour, length);
    }
}
