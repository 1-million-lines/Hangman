import java.util.*;
import java.io.*;

public class Hangman {

	private final InputStream input;
	private final OutputStream output;
	private final int max;
	private final String[] WORDS = { "mathematics", "simplicity", "neighborhood"};

	public Hangman(final InputStream input, final OutputStream output, final int max) {
		this.input = input;
		this.output = output;
		this.max = max;
	}

	public static void main(final String... args) {
		new Hangman(System.in, System.out, 5).exec();
	}

	public void exec() {
		String word = WORDS[new Random().nextInt(WORDS.length)];
		boolean[] visible = new boolean[word.length()]; 
		int mistakes = 0;
		try (final PrintStream out = new PrintStream(this.output)) {
			final Iterator<String> scanner = new Scanner(this.input);
			boolean done = true;
			while (mistakes < this.max) {
				done = true;
				for (int i = 0; i < word.length(); i++) {
					if (!visible[i]) {
						done = false;
					}
				}
				if (done) {
					break;
				}
				out.print("Guess a letter: ");
				char chr = scanner.next().charAt(0);
				boolean hit = false;
				for (int i = 0; i < word.length(); i++) {
					if (word.charAt(i) == chr && !visible[i]) {
						visible[i] = true;
						hit = true;
					}
				}
				if (hit) {
					out.print("Hit!\n");
				} else {
					out.printf(
						"Missed, mistake #%d out of %d\n",
						mistakes + 1, this.max
					);
					++mistakes;
				}
				out.append("The word: ");
				for (int i = 0; i < word.length(); i++) {
					if (visible[i]) {
						out.print(word.charAt(i));
					} else {
						out.print("-");
					}
				}
				out.append("\n\n");
			}
			if (done) {
				out.print("You won!\n");
			} else {
				out.print("You lose!\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
