import java.util.Random;

public class NaivePlayer extends Player{
	Random rand = new Random();

	public Card playCard(State currentState) {
		Card choice = null;
		int leadingSuit = currentState.getLeadingSuit();

		if(currentState.twoOfClubs == false){
			//first move, play two of clubs
			currentState.twoOfClubs = true;
			choice = new Card(2,2);
		}
		else if(leadingSuit == -1){
			//if we are leading
			if(hasOnlyHearts()){
				//only hearts - lowest heart
				choice = hand[0].get(0);
			}
			else{
				//lowest non heart
				choice = playLowestNonHeart();
			}
		}
		else if(isVoid(leadingSuit)){
			//if queen drop queen
			//else if hearts drop hearts
			//else high card
			if(hasQueen()){
				choice = new Card(12,3);
			}
			else if(hand[0].size()>0){
				choice = hand[0].get(hand[0].size()-1);
			}
			else{
				choice = playHighestNonHeartCard();
			}
		}
		else{
			//play lowest of suite
			choice = hand[leadingSuit].get(0);
		}
		remove(choice);
		return choice;
	}

	private Card playHighestNonHeartCard() {
		Card highest = new Card(0, 0);
		for(int i = 1; i < hand.length; i ++){
			for(Card card: hand[i]){
				if(card.rank > highest.rank){
					highest = card;
				}
			}
		}
		return highest;		
	}

	private Card playLowestNonHeart() {
		Card lowest = new Card(52, 0);
		for(int i = hand.length-1; i > 0; i --){
			for(Card card: hand[i]){
				if(card.rank < lowest.rank){
					lowest = card;
				}
			}
			
		}
		
		return lowest;
	}

}
