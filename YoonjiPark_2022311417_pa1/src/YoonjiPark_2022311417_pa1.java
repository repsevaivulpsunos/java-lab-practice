import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class YoonjiPark_2022311417_pa1 {
	public static void main(String[] args) {
		int seed = Integer.parseInt(args[0]);
		int numPlayers = Integer.parseInt(args[1]);
		
		Deck deck = new Deck();
		deck.shuffle(seed);
		
		House house = new House();
		Player player1 = new Player();
		
		Computer[] players = new Computer[numPlayers];
		
		for (int i = 0; i < numPlayers - 1; i++) {
			players[i] = new Computer(i+2);
		}
		
		house.addCard(deck.dealCard());
		house.addCard(deck.dealCard());
		
		player1.addCard(deck.dealCard());
		player1.addCard(deck.dealCard());
		
		for (int i = 0; i < numPlayers - 1; i++) {
			players[i].addCard(deck.dealCard());
			players[i].addCard(deck.dealCard());
		}
		
		house.firstCard();
		player1.printCard();
		for (int i = 0; i < numPlayers - 1; i++) {
			players[i].printCard();
		}
		
		if (house.getResult() != 21) {
			System.out.println("--- player1 turn---");
			player1.printCard();
			Scanner scanner = new Scanner(System.in);
			
			while(true) {
				String input = scanner.nextLine().toLowerCase();
				if (input.equals("hit")) {
					player1.addCard(deck.dealCard());
					player1.printCard();
					if (player1.checkBust()) {
						break;
					}
				}
				else if (input.equals("stand")) {
					player1.printCard();
					break;
				}
			}
			
			for (int i = 0; i < numPlayers-1; i++) {
				System.out.println("--- player"+players[i].getIndex()+" turn---");
				players[i].printCard();
				while(players[i].checkBust() != true) {
					if (players[i].checkHit()) {
						players[i].addCard(deck.dealCard());
						players[i].printCard();
					}
					else {
						players[i].printCard();
						break;
					}
				}
			}
			
			System.out.println("--- House turn ---");
			house.printCard();
			while (house.getResult() < 21) {
				if (house.checkHit()) {
					
					house.addCard(deck.dealCard());
					house.printCard();
				}
				else {
					house.printCard();
					break;
				}
			}
			
			System.out.println("--- Game Results ---");
			house.printCard();
			if (house.checkBust()) {
				if (player1.checkBust()) {
					System.out.print("[Lose] ");
					player1.printCard();
				}
				else {
					System.out.print("[Win] ");
					player1.printCard();
				}
				for (int i = 0;  i < numPlayers - 1; i++) {
					if (players[i].checkBust()) {
						System.out.print("[Lose] ");
						players[i].printCard();
					}
					else {
						System.out.print("[Win] ");
						players[i].printCard();
					}
				}
			}
			else {
				if (player1.checkBust()==false && player1.getResult() > house.getResult()) {
					System.out.print("[Win] ");
					player1.printCard();
				}
				else if (player1.checkBust()==false && player1.getResult() == house.getResult()) {
					System.out.print("[Draw] ");
					player1.printCard();
				}
				else {
					System.out.print("[Lose] ");
					player1.printCard();

				}
				for (int i = 0; i < numPlayers - 1; i++) {
					if (players[i].checkBust()==false && players[i].getResult() > house.getResult()) {
						System.out.print("[Win] ");
						players[i].printCard();
					}
					else if (players[i].checkBust()==false && players[i].getResult() == house.getResult()) {
						System.out.print("[Draw] ");
						players[i].printCard();
					}
					else {
						System.out.print("[Lose] ");
						players[i].printCard();
					}
				}
			}
		}
		else {
			System.out.println("--- Game Results ---");
			house.printCard();
			System.out.print("[Lose] ");
			player1.printCard();
			for (int i = 0; i < numPlayers - 1; i++) {
				System.out.print("[Lose] ");
				players[i].printCard();
			}
		}
	}
}

class Card {
	private int value;
	private int suit;
	private String name;
	
	public Card(int theValue, int theSuit) {
		value = theValue;
		suit = theSuit;
		cardStatus();
		name = cardStatus();
	}	
	
	int getValue() {
		return value;
	}
	
	int getSuit() {
		return suit;
	}
	
	String getName() {
		return name;
	}
	
	String cardStatus() {
		String setValue;
		switch(value) {
			case 1:
				setValue = "A";
				break;
		
			case 11:
				setValue = "J";
				break;
				
			case 12:
				setValue = "Q";
				break;
			
			case 13:
				setValue = "K";
				break;
				
			default:
				setValue = String.valueOf(value);
		}
		
		String setSuit;
		switch(suit) {
			case 0:
				setSuit = "c";
				break;
				
			case 1:
				setSuit = "h";
				break;
				
			case 2:
				setSuit = "d";
				break;
				
			case 3:
				setSuit = "s";
				break;
				
			default:
				setSuit = "";
				break;
		}
		return setValue + setSuit;
	}
}
	
class Deck {
	private Card[] deck;
	private int cardsUsed = 0;
	
	Deck() {
		deck = new Card[52];
		int index = 0;
		for (int suit = 0; suit <= 3; suit++) {
			for (int value = 1; value <=13; value++) {
				deck[index] = new Card(value, suit);
				index++;
			}
		}
	}
	
	public void shuffle (int seed) {
		Random random = new Random(seed);
		for (int i = deck.length - 1; i > 0; i--) {
			int rand = (int)(random.nextInt(i+1));
			Card temp= deck[i];
			deck[i] = deck[rand];
			deck[rand] = temp;
		}
		cardsUsed++;
	}
	
	public Card dealCard() {
		if (cardsUsed == deck.length) {
			throw new IllegalStateException("No cards are left in the deck.");
		}
		cardsUsed++;
		return deck[cardsUsed - 1];
	}
}

class Hand {
	protected ArrayList<Card> cardList;
	
	Hand() {
		cardList = new ArrayList<>();
		
	}
	
	void addCard(Card newCard) {
		cardList.add(newCard);
	}
	
	int getResult() {
		int sum = 0;
		int Ace = 0;
		
		for (Card Check : cardList) {
			int value = Check.getValue();
			if (value == 1) {
				Ace++;
				sum = sum + 11;
			}
			else if (value >= 10) {
				sum = sum + 10;
			}
			else {
				sum = sum + value;
			}
		}
		
		if (Ace > 0 && sum > 21) {
			sum = sum - 10;
			Ace--;
		}
		
		return sum;
	}
}

class Computer extends Hand{
	private int index;
	
	Computer(int index) {
		super();
		this.index = index;
	}
	
	int getIndex() {
		return index;
	}
	
	boolean checkHit() {
		int score = getResult();
		if (score > 17&&score<=21) {
			System.out.println("stand");
			return false;
		}
		else if (score > 21) {
			return false;
		}
		else if (score < 14) {
			System.out.println("hit");
			return true;
		}
		else {
			Random random = new Random();
			int is_hit = (int)(random.nextInt(2));
			
			if (is_hit == 1) {
				System.out.println("hit");
				return true;
			}
			else {
				System.out.println("stand");
				return false;
			}
		}
	}
	
	boolean checkBust() {
		
		int score = getResult();
		if (score > 21) {
			return true;
		}
		else {
			return false;
		}
	}
	
	void printCard() {
		int score = getResult();
		System.out.print("Player"+index+": ");
		for (int i = 0; i < cardList.size(); i++) {
			if (i == 0) {
				System.out.print(cardList.get(i).getName());
			}
			else {
				System.out.print(", "+cardList.get(i).getName());
			}

		}
		if (checkBust() == true) {
			System.out.println(" ("+score+") - bust");
		}
		else {
			System.out.println(" ("+score+")");
		}
	}
}

class Player extends Hand {
	
	Player() {
		super();
	}
	
	
	void printCard() {
		int score = getResult();
		System.out.print("Player1: ");
		for (int i = 0; i < cardList.size(); i++) {
			if (i == 0) {
				System.out.print(cardList.get(i).getName());
			}
			else {
				System.out.print(", "+cardList.get(i).getName());
			}
		}
		if (checkBust() == true) {
			System.out.println(" ("+score+") - bust");
		}
		else {
			System.out.println(" ("+score+")");
		}
	}
	
	boolean checkBust() {
		int score = getResult();
		if (score > 21) {
			return true;
		}
		else {
			return false;
		}
	}

}

class House extends Hand{
	
	House() {
		super();
	}
	
	boolean checkBust() {
		
		int score = getResult();
		if (score > 21) {
			return true;
		}
		else {
			return false;
		}
	}
	
	boolean checkHit() {
		int score = getResult();
		boolean hit;
		if (score < 17) {
			System.out.println("hit");
			return true;
		}
		else {
			System.out.println("stand");
			return false;
		}
	}
	
	void firstCard() {
		int score = getResult();
		System.out.print("House: ");
		for (int i = 0; i < cardList.size(); i++) {
			if (i == 0) {
				System.out.print("Hidden");
			}
			
			else {
				System.out.print(", "+cardList.get(i).getName());
			}
		}
	}
	
	void printCard() {
		int score = getResult();
		System.out.print("House: ");
		for (int i = 0; i < cardList.size(); i++) {
			if (i == 0) {
				System.out.print(cardList.get(i).getName());
			}
			else {
				System.out.print(", "+cardList.get(i).getName());
			}
		}
		if (checkBust() == true) {
			System.out.println(" ("+score+") - bust");
		}
		else {
			System.out.println(" ("+score+")");
		}
	}
}

