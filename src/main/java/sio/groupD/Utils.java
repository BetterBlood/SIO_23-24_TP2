package sio.groupD;

import sio.tsp.TspTour;

public class Utils {
    private TspTour tspTour;
    private int[] tour;
    public Utils(TspTour tspTour) {
        this.tspTour = tspTour;
    }

    private int getDistanceModulo(int i) {
        return tspTour.data().getDistance(tour[i], tour[(i + 1) % tour.length]);
    }

    private int getDistanceModulo(int i, int j) {
        return tspTour.data().getDistance(tour[i % tour.length], tour[j % tour.length]);
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
     * Necessary to call this method before any other public ones
     * @param tour
     */
    public void setTour(int[] tour) {
        this.tour = tour;
    }

    public int getGainedDistance(int i, int j) {
        return getPreviousDistance(i, j) - getSwappedDistance(i, j);
    }


    public int[] swapAllBetween(int i, int j) {
        int nbCities = tour.length;
        int totalSwap = (nbCities - i + j) / 2;
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
