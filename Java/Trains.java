import java.util.*;

public class Trains
{
    private DirectedNonNegativeGraph<Character> graph;
    
    public Trains()
    {
        graph = new DirectedNonNegativeGraph<Character>();
    }
    
    /**
     * Example argument: "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7"
     * @param s
     * @throws Exception
     */
    public void constructGraph(final String s) throws Exception
    {
        final String[] split = s.split(", ");
        
        for (String item : split)
        {
            final Character n1 = item.charAt(0);
            final Character n2 = item.charAt(1);
            final Double cost = Double.parseDouble(item.substring(2));
            
            graph.addNode(n1);
            graph.addNode(n2);
            graph.connectNode(n1, n2, cost);
        }
    }
    
    /**
     * Example argument: "A-B-C"
     * @param s
     * @return
     * @throws Exception
     */
    public Double getRouteDistance(final String s) throws Exception
    {
        final String[] split = s.split("-");
        List<Character> nodes = new ArrayList<Character>();
        for (String item : split)
        {
            if (item.length() > 1)
            {
                throw new Exception("...");
            }
            else
            {
                nodes.add(item.charAt(0));
            }
        }
        
        Double dist = 0.0;
        for (int i = 1; i < nodes.size(); i ++)
        {
            dist = dist + graph.getAdjacentDistance(nodes.get(i - 1), nodes.get(i));
        }
        
        return dist;
    }
    
    /**
     * Example argument: "A-C"
     * @param s
     * @return
     * @throws Exception
     */
    public Double getShortestRoute(final String s) throws Exception
    {
        final String[] split = s.split("-");
        if (split.length != 2 || split[0].length() > 1 || split[1].length() > 1)
        {
            throw new Exception("...");
        }
        
        return graph.getShortestDistance(split[0].charAt(0), split[1].charAt(0));
    }
    
    /**
     * Example argument: "C-C-29"
     * @param s
     * @return
     * @throws Exception
     */
    public int getNumberOfPathsWithinLimit(final String s) throws Exception
    {
        final String[] split = s.split("-");
        if (split.length != 3 || split[0].length() > 1 || split[1].length() > 1)
        {
            throw new Exception("...");
        }
        return graph.findAllPathsWithinLimit(split[0].charAt(0), split[1].charAt(0), Double.parseDouble(split[2]));
    }
    
    /**
     * Example argument: "A-C-4"
     * @param s
     * @return
     * @throws Exception
     */
    public int getNumberOfPathsEqualToEdge(final String s) throws Exception
    {
        final String[] split = s.split("-");
        if (split.length != 3 || split[0].length() > 1 || split[1].length() > 1)
        {
            throw new Exception("...");
        }
        return graph.findAllPathsEqualToEdges(split[0].charAt(0), split[1].charAt(0), Integer.parseInt(split[2]));
    }
    
    /**
     * Example argument: "A-C-4"
     * @param s
     * @return
     * @throws Exception
     */
    public int getNumberOfPathsWithinEdge(final String s) throws Exception
    {
        final String[] split = s.split("-");
        if (split.length != 3 || split[0].length() > 1 || split[1].length() > 1)
        {
            throw new Exception("...");
        }
        int n = 0;
        for (int i = 1; i <= Integer.parseInt(split[2]); i ++)
        {
            n = n + graph.findAllPathsEqualToEdges(split[0].charAt(0), split[1].charAt(0), i);
        }
        return n;
    }
    
    public static void main(String[] args) throws Exception
    {
        // Create graph.
        final String in0 = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        
        // Inputs.
        final String in1 = "A-B-C";
        final String in2 = "A-D";
        final String in3 = "A-D-C";
        final String in4 = "A-E-B-C-D";
        final String in5 = "A-E-D";
        final String in6 = "C-C-3";
        final String in7 = "A-C-4";
        final String in8 = "A-C";
        final String in9 = "B-B";
        final String in10 = "C-C-29";
        
        Trains t = new Trains();
        t.constructGraph(in0);
        
        // Outputs.
        System.out.println("1: " + t.getRouteDistance(in1));
        System.out.println("2: " + t.getRouteDistance(in2));
        System.out.println("3: " + t.getRouteDistance(in3));
        System.out.println("4: " + t.getRouteDistance(in4));
        System.out.println("5: " + t.getRouteDistance(in5));
        System.out.println("6: " + t.getNumberOfPathsWithinEdge(in6));
        System.out.println("7: " + t.getNumberOfPathsEqualToEdge(in7));
        System.out.println("8: " + t.getShortestRoute(in8));
        System.out.println("9: " + t.getShortestRoute(in9));
        System.out.println("10: " + t.getNumberOfPathsWithinLimit(in10));
    }
}
