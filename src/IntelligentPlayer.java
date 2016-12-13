import java.util.ArrayList;
import java.util.Random;

public class IntelligentPlayer extends Player {
	Random rand = new Random();

	public IntelligentPlayer(int ID) {
		super(ID);

	}

	public Card playCard(State currentState, ArrayList<Card>[] recursiveHand) {
		//this is baseline worst score to compare against card options
		int bestScore = 27;
		//this will be the card we output
		Card choice = null;
		
		int leadingSuit = currentState.getLeadingSuit();

		// first - copy hand into new arraylist (?)
		ArrayList<Card>[] currentHand = copyHand(recursiveHand);
		// second - loop through valid options in your hand and play out game

		// if first round:
		if (currentState.twoOfClubs == false) {
			// first move, play two of clubs
			currentState.twoOfClubs = true;
			choice = new Card(2, 2);
		} else if (leadingSuit == -1 || isVoidCopy(leadingSuit, currentHand)) {
			System.out.println("We are leading or void");
			// if you're leading or void
			if (currentState.heartsBroken) {
				// if hearts broken
				// run through all cards - consider all cards
				System.out.println("Hearts is broken");
				for (int suit = 0; suit < 4; suit++) {
					for (Card valid : currentHand[suit]) {//actual hand, is this right?
						int score = playout(valid, currentState, currentHand);
						if (score < bestScore) {
							choice = valid;
						}
					}
				}
				System.out.println("We chose "+choice.toString());
			} else {
				// if not hearts broken
				System.out.println("Hearts is not broken");

				// run through all valid cards - consider 1-3
				for (int suit = 1; suit < 4; suit++) {
					for (Card valid : hand[suit]) {
						int score = playout(valid, currentState, currentHand);
						if (score < bestScore) {
							choice = valid;
						}
					}
				}
				System.out.println("We chose "+choice.toString());
			}

		} else {
			// not leading and not void - following
			// run through all valid cards - consider leading suit
			System.out.println("Not leading nor void");
			for (Card valid : hand[leadingSuit]) {
				System.out.println("not leading, not void");
				int score = playout(valid, currentState, currentHand);
				if (score < bestScore) {
					choice = valid;
				}
			}
			System.out.println("We chose "+choice.toString());

		}
		remove(choice);
		setHand(currentHand);
		System.out.println(choice.toString());
		return choice;
	}


	private void setHand(ArrayList<Card>[] currentHand) {
		for(int suit = 0; suit <4; suit++){
			hand[suit].clear();
			for(Card card: currentHand[suit]){
				hand[suit].add(card);
			}
		}

	}

	private int playout(Card choice, State currentState, ArrayList<Card>[] currentHand) {
		// keep track of points
		int points = 0;

//		// make a safe copy of state
//		State currentState = new State(prevState);
//
//		// update copy
//		currentState.updateState(choice, this.playerID);// current player is our intelligent player

		// update hand
		removeFromCopy(choice, currentHand);		

		// finish trick
		for (int i = currentState.cardsInTrick.size(); i < 4; i++) {//size reflects our played card
			Card opponentChoice = playRandomCard(currentState.deck, currentHand);
			int oppenentID = (this.playerID + 1) % 4;
			currentState.updateState(opponentChoice, oppenentID);
		}
		
		// find player to start next round
		int startingPlayer = currentState.winningPlayer();

		// check if we won points
		if (startingPlayer == this.playerID) points += currentState.points;// we won, add points to tally

		// continue playing out round
		while (roundsRemaining(currentState.deck)) {

			int trickNum = currentState.deck.played.size() / 4 + 1;// the current trick number

			for (int trick = trickNum; trick < 14; trick++) {//rounds go from 1-13
				
				// create new state
				ArrayList<Card> cardsInTrick = new ArrayList<Card>();
				State newState = new State(currentState.deck, currentState.heartsBroken, currentState.twoOfClubs, cardsInTrick,
						startingPlayer);

				// go through each of the 4 players and have them play
				for (int player = startingPlayer; player < startingPlayer + 4; player++) {
					Card newChoice = null;
					int currentPlayer = player % 4;

					// check if the current player is the Intelligent Player
					if (currentPlayer == this.playerID) {
						// recursively choose card
						newChoice = playCard(newState, currentHand);
					} else {
						// simulate an opponents move
						newChoice = playRandomCard(newState.deck, currentHand);
					}
					
					//updates deck etc
					newState.updateState(newChoice, currentPlayer);

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

	private Card playRandomCard(Deck currentDeck, ArrayList<Card>[] currentHand) {
		/*
		 * Method to return a random unplayed card that is not in the
		 * IntelligentPlayer's hand
		 */
		boolean valid = false;
		Card choice = null;
		while (!valid) {
			int index = rand.nextInt(currentDeck.notPlayed.size());
			choice = currentDeck.notPlayed.get(index);

			if (!inHand(choice, currentHand)) {
				// not in Intelligent Player's hand
				valid = true;
			}

		}
		return choice;
	}

	private boolean roundsRemaining(Deck deck) {
		/*
		 * if there are unplayed cards, there are rounds remaining
		 */
		return deck.notPlayed.size() > 0;
	}

	public ArrayList<Card>[]  copyHand(ArrayList<Card>[] hand) {
		ArrayList<Card>[] currentHand =  new ArrayList[4];
		for (int i = 0; i < 4; i++) {
			//copyHand[i].clear();
			currentHand[i] = new ArrayList<Card>();
			for (Card current : hand[i]) {
//				System.out.print(current.toString()+", ");
				currentHand[i].add(current);
			}
//			System.out.println("next suit");
		}
		return currentHand;
	}

	public void removeFromCopy(Card card, ArrayList<Card>[] currentHand) {
		// remove card from hand
		printCopyHand(currentHand);
		for (Card current : currentHand[card.suit]) {
			if (current.rank == card.rank) {
				currentHand[card.suit].remove(current);
				break;
			}
		}
//		printCopyHand(currentHand);
	}

	public boolean inHand(Card card,  ArrayList<Card>[] currentHand) {
		for (int i = 0; i < 4; i++) {
			for (Card mine : currentHand[i]) {
				if (mine.equals(card)) {
					return true;
				}
			}
		}
		return false;

	}

	private boolean isVoidCopy(int leadingSuit, ArrayList<Card>[] currentHand) {
		return currentHand[leadingSuit].size() == 0;
	}

	public void printCopyHand( ArrayList<Card>[] currentHand) {
		// for debugging
		int length = 0;
		for (ArrayList<Card> suit : currentHand) {
//			for (Object card : suit) {
//				System.out.println(card.toString());
//			}
			length += suit.size();
		}
		System.out.println(length);
	}

}
