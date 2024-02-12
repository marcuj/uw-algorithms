import java.util.*;

public class HW7 {

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            tester(i);
        }
    }

    public static void tester(int i) {
        int[][] ints = randIntervals(10000,1000000,2000,100);
        ArrayList<Integer> est = greedyEST(ints);
        ArrayList<Integer> mvf = greedyMVF(ints);
        ArrayList<Integer> mvd = greedyMVD(ints);
        ArrayList<Integer> dp = schedulingDP(ints);
        Arrays.sort(ints, Comparator.comparingInt(x -> x[0]));
        System.out.println("      [Trial "+i+"]");
        System.out.println("       s      v");
        System.out.println("EST:  "+est.size()+"   "+sum(est, ints));
        System.out.println("MVF:  "+mvf.size()+"  "+sum(mvf, ints));
        System.out.println("MVD:  "+mvd.size()+"  "+sum(mvd, ints));
        System.out.println(" DP:  "+dp.size()+"  "+sum(dp, ints));
        System.out.println();
    }

    public static int sum(ArrayList<Integer> sol, int[][] ints) {
        int sum = 0;
        for (Integer ind : sol) {
            sum += ints[ind][3];
        }
        return sum;
    }

    public static int[][] randIntervals(int n, int L, int r, int v) {
        Random rand = new Random();
        int[][] ints = new int[n][4];
        for (int i = 0; i < n; i++) {
            ints[i][0] = i;                   // interval name
            ints[i][1] = rand.nextInt(L) + 1; // start
            ints[i][2] = rand.nextInt(r) + 1; // length
            ints[i][3] = rand.nextInt(v) + 1; // value
        }
        return ints;
    }

    public static ArrayList<Integer> schedulingDP(int[][] ints) {
        Arrays.sort(ints, Comparator.comparingInt(x -> x[1]+x[2]));
        int n = ints.length;
        int[] M = new int[n+1];
        int[] p = new int[n+1];
        int[] C = new int[n+1]; // tracker, -1 for false, >=0 value for pointer
        M[0] = 0;
        p[0] = 0;
        // p[j] computation
        for (int i = 0; i < n; i++) {
            for (int j = i-1; j >= 0; j--) {
                if (ints[i][1] >= ints[j][1]+ints[j][2]) {
                    p[i+1] = j+1;
                    break;
                }
            }
        }
        // iteration algorithm
        for (int i = 1; i <= n; i++) {
            if (M[i-1] >= ints[i-1][3]+M[p[i]]) {
                M[i] = M[i-1];
                C[i] = -1;
            } else {
                M[i] = ints[i-1][3] + M[p[i]];
                C[i] = p[i];
            }
        }
        // construct list solution
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = n; i > 0; i--) {
            if (C[i] != -1) {
                result.add(ints[i-1][0]);
                i = C[i]+1;
            }
        }
        return result;
    }

    public static ArrayList<Integer> deoverlap(int[][] ints) {
        Map<Integer, Integer> result = new HashMap<>();
        result.put(0, ints[0][0]);
        for (int i = 1; i < ints.length; i++) {
            boolean clear = true;
            int c_st = ints[i][1];                // curr start
            int c_end = ints[i][1] + ints[i][2];  // curr end
            for (int j : result.keySet()) {
                int n_st = ints[j][1];                // next start
                int n_end = ints[j][1] + ints[j][2];  // next end
                if (((c_st < n_end && c_st >= n_st) ||          // start in interval
                        (c_end <= n_end && c_end > n_st)) ||    // end in interval
                        (c_st <= n_st && c_end >= n_end)) {     // covers the interval
                    clear = false;
                    break;
                }
            }
            if (clear) {
                result.put(i, ints[i][0]);
            }
        }
        return new ArrayList<>(result.values());
    }

    public static ArrayList<Integer> greedyEST(int[][] ints) {
        Arrays.sort(ints, Comparator.comparingInt(x -> x[1]));
        return deoverlap(ints);
    }

    public static ArrayList<Integer> greedyMVF(int[][] ints) {
        Arrays.sort(ints, Collections.reverseOrder(Comparator.comparingInt(x -> x[3])));
        return deoverlap(ints);
    }

    public static ArrayList<Integer> greedyMVD(int[][] ints) {
        Arrays.sort(ints, Collections.reverseOrder(Comparator.comparingDouble(x -> (double) x[3] / (double) x[2])));
        return deoverlap(ints);
    }

}
