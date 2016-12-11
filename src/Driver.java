
public class Driver {

	public static void main(String[] args) {
//		Card queen = new Card(12, 3);
//		System.out.println(queen.toString());
		
		int[] totalPoints = new int[4];
		for(int i = 0; i <10; i++){
			Game game = new Game();
			for(int j = 0; j<4; j++){
				totalPoints[j] += game.points[j];
			}
		}
		for(int j = 0; j<4; j++){
			System.out.println("player " + j + " points: " + totalPoints[j]);
		};
		
		
	}

}
