/*
 PROJECT: MONOPOLY
 AUTHOR: MATHEW HUUSKO V
 COURSE: COMPUTER SCIENCE E-FORMAT
 TEACHER: MR. SEA
 */

public class SpaceNode {
  
  private int pos;
  private char type;
  private String name;				
  private SpaceNode next; 

  	//Highest/most general node that the rest inherit from
  	public SpaceNode(){}
  	
  	public SpaceNode(char type, String name, int pos){ 
  		this.type = type;
  		this.name = name;		
  		this.pos = pos;
  	}

	public void setPos(int pos) {
	  this.pos = pos;
  	}
  	public int getPos() {
  		return pos;
  	}
	public void setType(char type) {
		this.type = type;
	}
	
	public char getType() {
		return type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setNext(SpaceNode next) {
		this.next = next;
	}
	public SpaceNode getNext() {
		return next;
	}	

}
