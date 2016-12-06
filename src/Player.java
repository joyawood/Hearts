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

	public Card playCard(ArrayList<Card> currentTrick, int round, boolean broken) {
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
						// pass
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
				// void in suite
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
