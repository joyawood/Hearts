import java.util.ArrayList;
import java.util.Random;

public class IntelligentPlayer2 extends Player {
	Random rand = new Random();

	public IntelligentPlayer2(int ID) {
		super(ID);

	}

	public Card playCard(State currentState) {
		return playCard(currentState, this.hand);
	}



	public Card playCard(State currentState, ArrayList<Card>[] recursiveHand) {
		// this is baseline worst score to compare against card options
		int bestScore = 27;
		// this will be the card we output
		Card choice = null;

		int leadingSuit = currentState.getLeadingSuit();

		ArrayList<Card>[] currentHand = copyHand(recursiveHand);

		// second - loop through valid options in your hand and play out game

		if (currentState.twoOfClubs == false) {
			// first move, play two of clubs
			currentState.twoOfClubs = true;
			choice = new Card(2, 2);
		} else if (leadingSuit == -1) {
			if (currentState.heartsBroken) {
				// if you're leading
				// if hearts broken
				for (int suit = 0; suit < 4; suit++) {
					for (Card valid : currentHand[suit]) {
						int score = playout(valid, currentState, currentHand);
//						System.out.println("Playout of " + valid.toString() + " yields "+ score + " points " );
						if (score < bestScore) {
							choice = valid;
							bestScore = score;
						}
					}
				}
			} else {
				// if you're leading
				// if not hearts broken
				for (int suit = 1; suit < 4; suit++) {
					for (Card valid : currentHand[suit]) {
						int score = playout(valid, currentState, currentHand);
//						System.out.println("Playout of " + valid.toString() + " yields "+ score + " points " );
						if (score < bestScore) {
							choice = valid;
							bestScore = score;
						}
					}
				}
			}

		} else if (isVoid(leadingSuit, currentHand)) {
			// we are void in suit, play any suit
			for (int suit = 0; suit < 4; suit++) {
				for (Card valid : currentHand[suit]) {
					int score = playout(valid, currentState, currentHand);
//					System.out.println("Playout of " + valid.toString() + " yields "+ score + " points " );
					if (score < bestScore) {
						choice = valid;
						bestScore = score;

					}
				}
			}
		} else {
			// not leading and not void - following in suit
			// run through all valid cards - consider leading suit
			for (Card valid : currentHand[leadingSuit]) {
				int score = playout(valid, currentState, currentHand);
//				System.out.println("Playout of " + valid.toString() + " yields "+ score + " points " );
				if (score < bestScore) {
					choice = valid;
					bestScore = score;

				}
			}

		}
		if (choice == null && hasOnlyHearts(currentHand)) {
			choice = currentHand[0].get(0);
//			System.out.println("We have only hearts, playing lowest " + choice.toString());

		}
		// remove from actual hand
		remove(choice); 
		return choice;
	}


	public Card playCardNaive(State currentState, ArrayList<Card>[] recursiveHand) {
		Card choice = null;
		int leadingSuit = currentState.getLeadingSuit();

		if (currentState.twoOfClubs == false) {
			// first move, play two of clubs
			currentState.twoOfClubs = true;
			choice = new Card(2, 2);
		} else if (leadingSuit == -1) {
			// if we are leading
			if (hasOnlyHearts(recursiveHand)) {
				// only hearts - lowest heart
				choice = recursiveHand[0].get(0);
			} else {
				// lowest non heart
				choice = playLowestNonHeart(recursiveHand);
			}
		} else if (isVoid(leadingSuit, recursiveHand)) {
			if (hasQueen(recursiveHand)) {
				// if queen drop queen
				choice = new Card(12, 3);
			} else if (recursiveHand[0].size() > 0) {
				// else if hearts drop hearts
				choice = recursiveHand[0].get(recursiveHand[0].size() - 1);
			} else {
				// else high card
				choice = playHighestNonHeartCard(recursiveHand);
			}
		} else {
			// play lowest of suite
			choice = recursiveHand[leadingSuit].get(0);
		}

		if (choice == null && hasOnlyHearts(recursiveHand)) {
			choice = recursiveHand[0].get(0);
			System.out.println("We have only hearts, playing lowest " + choice.toString());
		}
		
		remove(choice, recursiveHand);
		return choice;
	}


	private int playout(Card startChoice, State startState, ArrayList<Card>[] startHand) {
		int points = 0;
		System.out.println("-----------------------------------------------");

		System.out.println("player 2 playing out "+startChoice.toString());

		System.out.println("-----------------------------------------------");

		// update hand - copy hand and remove played card
		ArrayList<Card>[] currentHand = copyHand(startHand);
		remove(startChoice, currentHand);

		// update state - copy state and update
		State playoutState = new State(startState);
		playoutState.updateState(startChoice, this.playerID);

		// second we finish the trick
		// we need to know where we are in the trick
		System.out.println("-------current trick " + playoutState.cardsInTrick);
		System.out.println("-------cards remaining " +playoutState.deck.notPlayed.size()+"\n");
		System.out.println(playoutState.deck.notPlayed);
		System.out.println("player 2 hand: ");
		printCopyHand(currentHand);

		int playersLeftToPlay = 4 - playoutState.cardsInTrick.size();
		int counter = 1;
//		System.out.println("playersLeftToPlay "+playersLeftToPlay +"\n");


		// for each player left to player:
		while (playersLeftToPlay > 0) {
			// choose random card

			Card choice = playRandomCard(playoutState, currentHand);

			// update state (removes card from deck)
			playoutState.updateState(choice, (this.playerID + counter) % 4);

			playersLeftToPlay--;
			counter++;
		}

		// we've finished the current trick, now to see who won
		if (this.playerID == playoutState.currentWinner) {
			// if we won, then assign points
			points += playoutState.points;
		}

		// Now ready to run through remaining tricks
		int trickNum = playoutState.deck.played.size() / 4 + 1;
		for (int trick = trickNum; trick < 14; trick++) {
			// clear trick info, new stuff possible source of error
			System.out.println("\n---Starting trick " + trick + " playing out card "+ startChoice.toString());
			System.out.println("-------cards remaining " +playoutState.deck.notPlayed.size()+"\n");
			System.out.println(playoutState.deck.notPlayed);
			System.out.println("player 2 hand: ");
			printCopyHand(currentHand);

			if(playoutState.deck.notPlayed.size()%4 != 0){
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!");
			}
			playoutState.clearTrick();

			int startingPlayer = playoutState.winningPlayer();
			System.out.println("** Player "+startingPlayer+" is starting the next round.");

			
			// we run through all of our players
			for (int player = startingPlayer; player < startingPlayer + 4; player++) {
				Card choice = null;
				int currentPlayer = player % 4;

				if (currentPlayer == this.playerID) {
					// call naive player
					choice = playCardNaive(playoutState, currentHand);// removed
																		// from
																		// hand
				} else {
					choice = playRandomCard(playoutState, currentHand);
				}
				System.out.println("-------Player "+currentPlayer+" played "+choice);
				playoutState.updateState(choice, currentPlayer);
			}

			System.out.println("-------** Player "+playoutState.currentWinner+" won the round.");

			if (this.playerID == playoutState.currentWinner) {
				// if we won, then assign points
				points += playoutState.points;
			}

		}

		return points;
	}

	private boolean hasQueen(ArrayList<Card>[] recursiveHand) {
		// returns true if card in hand
		int rank = 12;
		int suit = 3;

		for (Card option : recursiveHand[suit]) {
			if (option.rank == rank) {
				return true;
			}
		}
		return false;
	}

	private boolean hasOnlyHearts(ArrayList<Card>[] recursiveHand) {
		for (int i = 1; i < 4; i++) {
			if (recursiveHand[i].size() > 0) {
				return false;
			}
		}
		return true;
	}

	private void remove(Card card, ArrayList<Card>[] recursiveHand) {
		// remove card from hand
		// printCopyHand(currentHand);
		System.out.println("-------removing card----");
		for (Card current : recursiveHand[card.suit]) {
			if (current.rank == card.rank) {
				recursiveHand[card.suit].remove(current);
				break;
			}
		}

	}

	private boolean isVoid(int leadingSuit, ArrayList<Card>[] recursiveHand) {
		// checks if players is void in given suit
		return recursiveHand[leadingSuit].size() == 0;
	}

	private Card playHighestNonHeartCard(ArrayList<Card>[] recursiveHand) {
		Card highest = new Card(0, 0);
		for (int i = 1; i < recursiveHand.length; i++) {
			for (Card card : recursiveHand[i]) {
				if (card.rank > highest.rank) {
					highest = card;
				}
			}
		}
		return highest;
	}

	private Card playLowestNonHeart(ArrayList<Card>[] recursiveHand) {
		Card lowest = new Card(52, 0);
		for (int i = recursiveHand.length - 1; i > 0; i--) {
			for (Card card : recursiveHand[i]) {
				if (card.rank < lowest.rank) {
					lowest = card;
				}
			}

		}
		if (lowest.rank == 52) {
			lowest = recursiveHand[0].get(0);
		}

		return lowest;
	}



	private ArrayList<Card>[] copyHand(ArrayList<Card>[] startHand) {
		ArrayList<Card>[] newHand = new ArrayList[4];

		for (int i = 0; i < 4; i++) {
			newHand[i] = new ArrayList<Card>();

			for (Card c : startHand[i]) {
				newHand[i].add(c);
			}
		}

		return newHand;
	}

	private Card playRandomCard(State state, ArrayList<Card>[] playerHand) {
		/*
		 * Method to return a random unplayed card that is not in the
		 * IntelligentPlayer's hand
		 */
//		System.out.println("playing Random card, ignoring card in  ");
//		this.printCopyHand(playerHand);

		boolean valid = false;
		Card choice = null;
//		System.out.println("\n" + state.deck.notPlayed.size());

		while (!valid) {
			int index = rand.nextInt(state.deck.notPlayed.size());
			choice = state.deck.notPlayed.get(index);

			if (!inHand(choice, playerHand)) {
				// not in Intelligent Player's hand
				valid = true;
			}
		}
//		System.out.println("random card chosen " + choice.toString()+"\n");

		
		return choice;
	}

	public boolean inHand(Card card, ArrayList<Card>[] ourHand) {
		/*
		 * Used in choosing random card - verify that not in intelligent player
		 * hand
		 */
			for (Card mine : ourHand[card.suit]) {
				if (mine.rank == card.rank) {
					return true;
				}
		}
		return false;

	}
	public void printCopyHand(ArrayList<Card>[] currentHand) {

		for (ArrayList<Card> suit : currentHand) {
			 for (Object card : suit) {
			 System.out.print(card.toString()+", ");
			 }
		}
		System.out.println("done printing Player 2 hand");

	}
}
