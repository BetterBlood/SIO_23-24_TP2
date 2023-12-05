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

    private int getDistanceModulo(int i) {
        return tspData.getDistance(tour[i], tour[(i + 1) % nbCities]);
    }

    private int getDistanceModulo(int i, int j) {
        return tspData.getDistance(tour[i % nbCities], tour[j % nbCities]);
    }

    private int getDistance(int i, int j) {
        return tspData.getDistance(tour[i], tour[j]);
    }

    private int getPreviousDistance(int i, int j) {
        return getDistanceModulo(i) + getDistanceModulo(j);
    }

    private int getSwappedDistance(int i, int j) {
        return getDistance(i, j) + getDistanceModulo(i + 1, j + 1);
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
     * Gets the distance gained by doing a swap (i,j)
     *
     * @param i must be larger than j
     * @param j must be smaller than i
     * @return the distance gained by the swap. Can be negative, in this case this is not an improving swap
     */
    public int getGainedDistance(int i, int j) {
        return getPreviousDistance(i, j) - getSwappedDistance(i, j);
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
            int sw = tour[(i + nbCities) % nbCities];
            tour[(i + nbCities) % nbCities] = tour[(j + nbCities) % nbCities];
            tour[(j + nbCities) % nbCities] = sw;
            i -= incr;
            j += incr;
        }

        return tour;
    }

}
