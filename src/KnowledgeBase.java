
public class KnowledgeBase {
	KnowledgeConstraints[] playersInfo;
	KnowledgeConstraints playerA;
	KnowledgeConstraints playerB;
	KnowledgeConstraints playerC;

	
	public KnowledgeBase(){
		KnowledgeConstraints[] playersInfo = new KnowledgeConstraints[3];

		for(int i = 0; i < playersInfo.length; i++){
			playersInfo[i] = new KnowledgeConstraints();
		}
		
	}
	
	public void update(Card seen){
		for(int i = 0; i < playersInfo.length; i++){
			playersInfo[i].update(seen);
		}
	}


}
