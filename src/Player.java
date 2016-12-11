import java.util.ArrayList;
import java.util.Collections;

public class Player {
	int points;
	ArrayList<Card>[] hand = new ArrayList[4];
	KnowledgeBase knowledge = new KnowledgeBase();

	public Player() {
		for (int i = 0; i < 4; i++) {
			hand[i] = new ArrayList<Card>();
		}
	}

	public void add(Card card) {
		hand[card.suit].add(card);
		knowledge.update(card);
	}


	public boolean checkCard(int rank, int suit) {
		for (Card option : hand[suit]) {
			if (option.rank == rank) {
				return true;
			}
		}
		return false;
	}

	public boolean isVoid(int suit) {
		return hand[suit].size() == 0;
	}



	public Card dontLeadSpades() {
		Card choice;
		int minSize = 14;
		int minSuit = 0;
		int suitToAvoid = 3;

		minSuit = 1;
		for (int i = 1; i < 3; i++) {
			if (hand[i].size() < minSize && hand[i].size() > 0) {
				minSize = hand[i].size();
				minSuit = i;
			}

		}
		if (hand[minSuit].size() > 1) {
			choice = hand[minSuit].get(hand[minSuit].size() - 1);
		} else {
			choice = hand[suitToAvoid].get(0);
		}

		return choice;

	}

	public Card pop(int rank, int suit) {
		/*
		 * Return and remove called card from hand Use for 2 of C and Q of S???
		 */

		for (Card option : hand[suit]) {
			if (option.rank == rank) {
				hand[suit].remove(option);
				return option;
			}
		}
		return null;
	}

	public Card playCard(ArrayList<Card> currentTrick, int round, boolean broken, Card winningCard) {
		Card choice = null;

		if (currentTrick.size() == 0) {
			// if first round of game
			if (round == 0) {
				choice = new Card(2, 2);// Possible source of error
			} else {
				// if not first round
				int minLength = 14;
				int indexMin = 0;
				for (int i = 0; i < hand.length; i++) {
					if (!broken && i == 0) {
						// pass - skip hearts
					} else {
						ArrayList<Card> cardsBySuits = hand[i];
						if (cardsBySuits.size() < minLength && cardsBySuits.size() > 0) {
							minLength = cardsBySuits.size();
							indexMin = i;
						}
					}
				}
				// choose card from smallest suit
				choice = hand[indexMin].get(0);
			}
		} else {
			int suit = currentTrick.get(0).suit;

			if (hand[suit].size() > 0) {
				// we have card in suit
				choice = hand[suit].get(0);// lowest in suit
															

			} else {
				// void in suit
				choice = this.pop(12, 3);// try for Q of S
				if (choice == null) {
					// no Q of S
					if (hand[0].size() > 0) {
						// play heart
						choice = hand[0].get(0);// lowest
																	// heart
					} else {
						// else highest card of smallest suit
						int minLength = 14;
						int indexMin = 0;
						for (int i = 0; i < hand.length; i++) {
							ArrayList<Card> cardsBySuits = hand[i];
							if (cardsBySuits.size() < minLength && cardsBySuits.size() > 0) {
								minLength = cardsBySuits.size();
								indexMin = i;
							}
						}
						// choose card from smallest suit
						choice = hand[indexMin].get(0);
					}
				} else {
					return choice;
				}
			}

		}
		pop(choice.rank, choice.suit);
		return choice;
	}

	public Card followTest(ArrayList<Card> currentTrick, int round, boolean broken) {
		Card choice = null;

		if (currentTrick.size() == 0) {
			// if first round of game
			if (round == 0) {
				choice = new Card(2, 2);// Possible source of error
			} else {
				// if not first round
				int minLength = 14;
				int indexMin = 0;
				for (int i = 0; i < hand.length; i++) {
					if (!broken && i == 0) {
						// pass - skip hearts
					} else {
						ArrayList<Card> cardsBySuits = hand[i];
						if (cardsBySuits.size() < minLength && cardsBySuits.size() > 0) {
							minLength = cardsBySuits.size();
							indexMin = i;
						}
					}
				}
				// choose card from smallest suit
				choice = hand[indexMin].get(hand[indexMin].size() - 1);
			}
		} else {
			int suit = currentTrick.get(0).suit;

			if (hand[suit].size() > 0) {
				// we have card in suit
				choice = hand[suit].get(hand[suit].size() - 1);// highest in
																// suit

			} else {
				// void in suit
				choice = this.pop(12, 3);// try for Q of S
				if (choice == null) {
					// no Q of S
					if (hand[0].size() > 0) {
						// play heart
						choice = hand[0].get(hand[0].size() - 1);// highest
																	// heart
					} else {
						// else highest card of smallest suit
						int minLength = 14;
						int indexMin = 0;
						for (int i = 0; i < hand.length; i++) {
							ArrayList<Card> cardsBySuits = hand[i];
							if (cardsBySuits.size() < minLength && cardsBySuits.size() > 0) {
								minLength = cardsBySuits.size();
								indexMin = i;
							}
						}
						// choose card from smallest suit
						choice = hand[indexMin].get(hand[indexMin].size() - 1);
					}
				} else {
					return choice;
				}
			}

		}
		pop(choice.rank, choice.suit);
		return choice;
	}

	public void sort() {
		for (ArrayList<Card> suit : hand) {
			Collections.sort(suit);
		}
	}

	public void printHand() {
		for (ArrayList<Card> suit : hand) {
			for (Object card : suit) {
				System.out.println(card.toString());
			}
		}
	}

}
