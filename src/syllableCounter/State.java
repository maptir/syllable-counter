package syllableCounter;

public abstract class State {
	public abstract void handleChar(char c);

	public void enterState() {

	};

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

	public boolean isHyphen(char c) {
		return c == '-';
	}

}