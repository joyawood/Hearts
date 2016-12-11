
public class Driver1 {
	static Player[] players = new Player[4];
	static Deck1 deck;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] scores = new int[4];
		
		for(int a = 0; a <10; a++){
			Game1 game = new Game1();
			int[] currentScores = game.playGame();
			for(int i = 0; i < 4; i ++){
				scores[i]+=currentScores[i];
			}
		}
		System.out.println("Final scores: ");
		for(int i = 0; i < 4; i ++){
			System.out.println("Player " + i +": "+ scores[i]);
		}
		
	}
	
	

	
}
