/**
 * MarkovGraph.java : A Markov Chain graph structure for generating random text
 *
 * @author Michael W Flaherty
 * @version 1.0
 */

package bot.Markov;

import java.util.HashSet;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;

public class MarkovGraph extends Graph<String> implements MarkovBot
{
	private Random rand;
	private HashSet<SimpleVertex<String>> startingWords;
	
	/**
	 * Default constructor for MarkovGraph
	 */
    public MarkovGraph()
	{
	    super();
		this.rand = new Random();
		this.startingWords = new HashSet<>();
	}

    /**
     * Constructor for MarkovGraph which will train a
     * collection of phrases
     */
    public MarkovGraph(Collection<String> phrases)
    {
        super();
        this.rand = new Random();
        this.startingWords = new HashSet<>();

        for(String phrase : phrases)
        {
            if (phrase != null)
            {
                this.train(phrase);
            }
        }
    }

	/**
	 * Recursive method for building sentences starting from a given
	 * vertex.
	 * 
	 * @param vertex Vertex to start from
	 * @param sb String Builder to build words
	 */
	private void buildStringFromVertex(SimpleVertex<String> vertex, StringBuilder sb)
	{
		sb.append(vertex.getData());
		sb.append(" ");

		ArrayList<SimpleVertex<String>> list = new ArrayList<>();
		
		for (SimpleEdge<String> edge : vertex.getAdjacencies())
		{
			if (edge != null)
			{
				int weight = edge.getWeight();
				while (weight > 0)
				{
					list.add(edge.getTo());
					
					weight--;
				}
			}
		}
		
		if (list.size() > 0)
		{
			SimpleVertex<String> lucky = list.get(rand.nextInt(list.size()));
            buildStringFromVertex(lucky, sb);
		}
	}

	@Override
    public boolean contains(String word)
    {
        for (SimpleVertex<String> vertex : this.adjList)
        {
            if (vertex != null)
            {
                if (vertex.getData().equals(word))
                {
                    return true;
                }
            }
        }

        return false;
    }

	@Override
	public String getPhraseContains(String value)
	{
		if (!this.adjList.contains(new SimpleVertex<>(value)))
		{
			return null;
		}
		
		boolean found = false;
		String attempt = "";
		while (!found)
		{
			attempt = this.getPhrase();
			if (attempt.contains(value))
			{
				found = true;
			}
		}
		
		return attempt;
	}
	
	@Override
	public void train(String input)
	{
		String[] temp = input.split(" ");
		this.train(temp);
	}

	@Override
	public void train(String[] input)
	{
		ArrayList<SimpleVertex<String>> verticies = new ArrayList<>();
		
		for (int i = 0; i < input.length; i++)
		{
			SimpleVertex<String> vertex = new SimpleVertex<>(input[i]);
			verticies.add(vertex);
			if (i == 0)
			{
				startingWords.add(vertex);
			}
		}
		
		for (int i = 0; i < verticies.size()-1; i++)
		{
			SimpleVertex<String> vertex = verticies.get(i);
			
			this.addEdge(vertex, verticies.get(i+1), SimpleEdge.DEFAULT_WEIGHT);
		}
		
        this.addVertex(verticies.get(0));
	}

	@Override
	public String getPhrase()
	{
		StringBuilder sb = new StringBuilder();
		SimpleVertex<String> vertex = getStartingVertex();
		buildStringFromVertex(vertex, sb);
		return sb.toString();
	}
	
	/**
	 * Returns a random starting vertex.
	 * 
	 * @return random starting vertex.
	 */
	private SimpleVertex<String> getStartingVertex()
	{
		ArrayList<SimpleVertex<String>> list = new ArrayList<>();
		for (SimpleVertex<String> vertex : this.startingWords)
		{
			if (vertex != null)
			{
				list.add(vertex);
			}
		}
		
		return list.get(rand.nextInt(list.size()));
	}
}
