# BigLittleMatching

This program automatically pairs Bigs and Littles using the Gale-Shapley algorithm for the Stable Marriage Problem. 
In this case, the Littles are the "men" and the Bigs are the "women". This is because the algorithm favors men and we want to prioritize the Littles' preferences.
Note: There must be an equal number of Bigs and Littles and the preferences must be in a .csv file with each row in the format:

Name,1,2,3,4,...,N
