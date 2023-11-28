/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspImprovementHeuristic;
import sio.tsp.TspTour;

public final class TwoOptFirstImprovement implements TspImprovementHeuristic {
    private interface Utils {
        int getDistanceModulo(int i);
        int getDistanceModulo(int i, int j);
        int getDistance(int i, int j);
    }
    @Override
    public TspTour computeTour(TspTour tspTour) {
        /*
        PDF Notes to delete once implemented
        Plus spécifiquement un 2-échange (i, j) consiste à retirer du cycle l’arête {i, i + 1} (reliant les
        villes aux positions i et i+ 1 dans la tournée actuelle) ainsi que l’arête {j, j + 1} (reliant les villes
        aux positions j et j + 1) et à les remplacer par les arêtes {i, j} et {i + 1, j + 1}.
        Un 2-échange est améliorant si la longueur du nouveau cycle est plus petite que celle du cycle avant échange.
         */

        int nbCities = tspTour.data().getNumberOfCities();
        int[] tour = tspTour.tour();
        long length = tspTour.length();

        Utils utils = new Utils() {
            public int getDistanceModulo(int i) {
                return tspTour.data().getDistance(tour[i], tour[(i + 1) % nbCities]);
            }

            public int getDistanceModulo(int i, int j) {
                return tspTour.data().getDistance(tour[i % nbCities], tour[j % nbCities]);
            }

            public int getDistance(int i, int j) {
                return tspTour.data().getDistance(tour[i], tour[j]);
            }
        };

        for (int i = 0; i < nbCities; i++) {
            // We only iterate until i as to avoid the cases:
            // - (j,i), which is equivalent to (i,j), which is useless
            // - (i,i) which is useless
            for (int j = 0; j < i; j++) {
                // The special case (i, j = i + 1 ) is also useless as it shifts the direction of the turn and that's it
                if (tour[j] == tour[(i + 1) % nbCities]) {
                    continue;
                }
                System.out.format("%d and %d are valid cases !\n", i, j);

                int previousDistance = utils.getDistanceModulo(i) + utils.getDistanceModulo(j);
                int newDistance = utils.getDistance(i, j) + utils.getDistanceModulo(i + 1, j + 1);
                if (newDistance < previousDistance) {
                    System.out.println("swap !");
                    // k must be offset by i + 1 to point at
                    // nbCities + i + j are the total of items to swap. if it is odd, the last iteration can be skipped.
                    int totalSwap = (nbCities + i + j) / 2;
                    for (int k = 0; k <= totalSwap; k++) {
                        int tmp = tour[(k + i + 1) % nbCities];
                        tour[(k + i + 1) % nbCities] = tour[(j - k) % nbCities];
                        tour[(j - k) % nbCities] = tmp;
                    }

                    // Decrements length based on difference between previousDistance and newDistance
                    length -= previousDistance - newDistance;
                }
            }
        }

        return new TspTour(tspTour.data(), tour, length);
    }
}
