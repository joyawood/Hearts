import java.util.ArrayList;
import java.util.Collections;

public class Player {
	ArrayList<Card> hand;
	int points;
	KnowledgeBase knowledge;
	ArrayList[] suits = new ArrayList[4];//0 clubs, 1 hearts, 2 diamonds, 3 spades

	public Player() {
		this.hand = new ArrayList<Card>();
		this.points = 0;
		this.knowledge = new KnowledgeBase();
		for (int i = 0; i < 4; i++) {
			suits[i] = new ArrayList<Card>();
		}
	}

	public void add(Card card) {
		if (card.suit == Deck.Suit.CLUBS) {
			suits[0].add(card);
		}
		else if (card.suit == Deck.Suit.HEARTS) {
			suits[1].add(card);
		}
		else if (card.suit == Deck.Suit.DIAMONDS) {
			suits[2].add(card);
		}
		else if (card.suit == Deck.Suit.SPADES) {
			suits[3].add(card);
		}
	}

	public void setKnowledge() {
		for (Card card : hand) {
			knowledge.update(card);
		}
	}

	public void updateKnowledge(Card card) {
		knowledge.update(card);
	}
	public void sort(){
		for (int i = 0; i < 4; i++) {
			ArrayList<Card> suit = suits[i];
 			System.out.println("calling sort");

		    Collections.sort(suit, Card.compByRank());
		}
	}

}
