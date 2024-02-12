import java.util.LinkedList;
import java.util.Queue;

public class hw3 {

	public static void main(String[] args) {
		// Problem 4.
		// LinkedList<Integer>[] graph1 = randGraph(10, 0.2);
		// printAdjList(graph1, 10);
		// LinkedList<Integer>[] graph2 = randGraph(10, 0.2);
		// printAdjList(graph2, 10);
		
		// Problem 5.
		int n = 1000;
		double p = 1;
		// Dense graphs:
		p = 0.13; // 2 diameter
		LinkedList<Integer>[] graph2 = randGraph(n, p);
		graphDiameter(graph2, n);
		p = 0.03; // 3 diameter
		LinkedList<Integer>[] graph3 = randGraph(n, p);
		graphDiameter(graph3, n);
		p = 0.013; // 5 diameter
		LinkedList<Integer>[] graph4 = randGraph(n, p);
		graphDiameter(graph4, n);
		// Sparse graphs:
		p = 0.007; // 1 component, 7 diameter
		LinkedList<Integer>[] graph5 = randGraph(n, p);
		graphDiameter(graph5, n);
		p = 0.003; // 50 components, 14 diameter
		LinkedList<Integer>[] graph9 = randGraph(n, p);
		graphDiameter(graph9, n);
		p = 0.0009; // 560 components, 28 diameter
		LinkedList<Integer>[] graph6 = randGraph(n, p);
		graphDiameter(graph6, n);
		p = 0.0005; // 740 components, 8 diameter
		LinkedList<Integer>[] graph7 = randGraph(n, p);
		graphDiameter(graph7, n);
		p = 0.0001; // 940 components, 3 diameter
		LinkedList<Integer>[] graph8 = randGraph(n, p);
		graphDiameter(graph8, n);
		p = 0.00005; // 976 components, 2 diameter
		LinkedList<Integer>[] graph10 = randGraph(n, p);
		graphDiameter(graph10, n);
	}
	
	public static int graphDiameter(LinkedList<Integer>[] adj_list, int n) {
		int fin_diameter = 0;
		boolean unconnected = false;
		boolean[] visited_total = new boolean[n];
		int components = 0;
		for (int i = 0; i < n; i++) {
			Queue<Integer> queue = new LinkedList<>();
		    boolean[] visited = new boolean[n];
		    int num_visited = 1;
		    int[] dist = new int[n];
		    int max_dist = 0;
		    visited[i] = true;
		    if (visited_total[i] == false) {
		    	visited_total[i] = true;
		    	components += 1;
		    }
		    dist[i] = 0;
		    queue.add(i);
		    while (!queue.isEmpty()) {
		    	int v = queue.remove();
		    	for (int v_next : adj_list[v]) {
		    		if (!visited[v_next]) {
		    			dist[v_next] = dist[v] + 1;
		    			visited[v_next] = true;
		    			visited_total[v_next] = true;
		    			num_visited += 1;
		    			queue.add(v_next);
		    			if (dist[v_next] > max_dist) {
		    				max_dist = dist[v_next];
		    			}
		    		}
		    	}
		    }
		    if (max_dist > fin_diameter) {
		    	fin_diameter = max_dist;
		    }
		    if (num_visited < n) {
		    	unconnected = true;
		    }
		}
		if (unconnected) {
			System.out.println("Diameter: infinite ("+components+" components), Finite diameter: "+fin_diameter);
		} else {
			System.out.println("Diameter: "+fin_diameter+" ("+components+" component), Finite diameter: "+fin_diameter);

		}
		return fin_diameter;
	}
	
	public static LinkedList<Integer>[] randGraph(int n, double p) {
		@SuppressWarnings("unchecked")
		LinkedList<Integer>[] adj_list = new LinkedList[n];
		for (int i = 0; i < n; i++) {
			adj_list[i] = new LinkedList<Integer>();
		}
		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				if (Math.random() < p) {
					adj_list[i].add(j);
					adj_list[j].add(i);
				}
			}
		}
		return adj_list;
	}
	
	public static void printAdjList(LinkedList<Integer>[] adj_list, int n) {
		System.out.println("(V) (Edges to)");
		for (int i = 0; i < n; i++) {
			System.out.println(" "+i+"  "+adj_list[i]);
		}
		System.out.println();
	}

}
