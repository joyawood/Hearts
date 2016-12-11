import java.util.Comparator;

public class Card implements Comparable<Card> {
	int rank;
	int suit;
	String[] suitName = {"Hearts", "Diamonds", "Clubs", "Spades"};
	String[] rankName = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

	public Card(int rank, int suit){
		this.rank = rank;
		this.suit = suit;
	}

	public String toString() {
		return String.format("%s of %s", rankName[this.rank], suitName[this.suit]);
	}
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		if(this.rank > o.rank){
			return 1;
		}
		if (this.rank<o.rank){
			return -1;
		}
		return 0;
	}
	
	public boolean equals(Card card){
		return this.rank == card.rank && this.suit == card.suit;
	}



	
	
}
