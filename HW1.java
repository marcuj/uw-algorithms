import java.util.*;

public class hw1 {
	public static LinkedList<String> proposals;

	public static void main(String[] args) {
		// Problem 4:
		System.out.println("[ Problem 4 ]");
		int[][] mPref = { {2, 1, 3, 0},
						  {0, 1, 3, 2},
						  {0, 1, 2, 3},
						  {0, 1, 2, 3} };
		
		int[][] wPref = { {0, 2, 1, 3},
				          {2, 0, 3, 1},
				          {3, 2, 1, 0},
				          {2, 3, 1, 0} };
		
		int[] matches = matcher(mPref, wPref);
		System.out.println(Arrays.toString(matches));
		proposals.forEach(System.out::println);
		System.out.println();
		
		// Problem 5:
		System.out.println("[ Problem 5 ]");
		randomRankTest(5, 50);
		randomRankTest(50, 50);
		randomRankTest(250, 50);
		randomRankTest(1000, 50);
		randomRankTest(3000, 50);
	}
	
	// Takes sample size n, tests t times and takes the average result for the size
	public static void randomRankTest(int n, int t) {
		double[] mRanks = new double[t];
		double[] wRanks = new double[t];
		for (int i = 0; i <  t; i++) {
			int[][] mPref = generateMatrix(n);
			int[][] wPref = generateMatrix(n);
			int[] matches = matcher(mPref, wPref);
			double[] ranks = rank(matches, mPref, wPref);
			mRanks[i] = ranks[0];
			wRanks[i] = ranks[1];
		}
		double mAvg = Arrays.stream(mRanks).sum() / (double) t;
		double wAvg = Arrays.stream(wRanks).sum() / (double) t;
		System.out.println("Sample size "+n+", mean over "+t+" test: M-rank = "+mAvg+", W-rank = "+wAvg);
	}
	
	public static double[] rank(int[] matches, int[][] mPref, int[][] wPref) {
		int size = mPref.length;
		double result[] = new double[2];
		double mRank = 0.0;
		double wRank = 0.0;
		int[][] mPrefRev = revLookUp(mPref, size);
		int[][] wPrefRev = revLookUp(wPref, size);
		for (int m = 0; m < size; m++) {
			int w = matches[m];
			if (w != -1) {
				wRank += wPrefRev[w][m]+1;
				mRank += mPrefRev[m][w]+1;
			}
		}
		result[0] = mRank / (double) size;
		result[1] = wRank / (double) size;
		return result;
	}
	
	// gets random number
	public static int randNum(ArrayList<Integer> v) {
		int n = v.size();
		int index = (int)(Math.random() * n);
		int num = v.get(index);
		v.set(index, v.get(n - 1));
		v.remove(n - 1);
		return num;
	}
	
	// creates random list
	public static int[] randList(int n) {
		ArrayList<Integer> v = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			v.add(i);
		}
		int[] list = new int[n];
		for (int j = 0; j < n; j++) {
			list[j] = randNum(v);
		}
		return list;
	}
	
	// creates random preference matrix, based on Fisher-Yates shuffle
	public static int[][] generateMatrix(int n) {
		int[][] mat = new int[n][n];
		for (int i = 0; i < n; i++) {
			int[] temp = randList(n);
			mat[i] = temp;
		}
		return mat;
	}
	
	public static int[][] revLookUp(int[][] mat, int size) {
		int[][] rev = new int[size][size]; // reverse look up
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int m1 = mat[i][j];
				rev[i][m1] = j;
				}
		}
		return rev;
	}
	
	public static int[] matcher(int[][] mPref, int[][] wPref) {		
		int size = mPref.length; // # of items
		int wCol = 0;            // index of preference list
		int numMatched = 0;      // # of matched m's
		int[] mPartner = new int[size];
		int[] wPartner = new int[size];
		Arrays.fill(mPartner, -1);
		Arrays.fill(wPartner, -1);
		boolean[] mMatched = new boolean[size]; 
		boolean[] wMatched = new boolean[size];
		proposals = new LinkedList<String>();
		int[][] wPrefRev = revLookUp(wPref, size);
		while (numMatched <= size && wCol < size) {
			for (int m = 0; m < size; m++) {
				int mChoice = mPref[m][wCol];
				int m2 = wPartner[mChoice];
				if (mMatched[m] == false) {
					if (wMatched[mChoice] == false) {
						mPartner[m] = mChoice;
						mMatched[m] = true;
						wPartner[mChoice] = m;  
						wMatched[mChoice] = true;
						proposals.add(m +" proposes to "+mChoice+" ["+mChoice+", "+m2+"] "+"(Accepted)");
						numMatched++;
					} else {
						if (wPrefRev[mChoice][m] < wPrefRev[mChoice][m2]) { // check preference
							mPartner[m] = mChoice;
							mPartner[m2] = -1;
							wPartner[mChoice] = m;  
							wMatched[mChoice] = true;
							mMatched[m] = true;
							mMatched[m2] = false;
							proposals.add(m +" proposes to "+mChoice+" ["+mChoice+", "+m2+"] "+"(Accepted)");
						} else {
							proposals.add(m +" proposes to "+mChoice+" ["+mChoice+", "+m2+"] "+"(Rejected)");
						}
					}
				}
			}
			wCol++;
		}
		return mPartner;
	}

}
