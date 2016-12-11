import java.util.Random;

public class RandomPlayer1 extends Player{
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
			//if we are leading, play random card
			choice = playRandomCard(currentState.heartsBroken);
		}
		else if(isVoid(leadingSuit)){
			choice = playRandomCard(currentState.heartsBroken);
		}
		else{
			//play random card from suit
			int randCard = rand.nextInt(hand[leadingSuit].size());
			choice = hand[leadingSuit].get(randCard);
		}
		remove(choice);
		return choice;
	}
	
	public Card playRandomCard(boolean heartBroken){
//		System.out.println("Hearts broken: "+heartBroken);
		Card choice = null;
		
		//if hearts is broken
		if(heartBroken){
			boolean valid = false;
			int randSuit = 0;
			//while suit selected has cards
			while(!valid){
				//select suit from 0 - 3 (include hearts)
				randSuit = rand.nextInt(4);
				if(hand[randSuit].size() != 0){
					valid = true;
				}
			}
			//select random card from chosen random suit
			int randCard = rand.nextInt(hand[randSuit].size());
			choice = hand[randSuit].get(randCard);
		}else{
			//if hearts is not broken
			
			//if has only hearts, play hearts
			if(hasOnlyHearts()){
				int randCard = rand.nextInt(hand[0].size());
				choice = hand[0].get(randCard);
			}else{
				boolean valid = false;
				int randSuit = 0;
				//while suit selected has cards
				while(!valid){
					//select suit from 1 - 3 (exclude hearts)
					randSuit = rand.nextInt(3)+1;
					if(hand[randSuit].size() != 0){
						valid = true;
					}
				}
				//select random card from chosen random suit
				int randCard = rand.nextInt(hand[randSuit].size());
				choice = hand[randSuit].get(randCard);
			}
	
		}

		return choice;
	}
	
}
