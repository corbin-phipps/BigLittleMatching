import java.io.*;
import java.util.*;

// Implements the Gale-Shapley algorithm for the Stable Marriage Problem, but for Big/Little matching. Littles are the "men" in this case, "proposing" to the "women" (Bigs).
public class Matching {
    public static void main(String[] args) {
        String bigsPrefsFile = "bigs-little-preferences.csv";
        String littlesPrefsFile = "littles-big-preferences.csv";

        Map<String, String[]> bigsPrefs = getPreferences(bigsPrefsFile);
        Map<String, String[]> littlesPrefs = getPreferences(littlesPrefsFile);

        Map<String, String> matches = match(bigsPrefs, littlesPrefs);

        for (String key : matches.keySet()) {
            System.out.println("Big: " + key + " - Little: " + matches.get(key));
        }  
    }

    public static Map<String, String[]> getPreferences(String prefsFile) {
        BufferedReader br = null;
        String line = "";
        String delimiter = ",";
        boolean firstLine = true;

        Map<String, String[]> preferenceMap = new TreeMap<String, String[]>();

        try {
            br = new BufferedReader(new FileReader(prefsFile));
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] lineInput = line.split(delimiter);

                String name = lineInput[0];
                String[] prefs = Arrays.copyOfRange(lineInput, 1, lineInput.length);
                preferenceMap.put(name, prefs);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }     
        
        return preferenceMap;
    }

    // Run the Gale-Shapley algorithm to match the bigs and littles, based on the given preferences. Returns a map of the matches between bigs and littles.
    public static Map<String, String> match(Map<String, String[]> bigsPrefs, Map<String, String[]> littlesPrefs) {
        int N = bigsPrefs.keySet().size();
        Set<String> littles = littlesPrefs.keySet();
        int freeCount = N;
        Map<String, Boolean> littlesTaken = new TreeMap<String, Boolean>();
        Map<String, String> matches = new TreeMap<String, String>();
        

        while (freeCount > 0) {
            // Find the first free little
            String little = "";
            for (String pledge : littles) {
                if (littlesTaken.get(pledge) == null || !littlesTaken.get(pledge)) {
                    little = pledge;
                    break;
                }
            }

            // Go through the little's preferences of bigs
            for (int i = 0; i < N && (littlesTaken.get(little) == null || !littlesTaken.get(little)); i++) {
                String big = littlesPrefs.get(little)[i];
                // Match big and little if the big is free
                if (matches.get(big) == null) {
                    matches.put(big, little);
                    littlesTaken.put(little, true);
                    freeCount--;
                } else {    // Big is not free (already matched)                   
                    int currLittleIndex = -1;
                    int newLittleIndex = -1;
                    String[] bigPrefs = bigsPrefs.get(big);

                    String currLittle = matches.get(big);
                    
                    // Check if big prefers the new little over their current match
                    for (int pledgeIndex = 0; pledgeIndex < bigPrefs.length; pledgeIndex++) {
                        // Get index (preference level) of the big's current little
                        if (bigPrefs[pledgeIndex].equals(currLittle)) {
                            currLittleIndex = pledgeIndex;
                        }

                        // Get index (preference level) of the new little in the big's preference list
                        if (bigPrefs[pledgeIndex].equals(little)) {
                            newLittleIndex = pledgeIndex;
                        }    
                    }

                    // Big prefers new little over current little, so update matching
                    if (newLittleIndex < currLittleIndex) {
                        littlesTaken.put(little, true);
                        matches.put(big, little);
                        littlesTaken.put(currLittle, false);
                    }
                }
            }
        }

        return matches;
    }
}
