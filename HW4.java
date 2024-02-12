import java.text.DecimalFormat;
import java.util.*;

public class hw4 {

	public static void main(String[] args) {
		
		int n = 1000;
		System.out.println("[  Standard   ]");
		System.out.println("   p        C       d");
		for (double p = 0.002; p <= 0.021; p += 0.002) {
			DecimalFormat df = new DecimalFormat("0.000");
			System.out.println(" "+df.format(p)+"  |  "+normGraphTester(n, p, 10)[0]+"  |  "+normGraphTester(n, p, 10)[1]);
		}
		System.out.println();
		System.out.println("[ Incr Degree ]");
		System.out.println("   p        C       d");
		for (double p = 0.002; p <= 0.021; p += 0.002) {
			DecimalFormat df = new DecimalFormat("0.000");
			System.out.println(" "+df.format(p)+"  |  "+incrDegGraphTester(n, p, 10)[0]+"  |  "+incrDegGraphTester(n, p, 10)[1]);
		}
		System.out.println();
		System.out.println("[ Decr Degree ]");
		System.out.println("   p        C       d");
		for (double p = 0.002; p <= 0.021; p += 0.002) {
			DecimalFormat df = new DecimalFormat("0.000");
			System.out.println(" "+df.format(p)+"  |  "+decrDegGraphTester(n, p, 10)[0]+"  |  "+decrDegGraphTester(n, p, 10)[1]);
		}
	}
	
	public static double[] incrDegGraphTester(int n, double p, int times) {
		int tot = 0;
		int tot_max_degree = 0;
		for (int i = 0; i < times; i++) {
			LinkedList<Integer>[] graph = hw3.randGraph(n, p);
			tot_max_degree += maxDegree(graph, n);
			int color_count = incrDegGraphColor(graph, n);
			tot += color_count;
		}
		return new double[] {(double) tot / (double) times, (double) tot_max_degree / (double) times};
	}
	
	public static double[] decrDegGraphTester(int n, double p, int times) {
		int tot = 0;
		int tot_max_degree = 0;
		for (int i = 0; i < times; i++) {
			LinkedList<Integer>[] graph = hw3.randGraph(n, p);
			tot_max_degree += maxDegree(graph, n);
			int color_count = decrDegGraphColor(graph, n);
			tot += color_count;
		}
		return new double[] {(double) tot / (double) times, (double) tot_max_degree / (double) times};
	}
	
	public static double[] normGraphTester(int n, double p, int times) {
		int tot = 0;
		int tot_max_degree = 0;
		for (int i = 0; i < times; i++) {
			LinkedList<Integer>[] graph = hw3.randGraph(n, p);
			tot_max_degree += maxDegree(graph, n);
			int color_count = normGraphColor(graph, n);
			tot += color_count;
		}
		return new double[] {(double) tot / (double) times, (double) tot_max_degree / (double) times};
	}
	
	public static int maxDegree(LinkedList<Integer>[] adj_list, int n) {
		int max_degree = 0;
		for (int j = 0; j < n; j++) {
			if (adj_list[j].size() > max_degree) {
				max_degree = adj_list[j].size();
			}
		}
		return max_degree;
	}
	
	public static int incrDegGraphColor(LinkedList<Integer>[] adj_list, int n) {
		for (int i = 0; i < n; i++) {
			adj_list[i].addFirst(i);
		}
		Arrays.sort(adj_list, Comparator.comparing(LinkedList::size));
		return graphColor(adj_list, n);
	}
	
	public static int decrDegGraphColor(LinkedList<Integer>[] adj_list, int n) {
		for (int i = 0; i < n; i++) {
			adj_list[i].addFirst(i);
		}
		Arrays.sort(adj_list, Collections.reverseOrder(Comparator.comparing(LinkedList::size)));
		return graphColor(adj_list, n);
	}
	
	public static int normGraphColor(LinkedList<Integer>[] adj_list, int n) {
		for (int i = 0; i < n; i++) {
			adj_list[i].addFirst(i);
		}
		return graphColor(adj_list, n);
	}

	public static int graphColor(LinkedList<Integer>[] adj_list, int n) {
		int count = 0;
		int[] v_colors = new int[n]; // 0 = uncolored, 1+ = colors
		for (int i = 0; i < n; i++) {
			LinkedList<Integer> nbrs = adj_list[i];
			int v = nbrs.remove();
			if (count != 0) {		
				int[] pot_colors = new int[count+1];	
				for (int nbr : nbrs) {	
					int color = v_colors[nbr];
					pot_colors[color] = 1;
				}
				for (int j = 1; j <= count; j++) {
					int color = pot_colors[j];
					if (color == 0) {
						v_colors[v] = j;
						break;
					}
				}
			}
			if (v_colors[v] == 0) {
				count += 1;
				v_colors[v] = count;
			}
		}
		return count;
	}
}
