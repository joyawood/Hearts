
public class KnowledgeBase {
	int player;
	// hand...
	boolean[][] knowledge = new boolean[4][52];
	boolean[] played = new boolean[52];

	public KnowledgeBase() {
		for (int i = 0; i < 4; i++) {
			knowledge[i] = new boolean[52];
			for (int j = 0; j < 52; j++) {
				knowledge[i][j] = true;
			}
		}
		for (int j = 0; j < 52; j++) {
			played[j] = false;
		}

	}

	public void update(Card seen) {
		for (int i = 0; i < 4; i++) {
			knowledge[i][seen.suit*13 + seen.rank-2] = false;
		}
		played[seen.suit*13 + seen.rank-2] = true;

	}

	public void suiteVoid(int player, Card card) {
		int suit = card.suit;
		for (int i = 0 * suit; i < 0 * suit + 13; i++) {
			knowledge[player][i] = false;
		}
	}

	public void reset() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 52; j++) {
				knowledge[i][j] = true;
			}
		}
	}
//	public boolean cardInDomain(Card card){//out there
//		
//		return knowledge[0][card.suit*13 + card.rank-2]|| knowledge[1][card.suit*13 + card.rank-2]||knowledge[2][card.suit*13 + card.rank-2]||knowledge[3][card.suit*13 + card.rank-2];
//
//	}
	
	public boolean checkPlayed(Card card){
		return played[card.suit*13 + card.rank-2];
	}
	
	public boolean queen(){
		//is queen still in game
		return !played[3*13 + 12-2];
	}

}
