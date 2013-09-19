/*
 PROJECT: MONOPOLY
 AUTHOR: MATHEW HUUSKO V
 COURSE: COMPUTER SCIENCE E-FORMAT
 TEACHER: MR. SEA
 */

import java.io.*;
import java.util.Scanner;

public class BoardList {
  
private SpaceNode first;
private SpaceNode current;

  	public BoardList() {		//creates a new board list (a double ended linked list)
  		first = null;	
  		this.generateBoard(); //fills the list
  	}
  
  	public void generateBoard(){
  			
			try {
				File boardSpace = new File("boardSpaces.txt");	//reads text files of board spaces/properties
				Scanner boardScanner = new Scanner(boardSpace);
				
				for(int i = 1; i <= 40; i++){	//Big O(1)
					switch(boardScanner.nextLine().charAt(0)){			//each case takes in different values (names, rents, costs, etc.) depending on the type of space it is reading, and then passes these to the necessary constructors
		  			case 'g':											//type is determined by a single character before each space in the text file
		  				String name = boardScanner.nextLine();
		  				SpaceNode nPNode = new GoSpace('g', name, i);
		  				addFirst(nPNode);
		  				break;
		  			case 'p': 
		  				String name1 = boardScanner.nextLine();	//next line allows us to read and store one line at a time
		  				String color = boardScanner.nextLine();
		  				String price = boardScanner.nextLine();
		  				String buildCost = boardScanner.nextLine();
		  				String rent = boardScanner.nextLine();
		  				String oneHouseRent = boardScanner.nextLine();
		  				SpaceNode nPNode1 = new PropertySpace('p', name1, color, price, buildCost, rent, oneHouseRent, i);
		  				addLast(nPNode1);
		  				break;
		  			case 'c':
		  				String name2 = boardScanner.nextLine();
		  				SpaceNode nPNode2 = new CommunitySpace('c', name2, i);
		  				addLast(nPNode2);
		  				break;
		  			case 't':
		  				String name3 = boardScanner.nextLine();
		  				String tax = boardScanner.nextLine();
		  				SpaceNode nPNode3 = new TaxSpace('t', name3, tax, i);
		  				addLast(nPNode3);
		  				break;
		  			case 'r':
		  				String name4 = boardScanner.nextLine();
		  				String color1 = boardScanner.nextLine();
		  				String price2 = boardScanner.nextLine();
		  				String rent2 = boardScanner.nextLine();
		  				SpaceNode nPNode4 = new RailSpace('r', name4, color1, price2, null, rent2, null, i);
		  				addLast(nPNode4);
		  				break;
		  			case 'h':
		  				String name5 = boardScanner.nextLine();
		  				SpaceNode nPNode5 = new ChanceSpace('h', name5, i);
		  				addLast(nPNode5);
		  				break;
		  			case 'j':
		  				String name6 = boardScanner.nextLine();
		  				SpaceNode nPNode6 = new JailSpace('j', name6, i);
		  				addLast(nPNode6);
		  				break;
		  			case 'u':
		  				String name7 = boardScanner.nextLine();
		  				String color3 = boardScanner.nextLine();
		  				String price3 = boardScanner.nextLine();
		  				String rent4 = boardScanner.nextLine();
		  				SpaceNode nPNode7 = new UtilitySpace('u', name7, color3, price3, null, rent4, null, i);
		  				addLast(nPNode7);
		  				break;
		  			case 'f':
		  				String name8 = boardScanner.nextLine();
		  				SpaceNode nPNode8 = new FreeSpace('f', name8, i);
		  				addLast(nPNode8);
		  				break;
		  			case 'w':
		  				String name9 = boardScanner.nextLine();
		  				SpaceNode nPNode9 = new ToJailSpace('w', name9, i);
		  				addLast(nPNode9);
		  				break;
		  			default:
		  				break;
		  				}
				}
			} 
			catch (FileNotFoundException e) {e.printStackTrace();}
  	}
  	
	public void addFirst(SpaceNode nPNode) { //adds the first node (go for example) to the list
  		first = nPNode;
  		current = first;
  	}

  	public void addLast(SpaceNode nPNode) {
  		current.setNext(nPNode);			//adds a last, by setting it to the one after the current last
  		current = nPNode;
  		current.setNext(first);
  	}
  	
  	public SpaceNode getFirst() {	//returns first
		return first;
	}
}