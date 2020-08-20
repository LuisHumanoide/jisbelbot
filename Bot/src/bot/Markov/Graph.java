/**
 * Graph.java : A simple Graph data structure using an adjacency list implementation.
 *
 * @author Michael W Flaherty
 * @version 1.1
 */

package bot.Markov;

import java.util.HashSet;
import java.util.Set;

public class Graph<T> extends WeightedUndirectedGraph<SimpleVertex<String>, SimpleEdge<String>>
{
    protected HashSet<SimpleVertex<String>> adjList;

    public Graph()
    {
        this.adjList = new HashSet<>();
    }

    @Override
    protected void addVertex(SimpleVertex<String> vertex)
    {
        if (vertex.getAdjacencies().size() == 0)
        {
            return;
        }

        boolean retVal = adjList.add(vertex);

        if (!retVal)
        {
            for (SimpleVertex<String> x : this.adjList)
            {
                if (x.equals(vertex))
                {
                    x.addAdjacencies(vertex.getAdjacencies());
                }
            }
        }

        HashSet<SimpleEdge<String>> set = vertex.getAdjacencies();
        for (SimpleEdge<String> edge : set)
        {
            if (edge != null)
            {
                this.addVertex(edge.getTo());
            }
        }
    }

    @Override
    protected void addEdge(SimpleVertex<String> source, SimpleVertex<String> target, int weight)
    {
        SimpleEdge<String> edge = new SimpleEdge<>(target, weight);

        source.getAdjacencies().add(edge);
    }

    @Override
    protected Set<SimpleEdge<String>> edgeSet(SimpleVertex<String> vertex)
    {
        return new HashSet<>(vertex.getAdjacencies());
    }
}
