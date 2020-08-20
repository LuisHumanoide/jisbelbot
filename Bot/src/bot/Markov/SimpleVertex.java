/**
 * SimpleVertex.java : A simple vertex class for a graph structure
 * 
 * @author Michael W Flaherty
 * @version 1.0
 */

package bot.Markov;

import java.util.HashSet;

public class SimpleVertex<T>
{
	private T data;
	private HashSet<SimpleEdge<T>> adjacencies;

	/**
	 * Gets the data stored.
	 * 
	 * @return the data
	 */
	public T getData()
	{
		return data;
	}

	/**
	 * Sets the data to store
	 * 
	 * @param data the data to set
	 */
	public void setData(T data)
	{
		this.data = data;
	}
	
	/**
	 * Adds input adjacencies to the calling object's adjacencies, merging and
	 * incrementing weights as needed.
	 * 
	 * @param inputEdge edges to input
	 */
	public void addAdjacencies(HashSet<SimpleEdge<T>> inputEdge)
	{
		for (SimpleEdge<T> edge : inputEdge)
		{
			this.addAdjacency(edge);
		}
	}
	
	/**
	 * Adds input adjacency to the calling object's adjacency list, merging and
	 * incrementing weights as needed.
	 * 
	 * @param inputEdge edge to add
	 */
	protected void addAdjacency(SimpleEdge<T> inputEdge)
	{
		/* Search for pre-existing adjacency */
		for(SimpleEdge<T> edge : adjacencies)
		{
			if (edge != null)
			{
				/* If matched then we're gonna increase weight by 1 */
				if (edge.equals(inputEdge))
				{
					edge.setWeight(edge.getWeight()+1);
					return;
				}
			}
		}

		/* If not found, then just add the adjacency */
		adjacencies.add(inputEdge);
	}
	
	/**
	 * Gets the edge
	 * 
	 * @return the adjacencies
	 */
	public HashSet<SimpleEdge<T>> getAdjacencies()
	{
		return adjacencies;
	}

	/**
	 * Set the edge
	 * 
	 * @param adjacencies the edge adjacencies
	 */
	public void setAdjacencies(HashSet<SimpleEdge<T>> adjacencies)
	{
		this.adjacencies = adjacencies;
	}
	
	/**
	 * Constructs a simple vertex given some data and the edge leading to
	 * the next vertex, or none if null.
	 * 
	 * @param data The data.
	 */
	public SimpleVertex(T data)
	{
		this.data = data;
		this.adjacencies = new HashSet<>();
	}
	
	/**
	 * Constructs a simple vertex, assigning both data and edge to null
	 */
	public SimpleVertex()
	{
		this.data = null;
		this.adjacencies = new HashSet<>();
	}
	
	@Override
	public String toString()
	{
		return this.data.toString();
	}
	
	@Override 
	public boolean equals(Object object)
	{
		if (object == null || object.getClass() != this.getClass())
		{
			return false;
		}
		else
		{
			@SuppressWarnings("unchecked") SimpleVertex<T> other = (SimpleVertex<T>) object;
			
			return other.getData().equals(this.getData());	
		}
	}
	
	@Override
	public int hashCode()
	{
		return data.hashCode();
	}
}