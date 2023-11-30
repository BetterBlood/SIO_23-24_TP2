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
     * Checks that the swap (i,j) is not useless.
     * The case (i, j = i + 1) is considered useless.
     * Other special cases must be treated accordingly.
     *
     * @param i must be larger than j
     * @param j must be smaller than i
     * @return whether the swap is useless, or not
     */
    public boolean isSwapUseless(int i, int j) {
        //if (i == 474 && j == 472) System.out.println("???");
        return j == (i + 1) % nbCities;
    }

    /**
     * Swaps all elements between indexes i + 1 and j to realize the swap (i,j)
     *
     * @param i must be larger than j
     * @param j must be smaller than i
     * @return the symmetric tour as an array of int
     */
    public int[] swap(int i, int j) {
        int nbToSwap = nbCities - i + j; // pk pas i - j ? :

        // If the number of swaps to do (i, j) is bigger than (j, i), then we do the latter
        if (nbToSwap > i - j) {
            nbToSwap = i - j;
            int tmp = i;
            i = j;
            j = tmp;
        }//*/

        /*
        System.out.println("before swap (" + i + ", " + j + ")");
        for (int a :
                tour) {
            System.out.print(a + " ");
        }
        System.out.println();
        */

        // if it is odd, the last iteration can be skipped
        //TODO have another function variant with no modulo as to be faster in cases of (j,i) ? modulo is only needed for (i,j)
        int totalSwap = nbToSwap / 2;
        for (int k = 0; k < totalSwap; k++) {
            int indexI = (k + i + 1) % nbCities;
            int indexJ = (j - k + nbCities) % nbCities;
            int tmp = tour[indexI];
            tour[indexI] = tour[indexJ];
            tour[indexJ] = tmp;
        }//*/

        /*
        System.out.println("after swap (" + i + ", " + j + ")");
        for (int a : tour) {
            System.out.print(a + " ");
        }
        System.out.println();
        */
        //System.out.println(nbCities + " " + i + " " + j);
        /* TODO : check la proposition suivante que je pense être plus simple: */
        // plus simple à comprendre + pas besoin de nbSwap mais nbCities doit être plus petit que la moitié de max int je pense
        /*boolean normalSwap = i - j < nbCities - i + j;
        System.out.println(normalSwap + " " + (i - j) + " " + (nbCities - i + j));
        int incr = normalSwap ? 1 : -1;
        int end = normalSwap ? 0 : nbCities;
        while((i - j) * incr + end > 0) // (i - j dans un normal swap)
        {
            int sw = tour[(i+nbCities)%nbCities];
            tour[(i+nbCities)%nbCities] = tour[(j+nbCities)%nbCities];
            tour[(j+nbCities)%nbCities] = sw;
            i -= incr;
            j += incr;
            //System.out.println(i + " " + j);
        }//*/
        /* fin */

        return tour;
    }

}
