import java.util.ArrayList;
import java.util.Random;

public class State {
	//took out current player, maybe we don't need it, if we have playerIDs for updating winner
	int startingPlayer;
	int points;

	Card winningCard = new Card(2, 2);
	int currentWinner;

	Deck deck;
	boolean heartsBroken = false;
	boolean twoOfClubs = false;
	ArrayList<Card> cardsInTrick;
	Random rand = new Random();


	public State(Deck deck, boolean heartsBroken, boolean twoOfClubs, ArrayList<Card> cardsInTrick, int currentPlayer) {
		this.startingPlayer = currentPlayer;
		this.currentWinner = currentPlayer;
		this.points = 0;
		this.deck = new Deck(deck);
		this.heartsBroken = heartsBroken;
		this.twoOfClubs = twoOfClubs;
		this.cardsInTrick = new ArrayList<Card>(cardsInTrick);
	}

	public State(State stateToCopy) {
		this.points = stateToCopy.points;
		this.currentWinner = stateToCopy.currentWinner;
		this.startingPlayer = stateToCopy.startingPlayer;
		this.deck = new Deck(stateToCopy.deck);
		this.heartsBroken = stateToCopy.heartsBroken;
		this.twoOfClubs = stateToCopy.twoOfClubs;
		this.cardsInTrick = new ArrayList<Card>(stateToCopy.cardsInTrick);
	}

	public void updateState(Card played, int currentPlayer) {
		/*
		 * this function updates the deck to keep track of played card,
		 * and it updates the winning card and player in state,
		 * also updates flags
		 */
		System.out.println("updating state with " + played.toString() + " played by player "+currentPlayer + "\n");

		// update winning card and winning player
		if (cardsInTrick.size() > 0) {
			//System.out.println(played.toString());
			if (played.suit == winningCard.suit) {
				if (played.rank > winningCard.rank) {
					// update leading card if in suit and greater than leading
					// card
					winningCard = played;
					// keep track of index of player who is winning
					currentWinner = currentPlayer;
				}
			}
		} else {
			winningCard = played;
			// keep track of index of player who is winning
			currentWinner = currentPlayer;
		}


		// add card played to trick
		this.cardsInTrick.add(played);
		
		// update our copy of the deck to reflect the change
		this.deck.updatePlayed(played);
		
		if (played.suit == 0) {
			// break hearts
			this.heartsBroken = true;
			this.points++;
		}
		if (played.suit == 3 && played.rank == 12) {
			// queen of spades
			this.points += 13;
		} else if (played.suit == 2 && played.rank == 2) {
			// update 2 of clubs
			this.twoOfClubs = true;

		}


	}

	public int winningPlayer() {
		return currentWinner;
	}
	
//	public int advance(Card card, ArrayList<Card>[] playoutHand){
//		int points = 0;
//		//check round
//		update(card, currentPlayer);//?
//		
//		while(validTrick()){
//			Card choice = playRandomCard(playoutHand);
//			currentPlayer++;
//			update(choice, currentPlayer);
//		}
//		
//		
//		
//		return points;
//	}
//	
	
	public int getLeadingSuit() {
		if (this.cardsInTrick.size() != 0) {
			return this.cardsInTrick.get(0).suit;
		} else {
			return -1;
		}
	}
	
	public boolean roundsRemaining() {
		return this.deck.played.size() > 0;
	}
	
	public boolean validTrick() {
		return cardsInTrick.size()<4;
	}
	


	private Card playRandomCard(ArrayList<Card>[] currentHand) {
		/*
		 * Method to return a random unplayed card that is not in the
		 * IntelligentPlayer's hand
		 */
		boolean valid = false;
		Card choice = null;
		while (!valid) {
			int index = rand.nextInt(deck.notPlayed.size());
			choice = deck.notPlayed.get(index);

			if (!inHand(choice, currentHand)) {
				// not in Intelligent Player's hand
				valid = true;
			}

		}
		return choice;
	}

	public boolean inHand(Card card,  ArrayList<Card>[] currentHand) {
		for (int i = 0; i < 4; i++) {
			for (Card mine : currentHand[i]) {
				if (mine.equals(card)) {
					return true;
				}
			}
		}
		return false;

	}
	
	public void printState(){
		System.out.println("Printing State.");
		System.out.println("hearts broken: "+ heartsBroken);
		System.out.println("two of clubs: "+ twoOfClubs);
		System.out.println("starting player: "+ startingPlayer);
		System.out.println("cards played:  "+ deck.played);
		System.out.println("cards not played:  "+ deck.notPlayed);

		System.out.println(" ");
		System.out.println(" ");

	}

}
