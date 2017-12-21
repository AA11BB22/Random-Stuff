import java.util.*;
import java.util.LinkedList;

/**
 * The algorithm uses a map implementation and Dijkstra's for shortest path.
 * Some weird functions are added to answer some questions ...
 *
 * Assumptions:
 * 	1. No self loop.
 * 	2. No negative weights.
 * 	3. Only one path per direction for each connection.
 *
 * @param <T>
 */
public final class DirectedNonNegativeGraph<T>
{
    final static String INVALID_ARGUMENT = "Invalid argument.";
    
    @SuppressWarnings("hiding")
    private class Node<T>
    {
        public Map<Node<T>, Double> neighbours;
        
        public Node()
        {
            neighbours = new HashMap<Node<T>, Double>();
        }
    };
    
    private Map<T, Node<T>> graph;
    
    public DirectedNonNegativeGraph()
    {
        graph = new HashMap<T, Node<T>>();
    }
    
    public void addNode(final T id)
    {
        if (!graph.containsKey(id))
        {
            graph.put(id, new Node<T>());
        }
    }
    
    public void connectNode(final T src, final T dst, final Double weight) throws Exception
    {
        final Node<T> s = graph.get(src);
        final Node<T> d = graph.get(dst);
        
        if (s == d || s == null || d == null || weight < 0)
        {
            throw new Exception(INVALID_ARGUMENT);
        }
        else
        {
            s.neighbours.put(d, weight);
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
        if (graph.containsKey(node) && graph.containsKey(neighbour))
        {
            final Node<T> s = graph.get(node);
            final Node<T> d = graph.get(neighbour);
            
            if (s.neighbours.containsKey(d))
            {
                return s.neighbours.get(d);
            }
            else
            {
                return Double.POSITIVE_INFINITY;
            }
        }
        else
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
    public Double getShortestDistance(final T src, final T dst)
    {
        @SuppressWarnings("hiding")
        class Container<T>
        {
            public Node<T> node;
            public Double distance;
            
            public Container(final Node<T> node, final Double distance)
            {
                this.node = node;
                this.distance = distance;
            }
        };
        
        class MinHeap extends Heap<Container<T>>
        {
            @Override
            public int compare(final Container<T> x, final Container<T> y)
            {
                Double value = x.distance - y.distance;
                return value.intValue();
            }
        };
        
        // Dijkstra's algorithm - not the most optimized version
        
        if (src != dst)
        {
            final Node<T> s = graph.get(src);
            final Node<T> d = graph.get(dst);
            
            Map<Node<T>, Double> dist = new HashMap<Node<T>, Double>();
            dist.put(s, 0.0);
            
            MinHeap Q = new MinHeap();
            Map<Node<T>, Container<T>> ref = new HashMap<Node<T>, Container<T>>();
            
            for (Node<T> v : graph.values())
            {
                if (v != s)
                {
                    dist.put(v, Double.POSITIVE_INFINITY);
                }
                
                final Container<T> newItem = new Container<T>(v, dist.get(v));
                ref.put(v, newItem);
                Q.push(newItem);
            }
            
            while (!Q.isEmpty())
            {
                Container<T> q = Q.pop();
                final Node<T> u = q.node;
                
                if (u == d)
                {
                    return dist.get(u);
                }
                
                for (Map.Entry<Node<T>, Double> n : u.neighbours.entrySet())
                {
                    final Node<T> v = n.getKey();
                    final Double len = n.getValue();
                    final Double alt = dist.get(u) + len;
                    
                    if (alt < dist.get(v))
                    {
                        dist.put(v, alt);
                        final Container<T> newItem = new Container<T>(v, alt);
                        Q.update(ref.get(v), newItem);
                        ref.put(v, newItem);
                    }
                }
            }
            
            return Double.POSITIVE_INFINITY;
        }
        else // Path to self
        {
            Node<T> s = graph.get(src);
            
            List<T> possibleNodes = new LinkedList<T>();
            for (T p : graph.keySet())
            {
                if (src != p && graph.get(p).neighbours.containsKey(s))
                {
                    possibleNodes.add(p);
                }
            }
            
            Double shortestDistance = Double.POSITIVE_INFINITY;
            for (T p : possibleNodes)
            {
                shortestDistance = Math.min(shortestDistance,
                                            graph.get(p).neighbours.get(s) + getShortestDistance(src, p));
            }
            
            return shortestDistance;
        }
    }
    
    private class IntegerContainer
    {
        public int intByRef;
        
        public IntegerContainer()
        {
            intByRef = 0;
        }
    };
    
    public int findAllPathsWithinLimit(final T src, final T dst, final Double limit)
    {
        IntegerContainer count = new IntegerContainer();
        count.intByRef = -1;
        
        final Node<T> s = graph.get(src);
        final Node<T> d = graph.get(dst);
        
        recursiveDFS(s, d, 0.0, limit, count);
        
        return count.intByRef;
    }
    
    /**
     * INCLUDES duplicates !!!
     * @param c
     * @param d
     * @param current
     * @param limit
     * @param count
     */
    private void recursiveDFS(final Node<T> c, final Node<T> d, Double current, final Double limit, IntegerContainer count)
    {
        if (current > limit)
        {
            return;
        }
        
        if (c == d)
        {
            count.intByRef ++;
        }
        
        for (Map.Entry<Node<T>, Double> n : c.neighbours.entrySet())
        {
            recursiveDFS(n.getKey(), d, current + n.getValue(), limit, count);
        }
    }
    
    public int findAllPathsEqualToEdges(final T src, final T dst, final int edges)
    {
        IntegerContainer count = new IntegerContainer();
        
        final Node<T> s = graph.get(src);
        final Node<T> d = graph.get(dst);
        
        recursiveDFS2(s, d, 0, edges, count);
        
        return count.intByRef;
    }
    
    private void recursiveDFS2(final Node<T> c, final Node<T> d, int current, final int edges, IntegerContainer count)
    {
        if (current > edges)
        {
            return;
        }
        
        if (c == d && current == edges)
        {
            count.intByRef ++;
        }
        
        for (Map.Entry<Node<T>, Double> n : c.neighbours.entrySet())
        {
            recursiveDFS2(n.getKey(), d, current + 1, edges, count);
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        DirectedNonNegativeGraph<Character> g = new DirectedNonNegativeGraph<Character>();
        
        g.addNode('A');
        g.addNode('B');
        g.addNode('C');
        
        g.connectNode('A', 'B', 4.0);
        g.connectNode('A', 'C', 1.0);
        g.connectNode('B', 'A', 4.0);
        g.connectNode('C', 'A', 3.0);
        
        System.out.println(g.getShortestDistance('B', 'C'));
        System.out.println(g.findAllPathsWithinLimit('A', 'A', 10.0));
        System.out.println(g.findAllPathsEqualToEdges('A', 'A', 4));
    }
}
