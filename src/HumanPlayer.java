import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class HumanPlayer extends Player{
	int points;
	ArrayList<Card>[] hand = new ArrayList[4];
	Scanner input = new Scanner(System.in);
	int playerID;

	public HumanPlayer(int ID) {
		// initialize empty hand
		
		super(ID);
		for (int i = 0; i < 4; i++) {
			hand[i] = new ArrayList<Card>();
		}
	}


	public Card playCard(State currentState) {
		System.out.println("\nIt's your turn! Choose a card to play.");
		System.out.println("\nHere is your hand:");
//		printHand();
		for (ArrayList<Card> suit : hand) {
			for (int i = 0; i < suit.size(); i ++) {
				Card card = suit.get(i);
				System.out.print(card.toString());
				if (i != suit.size() -1){
					System.out.print(", ");

				}

			}
			System.out.println("");

		}
		System.out.println("");

		
		Card choice = null;
		int leadingSuit = currentState.getLeadingSuit();
		
		if (currentState.twoOfClubs == false) {
			// first move, play two of clubs
			System.out.println("You are starting with the 2 of Clubs.");
			currentState.twoOfClubs = true;
			choice = new Card(2, 2);
		} 
		else{
			ArrayList<Card> validOptions = getValidPlayOptions(currentState);
			System.out.println("Choose a valid card to play by typing in the choice number: ");

			for(int i = 0; i < validOptions.size(); i ++){
				System.out.println((i+1)+": "+validOptions.get(i).toString());
			}
			
			boolean validInput = false;
			String userChoice = input.nextLine();
			Integer selection = null;
			while(!validInput){
				selection = tryParse(userChoice);
				if(selection == null){
					System.out.println("Invalid choice. ");
					System.out.println("Please choose a valid card to play by typing in the choice number: ");
					for(int i = 0; i < validOptions.size(); i ++){
						System.out.println((i+1)+": "+validOptions.get(i).toString());
					}
					userChoice = input.nextLine();
				}
				else if((selection-1)>validOptions.size()-1 || (selection-1) < 0){
					System.out.println("Invalid choice. ");
					System.out.println("Please choose a valid card to play by typing in the choice number: ");
					for(int i = 0; i < validOptions.size(); i ++){
						System.out.println((i+1)+": "+validOptions.get(i).toString());
					}
					userChoice = input.nextLine();
				}
				else{
					validInput = true;
				}
			}
			
			choice = validOptions.get(selection-1);
			
		}
		
		remove(choice);
		return choice;

	}
	
	public ArrayList<Card>  getValidPlayOptions(State currentState){
		ArrayList<Card> valid = new ArrayList();
		int leadingSuit = currentState.getLeadingSuit();

		if(hasTwoOfClubs()){
			System.out.println("Play starts with the 2 of clubs.");
			Card two = new Card(2,2);
			valid.add(two);
		}
		else if(leadingSuit == -1){
			//leading
			if(currentState.heartsBroken){
				for(Card card : hand[0]){
					valid.add(card);
				}
			}
			for(int i = 1; i < 4; i ++){
				for(Card card : hand[i]){
					valid.add(card);
				}
			}
			
		}
		else{
			//following

			if(hand[leadingSuit].size()==0){
				//void
				for(int i = 0; i < 4; i ++){
					for(Card card : hand[i]){
						valid.add(card);
					}
				}
				
			}
			else{
				//not void
				for(Card card : hand[leadingSuit]){
					valid.add(card);
				}
			}
		}
		
		return valid;
		
	}
	public void add(Card card) {
		// add card to hand by suit
		hand[card.suit].add(card);
	}
	
	public static Integer tryParse(String text) {
		  try {
		    return Integer.parseInt(text);
		  } catch (NumberFormatException e) {
		    return null;
		  }
		}
}
