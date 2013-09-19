/*
 PROJECT: MONOPOLY
 AUTHOR: MATHEW HUUSKO V
 COURSE: COMPUTER SCIENCE E-FORMAT
 TEACHER: MR. SEA
 */

import java.util.*;
import java.io.*;


public class CardList {

	private LinkedList<Card> chance = new LinkedList<Card>();  //linked list for chance cards
	private LinkedList<Card> community = new LinkedList<Card>(); //linked list for community chest cards

	public CardList(){
		try{
			File cards = new File("Cards.txt");	//reads chance/community cards text file
			Scanner cardScanner = new Scanner(cards);
			
			for(int i = 0; i <= 24; i++){ //Big O(1)
	  			switch(cardScanner.nextLine().charAt(0)){
	  			case 'h':
	  				String message = cardScanner.nextLine();		//reads each line in and stores necessary values, then creates nodes with values
	  				int action = Integer.parseInt(cardScanner.nextLine());
	  				int value = Integer.parseInt(cardScanner.nextLine());
	  				int pos = Integer.parseInt(cardScanner.nextLine());
	  				Card chcard = new Card('h', message, action, value, pos);
	  				chance.add(chcard); //adds to end of list
	  				break;
	  			case 'c':
	  				String message1 = cardScanner.nextLine();					//case h will call on chance, whereas case c called on community
	  				int action1 = Integer.parseInt(cardScanner.nextLine());
	  				int value1 = Integer.parseInt(cardScanner.nextLine());	//makes sure to parse to int
	  				int pos1 = Integer.parseInt(cardScanner.nextLine());
	  				Card commcard = new Card('c', message1, action1, value1, pos1);
	  				community.add(commcard); //adds to end of list
	  				break;
	  			default:
	  				break;

	  			}
			}
		}catch(IOException e){}
		
		Collections.shuffle(chance);		//uses built in shuffle function to shuffle cards at beginning of game
		Collections.shuffle(community);
	}
	
	public Card getChanceCard(){
		Card newChance = chance.removeFirst();	//return chance card from top, and adds to bottom
		chance.addLast(newChance);
		return newChance;
	}
	
	public Card getCommunityCard(){
		Card newCommunity = community.removeFirst();	//returns community card from top, and also adds it to bottom (so cards dont run out)
		chance.addLast(newCommunity);
		return newCommunity;
	}
	
}
