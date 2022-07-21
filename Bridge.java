import java.util.*;

public class Bridge
{
	//store number of pedestals
	public static int v;
	//search tree graph
	public static ArrayList<Integer>[] graph2;
	//store diameter
	public static long answer;
	
	//method to find the diameter
	public static void findDiameter(ArrayList<Integer>[] graph)
	{
		//max distance
		int max = 0;
		//bfs in search tree to get diameter point
		int start = bfs(0, graph);
		//bfs from that diameter point
		boolean[] vis = new boolean[v];
		vis[start] = true;
		int[] dist = new int[v];
		int[] q = new int[v];
		int fPtr = 0;
		int bPtr = 0;
		q[bPtr++] = start;
		while (fPtr != bPtr)
		{
			int cur = q[fPtr++];
			for (Integer next : graph[cur])
			{
				if (!vis[next])
				{
					vis[next] = true;
					dist[next] = dist[cur] + 1;
					q[bPtr++] = next;
				}
			}
		}
		//get the max distance from the dist array
		for(int i = 0; i < dist.length; i++)
		{
			if(dist[i] > max)
			{
				max = dist[i];
			}
		}
		//reduce the diameter
		if(answer > max)
		{
			answer = max;
		}
	}
	
	//method to generate search tree from graph
	public static ArrayList<Integer>[] genTree(int start, ArrayList<Integer>[] graph)
	{
		//initialize new search tree
		graph2 = new ArrayList[v];
		for (int i = 0; i < v; i++)
		{
			graph2[i] = new ArrayList<Integer>();
		}
		//bfs to make the tree
		boolean[] vis = new boolean[v];
		vis[start] = true;
		int[] q = new int[v];
		int fPtr = 0;
		int bPtr = 0;
		q[bPtr++] = start;
		while (fPtr != bPtr)
		{
			int cur = q[fPtr++];
			for (Integer next : graph[cur])
			{
				//don't add a node if it creates a cycle
				if (!vis[next])
				{
					//add unvisited nodes to the tree in undirected manner
					graph2[cur].add(next);
					graph2[next].add(cur);
					vis[next] = true;
					q[bPtr++] = next;
				}
			}	
		}
		return graph2;
	}
	
	//method for bfs that returns diameter points
	public static int bfs(int start, ArrayList<Integer>[] tree)
	{
		//store visited nodes
		boolean[] vis = new boolean[v];
		//mark start node as visited
		vis[start] = true;
		//queue for nodes to be processed
		int[] q = new int[v];
		int fPtr = 0;
		int bPtr = 0;
		q[bPtr++] = start;
		while (fPtr != bPtr)
		{
			//get and remove the front
			int cur = q[fPtr++]; 
			//loop through the tree
			for (Integer next : tree[cur])
			{
				//don't use any visited nodes
				if (!vis[next])
				{
					//set current node to true
					vis[next] = true;
					//update queue back pointer next node
					q[bPtr++] = next;
				}
			}
		}
		//return last node in queue
		return q[bPtr - 1];
	}
		
	public static void main(String[] args)
	{
		//number of edges
		int e;
		//read in input
		Scanner sc = new Scanner(System.in);
		v = sc.nextInt();
		e = sc.nextInt();
		//initialize new undirected graph
		ArrayList<Integer>[] bridge = new ArrayList[v];
		for (int i = 0; i < v; i++)
		{
			bridge[i] = new ArrayList<Integer>();
		}
		//store the inputs
		for(int i = 0; i < e; i++)
		{
			int start, end;
			start = sc.nextInt() - 1;
			end = sc.nextInt() - 1;
			//add edges both ways
			bridge[start].add(end);
			bridge[end].add(start);
		}
		//close scanner
		sc.close();
		//initialize the answer to large value
		answer = Long.MAX_VALUE;
		//try ever node as center
		for(int i = 0; i < v; i++)
		{
			//find the diameter
			findDiameter(genTree(i, bridge));
		}
		//print out the max reduced diameter
		System.out.println(answer);
	}
}
