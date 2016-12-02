import java.util.Comparator;

public class Card  {
	Deck.Rank rank;
	Deck.Suit suit;

	public Card(Deck.Rank rank, Deck.Suit suit){
		this.rank = rank;
		this.suit = suit;
	}

	public String toString() {
		return String.format("%s of %s", rank, suit);
	}

	
	public static Comparator<Card> compByRank()
	{   
	 Comparator comp = new Comparator<Card>(){
		 
	     public int compare(Card o, Card o1) {
	 			System.out.println("arg");

	 		if(o1.suit.compareTo(o.suit) == 0){
	 			System.out.println(o1.toString()+ o1.rank.compareTo(o.rank) + o.toString());
	 			return o1.rank.compareTo(o.rank);
	 		}
	 		else{
	 			System.out.println("different suits");
	 			return 0;
	 		}
	 	}

		    
	 };
	 return comp;
	} 
}
