import java.util.Random;

public class NaivePlayer extends Player {
	Random rand = new Random();

	public NaivePlayer(int ID) {
		super(ID);
	}

	public Card playCard(State currentState) {
		Card choice = null;
		int leadingSuit = currentState.getLeadingSuit();

		if (currentState.twoOfClubs == false) {
			// first move, play two of clubs
			currentState.twoOfClubs = true;
			choice = new Card(2, 2);
		} else if (leadingSuit == -1) {
			// if we are leading
			if (hasOnlyHearts()) {
				// only hearts - lowest heart
				choice = hand[0].get(0);
			} else {
				// lowest non heart
				choice = playLowestNonHeart();
			}
		} else if (isVoid(leadingSuit)) {
			if (hasQueen()) {
				// if queen drop queen
				choice = new Card(12, 3);
			} else if (hand[0].size() > 0) {
				// else if hearts drop hearts
				choice = hand[0].get(hand[0].size() - 1);
			} else {
				// else high card
				choice = playHighestNonHeartCard();
			}
		} else {
			// play lowest of suite
			choice = hand[leadingSuit].get(0);
		}
		remove(choice);
		return choice;
	}



	

}
