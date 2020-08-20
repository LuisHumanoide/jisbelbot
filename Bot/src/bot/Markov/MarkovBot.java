/**
 * MarkovBot.java : An interface for interacting with a MarkovBot
 * 
 * @author Michael W Flaherty
 * @version 1.1
 */

package bot.Markov;

public interface MarkovBot
{
	/**
	 * Train the MarkovBov a phrase, which is separated by spaces.
	 * 
	 * @param input Phrase to train.
	 */
	void train(String input);
	
	/**
	 * Train the MarkovBot individual words and maintain their links together. If you're intending
	 * on separating words by something other than spaces, split into an array and use this instead 
	 * of train(String);
	 * 
	 * @param input array of inputs to feed. 
	 */
	void train(String[] input);

	/**
	 * Generates a random phrase created from inputs. The more training given, the better the responses.
	 * 
	 * @return randomly generated phrase
	 */
	String getPhrase();
	
	/**
	 * Gets a phrase which contains the following word, if it's contained in the graph
	 * 
	 * @param string input
	 * @return Phrase, or null if input does not exist in the graph
	 */
	String getPhraseContains(String string);

    /**
     * Determines if the MarkovBot contains an input with the
     * given word.
     *
     * @param word Word to determine the existence of
     * @return True if found.
     */
	boolean contains(String word);
}
