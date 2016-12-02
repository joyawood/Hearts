
public class Deck {
	Card[] cards;
	int length = 52;

	public enum Suit {
		CLUBS, DIAMONDS, HEARTS, SPADES
	}

	public enum Rank {
		DEUCE, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
	}

	public Deck() {
		int index = 0;
		cards = new Card[52];
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				Card card = new Card(rank, suit);
				cards[index] = card;
				index++;
			}
		}
	}

	public void shuffle() {
		for (int i = length - 1; i > 0; i--) {
			int rand = (int) (Math.random() * (i + 1));
			Card temp = cards[i];
			cards[i] = cards[rand];
			cards[rand] = temp;
		}
	}

	public void deal(Player one, Player two, Player three, Player four) {
		for (int i = 0; i < length; i += 4) {
			one.add(cards[i]);
			two.add(cards[i + 1]);
			three.add(cards[i + 2]);
			four.add(cards[i + 3]);
		}
	}

}
