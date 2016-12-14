import java.util.ArrayList;
import java.util.Collections;

public class Player {
	int points;
	ArrayList<Card>[] hand = new ArrayList[4];
	int playerID;

	public Player(int ID) {
		// initialize empty hand
		for (int i = 0; i < 4; i++) {
			hand[i] = new ArrayList<Card>();
		}
		playerID = ID;
	}

	public void add(Card card) {
		// add card to hand by suit
		hand[card.suit].add(card);
	}

	public void remove(Card card) {
		// remove card from hand
		System.out.println("printing card to remove and hand");
		printHand();
		System.out.println(card.toString());
		for (Card current : hand[card.suit]) {
			if (current.rank == card.rank) {
				hand[card.suit].remove(current);
				break;
			}	
		}
	}

	public void clearHand() {
		// delete hand
		for (int i = 0; i < 4; i++)
			hand[i].clear();
	}

	public boolean hasTwoOfClubs() {
		// returns true if player has 2 of clubs; if going first
		for (Card club : hand[2]) {
			if (club.rank == 2) {
				return true;
			}
		}
		return false;
	}

	public boolean hasQueen() {
		// returns true if card in hand
		int rank = 12;
		int suit = 3;

		for (Card option : hand[suit]) {
			if (option.rank == rank) {
				return true;
			}
		}
		return false;
	}

	public boolean isVoid(int suit) {
		// checks if players is void in given suit
		return hand[suit].size() == 0;
	}

	public Card playCard(State currentState) {
		return null;
	}

	public void sort() {
		// sorts hand by sorting individual suit arraylists
		for (ArrayList<Card> suit : hand) {
			Collections.sort(suit);
		}
	}
	public Card playLowestNonHeart() {
		Card lowest = new Card(52, 0);
		for (int i = hand.length - 1; i > 0; i--) {
			for (Card card : hand[i]) {
				if (card.rank < lowest.rank) {
					lowest = card;
				}
			}

		}

		return lowest;
	}
	public void printHand() {
		// for debugging
		for (ArrayList<Card> suit : hand) {
			for (Object card : suit) {
				System.out.print(card.toString() + ", ");
			}
		}
	}

	public boolean hasOnlyHearts() {
		for (int i = 1; i < 4; i++) {
			if (hand[i].size() > 0) {
				return false;
			}
		}
		return true;
	}
	public Card playHighestNonHeartCard() {
		Card highest = new Card(0, 0);
		for (int i = 1; i < hand.length; i++) {
			for (Card card : hand[i]) {
				if (card.rank > highest.rank) {
					highest = card;
				}
			}
		}
		return highest;
	}
}
