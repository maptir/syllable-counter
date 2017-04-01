package syllableCounter;

public class WordCounter {
	private final State START = new StartState();
	private final State SINGLEVOWEL = new SingleVowelState();
	private final State MULTIVOWEL = new MultiVowelState();
	private final State CONSONANT = new ConsonantState();
	private final State HYPHEN = new HyphenState();
	private final State NONWORD = new NonwordState();
	private State state = START;
	private int syllableCount = 0;
	private boolean lastChar = false;

	public int countSyllables(String word) {
		char c = ' ';
		syllableCount = 0;
		state = new StartState();
		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (c == '\'')
				continue;
			lastChar = (i == word.length() - 1);
			state.handleChar(c);
		}
		return syllableCount;
	}

	public void setState(State newState) {
		state = newState;
	}

	class StartState extends State {

		@Override
		public void handleChar(char c) {
			if (isVowelOrY(c)) {
				setState(SINGLEVOWEL);
				if (lastChar)
					syllableCount++;
			} else
				setState(CONSONANT);
			if (!isLetter(c) || c == '-')
				setState(NONWORD);
		}

	}

	class SingleVowelState extends State {

		@Override
		public void handleChar(char c) {
			if (isVowel(c)) {
				setState(MULTIVOWEL);
				if (lastChar)
					syllableCount++;
			} else if (isLetter(c) || c == '-') {
				syllableCount++;
				setState(CONSONANT);
			}
		}

		public void enterState() {
			syllableCount++;
		}

	}

	class MultiVowelState extends State {

		@Override
		public void handleChar(char c) {
			if (!isVowel(c)) {
				syllableCount++;
				setState(CONSONANT);
			}
			if (isVowel(c) && lastChar)
				syllableCount++;
		}

	}

	class ConsonantState extends State {

		@Override
		public void handleChar(char c) {
			if (lastChar && c == 'e' && syllableCount == 0)
				syllableCount++;
			if (isVowelOrY(c)) {
				if (lastChar && c != 'e' && c != 'E')
					syllableCount++;
				setState(SINGLEVOWEL);
			} else if (isLetter(c) || c == '-')
				setState(CONSONANT);
			else
				setState(NONWORD);
		}

	}

	class HyphenState extends State {

		@Override
		public void handleChar(char c) {
			if (isVowelOrY(c)) {
				setState(SINGLEVOWEL);
				if (lastChar)
					syllableCount++;
			} else if (isLetter(c))
				setState(CONSONANT);
			else if (c == '-')
				setState(HYPHEN);
			else
				setState(NONWORD);
		}

	}

	class NonwordState extends State {

		@Override
		public void handleChar(char c) {
			syllableCount = 0;
		}

	}
}