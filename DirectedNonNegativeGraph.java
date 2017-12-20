import java.util.*;

class DirectedNonNegativeGraph<T>
{
	final static String INVALID_ARGUMENT = "Invalid argument.";
	
	@SuppressWarnings("hiding")
	private class Node<T>
	{
		public Map<T, Double> neighbours;
		
		public Node()
		{
			this.neighbours = new HashMap<T, Double>();
		}
	};
	
	private Map<T, Node<T>> graph;
	
	public DirectedNonNegativeGraph()
	{
		this.graph = new HashMap<T, Node<T>>();
	}
	
	public void addNode(final T id)
	{
		this.graph.put(id, new Node<T>());
	}
	
	public void connectNode(final T src, final T dst, final Double weight) throws Exception
	{
		if (weight < 0 || src == dst)
		{
			throw new Exception(INVALID_ARGUMENT);
		}
		else
		{
			this.graph.get(src).neighbours.put(dst, weight);
		}
	}
	
	/**
	 * @brief Returns the distance between a node and its adjacent neighbour. INFINITY if nodes are not adjacent.
	 * @param node
	 * @param neighbour
	 * @return
	 */
	public Double getAdjacentDistance(final T node, final T neighbour)
	{
		try
		{
			return this.graph.get(node).neighbours.get(neighbour);
		}
		catch (Exception e)
		{
			return Double.POSITIVE_INFINITY;
		}
	}
	
	/**
	 * @brief Returns the shortest distance between two nodes. INFINITY if path does not exist.
	 * @param src
	 * @param dst
	 * @return
	 */
	public Integer getShortestDistance(final T src, final T dst)
	{
		class Container
		{
			public T node;
			public Double cost;
			
			public Container(final T node, final Double cost)
			{
				this.node = node;
				this.cost = cost;
			}
		};
		
		class CComparator implements Comparator<Container>
		{
			@Override
			public int compare(Container x, Container y)
			{
				Double value = x.cost - y.cost;
				return value.intValue();
			}
		};
		
		Comparator<Container> comparator = new CComparator();
		PriorityQueue<Container> Q = new PriorityQueue<Container>(comparator);
		
		// Dijkstra's algorithm
		
		Map<T, Double> dist = new HashMap<T, Double>();
		
		dist.put(src, 0.0);
		
		for (Map.Entry<T, Node<T>> v : this.graph.entrySet())
		{
			if (v.getKey() != src)
			{
				dist.put(v.getKey(), Double.POSITIVE_INFINITY);
			}
			Q.add(new Container(v.getKey(), dist.get(v.getKey())));
		}
		
		while (!Q.isEmpty())
		{
			Container u = Q.remove();
			
			for (Map.Entry<T, Double> v : this.graph.get(u.node).neighbours.entrySet())
			{
				Container alt = new Container(u.node, u.cost + this.getAdjacentDistance(u.node, v.getKey()));
				
				if (alt.cost < v.getValue())
				{
					v.setValue(alt.cost);
				}
			}
		}
		
		return 0;
	}
}
