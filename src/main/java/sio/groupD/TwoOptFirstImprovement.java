/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspImprovementHeuristic;
import sio.tsp.TspTour;

public final class TwoOptFirstImprovement implements TspImprovementHeuristic {
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


                // TODO swap if length is smaller
            }
        }

        return new TspTour(tspTour.data(), tour, length);
    }
}
