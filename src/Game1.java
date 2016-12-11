import java.util.ArrayList;

public class Game1 {
	static Player[] players = new Player[4];
	Deck1 deck;
	boolean heartsBroken = false;
	boolean twoOfClubs = false;

	public Game1() {
		players[0] = new RandomPlayer1();
		players[1] = new RandomPlayer1();
		players[2] = new RandomPlayer1();
		players[3] = new RandomPlayer1();
		this.deck = new Deck1();		
	}

	public void playRound() {
		//setup round
		setupRound();
		//find starting player
		int startingPlayer = 0;
		for(int i = 0; i < players.length; i ++){
			if(players[i].hasTwoOfClubs()) startingPlayer = i;
		}
		
		//go through the 13 tricks of the round
		for(int round = 1; round < 14; round++){
			System.out.println("Round: "+round);

			//create new state
			ArrayList<Card> cardsInTrick = new ArrayList<Card>();
			State currentState = new State(deck, heartsBroken, twoOfClubs, cardsInTrick, startingPlayer);//starting player = currentPlayer?
			//go through each of the 4 players and have them play card
			for(int player = startingPlayer; player < startingPlayer+4; player++){
				//get index of current player
				int currentPlayer = player%4;
				//tell player to play
				Card choice = players[currentPlayer].playCard(currentState);
				currentState.update(choice);
				
				System.out.println("Player "+currentPlayer+" played "+choice.toString());
				
			}
			//all cards in trick have been played

			startingPlayer = currentState.winningPlayer();
			//update points for starting player (who just won last round)
			players[startingPlayer].points += currentState.points;
			System.out.println("Player "+startingPlayer+" won "+currentState.points+" points");
			
			System.out.println("");
			updateGame(currentState);
		}

		
	}

	private void updateGame(State currentState) {
		//update master game state
		heartsBroken = currentState.heartsBroken;
		twoOfClubs = currentState.twoOfClubs;
		
		//takes in deck of last current state, makes copy and updates master deck
		deck = new Deck1(currentState.deck);
	}

	private void setupRound() {
		//clear all player hands
		for(Player current: players) current.clearHand();
		// resets game state booleans
		heartsBroken = false;
		twoOfClubs = false;
		// puts all cards in not played, clears played, shuffles deck (?)
		deck.reset();
		// deals cards to players
		deck.deal(players);
		//sorts player hands
		for(Player current: players){
			current.sort();
//			current.printHand();
		}

	}

	public void playGame() {
		boolean play = true;
		//counter keeps track of round number - used in print statement
		int counter = 0;
		
		//while no player exceeds 100 points
		while(play){
			//setup and play new round
			playRound();
			counter++;

			//check that no players have more than 100 points
			for(int i = 0; i < 4; i ++){
				//if player exceeds 100:
				if(players[i].points >= 100){
					//end game
					play = false;
				}
			}
			printRound(counter);
		}
		
	}

	public void printRound(int counter) {
		System.out.println("Round: " + counter);
		System.out.println("Player 0 points : "+ players[0].points);
		System.out.println("Player 1 points : "+ players[1].points);
		System.out.println("Player 2 points : "+ players[2].points);
		System.out.println("Player 3 points : "+ players[3].points);
	}

}
