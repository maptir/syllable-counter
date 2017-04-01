package syllableCounter;

public class OOSyllableCounter extends WordCounter {

	public int countSyllables(String word) {
		char c = ' ';
		setSyllableCount(0);
		setState(new StartState());
		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (c == '\'')
				continue;
			setLastChar(i == word.length() - 1);
			getState().handleChar(c);
		}
		return getSyllableCount();
	}
}
