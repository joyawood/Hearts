import java.util.ArrayList;


public class KnowledgeConstraints {

	ArrayList<Card> clubs;
	ArrayList<Card> hearts;
	ArrayList<Card> spades;
	ArrayList<Card> diamonds;
	@SuppressWarnings("rawtypes")
	ArrayList[] suits = {clubs, diamonds, hearts, spades};//CLUBS, DIAMONDS, HEARTS, SPADES


	
	@SuppressWarnings("unchecked")
	public KnowledgeConstraints(){
		int i = 0;
		for(Deck.Suit suit: Deck.Suit.values()){
			suits[i]= new ArrayList<Card>(); 
			for (Deck.Rank rank : Deck.Rank.values()) {
				Card card = new Card(rank, suit);
				suits[i].add(card);
			}
			i++;
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
