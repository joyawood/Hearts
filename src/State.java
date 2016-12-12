import java.util.ArrayList;

public class State {
	int currentPlayer;
	int startingPlayer;
	int points;

	Card winningCard = new Card(2, 2);
	int currentWinner;

	Deck deck;
	boolean heartsBroken = false;
	boolean twoOfClubs = false;
	ArrayList<Card> cardsInTrick;

	public State(Deck deck, boolean heartsBroken, boolean twoOfClubs, ArrayList<Card> cardsInTrick, int currentPlayer) {
		this.currentPlayer = currentPlayer;
		this.startingPlayer = currentPlayer;
		this.currentWinner = currentPlayer;
		this.points = 0;
		this.deck = new Deck(deck);
		this.heartsBroken = heartsBroken;
		this.twoOfClubs = twoOfClubs;
		this.cardsInTrick = new ArrayList<Card>(cardsInTrick);
	}

	public State(State stateToCopy) {
		this.points = stateToCopy.points;
		this.currentPlayer = stateToCopy.currentPlayer;
		this.currentWinner = stateToCopy.currentWinner;
		this.startingPlayer = stateToCopy.startingPlayer;
		this.deck = new Deck(stateToCopy.deck);
		this.heartsBroken = stateToCopy.heartsBroken;
		this.twoOfClubs = stateToCopy.twoOfClubs;
		this.cardsInTrick = new ArrayList<Card>(stateToCopy.cardsInTrick);
	}

	public void update(Card played, int currentPlayer) {
		/*
		 * this function updates the deck to keep track of played card,
		 * and it updates the winning card and player in state,
		 * also updates flags
		 */
		// update winning card and winning player
		if (cardsInTrick.size() > 0) {
			if (played.suit == winningCard.suit) {
				if (played.rank > winningCard.rank) {
					// update leading card if in suit and greater than leading
					// card
					winningCard = played;
					// keep track of index of player who is winning
					currentWinner = currentPlayer;
				}
			}
		} else {
			winningCard = played;
			// keep track of index of player who is winning
			currentWinner = currentPlayer;
		}

		// update current player to next player
		this.currentPlayer = currentPlayer;

		// add card played to trick
		this.cardsInTrick.add(played);
		// update our copy of the deck to reflect the change
		this.deck.updatePlayed(played);
		if (played.suit == 0) {
			// break hearts
			this.heartsBroken = true;
			this.points++;
		}
		if (played.suit == 3 && played.rank == 12) {
			// queen of spades
			this.points += 13;
		} else if (played.suit == 2 && played.rank == 2) {
			// update 2 of clubs
			this.twoOfClubs = true;

		}

	}

	public int winningPlayer() {
		return currentWinner;
	}

	public int getLeadingSuit() {
		if (this.cardsInTrick.size() != 0) {
			return this.cardsInTrick.get(0).suit;
		} else {
			return -1;
		}
	}
}
