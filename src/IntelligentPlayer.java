import java.util.ArrayList;
import java.util.Random;

public class IntelligentPlayer extends Player {
	Random rand = new Random();
	ArrayList<Card>[] copyHand = new ArrayList[4];

	public IntelligentPlayer(int ID) {
		super(ID);
		// initialize empty hand
		for (int i = 0; i < 4; i++) {
			hand[i] = new ArrayList<Card>();
		}
		// initialize copied hand - to pass in playouts
		for (int i = 0; i < 4; i++) {
			copyHand[i] = new ArrayList<Card>();
		}
	}

	public Card playCard(State currentState) {
		int bestScore = 27;
		Card choice = null;
		int leadingSuit = currentState.getLeadingSuit();

		// first - copy hand
		copyHand(hand);
		// second - loop through valid options in your hand and play out game

		// if first round:
		if (currentState.twoOfClubs == false) {
			// first move, play two of clubs
			currentState.twoOfClubs = true;
			choice = new Card(2, 2);
		} else if (leadingSuit == -1 || isVoidCopy(leadingSuit)) {
			// if you're leading or void
			if (currentState.heartsBroken) {
				// if hearts broken
				// run through all cards - consider all cards
				for (int suit = 0; suit < 4; suit++) {
					for (Card valid : hand[suit]) {
						int score = playout(valid, currentState);
						if (score < bestScore) {
							choice = valid;
						}
					}
				}
			} else {
				// if not hearts broken
				// run through all valid cards - consider 1-3
				for (int suit = 1; suit < 4; suit++) {
					for (Card valid : hand[suit]) {
						int score = playout(valid, currentState);
						if (score < bestScore) {
							choice = valid;
						}
					}
				}
			}

		} else {
			// not leading and not void
			// run through all valid cards - consider leading suit
			for (Card valid : hand[leadingSuit]) {
				System.out.println("not leading, not void");
				int score = playout(valid, currentState);				
				if (score < bestScore) {
					choice = valid;
				}
			}
		}
		remove(choice);
		System.out.println(choice.toString());
		return choice;
	}

	
	private int playout(Card choice, State prevState, ArrayList<Card>[] currentHand) {
		// keep track of points
		int points = 0;
		
		// make a safe copy of state
		State currentState = new State(prevState);

		// update copy
		currentState.update(choice, prevState.currentPlayer);// off by one maybe

		// make safe copy of hand
		copyHand(copyHand);
//		printCopyHand();
		

		// update hand
		removeFromCopy(choice);

		// pointer to current deck
		Deck deck = currentState.deck;
		int currentPlayer = prevState.currentPlayer;//+1?

		// finish trick
		for (int i = currentState.cardsInTrick.size() - 1; i < 4; i++) {
			Card opponentChoice = playRandomCard(deck);
			currentPlayer = (currentPlayer + 1) % 4;
			currentState.update(choice, currentPlayer);
		}
		// find player to start next round
		int startingPlayer = currentState.winningPlayer();

		// check if we won points
		if (startingPlayer == this.playerID) points += currentState.points;// we won, add points to tally

		// continue playing out round
		while (roundsRemaining(deck)) {

			int trickNum = deck.played.size() / 4 + 1;// the current trick number

			for (int trick = trickNum; trick < 14; trick++) {
				// create new state
				ArrayList<Card> cardsInTrick = new ArrayList<Card>();
				State newState = new State(deck, currentState.heartsBroken, currentState.twoOfClubs, cardsInTrick,
						startingPlayer);

				// go through each of the 4 players and have them play 
				for (int player = startingPlayer; player < startingPlayer + 4; player++) {
					Card newChoice = null;
					int newCurrentPlayer = player % 4;

					// is the current player the Intelligent Player?
					if (newCurrentPlayer == this.playerID) {
						// recursively choose card
						newChoice = playCard(newState);
					} else {
						// simulate an opponents move
						newChoice = playRandomCard(deck);
					}
					newCurrentPlayer = (newCurrentPlayer + 1) % 4;
					newState.update(newChoice, newCurrentPlayer);

				}

				// trick is completed
				// set starting player to be the player who just won the round
				startingPlayer = newState.winningPlayer();

				// check if we won points
				if (startingPlayer == this.playerID)
					points += newState.points;// we won, add points to tally
			}

		}

		return points;
	}

	private Card playRandomCard(Deck currentDeck) {
		/*
		 * Method to return a random unplayed card that is not in the
		 * IntelligentPlayer's hand
		 */
		boolean valid = false;
		Card choice = null;
		while (!valid) {
			int index = rand.nextInt(currentDeck.notPlayed.size());
			choice = currentDeck.notPlayed.get(index);

			if (!inHand(choice)) {
				// not in Intelligent Player's hand
				valid = true;
			}

		}
		return choice;
	}

	private boolean roundsRemaining(Deck deck) {
		return deck.played.size() > 0;
	}

	public void copyHand(ArrayList<Card>[] hand) {

		for (int i = 0; i < 4; i++) {
			copyHand[i].clear();
			for (Card current : hand[i]) {
//				System.out.print(current.toString()+", ");
				copyHand[i].add(current);
			}
//			System.out.println("next suit");
		}
	}

	public void removeFromCopy(Card card) {
		// remove card from hand
		System.out.println("removing from copy "+card.toString());
		printCopyHand();
		for (Card current : copyHand[card.suit]) {
			if (current.rank == card.rank) {
				copyHand[card.suit].remove(current);
				break;
			}
		}
		printCopyHand();

	}

	public boolean inHand(Card card) {
		for (int i = 0; i < 4; i++) {
			for (Card mine : copyHand[i]) {
				if (mine.equals(card)) {
					return true;
				}
			}
		}
		return false;

	}
	private boolean isVoidCopy(int leadingSuit) {
		return copyHand[leadingSuit].size() == 0;
	}
	

	public void printCopyHand() {
		// for debugging
		int length = 0;
		for (ArrayList<Card> suit : copyHand) {
//			for (Object card : suit) {
//				System.out.println(card.toString());
//			}
			length += suit.size();
		}
		System.out.println(length);
	}

}
