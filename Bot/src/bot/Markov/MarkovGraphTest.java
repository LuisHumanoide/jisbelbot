/*
 * MarkovGraphTest.java : A tester for the MarkovGraph
 *
 * @author Michael W Flaherty
 * @version 1.0
 */

package bot.Markov;

public class MarkovGraphTest
{
	public static void main(String[] args)
	{
		MarkovGraph graph = new MarkovGraph();
		String[] inputs = {
				"I'll be back.",
				"You talking to me?",
				"May the Force be with you.",
				"Frankly, my dear, I don't give a damn.",
				"The only difference between me and a madman is that I'm not mad.",
				"A mathematician is a device for turning coffee into theorems.",
				"He is one of those people who would be enormously improved by death.",
				"Mama always said life was like a box of chocolates. You never know what you're gonna get.",
				"Many wealthy people are little more than janitors of their possessions.",
				"The optimist proclaims that we live in the best of all possible worlds, and the pessimist fears this is true.",
				"Give me chastity and continence, but not yet.",
				"Well, here's another nice mess you've gotten me into!",
		};
		
		for(String input : inputs)
		{
			graph.train(input);
		}
		
		System.out.println(graph.getPhrase());
	}
}
