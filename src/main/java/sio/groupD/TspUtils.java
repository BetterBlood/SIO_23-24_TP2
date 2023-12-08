/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspData;

/**
 * Class offering various methods to compute or manipulate a TspTour.
 */
public class TspUtils {
    private final TspData tspData;
    private int[] tour;
    private int nbCities;

    /**
     * Utils Constructor
     *
     * @param tspData the data of the TspTour to analyze
     */
    public TspUtils(TspData tspData) {
        this.tspData = tspData;
    }

    private int getDistance(int i, int j) {
        return tspData.getDistance(tour[i], tour[j]);
    }

    /**
     * Computes the distance [i, i + 1] while applying modulo
     *
     * @param i the index of a city whose distance with i + 1 is computed
     * @return the distance [i, i + 1]
     */
    public int getDistanceSafe(int i) {
        return getDistance(i, (i + 1) % nbCities);
    }

    /**
     * Computes the distance [i, i + 1] without applying modulo for better performances
     * Make sure i is not equal to tour.length - 1 else it will crash
     *
     * @param i the index of a city whose distance with i + 1 is computed
     * @return the distance [i, i + 1]
     */
    public int getDistanceUnsafe(int i) {
        return getDistance(i, i + 1);
    }

    /**
     * Computes the complementary distance gained by a swap (i, j)
     * Make sure j is not equal to tour.length - 1 else it will crash
     *
     * @param i the index of a city whose distance [i, i + 1] is already computed
     * @param j the index of a city whose distance with i remains to be computed
     * @return the distance gained by a swap (i, j) minus the distance [i, i + 1]
     */
    public int getGainedDistanceComplementary(int i, int j) {
        return getDistanceUnsafe(j) - (getDistance(i, j) + getDistance((i + 1) % nbCities, j + 1));
    }

    /**
     * Initialize the utils class with a tour.
     * Must be used before using other functions else data will refer to that previous tour.
     *
     * @param tour an array of int representing a symmetric tour
     */
    public void setTour(int[] tour) {
        this.tour = tour;
        this.nbCities = tour.length;
    }

    /**
     * Checks that the swap (i,j) is not useless.
     * The case (i, j = i + 1) is considered useless.
     * Other special cases must be treated accordingly.
     *
     * @param i must be larger than j
     * @param j must be smaller than i
     * @return whether the swap is useless, or not
     */
    public boolean isSwapUseless(int i, int j) {
        return j == (i + 1) % nbCities;
    }

    /**
     * Swaps all elements between indexes i + 1 and j to realize the swap (i,j)
     * If the swap (j,i) is more efficient to do, it will be done instead of (i,j)
     *
     * @param i must be larger than j
     * @param j must be smaller than i
     * @return the symmetric tour as an array of int
     */
    public int[] swap(int i, int j) {
        boolean normalSwap = i - j < nbCities - i + j;
        int incr = normalSwap ? 1 : -1;
        int end = normalSwap ? 0 : nbCities;
        j = normalSwap ? j + 1 : j;
        i = normalSwap ? i : i + 1;

        while (incr * (i - j) + end > 0) {
            int tmp = tour[(i + nbCities) % nbCities];
            tour[(i + nbCities) % nbCities] = tour[(j + nbCities) % nbCities];
            tour[(j + nbCities) % nbCities] = tmp;
            i -= incr;
            j += incr;
        }

        return tour;
    }
}
