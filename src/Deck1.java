import java.util.ArrayList;

public class Deck1 {
	Card[] cards = new Card[52];
	int length = 52;
	ArrayList<Card> played;
	ArrayList<Card> notPlayed;


	public Deck1() {
		newDeck();
		shuffle();
		played = new ArrayList<Card>();
		notPlayed = new ArrayList<Card>();

		for(Card current: cards){
			notPlayed.add(current);
		}
	}
	
	public Deck1(Deck1 toCopy) {
		//makes a deep copy of the passed in deck object
		newDeck();
		played = new ArrayList<Card>();
		notPlayed = new ArrayList<Card>();
		
		for(Card card: toCopy.played){
			played.add(card);
		}
		for(Card card: toCopy.notPlayed){
			notPlayed.add(card);
		}
		

	}
	
	public void reset(){
		played.clear();
		for(Card current: cards){
			notPlayed.add(current);
		}
		shuffle();
	}
	
	public void newDeck() {
		int index = 0;
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
	
	public void updatePlayed(Card current){
		 for(Card card:notPlayed){
			 if(card.equals(current)){
				 notPlayed.remove(card);
				 played.add(card);
				 break;
			 }
		 }
	}

	public void deal(Player[] players) {
		int cardIndex = 0;
	
		for(int i = 0; i < 13; i++){
			for(Player current: players){
				current.add(cards[cardIndex]);
				cardIndex++;
			}
		}
	}
	
}
