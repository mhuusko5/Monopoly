/*
 PROJECT: MONOPOLY
 AUTHOR: MATHEW HUUSKO V
 COURSE: COMPUTER SCIENCE E-FORMAT
 TEACHER: MR. SEA
 */

import java.awt.*;
import java.util.*;


import javax.swing.*;

public class BoardGui extends JPanel{
	
	private static final long serialVersionUID = 1L; //somehow necessary serial inserted by IDE
	private ArrayList<Player> playerList;
	
	public BoardGui(ArrayList<Player> playerList){ //create board gui with new player list
		this.playerList = playerList;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Image board = new ImageIcon("Board.png").getImage();						//paints on panel the board image with "preferred"/starting dimension
		g.drawImage(board,0,0,this.getWidth(), this. getHeight(),0,0,965,868, this);
		
			for(int i = 0; i<playerList.size(); i++){ //max player size is 4 - BIG O(1)
				if(i == 0){
					g.setColor(Color.magenta);
				}else if(i == 1){
					g.setColor(Color.green);		//sets colors of token depending on amount of players
				}else if(i == 2){
					g.setColor(Color.blue);
				}else if(i== 3){
					g.setColor(Color.red);
				}
			
				if(playerList.get(i).getCNode().getPos() >= 1 && playerList.get(i).getCNode().getPos()<= 10){
					g.fillOval((this.getWidth()/12)*(12-(playerList.get(i).getCNode().getPos())) - 5, (this.getHeight()/11)*(10) + 3 + 6*i , this.getWidth()/40, this.getHeight()/40);
				}
										//these four if statements calculate where to draw the tokens, using the current size of the window, and the player's current position on the board
				else if(playerList.get(i).getCNode().getPos() >= 11 && playerList.get(i).getCNode().getPos() <= 20){
					g.fillOval(((this.getWidth()/11)/4) - 5*i + 7, ((this.getHeight()/12)*(11-(playerList.get(i).getCNode().getPos()%11)) - 3), this.getWidth()/40, this.getHeight()/40);
				}
				
				else if(playerList.get(i).getCNode().getPos() >= 21 && playerList.get(i).getCNode().getPos() <= 30){
					g.fillOval((this.getWidth()/12)*(playerList.get(i).getCNode().getPos()%20), ((this.getHeight()/11) - 5) - 6*i+1, this.getWidth()/40, this.getHeight()/40);
				}
				
				else if(playerList.get(i).getCNode().getPos() >= 31 && playerList.get(i).getCNode().getPos() <= 40){
					g.fillOval(((((this.getWidth()/11))*(10)) - 3) + 6*i, (this.getHeight()/12)*(playerList.get(i).getCNode().getPos()%30)-3,this.getWidth()/40, this.getHeight()/40);
				}
				
	}
	}
}
	
	


	