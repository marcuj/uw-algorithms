import java.text.DecimalFormat;
import java.util.Random;

public class HW9 {

    public static void main(String[] args) {
        testes(50000, 16, 3);
    }

    public static void testes(int n, int k, int trials) {
        System.out.println(" k   LCS   gamma");
        for (int i = 1; i <= k; i++) {
            int lcs = 0;
            for (int j = 0; j < trials; j++) {
                int[] str1 = randString(n, i);
                int[] str2 = randString(n, i);
                lcs += leastCommonSubstring(str1, str2);
            }
            double avglcs = (double) lcs / (double) trials;
            double gamma = avglcs / (double) n;
            DecimalFormat df = new DecimalFormat("#.####");
            DecimalFormat df2 = new DecimalFormat("#.#");
            if (i < 10) {
                System.out.println(" "+i+"  "+df2.format(avglcs)+"  "+df.format(gamma));
            } else {
                System.out.println(i + "  " + df2.format(avglcs) + "  " + df.format(gamma));
            }
        }
    }

    public static int[] randString(int n, int k) {
        Random r = new Random();
        int[] string = new int[n];
        for (int i = 0; i < n; i++) {
            string[i] = r.nextInt(k);
        }
        return string;
    }
    public static int leastCommonSubstring(int[] str1, int[] str2) {
        int n = str1.length;
        int[] prevRow = new int[n+1];
        int[] currRow = new int[n+1];
        for (int i = 1; i <= n; i++) {
            currRow[0] = 0;
            for (int j = 1; j <= n; j++) {
                if (str1[i-1] == str2[j-1]) {
                    currRow[j] = prevRow[j-1]+1;
                } else if (prevRow[j] >= currRow[j-1]) {
                    currRow[j] = prevRow[j];
                } else {
                    currRow[j] = currRow[j-1];
                }
            }
            for (int j = 1; j <= n; j++) {
                prevRow[j] = currRow[j];
            }
        }
        return currRow[n];
    }
}
