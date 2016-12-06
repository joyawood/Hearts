
public class KnowledgeBase {
	int player;
	// hand...
	boolean[][] knowledge = new boolean[4][52];

	public KnowledgeBase() {
		for (int i = 0; i < 4; i++) {
			knowledge[i] = new boolean[52];
			for (int j = 0; j < 52; j++) {
				knowledge[i][j] = true;
			}
		}

	}

	public void update(Card seen) {
		for (int i = 0; i < 4; i++) {
			knowledge[i][seen.suit*13 + seen.rank-2] = false;
		}

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
	public boolean cardInDomain(Card card){
		
		return knowledge[0][card.suit*13 + card.rank-2];

	}

}
