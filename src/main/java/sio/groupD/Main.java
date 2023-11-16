/**
 * Auteurs : Jeremiah Steiner et Simon Guggisberg
 */

package sio.groupD;

public final class Main {
    /**
     * Record contenant les méta informations pour un problème TSP
     *
     * @param name          le nom d'un fichier contenant les données du problème
     * @param optimalLength la longueur optimale correspondant aux données du problème
     */
    private record TspMetaData(String name, int optimalLength) {
    }

    private static final TspMetaData[] METADATA = {
            new TspMetaData("att532", 86729),
            new TspMetaData("rat575", 6773),
            new TspMetaData("rl1889", 316536),
            new TspMetaData("u574", 36905),
            new TspMetaData("u1817", 57201),
            new TspMetaData("vm1748", 336556),
            new TspMetaData("carre", 396),
    };

    public static void main(String[] args) {
        // TODO
        //  - Renommage du package ;
        //  - Implémentation des classes TwoOptBestImprovement et TwoOptFirstImprovement ;
        //  - Documentation abondante des classes comprenant :
        //    - la javadoc, avec auteurs et description des implémentations ;
        //    - des commentaires sur les différentes parties de vos algorithmes.

        // Exemple de lecture d'un jeu de données :
        // TspData data = TspData.fromFile("data/att532.dat");
    }
}