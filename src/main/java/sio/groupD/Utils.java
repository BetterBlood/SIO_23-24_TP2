/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspTour;

public class Utils {
    private TspTour tspTour;
    private int[] tour;
    private int nbCities;

    public Utils(TspTour tspTour) {
        this.tspTour = tspTour;
    }

    private int getDistanceModulo(int i) {
        return tspTour.data().getDistance(tour[i], tour[(i + 1) % nbCities]);
    }

    private int getDistanceModulo(int i, int j) {
        return tspTour.data().getDistance(tour[i % nbCities], tour[j % nbCities]);
    }

    private int getDistance(int i, int j) {
        return tspTour.data().getDistance(tour[i], tour[j]);
    }

    private int getPreviousDistance(int i, int j) {
        return getDistanceModulo(i) + getDistanceModulo(j);
    }

    private int getSwappedDistance(int i, int j) {
        return getDistance(i, j) + getDistanceModulo(i + 1, j + 1);
    }

    /**
     * Initialize the utils class with a tour. Must be used before using other functions else data will refer to that previous tour.
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
     * Checks that the swap (i,j) is not useless. The cases (i, j = i + 1) and (i = j + 1, j) are considered useless. Other special cases must be treated
     *
     * @param i must be larger than j
     * @param j must be smaller than i
     * @return whether the swap is useless, or not
     */
    public boolean isSwapUseless(int i, int j) {
        return tour[j] == tour[(i + 1) % nbCities] || tour[i] == tour[(j + 1)];
    }

    /**
     * Swaps all elements between indexes i + 1 and j to realize the swap (i,j)
     *
     * @param i must be larger than j
     * @param j must be smaller than i
     * @return the symmetric tour as an array of int
     */
    public int[] swap(int i, int j) {
        int nbToSwap = nbCities - i + j;

        // If the number of swaps to do (i, j) is bigger than (j, i), then we do the latter
        if (nbToSwap > i - j) {
            nbToSwap = i - j;
            int tmp = i;
            i = j;
            j = tmp;
        }

        // if it is odd, the last iteration can be skipped
        //TODO have another function variant with no modulo as to be faster in cases of (j,i) ? modulo is only needed for (i,j)
        int totalSwap = nbToSwap / 2;
        for (int k = 0; k < totalSwap; k++) {
            int indexI = (k + i + 1) % nbCities;
            int indexJ = (j - k + nbCities) % nbCities;
            int tmp = tour[indexI];
            tour[indexI] = tour[indexJ];
            tour[indexJ] = tmp;
        }

        return tour;
    }

}
