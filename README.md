# SIO_23-24_TP2

## Améliorations

- itérer i de 1 à nbCities - 1 : les itérations 0 et 1 ne servent à rien car les swaps sont inutiles 
- itérer j de 0 à i - 2 :
  - l'itération j = i cause des erreurs
  - un swap (i, j) est équivalent, pour une tournée symmétrique, à un swap (j, i)
  - l'itération j = i - 1 ne sert à rien car cela revient à j + 1 = i
- cas i + 1 = j :
  - dans le cas du First Improvement : l'itération est interrompue
  - dans le cas du Best Improvement : il n'y a pas de vérification de cette condition car, après avoir testé de manière empirique, les temps obtenus étaient meilleurs sans cette vérification
- enregistrer en mémoire la distance [i, i + 1] pour ne la calculer qu'une fois par i et non pas j fois
  - dans le cas du First Improvement cela demande un recalcul de la distance [i, i + 1] si un swap est effectué
  - dans le cas du Best Improvement cela ne demande aucune modification car le swap est effectué après avoir itéré sur toutes les pairs possibles (i, j)
- enregistrer en mémoire i + 1 car un calcul de modulo est effectué et ceci peut être fait i fois plutôt que i * j fois
- puisque le swap (i, j) est équivalent à un swap (j, i) seul celui-ci demandant le moins de swaps d'éléments est effectué

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

Ainsi, une tournée 1.1 comme longueur est 1.1 fois plus longue que la tournée optimale, soit, 10% plus longue.

De plus, le temps correspond au temps requis pour améliorer une tournée existante. 
Le temps pour construire une tournée est considérée comme déjà traité lors du laboratoire précédent.

TODO UPDATE OUTDATED DATA BY NEW ONE

```
| file    | constructiveAlgorithm     | improvementAlgorithm   |    averageTime |       bestTime |      worstTime | averageLength | bestLength | worstLength |
+---------+---------------------------+------------------------+----------------+----------------+----------------+---------------+------------+-------------+
| att532  | RandomTour                | TwoOptFirstImprovement |      14,843259 |       7,588800 |      39,172100 |      1,107914 |   1,097730 |    1,132793 |
| att532  | RandomTour                | TwoOptBestImprovement  |     408,912506 |     330,873688 |     662,830750 |      1,094634 |   1,062851 |    1,119429 |
| att532  | NearestNeighbor           | TwoOptFirstImprovement |       5,362640 |       3,587900 |       7,259200 |      1,076311 |   1,061929 |    1,093579 |
| att532  | NearestNeighbor           | TwoOptBestImprovement  |      51,840687 |      42,893002 |      56,900101 |      1,046947 |   1,041993 |    1,062597 |
| att532  | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |       4,911450 |       4,233200 |       6,324600 |      1,071823 |   1,064211 |    1,086292 |
| att532  | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |      50,397568 |      47,349098 |      53,610600 |      1,044759 |   1,036989 |    1,066437 |
| rat575  | RandomTour                | TwoOptFirstImprovement |       9,953670 |       7,758900 |      14,876500 |      1,126104 |   1,105566 |    1,144692 |
| rat575  | RandomTour                | TwoOptBestImprovement  |     418,350739 |     405,963013 |     433,777222 |      1,120065 |   1,107338 |    1,156947 |
| rat575  | NearestNeighbor           | TwoOptFirstImprovement |       5,974380 |       4,850300 |       7,096200 |      1,079359 |   1,069688 |    1,095822 |
| rat575  | NearestNeighbor           | TwoOptBestImprovement  |      49,473976 |      45,854698 |      55,148499 |      1,048885 |   1,035435 |    1,059353 |
| rat575  | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |       5,387950 |       4,028300 |       7,014200 |      1,085117 |   1,072346 |    1,099070 |
| rat575  | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |      54,008018 |      48,084000 |      61,370300 |      1,053566 |   1,043998 |    1,072641 |
| u574    | RandomTour                | TwoOptFirstImprovement |       9,959630 |       8,288200 |      13,985600 |      1,124756 |   1,101694 |    1,166346 |
| u574    | RandomTour                | TwoOptBestImprovement  |     441,419861 |     427,065491 |     455,698578 |      1,101404 |   1,083430 |    1,112451 |
| u574    | NearestNeighbor           | TwoOptFirstImprovement |       6,240620 |       4,764400 |       6,854700 |      1,088866 |   1,065438 |    1,102940 |
| u574    | NearestNeighbor           | TwoOptBestImprovement  |      65,993271 |      58,887299 |      81,253105 |      1,058916 |   1,043273 |    1,071562 |
| u574    | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |       5,990660 |       4,936700 |       8,053400 |      1,079230 |   1,063731 |    1,103103 |
| u574    | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |      64,696053 |      61,169701 |      68,794998 |      1,059545 |   1,050264 |    1,068663 |
| rl1889  | RandomTour                | TwoOptFirstImprovement |     331,742645 |     266,994995 |     480,067719 |      1,139747 |   1,125866 |    1,162297 |
| rl1889  | RandomTour                | TwoOptBestImprovement  |   79543,054688 |   76933,992188 |   82404,375000 |      1,120644 |   1,085418 |    1,139633 |
| rl1889  | NearestNeighbor           | TwoOptFirstImprovement |     210,151642 |     184,731598 |     248,073700 |      1,089467 |   1,081030 |    1,098630 |
| rl1889  | NearestNeighbor           | TwoOptBestImprovement  |    6295,000000 |    5317,923828 |    7747,665039 |      1,053333 |   1,045161 |    1,070589 |
| rl1889  | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |     231,288528 |     156,704102 |     379,408691 |      1,087632 |   1,069995 |    1,100241 |
| rl1889  | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |    6051,571777 |    5519,687500 |    6705,834473 |      1,051615 |   1,040760 |    1,061096 |
| u1817   | RandomTour                | TwoOptFirstImprovement |     238,956039 |     207,854202 |     291,365875 |      1,187911 |   1,163931 |    1,207496 |
| u1817   | RandomTour                | TwoOptBestImprovement  |   67401,523438 |   61905,359375 |   85174,500000 |      1,150235 |   1,131746 |    1,163336 |
| u1817   | NearestNeighbor           | TwoOptFirstImprovement |     318,421082 |     203,349594 |     426,020294 |      1,085497 |   1,068775 |    1,100820 |
| u1817   | NearestNeighbor           | TwoOptBestImprovement  |    5220,363281 |    4023,143311 |    5849,487305 |      1,071941 |   1,054090 |    1,086135 |
| u1817   | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |     181,153412 |     136,329407 |     284,408203 |      1,096072 |   1,084491 |    1,112970 |
| u1817   | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |    5046,296875 |    4134,383301 |    5684,392578 |      1,065198 |   1,058828 |    1,080873 |
| vm1748  | RandomTour                | TwoOptFirstImprovement |     287,469513 |     223,544006 |     439,330414 |      1,128303 |   1,117264 |    1,143376 |
| vm1748  | RandomTour                | TwoOptBestImprovement  |   71702,000000 |   68390,226563 |   76705,992188 |      1,102658 |   1,094088 |    1,119823 |
| vm1748  | NearestNeighbor           | TwoOptFirstImprovement |     207,352646 |     150,788101 |     283,261169 |      1,083630 |   1,073896 |    1,093981 |
| vm1748  | NearestNeighbor           | TwoOptBestImprovement  |    5676,188477 |    5261,852539 |    6013,141602 |      1,056749 |   1,046444 |    1,064096 |
| vm1748  | DoubleEndsNearestNeighbor | TwoOptFirstImprovement |     157,730362 |     133,057007 |     196,993698 |      1,092178 |   1,078026 |    1,099674 |
| vm1748  | DoubleEndsNearestNeighbor | TwoOptBestImprovement  |    5455,574219 |    4194,915039 |    7235,016602 |      1,057625 |   1,050455 |    1,067106 |
+---------+---------------------------+------------------------+----------------+----------------+----------------+---------------+------------+-------------+

| constructiveAlgorithm     | improvementAlgorithm   |   averageTime |      bestTime |     worstTime | averageLength | bestLength | worstLength |
+---------------------------+------------------------+---------------+---------------+---------------+---------------+------------+-------------+
| RandomTour                | TwoOptFirstImprovement |    148,820786 |    120,338181 |    213,133041 |      1,135789 |   1,118675 |    1,159500 |
| NearestNeighbor           | TwoOptFirstImprovement |    125,583839 |     92,011993 |    163,094208 |      1,083855 |   1,070126 |    1,097629 |
| DoubleEndsNearestNeighbor | TwoOptFirstImprovement |     97,743721 |     73,214783 |    147,033798 |      1,085342 |   1,072134 |    1,100225 |
| RandomTour                | TwoOptBestImprovement  |  36652,542969 |  34732,246094 |  40972,863281 |      1,114940 |   1,094145 |    1,135270 |
| NearestNeighbor           | TwoOptBestImprovement  |   2893,143311 |   2458,425781 |   3300,599121 |      1,056129 |   1,044399 |    1,069056 |
| DoubleEndsNearestNeighbor | TwoOptBestImprovement  |   2787,090576 |   2334,264893 |   3301,503174 |      1,055385 |   1,046882 |    1,069469 |
+---------------------------+------------------------+---------------+---------------+---------------+---------------+------------+-------------+
```