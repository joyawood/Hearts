import java.util.ArrayList;

public class State {
	int currentPlayer;
	int startingPlayer;
	int points;
	Deck1 deck;
	boolean heartsBroken = false;
	boolean twoOfClubs = false;//?
	ArrayList<Card> cardsInTrick;
	
	public State(Deck1 deck, boolean heartsBroken, boolean twoOfClubs, ArrayList<Card> cardsInTrick, int currentPlayer){
		this.currentPlayer = currentPlayer;
		this.startingPlayer = currentPlayer;
		this.points = 0;
		this.deck = new Deck1(deck);
		this.heartsBroken = heartsBroken;
		this.twoOfClubs = twoOfClubs;
		this.cardsInTrick = new ArrayList<Card>(cardsInTrick);
	}

	public State(State stateToCopy){
		this.points = stateToCopy.points;
		this.currentPlayer = stateToCopy.currentPlayer;
		this.startingPlayer = stateToCopy.startingPlayer;
		this.deck = new Deck1(stateToCopy.deck);
		this.heartsBroken = stateToCopy.heartsBroken;
		this.twoOfClubs = stateToCopy.twoOfClubs;
		this.cardsInTrick = new ArrayList<Card>(stateToCopy.cardsInTrick);
	}
	
	public void update(Card played){
		//update current player to next player
		this.currentPlayer = (currentPlayer+1)%4;
		//add card played to trick
		this.cardsInTrick.add(played);
		//update our copy of the deck to reflect the change
		this.deck.updatePlayed(played);
		if(played.suit == 0){
			//break hearts
			this.heartsBroken = true;
			this.points++;
		}
		if(played.suit == 3 && played.rank == 12){
			//queen of spades
			this.points+=13;
		}
		else if(played.suit == 2 && played.rank == 2){
			//update 2 of clubs
			this.twoOfClubs = true;

		}
	}
	public int winningPlayer(){
		// get suit and rank of first card added to trick (therefore the leading card)
		Card leadingCard = cardsInTrick.get(0);
		int winningPlayerIndex = 0;
	
		//iterate through all cards in trick
		for(int i = 0; i < cardsInTrick.size(); i++){
			Card curr = cardsInTrick.get(i);
			if(curr.suit == leadingCard.suit){
				if(curr.rank > leadingCard.rank){
					//update leading card if in suit and greater than leading card
					leadingCard = curr;
					//keep track of index of player who is winning
					winningPlayerIndex = (startingPlayer + i)  % 4; 
				}
			}
		}
		return winningPlayerIndex;
	}
	
	public int getLeadingSuit(){
		if(this.cardsInTrick.size() != 0){
			return this.cardsInTrick.get(0).suit;
		}else{
			return -1;
		}
	}
}
