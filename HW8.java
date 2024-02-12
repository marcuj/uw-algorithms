import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HW8 {

    public static void main(String[] args) {
//        String[] states = {"Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware","District of Columbia","Florida",
//                "Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts",
//                "Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico",
//                "New York","North Carolina","North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina",
//                "South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming"};
//        int[] electoralVotes2024 = {9,3,11,6,54,10,7,3,3,30,16,4,4,19,11,6,6,8,8,4,10,11,15,10,6,10,4,5,6,4,14,5,28,16,3,17,7,8,19,4,9,3,11,40,6,3,13,12,4,10,3};
//        int[] test = {4,2,12,8,3,9,6,10};
//        long count = numTies(electoralVotes2024, 269);
//        long count1 = numTies(test, 1);
//        System.out.println(count1);
//        ArrayList<String> set = smallestSet(electoralVotes2024, 269, states);
//        ArrayList<String> set2 = smallestSet(test, 1, states);
//        System.out.println(count);
//        System.out.println(set);
//        System.out.println(set2);
        segmentation("bytheway");
    }

    public static long numTies(int[] v, int K) {
        int n = v.length;
        long[][] M = new long[n+1][K+1];
        for (int i = 0; i <= n; i++) {
            M[i][0] = 1;
        }
        for (int i = 1; i <= n; i++) {
            for (int k = 1; k <= K; k++) {
                if (v[i-1] <= k) {
                    M[i][k] = M[i-1][k] + M[i-1][k - v[i-1]];
                } else {
                    M[i][k] = M[i-1][k];
                }
            }
        }
        return M[n][K];
    }

    public static ArrayList<String> smallestSet(int[] v, int K, String[] states) {
        int n = v.length;
        boolean[][] T = new boolean[n+1][K+1];
        int[][] C = new int[n+1][K+1];
        for (int i = 0; i <= n; i++) {
            T[i][0] = true;
        }
        // dynamic program
        for (int i = 1; i <= n; i++) {
            for (int k = 1; k <= K; k++) {
                if (v[i-1] <= k) {
                    T[i][k] = T[i-1][k] || T[i-1][k - v[i-1]];
                    if (T[i-1][k] && T[i-1][k - v[i-1]]) {
                        C[i][k] = Math.min(C[i-1][k-v[i-1]]+1, C[i-1][k]);
                    } else if (T[i-1][k]) {
                        C[i][k] = C[i-1][k];
                    } else if (T[i-1][k - v[i-1]]) {
                        C[i][k] = C[i-1][k-v[i-1]]+1;
                    }
                } else {
                    T[i][k] = T[i-1][k];
                    C[i][k] = C[i-1][k];
                }
            }
        }
        // creates subset
        ArrayList<String> subset = new ArrayList<String>();
        int i = n;
        int j = K;
        while (j > 0) {
            while (i > 0 && C[i][j] == C[i-1][j]) {
                i--;
            }
            if (i == 0) {
                break;
            }
            subset.add(states[i - 1]);
            j -= v[i - 1];
            i--;
        }
        return subset;
    }

    public static void segmentation(String str) {

        // Initialize values.
        String text = str;
        int [] SPLITS = new int[text.length() + 1];
        int[] OPT = new int[text.length() + 1];
        for (int index = 0; index < OPT.length; index ++) {
            OPT[index] = 0;
            SPLITS[index] = 0;
        }

        for (int i = 1; i <= text.length(); i++) {
            int tempOpt = 0;
            System.out.println();
            for (int j = 1; j <= i; j++) {
                System.out.print(" | "+text.substring(j-1,i) +" "+quality(text.substring(j-1,i))+" "+OPT[j-1] +" "+(j-1));
                //tempOpt = Math.max(tempOpt, OPT[j-1] + quality(text.substring(j-1,i)));
                if (OPT[j-1] + quality(text.substring(j-1,i)) > tempOpt) {
                    tempOpt = OPT[j-1] + quality(text.substring(j-1,i));
                    System.out.print(" X");
                    SPLITS[i] = j - 1;

                }
            }
            OPT[i] = tempOpt;
        }
        System.out.println("Max Val: " + OPT[text.length()]);

        System.out.println("Assume the first letter occupies position 1\n");
        int lastSplit = SPLITS[SPLITS.length - 1];
        int numberOfSplits = 0;
        while (lastSplit != 0) {
            numberOfSplits ++;
            System.out.println("Split After Character At Position " + lastSplit);
            lastSplit = SPLITS[lastSplit];
        }
        if (numberOfSplits <= 0) {
            System.out.println("Don't Split The Text Anywhere.");
        }

    }

    public static int quality(String s) {
        // Return positive values for real English words you
        // can find in the string bytheway.
        if (s.equals("by")) {
            return 2;
        }
        if (s.equals("the")) {
            return 8;
        }
        if (s.equals("he")) {
            return 4;
        }
        if (s.equals("hew")) {
            return 6;
        }
        if (s.equals("a")) {
            return 8;
        }
        if (s.equals("way")) {
            return 8;
        }
        // Return a negative value for strings that are not words in English.
        return -1;
    }

}
