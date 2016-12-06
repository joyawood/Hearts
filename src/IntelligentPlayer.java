import java.util.ArrayList;

public class IntelligentPlayer extends Player{
	
	public Card playCard(ArrayList<Card> currentTrick, int round, boolean broken) {
		Card choice = null;
		if (currentTrick.size() == 0){
			choice = lead(currentTrick, round, broken);
		}
		else{
			choice = follow(currentTrick, round, broken);
		}
		System.out.println(choice.toString());
		pop(choice.rank, choice.suit);
		return choice;
		
	}

	private Card follow(ArrayList<Card> currentTrick, int round, boolean broken) {
		Card choice = followTest(currentTrick, round, broken);

		return choice;
	}

	private Card lead(ArrayList<Card> currentTrick, int round, boolean broken) {
		Card choice = null;
		
		if (round == 0) {
			choice = new Card(2, 2);
		}
		else{
			//if queen in game
			if(knowledge.queen()){
				//if queen in our hand
				if(checkCard(12, 3)){
					//defend spades
					choice = defendSpades(currentTrick, round, broken);
				}
				//queen not in our hand
				else{
					//do we have spades?
					if(!isVoid(3)){
						// we have spades
						//do we have high spades?
						if(checkCard(13, 3) || checkCard(14, 3)){
							choice = defendSpades(currentTrick, round, broken);
						}else{
							//do we have low spades?
							choice = bleedSpades(currentTrick, round, broken);
						}
					}else{
						// we have no spades
						choice = safeLead(currentTrick, round, broken);
					}
				}
			}else{
				//if queen not in game
				choice = safeLead(currentTrick, round, broken);
			}
		}
		return choice;
	}

	private Card safeLead(ArrayList<Card> currentTrick, int round, boolean broken) {
		Card choice = null;
		
		for(int player = 0; player < 4; player++){
			for(int suit = 0; suit < 4; suit++)
				suitVoid(player, suit);
		}
		
		

		switch(round){
			
		  
			case 13:
				// get last card
				break;
		}
		
		choice = followTest(currentTrick, round, broken);
		
		return choice;
	}

	private Card bleedSpades(ArrayList<Card> currentTrick, int round, boolean broken) {
		// play low spades to force queen play
		Card choice = null; 
		
		//because we don't have Q, K, A, take last/highest card from spade array
		choice = hand[3].get(hand[3].size()-1);
		
		return choice;
	}

	private Card defendSpades(ArrayList<Card> currentTrick, int round, boolean broken) {
		// void self in suits to eventually dump Q, K or A
		Card choice = null; 
		
		choice = dontLeadSpades();
		
		return choice;
	}


}
