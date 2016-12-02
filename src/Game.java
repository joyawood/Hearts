import java.util.ArrayList;

public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deck.Rank queen = Deck.Rank.QUEEN;
		Deck.Rank deuce = Deck.Rank.DEUCE;
		///System.out.println("" + (queen.compareTo(Deck.Rank.KING)));
		//newGame();

	}
	
	public static void newGame(){
		Deck deck = new Deck();
		Player one = new Player();
		Player two = new Player();
		Player three = new Player();
		Player four = new Player();
		one.sort();
		deck.shuffle();
		deck.deal(one, two, three, four);
		for(int i = 0; i < 4; i ++){
			ArrayList<Card> suit = one.suits[i];
			for(Card card: suit){
				System.out.println(card.toString());
			}
		}
	}

}
