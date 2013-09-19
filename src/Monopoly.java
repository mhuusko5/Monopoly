/*
 PROJECT: MONOPOLY
 AUTHOR: MATHEW HUUSKO V
 COURSE: COMPUTER SCIENCE E-FORMAT
 TEACHER: MR. SEA
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.*;
import javax.swing.*;

public class Monopoly {

private JFrame frame = new JFrame("Monopoly");;
private SideGui east;
private TopGui north;
private SideGui west;
private TopGui south;
private ArrayList<Player> listPlayers;	
private BoardGui center;
private Player cPlayer;
private BoardList board;
private boolean win = false;
private int cpIndex;
private CardList cardStack;
private int nPlayers;

	public Monopoly(){}
	
	public static void main(String[] args){
		Monopoly game = new Monopoly();
		game.newGame();
	}

	public void newGame(){						//at start user is prompted for number of players
		String snPlayers = JOptionPane.showInputDialog("Please enter number of players (between 2 and 4):");
		if(snPlayers.charAt(0) == '2' || snPlayers.charAt(0) == '3' || snPlayers.charAt(0) == '4'){
			nPlayers = Integer.parseInt(snPlayers);
		}else{
			newGame();
		}
			
		listPlayers = new ArrayList<Player>(); //arraylist is made to handle current players
		board = new BoardList(); //new board (linked list) generated
		cardStack = new CardList(); //stacks (lists) of cards are generated/shuffled
		
		for(int i = 0; i<nPlayers; i++){ //max players is 4 - Big O(1)
			listPlayers.add(i, new Player("Player"+(i+1), board.getFirst())); //created number of players with sequential names, all have a starting position of 1 (GO)
		} 
		
		cpIndex = 0;  //index to keep track of which of the players in the list is current
		cPlayer = new Player(); //current player object
		cPlayer = listPlayers.get(cpIndex);
		center = new BoardGui(listPlayers); //makes a new board gui
		
		createGUI(listPlayers); //creates jframe and adds all components
		JOptionPane.showMessageDialog(null, "Enjoy!", "Monopoly", JOptionPane.INFORMATION_MESSAGE); //greets the player(s)
		
		//while statement keeps game running until someone wins
		while(win == false){ //START TURN
			
			turnPhase1(); //starts first part of turn
			try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();} //pauses momentarily before continueing
			turnPhase2(); //starts part two of ones turn
			
			//END TURN
			for(int k = 0; k<listPlayers.size(); k++){ //max players is 4 - Big O(1)
				if(listPlayers.get(k).getmCount() < 1){				//checks and notifies if anyone has gone bankrupt
					String loserName = listPlayers.get(k).getName();
					
					JOptionPane.showMessageDialog(null, "You're bankrupt - mortgage (if you can) to save yourself", loserName + " - Bankrupt!", JOptionPane.INFORMATION_MESSAGE);
				
					lastStand();
					
					if(listPlayers.get(k).getmCount() < 1){
						listPlayers.remove(k);
						JOptionPane.showMessageDialog(null, "You are removed from the game.", loserName + " - Bankrupt!", JOptionPane.INFORMATION_MESSAGE);
					}else{}
				}
				
				if(listPlayers.size() == 1){		//check if anyone has one (last man standing)
					String winner = listPlayers.get(0).getName();
					JOptionPane.showMessageDialog(null, winner + " WINS!", "We have a winner!", JOptionPane.INFORMATION_MESSAGE);
					win = true;
				}
			}
			
			cpIndex = (cpIndex + 1) % listPlayers.size();	
			cPlayer = listPlayers.get(cpIndex);
		}
		
	}
	
	public void lastStand(){
		
		String[] dontdie = {"Done", "Mortgage", "Trade"};
		int pSelect1 = JOptionPane.showOptionDialog (null, "What do you want to do?", cPlayer.getName(), 0, JOptionPane.QUESTION_MESSAGE, null, dontdie, null);
		if(pSelect1 == 0){}else if(pSelect1 == 1){
			if(cPlayer.getPropsOwned().size() < 1){JOptionPane.showMessageDialog(null, "Sorry, no properties owned...", "Sorry!", JOptionPane.INFORMATION_MESSAGE);
			lastStand();}
			else{
			String[] props = new String[cPlayer.getPropsOwned().size()];
			for(int i = 0; i < props.length; i++){ //max 28 - big O(1)
				props[i] = cPlayer.getPropsOwned().get(i).getName();
			}								//get property from option dialog
			PropertySpace choice = cPlayer.getPropsOwned().get(JOptionPane.showOptionDialog (null, "What property would you like to mortgage?", "Mortgage", 0, JOptionPane.QUESTION_MESSAGE, null, props, null));
			if(choice.isMortgaged() == "true"){
				JOptionPane.showMessageDialog(null, "This property is already mortgaged.", "Aleady mortgaged", JOptionPane.INFORMATION_MESSAGE);
				lastStand();
			}else{
				cPlayer.mortgage(choice); //then applies mortgage method to the property
				JOptionPane.showMessageDialog(null, "Your property has been mortgaged.", "Mortgaged", JOptionPane.INFORMATION_MESSAGE);
			}
			
			refreshAll(); //refreshed, then returns to top
			
			lastStand();
			}
		}else if(pSelect1 == 2){
			if(cPlayer.getPropsOwned().size() < 1){JOptionPane.showMessageDialog(null, "Sorry, no properties owned...", "Sorry!", JOptionPane.INFORMATION_MESSAGE);
			lastStand();} //if you own no properties, returns to top
			else{
			String[] names = new String[listPlayers.size()];
			for(int i = 0; i < names.length; i++){ //max players is 4 - Big O(1)
				names[i] = listPlayers.get(i).getName(); //makes array of names for dialog
			}
			Player trader2 = listPlayers.get(JOptionPane.showOptionDialog (null, "Who do you want to trade with?", "Trade", 0, JOptionPane.QUESTION_MESSAGE, null, names, null));
			if(trader2 == cPlayer){lastStand();} //if you select yourself, returns to top
			
			if(trader2.getPropsOwned().size() < 1){ //max 28 - big O(1)
				JOptionPane.showMessageDialog(null, "This player owns no properties.", "Sorry.", JOptionPane.INFORMATION_MESSAGE);
				lastStand(); //notifies if selected player has no properties
			}
			else{
			String[] t1props = new String[cPlayer.getPropsOwned().size()];
			for(int i = 0; i < t1props.length; i++){ //max 4 - big O(1)
				t1props[i] = cPlayer.getPropsOwned().get(i).getName();
			}			///also what property you would like to offer
			PropertySpace give = cPlayer.getPropsOwned().get(JOptionPane.showOptionDialog (null, "What property would you like to offer?", "Trade", 0, JOptionPane.QUESTION_MESSAGE, null, t1props, null));
			
			String[] t2props = new String[trader2.getPropsOwned().size()];
			for(int i = 0; i < t2props.length; i++){
				t2props[i] = trader2.getPropsOwned().get(i).getName();
			}		//asks what property of the selected player you want
			PropertySpace take = trader2.getPropsOwned().get(JOptionPane.showOptionDialog (null, "What property would you like to take?", "Trade", 0, JOptionPane.QUESTION_MESSAGE, null, t2props, null));
		
			//asks if you want to include a money offer
			int cash = Integer.parseInt(JOptionPane.showInputDialog("How much money would you like to offer?"));
			
			String[] yesnoOp = {"Yes", "No"};  //presents an offer to the other player
			int yesno = JOptionPane.showOptionDialog (null, "Do you accept this offer? \n\n" + give.getName() + " and " + Integer.toString(cash) + " for your " + take.getName() + "?" , trader2.getName() + " - New Offer!", 0, JOptionPane.QUESTION_MESSAGE, null, yesnoOp, null);
			if(yesno == 1){JOptionPane.showMessageDialog(null, "The offer has been declined", "Declined!", JOptionPane.INFORMATION_MESSAGE);}
			else{JOptionPane.showMessageDialog(null, "The offer has been accepted!", "Accepted!", JOptionPane.INFORMATION_MESSAGE);
			
			//if accept, enables trade
			cPlayer.trade(trader2, give, take, cash);
			
			refreshAll(); //refreshes
			}
			
			}
			}
			
			lastStand();
		}
		
	}
	public void createGUI(ArrayList<Player> listPlayers){
		
		frame.setResizable(true);
		frame.setSize(830, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);								
	    frame.getContentPane().add(BorderLayout.CENTER, center);
	    frame.setLocationRelativeTo(null);
	    frame.setBackground(new Color(80, 160, 250));
	    
		
		if(listPlayers.size() >= 2){
			frame.setSize(920, 560);
			west = new SideGui();
			west.setBackground(Color.magenta);
			frame.getContentPane().add(BorderLayout.WEST, west);
			west.refreshList(listPlayers.get(0));
			west.repaint();												//adds panels depending on how many players are playing
			east = new SideGui();
			east.setBackground(Color.green);
			frame.getContentPane().add(BorderLayout.EAST, east);
			east.refreshList(listPlayers.get(1));
			east.repaint();
		}
		if(listPlayers.size() >= 3){
			frame.setSize(870, 640);
			north = new TopGui();
			north.setBackground(Color.blue);
			frame.getContentPane().add(BorderLayout.NORTH, north);
			north.refreshList(listPlayers.get(2));
			north.repaint();
		}
		if(listPlayers.size() == 4){
			frame.setSize(830, 720);
			
			south = new TopGui();
			south.setBackground(Color.red);
			frame.getContentPane().add(BorderLayout.SOUTH, south);
			south.refreshList(listPlayers.get(3));
			south.repaint();
		}
		
		frame.validate(); //refreshes/makes sure everything is loaded/visible

	}
	
	public void turnPhase1(){
		try {Thread.sleep(400);} catch (InterruptedException e) {e.printStackTrace();}
	
		if(cPlayer.getJailTime()>0){
			cPlayer.setJailTime(cPlayer.getJailTime()-1);
			JOptionPane.showMessageDialog(null, cPlayer.getName() + ", you're locked In!", "You're still in jail!", JOptionPane.INFORMATION_MESSAGE);
			if(cPlayer.getJfCount()>0){
				JOptionPane.showMessageDialog(null, "Get out of jail free!", "Use you're card!", JOptionPane.INFORMATION_MESSAGE);
				cPlayer.setJfCount(cPlayer.getJfCount()-1);
			}else{return;}
		}
		
		String[] phase1 = {"Quit Game!", "View Properties", "Buy Houses", "Un-Mortgage", "Mortgage", "Trade", "Roll!"};
		int pSelect = JOptionPane.showOptionDialog (null, "What do you want to do?", cPlayer.getName(), 0, JOptionPane.QUESTION_MESSAGE, null, phase1, null);
		if(pSelect == 6){ //present option box to roll/trade/mortgage, etc.
			cPlayer.roll(); //rolls and notifies player of roll
		    JOptionPane.showMessageDialog(null, "You rolled a total of: " + cPlayer.getrCount(), "Nice Roll!", JOptionPane.INFORMATION_MESSAGE);
			cPlayer.move();
			center.repaint(); //then moves and repaints
		}else if(pSelect == 5){
			if(cPlayer.getPropsOwned().size() < 1){JOptionPane.showMessageDialog(null, "Sorry, no properties owned...", "Sorry!", JOptionPane.INFORMATION_MESSAGE);
			turnPhase1();} //if you own no properties, returns to top
			else{
			String[] names = new String[listPlayers.size()];
			for(int i = 0; i < names.length; i++){ //max players is 4 - Big O(1)
				names[i] = listPlayers.get(i).getName(); //makes array of names for dialog
			}
			Player trader2 = listPlayers.get(JOptionPane.showOptionDialog (null, "Who do you want to trade with?", "Trade", 0, JOptionPane.QUESTION_MESSAGE, null, names, null));
			if(trader2 == cPlayer){turnPhase1();} //if you select yourself, returns to top
			
			if(trader2.getPropsOwned().size() < 1){ //max 28 - big O(1)
				JOptionPane.showMessageDialog(null, "This player owns no properties.", "Sorry.", JOptionPane.INFORMATION_MESSAGE);
				turnPhase1(); //notifies if selected player has no properties
			}
			else{
			String[] t1props = new String[cPlayer.getPropsOwned().size()];
			for(int i = 0; i < t1props.length; i++){ //max 4 - big O(1)
				t1props[i] = cPlayer.getPropsOwned().get(i).getName();
			}			///akso what property you would like to offer
			PropertySpace give = cPlayer.getPropsOwned().get(JOptionPane.showOptionDialog (null, "What property would you like to offer?", "Trade", 0, JOptionPane.QUESTION_MESSAGE, null, t1props, null));
			
			String[] t2props = new String[trader2.getPropsOwned().size()];
			for(int i = 0; i < t2props.length; i++){
				t2props[i] = trader2.getPropsOwned().get(i).getName();
			}		//asks what property of the selected player you want
			PropertySpace take = trader2.getPropsOwned().get(JOptionPane.showOptionDialog (null, "What property would you like to take?", "Trade", 0, JOptionPane.QUESTION_MESSAGE, null, t2props, null));
		
			//asks if you want to include a money offer
			int cash = Integer.parseInt(JOptionPane.showInputDialog("How much money would you like to offer?"));
			
			String[] yesnoOp = {"Yes", "No"};  //presents an offer to the other player
			int yesno = JOptionPane.showOptionDialog (null, "Do you accept this offer? \n\n" + give.getName() + " and " + Integer.toString(cash) + " for your " + take.getName() + "?" , trader2.getName() + " - New Offer!", 0, JOptionPane.QUESTION_MESSAGE, null, yesnoOp, null);
			if(yesno == 1){JOptionPane.showMessageDialog(null, "The offer has been declined", "Declined!", JOptionPane.INFORMATION_MESSAGE);}
			else{JOptionPane.showMessageDialog(null, "The offer has been accepted!", "Accepted!", JOptionPane.INFORMATION_MESSAGE);
			
			//if accept, enables trade
			cPlayer.trade(trader2, give, take, cash);
			
			refreshAll(); //refreshes
			}
			
			turnPhase1(); //returns to top
			}
			}
		}
		else if(pSelect == 4){
			if(cPlayer.getPropsOwned().size() < 1){JOptionPane.showMessageDialog(null, "Sorry, no properties owned...", "Sorry!", JOptionPane.INFORMATION_MESSAGE);
			turnPhase1();}
			else{
			String[] props = new String[cPlayer.getPropsOwned().size()];
			for(int i = 0; i < props.length; i++){ //max 28 - big O(1)
				props[i] = cPlayer.getPropsOwned().get(i).getName();
			}								//get property from option dialog
			PropertySpace choice = cPlayer.getPropsOwned().get(JOptionPane.showOptionDialog (null, "What property would you like to mortgage?", "Mortgage", 0, JOptionPane.QUESTION_MESSAGE, null, props, null));
			if(choice.isMortgaged() == "true"){
				JOptionPane.showMessageDialog(null, "This property is already mortgaged.", "Aleady mortgaged", JOptionPane.INFORMATION_MESSAGE);
				turnPhase1();
			}else{
				cPlayer.mortgage(choice); //then applies mortgage method to the property
				JOptionPane.showMessageDialog(null, "Your property has been mortgaged.", "Mortgaged", JOptionPane.INFORMATION_MESSAGE);
			}
			
			refreshAll(); //refreshed, then returns to top
			
			turnPhase1();
			}
		}
		else if(pSelect == 3){
			if(cPlayer.getPropsOwned().size() < 1){JOptionPane.showMessageDialog(null, "Sorry, no properties owned...", "Sorry!", JOptionPane.INFORMATION_MESSAGE);
			turnPhase1();}
			else{
			String[] props = new String[cPlayer.getPropsOwned().size()];
			for(int i = 0; i < props.length; i++){ 
				props[i] = cPlayer.getPropsOwned().get(i).getName();
			}
			PropertySpace choice = cPlayer.getPropsOwned().get(JOptionPane.showOptionDialog (null, "What property would you like to un-mortgage?", "Un-Mortgage", 0, JOptionPane.QUESTION_MESSAGE, null, props, null));
			if(choice.isMortgaged() == "false"){
				JOptionPane.showMessageDialog(null, "This property is already un-mortgaged.", "Aleady un-mortgaged", JOptionPane.INFORMATION_MESSAGE);
				turnPhase1();
			}else{
				cPlayer.unMortgage(choice); //then applies mortgage method to the property
				JOptionPane.showMessageDialog(null, "Your property has been un-mortgaged.", "Un-Mortgaged", JOptionPane.INFORMATION_MESSAGE);
			}
			
			refreshAll();
			
			turnPhase1();
			}
		}
		else if(pSelect == 2){
			if(cPlayer.getPropsOwned().size() < 1){JOptionPane.showMessageDialog(null, "Sorry, no properties owned...", "Sorry!", JOptionPane.INFORMATION_MESSAGE);
			turnPhase1();}							//if no props owned, returns to top
			else{
			String[] props = new String[cPlayer.getPropsOwned().size()];
			for(int i = 0; i < props.length; i++){  //MAX 28 - big O(1)
				props[i] = cPlayer.getPropsOwned().get(i).getName();
			}
			//buys a single house for selecte property
			PropertySpace choice = cPlayer.getPropsOwned().get(JOptionPane.showOptionDialog (null, "What property would you like to buy a house for?", "Buy Houses", 0, JOptionPane.QUESTION_MESSAGE, null, props, null));
			boolean able = cPlayer.hasMonopoly(choice);
			if(able == true){
			cPlayer.buyHouse(choice);
			}else{
				JOptionPane.showMessageDialog(null, "Sorry, you don't have a monopoly yet...", "Sorry!", JOptionPane.INFORMATION_MESSAGE);
			}
			
			refreshAll();
			
			turnPhase1();
		}
		}else if (pSelect == 1){
			String[] names = new String[listPlayers.size()];
			for(int i = 0; i < names.length; i++){ //max players is 4 - Big O(1)
				names[i] = listPlayers.get(i).getName(); //makes array of names for dialog
			}
			Player trader2 = listPlayers.get(JOptionPane.showOptionDialog (null, "Who's properties do you want to view?", "Viewing", 0, JOptionPane.QUESTION_MESSAGE, null, names, null));
			
			if(trader2.getPropsOwned().size() < 1){ //max 28 - big O(1)
				JOptionPane.showMessageDialog(null, "This player owns no properties.", "Sorry.", JOptionPane.INFORMATION_MESSAGE);
				turnPhase1(); //notifies if selected player has no properties
			}
			else{
			String[] t1props = new String[trader2.getPropsOwned().size()];
			for(int i = 0; i < t1props.length; i++){ //max 4 - big O(1)
				t1props[i] = trader2.getPropsOwned().get(i).getName();
			}			
			PropertySpace viewed = trader2.getPropsOwned().get(JOptionPane.showOptionDialog (null, "What property would you like to view?", "View", 0, JOptionPane.QUESTION_MESSAGE, null, t1props, null));
			
			String[] yesnoOp = {"Ok."};
			String details = "Name: " + viewed.getName() + "\n" + "Type: " + ((PropertySpace) viewed).getColor() + "\n" + "Rent: " + ((PropertySpace) viewed).getRent() + "\n" + "Price: " + ((PropertySpace) viewed).getPrice() + "\n" + "Mortgaged: " + ((PropertySpace) viewed).isMortgaged() + "\n" + "Houses: " + ((PropertySpace) viewed).getHouses() + "\n" + "Build cost: " + ((PropertySpace) viewed).getBuildCost();
			JOptionPane.showOptionDialog (null, details, "View", 0, JOptionPane.QUESTION_MESSAGE, null, yesnoOp, null);
			
			turnPhase1(); //returns to top
			}
			
		}else if(pSelect == 0){
			System.exit(0); 
		}
		
		if(cPlayer.getDice1() == cPlayer.getDice2()){     
			cPlayer.setdCount(cPlayer.getdCount() + 1);  
			if(cPlayer.getdCount() == 3){ 
		        JOptionPane.showMessageDialog(null, "Go To Jail!", "You have rolled three doubles in a row - go to jail.", JOptionPane.INFORMATION_MESSAGE);
		        cPlayer.moveToIdentity(11);
		        cPlayer.setdCount(0);
		        cPlayer.setJailTime(3);
		        return;
		    }
			turnPhase2(); //starts part two of ones turn
	        JOptionPane.showMessageDialog(null, "Roll again!", "You rolled doubles!", JOptionPane.INFORMATION_MESSAGE);
	        turnPhase1();
	      
	    }else{cPlayer.setdCount(0);}    
		
	}
	
	public void turnPhase2(){ 
		
		if(cPlayer.getJailTime()>0){
			return;
		}
		
		int reference = cPlayer.getCNode().getPos();
		SpaceNode landed = board.getFirst();
		while(landed.getPos() != reference){ 	
			landed = landed.getNext();
		}
		switch(landed.getType()){
		case 'p':   
			if(((PropertySpace) landed).getOwnership() == null){
				String[] yesnoOp = {"Yes", "No"};
				String details = "Name: " + landed.getName() + "\n" + "Type: " + ((PropertySpace) landed).getColor() + "\n" + "Rent: " + ((PropertySpace) landed).getRent() + "\n" + "Price: " + ((PropertySpace) landed).getPrice();
				int yesno = JOptionPane.showOptionDialog (null, details, "Want to buy?", 0, JOptionPane.QUESTION_MESSAGE, null, yesnoOp, null);
					if(yesno == 0){
						cPlayer.buy((PropertySpace) landed);
						JOptionPane.showMessageDialog(null, "You now own " + landed.getName(), "Property Bought!", JOptionPane.INFORMATION_MESSAGE);}
					else{}
					
			}else if(((PropertySpace) landed).getOwnership() == cPlayer){
				
			}else{Player pReference = ((PropertySpace) landed).getOwnership();
				int payed = cPlayer.payRent(pReference, (PropertySpace) landed);
				JOptionPane.showMessageDialog(null, "You payed " + pReference.getName() + " a rent of " + payed + " dollars." , "Rent payed!", JOptionPane.INFORMATION_MESSAGE);
			}
			
			refreshAll();
		
			break;
			
		case 'c':  
			int action = cardStack.getCommunityCard().executeCard(cPlayer, listPlayers);
			if(action == 3){
				turnPhase2();
			}else{}
			refreshAll();
			break;
		case 'h': 
			int action1 = cardStack.getChanceCard().executeCard(cPlayer, listPlayers);
			if(action1 == 3){
				turnPhase2();
			}else{}
			refreshAll();
			break;
		case 'u':  
			if(((PropertySpace) landed).getOwnership() == null){
				String[] yesnoOp = {"Yes", "No"};
				String details = "Name: " + landed.getName() + "\n" + "Type: " + ((PropertySpace) landed).getColor() + "\n" + "Rent: " + ((PropertySpace) landed).getRent() + "\n" + "Price: " + ((PropertySpace) landed).getPrice();
				int yesno = JOptionPane.showOptionDialog (null, details, "Want to buy?", 0, JOptionPane.QUESTION_MESSAGE, null, yesnoOp, null);
					if(yesno == 0){
						cPlayer.buy((PropertySpace) landed);
						JOptionPane.showMessageDialog(null, "You now own " + landed.getName(), "Property Bought!", JOptionPane.INFORMATION_MESSAGE);}
					else{}
					
			}else if(((PropertySpace) landed).getOwnership() == cPlayer){
				
			}else{Player pReference = ((PropertySpace) landed).getOwnership();
				int payed = cPlayer.payRent(pReference, (PropertySpace) landed);
				JOptionPane.showMessageDialog(null, "You payed " + pReference.getName() + " a rent of " + payed + " dollars." , "Rent payed!", JOptionPane.INFORMATION_MESSAGE);
			}
			refreshAll();
			break;
		case 'r':  
			if(((PropertySpace) landed).getOwnership() == null){
				String[] yesnoOp = {"Yes", "No"};
				String details = "Name: " + landed.getName() + "\n" + "Type: " + ((PropertySpace) landed).getColor() + "\n" + "Rent: " + ((PropertySpace) landed).getRent() + "\n" + "Price: " + ((PropertySpace) landed).getPrice();
				int yesno = JOptionPane.showOptionDialog (null, details, "Want to buy?", 0, JOptionPane.QUESTION_MESSAGE, null, yesnoOp, null);
					if(yesno == 0){
						cPlayer.buy((PropertySpace) landed);
						JOptionPane.showMessageDialog(null, "You now own " + landed.getName(), "Property Bought!", JOptionPane.INFORMATION_MESSAGE);}
					else{}
					
			}else if(((PropertySpace) landed).getOwnership() == cPlayer){
				
			}else{Player pReference = ((PropertySpace) landed).getOwnership();
				int payed = cPlayer.payRent(pReference, (PropertySpace) landed);
				JOptionPane.showMessageDialog(null, "You payed " + pReference.getName() + " a rent of " + payed + " dollars." , "Rent payed!", JOptionPane.INFORMATION_MESSAGE);
			}
			
			refreshAll();
			break;
		case 't': 
			JOptionPane.showMessageDialog(null, "You've landed on Income Tax - pay $200 to the bank", "Income Tax!", JOptionPane.INFORMATION_MESSAGE);
			cPlayer.setmCount(cPlayer.getmCount() - 200);
			refreshAll();
			
			break;
		case 'l': 
			JOptionPane.showMessageDialog(null, "You've landed on Luxury Tax - pay $75 to the bank", "Luxury Tax!", JOptionPane.INFORMATION_MESSAGE);
			cPlayer.setmCount(cPlayer.getmCount() - 75);
		
			refreshAll();
			
			break;
		case 'j': 
			JOptionPane.showMessageDialog(null, "You're visiting jail.", "JAIL", JOptionPane.INFORMATION_MESSAGE);
			break;
		case 'g': 
			JOptionPane.showMessageDialog(null, "You're at go - collect $200!", "GO", JOptionPane.INFORMATION_MESSAGE);
			cPlayer.setmCount(cPlayer.getmCount() + 200);
			
			refreshAll();
			
			break;
		case 'f':
			JOptionPane.showMessageDialog(null, "You're on Free Parking - rest easy.", "Free Parking", JOptionPane.INFORMATION_MESSAGE);
			refreshAll();
			break;
		case 'w':  
			JOptionPane.showMessageDialog(null, "GO TO JAIL!\nDO NOT PASS GO!", "GO TO JAIL", JOptionPane.INFORMATION_MESSAGE);
			cPlayer.moveToIdentity(11);
			cPlayer.setJailTime(3);
			refreshAll();
			break;
		}
	}
	
	public void refreshAll(){
		west.refreshList(listPlayers.get(0));
		east.refreshList(listPlayers.get(1));
		west.repaint();
		east.repaint();
		
		if(listPlayers.size() > 2){					
		north.refreshList(listPlayers.get(2));
		north.repaint();
		}
		if(listPlayers.size() > 3){
		south.refreshList(listPlayers.get(3));
		south.repaint();
		}
		
		center.repaint();
	}
	
}
