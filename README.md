# BigLittleMatching

This program automatically pairs Bigs and Littles using the Gale-Shapley algorithm for the Stable Marriage Problem. 
In this case, the Littles are the "men" and the Bigs are the "women". This is because the algorithm favors men and we want to prioritize the Littles' preferences.

Note: There must be an equal number of Bigs and Littles.

Should have two spreadsheets: bigs-little-preferences.csv and littles-big-preferences.csv. Each must have the following header as the first row:

Name,1,2,3,4,...,N

Each row after the header should follow the format as the header. 

For example, for bigs-little-preferences.csv:

<big>,<little_first_choice>,<little_second_choice>,...,<little_nth_choice>

For littles-big-preferences.csv:
  
<little>,<big_first_choice>,<big_second_choice>,...,<big_nth_choice>
