import java.util.ArrayList;


public class KnowledgeConstraints {
	ArrayList<Card> clubs;
	ArrayList<Card> hearts;
	ArrayList<Card> spades;
	ArrayList<Card> diamonds;
	@SuppressWarnings("rawtypes")
	ArrayList[] suits = new ArrayList[4];

	
	@SuppressWarnings("unchecked")
	public KnowledgeConstraints(){
		for(int i = 0; i < 3; i++){
			suits[i]= new ArrayList<Card>(); 
			for (Deck.Rank rank : Deck.Rank.values()) {
				Card card = new Card(rank, Deck.Suit.CLUBS);
				suits[i].add(card);
			}		
		}
	}
	public void update(Card seen) {
		if(seen.suit == Deck.Suit.CLUBS){
			for(Card card: clubs){
				if(card.rank == seen.rank){
					clubs.remove(card);
				}
			}
		}
		else if(seen.suit == Deck.Suit.HEARTS){
			for(Card card: hearts){
				if(card.rank == seen.rank){
					clubs.remove(card);
				}
			}
		}
		else if(seen.suit == Deck.Suit.SPADES){
			for(Card card: spades){
				if(card.rank == seen.rank){
					clubs.remove(card);
				}
			}
		}
		else if(seen.suit == Deck.Suit.DIAMONDS){
			for(Card card: diamonds){
				if(card.rank == seen.rank){
					clubs.remove(card);
				}
			}
		}
	}

}
