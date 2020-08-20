/**
 * WeightedUndirectedGraph.java : An abstract class to dictate basic implementations of a graph
 * 
 * @author Michael W Flaherty
 * @version 1.0
 */

package bot.Markov;

import java.util.Set;

public abstract class WeightedUndirectedGraph<V, E>
{
	/**
	 * Adds the specified vertex into the graph, if it does not already exist in the graph.
	 * @param vertex vertex to add
	 */
	protected abstract void addVertex(V vertex);
	
	/**
	 * Creates an edge between the to vertices
	 * @param source Source vertex
	 * @param target Target vertex
	 */
	protected void addEdge(V source, V target)
	{
		this.addEdge(source, target, SimpleEdge.DEFAULT_WEIGHT);
	}
	
	/**
	 * Creates an edge between the to vertices
	 * @param source Source vertex
	 * @param target Target vertex
	 * @param weight for the edge
	 */
	protected abstract void addEdge(V source, V target, int weight);

	/**
	 * Returns a set of all edges of a given vertex
	 * @param vertex to get the Set for
	 * @return a set of edges
	 */
	protected abstract Set<E> edgeSet(V vertex);
}
