import java.util.ArrayList;
import java.util.Random;




public class RandomPlayer extends Player {
	
	public Random rand = new Random();
	
	public Card playCard(ArrayList<Card> currentTrick, int round, boolean broken, Card winningCard) {
		Card choice = null;

		if (currentTrick.size() == 0) {
			//leading 	
			if (round == 0) {
				choice = new Card(2, 2);
			} else {
				if(broken){
					//play random
					// max = 3, min = 0 -> include hearts
					boolean valid = false;
					int randSuit = 0;
					while(!valid){
						randSuit = rand.nextInt(4);
						if(hand[randSuit].size() != 0){
							valid = true;
						}
					}
					int randCard = rand.nextInt(hand[randSuit].size());
					choice = hand[randSuit].get(randCard);
				}else{
					//not broken
					// max = 3, min = 1 -> exclude hearts
					boolean valid = false;
					int randSuit = 0;
					while(!valid){
						randSuit = rand.nextInt(3)+1;
						if(hand[randSuit].size() != 0){
							valid = true;
						}
					}
					int randCard = rand.nextInt(hand[randSuit].size());
					choice = hand[randSuit].get(randCard);
				}
			}
		}
		else{
			// are we void in this suit?
			int suit = currentTrick.get(0).suit;

			if (hand[suit].size() > 0) {
				// we have card in suit
				int randCard = rand.nextInt(hand[suit].size());
				choice = hand[suit].get(randCard);
			}else{
				//play random
				// max = 3, min = 0 -> include hearts
				boolean valid = false;
				int randSuit = 0;
				while(!valid){
					randSuit = rand.nextInt(4);
					if(hand[randSuit].size() != 0){
						valid = true;
					}
				}
				int randCard = rand.nextInt(hand[randSuit].size());
				choice = hand[randSuit].get(randCard);
			}
		}
		
		pop(choice.rank, choice.suit);
		return choice;
	}

	
	
	
}