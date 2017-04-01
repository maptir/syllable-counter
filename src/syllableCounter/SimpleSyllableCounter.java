package syllableCounter;

public class SimpleSyllableCounter {
	public enum State {
		START, SINGLEVOWEL, MULTIVOWEL, CONSONANT, HYPHEN, NONWORD;
	}

	public boolean isVowel(char c) {
		return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O'
				|| c == 'U';
	}

	public boolean isVowelOrY(char c) {
		return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y' || c == 'A' || c == 'E' || c == 'I'
				|| c == 'O' || c == 'U' || c == 'Y';
	}

	public boolean isLetter(char c) {
		return Character.isLetter(c);
	}

	public int countSyllables(String word) {
		int syllables = 0;
		char c = ' ';
		State state = State.START;
		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			boolean lastChar = (i == word.length() - 1);
			if (c == '\'')
				continue;
			switch (state) {
			case START:
				if (isVowelOrY(c)) {
					state = State.SINGLEVOWEL;
					if (lastChar)
						syllables++;
				} else
					state = State.CONSONANT;
				if (!isLetter(c) || c == '-')
					state = State.NONWORD;
				break;

			case CONSONANT:
				if (lastChar && c == 'e' && syllables == 0)
					syllables++;
				if (isVowelOrY(c)) {
					if (lastChar && c != 'e' && c != 'E')
						syllables++;
					state = State.SINGLEVOWEL;
				} else if (isLetter(c) || c == '-')
					state = State.CONSONANT;
				else if (c == '-')
					state = State.HYPHEN;
				else
					state = State.NONWORD;
				break;

			case SINGLEVOWEL:
				if (isVowel(c)) {
					state = State.MULTIVOWEL;
					if (lastChar)
						syllables++;
				} else if (isLetter(c) || c == '-') {
					syllables++;
					state = State.CONSONANT;
				}
				break;

			case MULTIVOWEL:
				if (!isVowel(c)) {
					syllables++;
					state = State.CONSONANT;
				}
				if (isVowel(c) && lastChar)
					syllables++;
				break;

			case HYPHEN:
				if (isVowelOrY(c)) {
					state = State.SINGLEVOWEL;
					if (lastChar)
						syllables++;
				} else if (isLetter(c))
					state = State.CONSONANT;
				else if (c == '-')
					state = State.HYPHEN;
				else
					state = State.NONWORD;
				break;

			case NONWORD:
				return 0;

			default:
				break;
			}
		}
		return syllables;
	}

	public static void main(String[] args) {
		SimpleSyllableCounter count = new SimpleSyllableCounter();
		System.out.println(count.countSyllables("home-brew"));
	}
}
