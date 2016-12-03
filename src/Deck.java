
public class Deck {
	Card[] cards;
	int length = 52;

	public Deck() {
		int index = 0;
		cards = new Card[52];
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j < 15; j++) {
				Card card = new Card(j, i);
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

	public int deal(Player[] players) {
		/*
		 * Deals the deck and returns index of player with 2 of clubs
		 */
		int player = 0;// this is the player with the 2 of clubs, starts round
		for (int i = 0; i < length; i += 4) {
			for (int j = 0; j < 4; j++) {
				if (cards[i + j].suit == 2 && cards[i + j].rank == 2)
					player = j;
				players[j].add(cards[i + j]);
			}
		}

		for (int i = 0; i < 4; i++) {
			System.out.println("Player " + i + " hand: ");
			players[i].sort();
			for (int j = 0; j < 4; j++) {
				for (Card card : players[i].hand[j]) {
					System.out.print(card.toString() + ", ");
				}

			}
			System.out.println(" ");
			System.out.println(" ");

		}
		System.out.println(" ");

		return player;
	}

}
