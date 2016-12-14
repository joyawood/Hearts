
public class Driver {
	static Player[] players = new Player[4];
	static Deck deck;

	public static void main(String[] args) {
		int[] scores = new int[4];

		for (int a = 0; a < 100; a++) {
			Game game = new Game();
			int[] currentScores = game.playGame();
			for (int i = 0; i < 4; i++) {
				scores[i] += currentScores[i];
			}
		}
		System.out.println("");

		System.out.println("Final scores: ");
		for (int i = 0; i < 4; i++) {
			System.out.println("Player " + i + ": " + scores[i]);
		}

	}

}
