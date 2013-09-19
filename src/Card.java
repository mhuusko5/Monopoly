/*
 PROJECT: MONOPOLY
 AUTHOR: MATHEW HUUSKO V
 COURSE: COMPUTER SCIENCE E-FORMAT
 TEACHER: MR. SEA
 */

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Card
{
	private String message;
	private int action; 
	private int value;
	private int pos;
	private char type;
	
	public Card(char type, String message, int action, int value, int pos)
	{
		this.message = message;
		this.action = action;
		this.value = value;		//variables necesary for chance/community card node 
		this.pos = pos;
		this.type = type;
	}
	
	public int executeCard(Player player, ArrayList<Player> players)
	{
		
		String title;
		
		if(this.type == 'c'){
			title = "Community Chest!";		//determines title of card by char type 'c' or 'h'
		}
		else{title = "Chance!";}
			
	
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE); //displays vital information about card
		
		switch (action)
		{
	        case 1:
	        	player.netMoney(value);
	        	break;						//ennacts the card, which all have action types of 1 (gain/loss of money), 2 (gain/loss of money involving all players), 3 (moving to a position), or 4 (a get out of jail free card)
	        case 2:
	        	player.netMoney(players.size()*value);
	        	for(Player p: players)
	        		p.netMoney(-value);
	        	break;
	        case 3:
	        	player.moveToIdentity(pos);
	        	break;
	        case 4:
	        	player.setJfCount(player.getJfCount()+1);
	        default:
	       		break;		
		}
		
		return this.action;
	}
}

