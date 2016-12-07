import java.util.ArrayList;

public class IntelligentPlayer extends Player {

	public Card playCard(ArrayList<Card> currentTrick, int round,
			boolean broken, Card winningCard) {
		Card choice = null;
		if (currentTrick.size() == 0) {
			choice = lead(currentTrick, round, broken);
		} else {
			choice = follow(currentTrick, round, broken, winningCard);
		}
		System.out.println(choice.toString());
		pop(choice.rank, choice.suit);
		return choice;

	}

	private Card follow(ArrayList<Card> currentTrick, int round,
			boolean broken, Card winningCard) {
		// Card choice = followTest(currentTrick, round, broken);
		Card choice = null;
		int leadSuit = winningCard.suit;

		if (round == 0) {
			// this is a really specific situation
		} else {
			switch (leadSuit) {
			case 0:
				if (isVoid(0)) {
					// QT or !Q
						// if (queen()) {
				} else {
					// canDuck? if yes, duck. if no, win high
					choice = duckOrWin(currentTrick, round, broken, winningCard);
				}
				break;

			case 1:
				if (isVoid(1)) {
					// QT

				} else {
					// high prob of points? duck. low prob of points? win.
					choice = pointProb(currentTrick, round, broken, winningCard);
				}
				break;

			case 2:
				if (isVoid(2)) {
					// QT

				} else {
					// high prob of points? duck. low prob of points? win.
					choice = pointProb(currentTrick, round, broken, winningCard);
				}
				break;

			case 3:
				if (isVoid(3)) {
					// points, high void

				} else {
					if (knowledge.queen()) {
						if (checkCard(12, 3)) {
							// YQ
						} else {
							// OQ
						}
					} else {
						// canDuck? if yes, duck. if no, win high
						choice = duckOrWin(currentTrick, round, broken, winningCard);
					}
				}
				break;
			}
		}

		return choice;
	}

	private Card pointProb(ArrayList<Card> currentTrick, int round, boolean broken, Card winningCard) {
		// TODO Auto-generated method stub
		return null;
	}

	private Card duckOrWin(ArrayList<Card> currentTrick, int round, boolean broken, Card winningCard, int currentPlayerNum) {
		int winningSuit = winningCard.suit;
		Card choice = null;
		if(hand[winningSuit].size()>0){
			if(hand[winningSuit].get(0).rank > winningCard.rank){
				//can't duck
				switch(currentTrick.size()){
					case 3:
						//win high;
						choice = hand[winningSuit].get(hand[winningSuit].size()-1);
						break;
					case 2 : //& 1:
						//if next player void,  win high
						if(knowledge.checkSuitVoid(((currentPlayerNum+1)%4), winningSuit)){
							choice = hand[winningSuit].get(hand[winningSuit].size()-1);
						}
						else{
							choice = hand[winningSuit].get(0);//win low
						}
						break;
					case 1:
						if(knowledge.checkSuitVoid(((currentPlayerNum+1)%4), winningSuit)){
							if(knowledge.checkSuitVoid(((currentPlayerNum+2)%4), winningSuit)){
								choice = hand[winningSuit].get(hand[winningSuit].size()-1);//win high
							}
							else{
								choice = hand[winningSuit].get(hand[winningSuit].size()/2);//play mid
							}
						}
						else{
							//go low
							choice = hand[winningSuit].get(0);//win low
						}

						break;
					
				}
			}
			else{
				//can duck
				choice = hand[winningSuit].get(0);
				for(Card current: hand[winningSuit]){
					if(current.rank>choice.rank && current.rank < winningCard.rank){
						choice = current;
					}
				}
			}
		}
		
		return choice;
	}

	private Card lead(ArrayList<Card> currentTrick, int round, boolean broken) {
		Card choice = null;

		if (round == 0) {
			choice = new Card(2, 2);
		} else {
			// if queen in game
			if (knowledge.queen()) {
				// if queen in our hand
				if (checkCard(12, 3)) {
					// defend spades
					choice = defendSpades(currentTrick, round, broken);
				}
				// queen not in our hand
				else {
					// do we have spades?
					if (!isVoid(3)) {
						// we have spades
						// do we have high spades?
						if (checkCard(13, 3) || checkCard(14, 3)) {
							choice = defendSpades(currentTrick, round, broken);
						} else {
							// do we have low spades?
							choice = bleedSpades(currentTrick, round, broken);
						}
					} else {
						// we have no spades
						choice = safeLead(currentTrick, round, broken);
					}
				}
			} else {
				// if queen not in game
				choice = safeLead(currentTrick, round, broken);
			}
		}
		return choice;
	}

	private Card safeLead(ArrayList<Card> currentTrick, int round,
			boolean broken) {
		Card choice = null;

		// play card from least common suit
		if (broken) {
			// find least common suit

		}

		choice = followTest(currentTrick, round, broken);

		return choice;
	}

	private Card bleedSpades(ArrayList<Card> currentTrick, int round,
			boolean broken) {
		// play low spades to force queen play
		Card choice = null;

		// because we don't have Q, K, A, take last/highest card from spade
		// array
		choice = hand[3].get(hand[3].size() - 1);

		return choice;
	}

	private Card defendSpades(ArrayList<Card> currentTrick, int round,
			boolean broken) {
		// void self in suits to eventually dump Q, K or A
		Card choice = null;

		choice = dontLeadSpades();

		return choice;
	}

}
