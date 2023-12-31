/**
 * Authors : Jeremiah Steiner & Simon Guggisberg
 */

package sio.groupD;

import sio.tsp.TspConstructiveHeuristic;
import sio.tsp.TspData;
import sio.tsp.TspTour;

/**
 * Classe DoubleEndsNearestNeighbor permet de calculer un tour à l'aide de l'heuristique des plus proches voisins par les deux bouts
 */
public final class DoubleEndsNearestNeighbor implements TspConstructiveHeuristic {
    private record TspDistanceToCity(int distance, int cityIndex, boolean byFirst) { }

    private int numberOfCities;
    private boolean[] citiesVisited;
    private int firstCounter = 0;
    private int[] orderVisited;
    private int lastCounter = 0;
    private int countVisited = 0;
    private int distTot = 0;


    /**
     * Calcule un tour en utilisant l'heuristique du DoubleEndsNearestNeighbor
     *
     * @param data           Data of problem instance
     * @param startCityIndex Index of starting city, if needed by the implementation
     * @return un TspTour, stockant les infos relatives au tour trouvé
     */
    @Override
    public TspTour computeTour(TspData data, int startCityIndex) {
        Init(data.getNumberOfCities(), startCityIndex);
        //System.out.println("dist : " + distTot);

        // Initialise t pour la première itération qui servira de queue d'array
        TspDistanceToCity t = getFirstCity(data);
        countVisited++;
        orderVisited[lastCounter] = t.cityIndex;
        citiesVisited[t.cityIndex] = true;
        distTot += t.distance;
        //System.out.println(data.getDistance(orderVisited[firstCounter], orderVisited[lastCounter]));
        //System.out.println(t.cityIndex + ", " + firstCounter + ", " + lastCounter + ", d: " + distTot + ", " + t.distance + " ---");

        while (countVisited++ < numberOfCities) {
            t = getClosestCity(data);
            addCityToTour(t);
            //System.out.println(t.cityIndex + ", " + firstCounter + ", " + lastCounter + ", d: " + distTot + ", " + t.distance);
        }

        // Ajoute le chemin de retour à la distance totale
        distTot += data.getDistance(orderVisited[firstCounter], orderVisited[lastCounter]);

        //System.out.println("distance totale : " + distTot);
        for (int i = 0; i < numberOfCities; ++i)
        {
            //System.out.print(orderVisited[i] + " ");
        }
        //System.out.println();
        return new TspTour(data, orderVisited, distTot);
    }

    /**
     * Initialise les variables nécessaires au calcul du tour
     *
     * @param numberOfCities nombre total de villes lors de la simulation
     * @param startCityIndex index de la ville de départ
     */
    private void Init(int numberOfCities, int startCityIndex) {
        this.numberOfCities = numberOfCities;
        citiesVisited = new boolean[numberOfCities];
        orderVisited = new int[numberOfCities];
        firstCounter = 0;
        lastCounter = numberOfCities - 1;
        countVisited = 0;
        distTot = 0;
        // Initialise s qui servira de tête à l'array orderVisited
        citiesVisited[startCityIndex] = true;
        orderVisited[countVisited++] = startCityIndex;
    }

    /**
     * Met à jour le tour en l'ajoutant au bon endroit de la liste (à l'avant ou à l'arrière suivant la distance)
     *
     * @param t données à fournir pour le calcul, contient notamment la distance entre chaque ville
     */
    private void addCityToTour(TspDistanceToCity t) {
        distTot += t.distance;
        citiesVisited[t.cityIndex] = true;
        orderVisited[t.byFirst ? ++firstCounter: --lastCounter] = t.cityIndex;
    }

    private TspDistanceToCity getClosestCity(TspData data, boolean byFirst) {
        TspDistanceToCity dist = getClosestCity(data);
        return new TspDistanceToCity(dist.distance, dist.cityIndex, byFirst);
    }

    private TspDistanceToCity getFirstCity(TspData data) {
        int closestCity = -1;
        int distMin = Integer.MAX_VALUE;

        for (int i = 0; i < numberOfCities; ++i) {
            if (citiesVisited[i]) {
                continue;
            }

            int currentDistance = data.getDistance(i, orderVisited[firstCounter]);
            if (distMin > currentDistance) {
                closestCity = i;
                distMin = currentDistance;
            }
        }
        return new TspDistanceToCity(distMin, closestCity, false);
    }

    private TspDistanceToCity getClosestCity(TspData data) {
        int closestCity = -1;
        int distMin = Integer.MAX_VALUE;
        boolean byFirst = false;

        for (int i = 0; i < numberOfCities; ++i) {
            if (citiesVisited[i]) {
                continue;
            }

            int currentDistance = data.getDistance(i, orderVisited[firstCounter]);
            //if (currentDistance == 140) System.out.println("i = " + i);
            if (distMin > currentDistance) {
                closestCity = i;
                distMin = currentDistance;
                byFirst = true;
            }

            // à distance égale, on préfère ajouter après s plutôt que t
            currentDistance = data.getDistance(i, orderVisited[lastCounter]);
            //if (currentDistance == 140) System.out.println("i = " + i);
            if (distMin > currentDistance) {
                closestCity = i;
                distMin = currentDistance;
                byFirst = false;
            }
        }

        return new TspDistanceToCity(distMin, closestCity, byFirst);
    }
}
