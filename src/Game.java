import java.util.ArrayList;

public class Game {
	Player[] players = new Player[4];
	Deck deck;

	public Game() {
		// TODO Auto-generated method stub
		players[0] = new IntelligentPlayer();
		for(int i = 1; i < 4; i ++){
			players[i] = new Player();
		}
		
		deck = new Deck();	
		boolean play = true;
		int counter = 0;
		while(play){
			newRound();
			for(int i = 0; i < 4; i ++){
				counter++;
				if(players[i].points >= 100){
					//end game
					play = false;
				}
			}
			System.out.println("Round: " + counter);
			System.out.println("Player 0 points : "+ players[0].points);
			System.out.println("Player 1 points : "+ players[1].points);
			System.out.println("Player 2 points : "+ players[2].points);
			System.out.println("Player 3 points : "+ players[3].points);


		}
		
		System.out.println("Game over!");

		
		
	}
	
	public void newRound(){
		//round is 13 tricks
		boolean heartsBroken = false;
		deck.shuffle();
		//deal deals deck and returns index of player with 2 of clubs
		int startingPlayer = deck.deal(players);
		
		for(int trick = 0; trick < 13; trick ++){
			System.out.println("Starting trick "+trick + " starting player " + startingPlayer);
			int points = 0;
			Card winningCard = new Card(2,2); //set the initial "high card" to 2 of clubs (first card played)
											  //only works for first round
			ArrayList<Card> currentTrick = new ArrayList<Card>();
			
			int lastPlayer = startingPlayer+4;
			for(int order = startingPlayer; order < lastPlayer; order++){
				//System.out.println("order: " + (order%4)+ " " + order);

				Card current = players[order%4].playCard(currentTrick, trick, heartsBroken);
				currentTrick.add(current);
				if(order == startingPlayer){ 
					//set winning card to first card played
					winningCard = current;
				}
				
				//add points if heart
				if(current.suit == 0){
					heartsBroken = true;
					points ++;
				}
				//add points for queen of spade
				if(current.suit == 3 && current.rank == 12){
					points += 13;
				}
				
				//update person currently winning trick
				if(current.suit == winningCard.suit && current.rank > winningCard.rank){
					winningCard = current;
					startingPlayer = order; //this player starts next round (curr. winner)
				}
				if(current.suit != winningCard.suit){
					for(int i = 0; i <4; i ++){
						players[i].knowledge.suiteVoid(order%4, winningCard);
					}
				}
				System.out.println("Player "+(order%4) + " played " + current.toString());
				for(int i = 0; i < 4; i ++){
					//add played card to knowledgebase
					players[i].knowledge.update(current);
				}

			}
			startingPlayer = startingPlayer%4;
			//end of trick, players done playing this trick
			System.out.println("Player "+startingPlayer + " won with " + winningCard.toString() + " with " + points + " points");
			System.out.println( "") ;

			//give points to player
			players[startingPlayer].points += points;
		}
		
	}

}
