# SIO_23-24_TP2

## Améliorations

- itérer i de 1 à nbCities - 1 : les itérations 0 et 1 ne servent à rien car les swaps sont inutiles 
- itérer j de 0 à i - 2 :
  - l'itération j = i cause des erreurs
  - un swap (i, j) est équivalent, pour une tournée symmétrique, à un swap (j, i)
  - l'itération j = i - 1 ne sert à rien car cela revient à j + 1 = i
- ne pas itérer si i + 1 = j : ne sert à rien
- enregistrer en mémoire la distance [i, i + 1] pour ne la calculer qu'une fois par i et non pas j fois
  - dans le cas du first improvement cela demande un recalcul de la distance [i, i + 1] si un swap est effectué
- puisque le swap (i, j) est équivalent à un swap (j, i) seul celui-ci demandant le moins de swaps d'éléments est effectué
- certains calculs requiérant des opérations modulo ne sont exécutés que si il est vraiment requis d'utiliser le modulo. Cela rend le code plus obtus à comprendre malheureusement.

## Discussion des résultats

Au vu des résultats ci-dessous, il est évident que :
- l'algorithme constructif RandomTour est suboptimal, entraînant considérables délais pour de grandes tournées avec BestImprovement
- en terme de temps d'exécution, l'algorithme First Improvement est nettement préférable par rapport à Best Improvement. On observe un temps environ 20 fois plus rapide que Best Improvement.
- en terme de longueurs de tournées, l'algorithme Best Improvement est légèrement préférable par rapport à First Improvement. On observe une amélioration de longueur d'environ 3% par rapport à First Improvement.
- le choix de l'algorithme constructif n'influe pas de manière notable le temps de calculs de l'algorithme amélioratif ni la longueur trouvée.

Pour ces raisons nous recommendons, dans un premier temps d'utiliser un algorithme constructif, le plus efficace le mieux c'est (RandomTour à proscrire).
Puis d'utiliser un algorithm amélioratif performant.
En raison de nos résultats nous recommandons FirstImprovement mais si la vitesse n'est pas une priorité, alors BestImprovement trouve un résultat plus proche de l'optimal.

## Résultats

Les temps sont en millisecondes [ms] et les longueurs sont représentées sous forme de multiplicateurs par rapport à la longueur optimale d'une tournée.

Ainsi, une tournée 1,1 comme longueur est 1,1 fois plus longue que la tournée optimale, soit, 10% plus longue.

De plus, le temps correspond au temps requis pour améliorer une tournée existante. 
Le temps pour construire une tournée est considérée comme déjà traité lors du laboratoire précédent.

```
| file    | constructiveAlgorithm     | improvementAlgorithm   |    averageTime |       bestTime |      worstTime | averageLength | bestLength | worstLength |
+---------+---------------------------+------------------------+----------------+----------------+----------------+---------------+------------+-------------+
| att532  | RandomTour                | TwoOptFirstImprovement |      21,151188 |      12,399800 |      63,095501 |      1,107914 |   1,097730 |    1,132793 |
| att532  | RandomTour                | TwoOptBestImprovement  |     760,488220 |     653,803345 |    1192,422241 |      1,094634 |   1,062851 |    1,119429 |
| att532  | NearestNeighbor           | TwoOptFirstImprovement |       9,235890 |       6,420300 |      12,209300 |      1,076311 |   1,061929 |    1,093579 |
| att532  | NearestNeighbor           | TwoOptBestImprovement  |     104,010422 |      89,674202 |     118,469696 |      1,046947 |   1,041993 |    1,062597 |
| att532  | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |       8,377840 |       7,453000 |      10,322700 |      1,071823 |   1,064211 |    1,086292 |
| att532  | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |     103,696815 |      97,061600 |     119,710899 |      1,044759 |   1,036989 |    1,066437 |
| rat575  | RandomTour                | TwoOptFirstImprovement |      14,286350 |      12,077400 |      16,765100 |      1,126104 |   1,105566 |    1,144692 |
| rat575  | RandomTour                | TwoOptBestImprovement  |     864,119995 |     833,877197 |     934,119507 |      1,120065 |   1,107338 |    1,156947 |
| rat575  | NearestNeighbor           | TwoOptFirstImprovement |      11,145630 |       8,716000 |      14,442200 |      1,079359 |   1,069688 |    1,095822 |
| rat575  | NearestNeighbor           | TwoOptBestImprovement  |     107,000473 |      94,674706 |     120,949104 |      1,048885 |   1,035435 |    1,059353 |
| rat575  | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |       9,785310 |       6,904600 |      12,934200 |      1,085117 |   1,072346 |    1,099070 |
| rat575  | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |     107,984985 |      94,812096 |     126,943398 |      1,053566 |   1,043998 |    1,072641 |
| u574    | RandomTour                | TwoOptFirstImprovement |      15,302490 |      13,378500 |      17,667999 |      1,124756 |   1,101694 |    1,166346 |
| u574    | RandomTour                | TwoOptBestImprovement  |     993,866089 |     884,673035 |    1423,321167 |      1,101404 |   1,083430 |    1,112451 |
| u574    | NearestNeighbor           | TwoOptFirstImprovement |      10,751619 |       9,046500 |      12,848000 |      1,088866 |   1,065438 |    1,102940 |
| u574    | NearestNeighbor           | TwoOptBestImprovement  |     146,850891 |     129,063507 |     201,933289 |      1,058916 |   1,043273 |    1,071562 |
| u574    | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |      10,827640 |       8,460500 |      15,081300 |      1,079230 |   1,063731 |    1,103103 |
| u574    | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |     166,257614 |     132,486603 |     237,603699 |      1,059545 |   1,050264 |    1,068663 |
| rl1889  | RandomTour                | TwoOptFirstImprovement |     409,896576 |     328,716614 |     560,922302 |      1,139747 |   1,125866 |    1,162297 |
| rl1889  | RandomTour                | TwoOptBestImprovement  |   89935,429688 |   87506,992188 |   92425,664063 |      1,120644 |   1,085418 |    1,139633 |
| rl1889  | NearestNeighbor           | TwoOptFirstImprovement |     257,967010 |     222,709702 |     295,137695 |      1,089467 |   1,081030 |    1,098630 |
| rl1889  | NearestNeighbor           | TwoOptBestImprovement  |    6545,085449 |    5728,930176 |    7113,958496 |      1,053333 |   1,045161 |    1,070589 |
| rl1889  | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |     248,390869 |     199,623291 |     303,863007 |      1,087632 |   1,069995 |    1,100241 |
| rl1889  | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |    6266,303711 |    5877,711914 |    6685,662109 |      1,051615 |   1,040760 |    1,061096 |
| u1817   | RandomTour                | TwoOptFirstImprovement |     299,807678 |     255,783203 |     359,296600 |      1,187911 |   1,163931 |    1,207496 |
| u1817   | RandomTour                | TwoOptBestImprovement  |   73363,109375 |   69055,351563 |   77371,609375 |      1,150235 |   1,131746 |    1,163336 |
| u1817   | NearestNeighbor           | TwoOptFirstImprovement |     240,730118 |     193,523605 |     327,676605 |      1,085497 |   1,068775 |    1,100820 |
| u1817   | NearestNeighbor           | TwoOptBestImprovement  |    4956,132324 |    4166,990234 |    5753,649902 |      1,071941 |   1,054090 |    1,086135 |
| u1817   | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |     196,169739 |     157,893997 |     295,150696 |      1,096072 |   1,084491 |    1,112970 |
| u1817   | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |    4879,250488 |    4178,345215 |    5333,182129 |      1,065198 |   1,058828 |    1,080873 |
| vm1748  | RandomTour                | TwoOptFirstImprovement |     278,166107 |     238,464401 |     336,248291 |      1,128303 |   1,117264 |    1,143376 |
| vm1748  | RandomTour                | TwoOptBestImprovement  |   64891,105469 |   63155,972656 |   67116,343750 |      1,102658 |   1,094088 |    1,119823 |
| vm1748  | NearestNeighbor           | TwoOptFirstImprovement |     212,732391 |     154,370895 |     279,542511 |      1,083630 |   1,073896 |    1,093981 |
| vm1748  | NearestNeighbor           | TwoOptBestImprovement  |    5478,538574 |    5156,353027 |    6357,479492 |      1,056749 |   1,046444 |    1,064096 |
| vm1748  | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |     184,058167 |     152,941498 |     259,753693 |      1,092178 |   1,078026 |    1,099674 |
| vm1748  | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |    5175,317383 |    4706,983398 |    5847,791504 |      1,057625 |   1,050455 |    1,067106 |
+---------+---------------------------+------------------------+----------------+----------------+----------------+---------------+------------+-------------+

| constructiveAlgorithm     | improvementAlgorithm   |   averageTime |     bestTime |    worstTime | averageLength | bestLength | worstLength |
+---------------------------+------------------------+---------------+--------------+--------------+---------------+------------+-------------+
| RandomTour                | TwoOptFirstImprovement |    173,101746 |   143,469986 |   225,665970 |      1,135789 |   1,118675 |    1,159500 |
| NearestNeighbor           | TwoOptFirstImprovement |    123,760445 |    99,131165 |   156,976044 |      1,083855 |   1,070126 |    1,097629 |
| DoubleEndsNearestNeighbor | TwoOptFirstImprovement |    109,601601 |    88,879478 |   149,517609 |      1,085342 |   1,072134 |    1,100225 |
| RandomTour                | TwoOptBestImprovement  |  38468,023438 | 37015,113281 | 40077,246094 |      1,114940 |   1,094145 |    1,135270 |
| NearestNeighbor           | TwoOptBestImprovement  |   2889,603027 |  2560,947754 |  3277,739990 |      1,056129 |   1,044399 |    1,069056 |
| DoubleEndsNearestNeighbor | TwoOptBestImprovement  |   2783,135254 |  2514,566895 |  3058,482178 |      1,055385 |   1,046882 |    1,069469 |
+---------------------------+------------------------+---------------+--------------+--------------+---------------+------------+-------------+
```