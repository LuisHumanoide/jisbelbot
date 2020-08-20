/**
 * MarkovBot.java : A simple edge class for a graph structure
 * 
 * @author Michael W Flaherty
 * @version 1.0
 */

package bot.Markov;

public class SimpleEdge<T>
{
	public static final int DEFAULT_WEIGHT = 1; 
	
	private int weight;
	private SimpleVertex<T> to;

	/**
	 * Gets the weight for the edge
	 * 
	 * @return the weight
	 */
	public int getWeight()
	{
		return weight;
	}

	/**
	 * Sets the weight for the edge
	 * 
	 * @param weight the weight to set
	 */
	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	/**
	 * Gets the next vertex
	 * 
	 * @return the next vertex
	 */
	public SimpleVertex<T> getTo()
	{
		return to;
	}

	/**
	 * Sets the next vertex
	 * 
	 * @param to The next vertex
	 */
	public void setTo(SimpleVertex<T> to)
	{
		this.to = to;
	}
	
	/**
	 * Constructs a simple vertex given some data and the edge leading to
	 * the next vertex, or none if null.
	 * 
	 * @param to data The data.
	 * @param weight next The edge.
	 */
	public SimpleEdge(SimpleVertex<T> to, int weight)
	{
		this.weight = weight;
		this.to = to;
	}
	
	/**
	 * Constructs a simple vertex, assigning both data and edge to null
	 */
	public SimpleEdge()
	{
		this.weight = SimpleEdge.DEFAULT_WEIGHT;
		this.to = null;
	}
	
	@Override
	public String toString()
	{
		return "Weight: " + weight + " | Link: " + to.toString();
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
			@SuppressWarnings("unchecked") SimpleEdge<T> other = (SimpleEdge<T>) object;
			
			return this.to.equals(other.getTo()) && this.getWeight() == other.getWeight();	
		}
	}
	
	@Override
	public int hashCode()
	{
		return this.getTo().hashCode();
	}
}