/*
 PROJECT: MONOPOLY
 AUTHOR: MATHEW HUUSKO V
 COURSE: COMPUTER SCIENCE E-FORMAT
 TEACHER: MR. SEA
 */

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Player {

private String name; 
private int rCount; 
private int mCount; 
private int dCount; 
private int dice1, dice2; 
private boolean onDice2 = false;
private int diceTotal;
private SpaceNode currentNode = new SpaceNode(); 
private ArrayList<PropertySpace> propsOwned; 
private int jfCount; 
private int jailTime;
    
	//Object that represents each player in the game (2-4)
    public Player(){}
    
    public Player(String name, SpaceNode newCNode){  
        this.name = name;
        this.dCount = 0;
        this.mCount = 1500;
        this.currentNode = newCNode;
        propsOwned = new ArrayList<PropertySpace>(); 
    }
    
    public void netMoney(int value) { 
        mCount += value;
                
    }
    
    //Moves player through the LinkedList of board spaces
    public void move(){
        
        for(int j = 0; j<this.getrCount(); j++){
            this.setCNode(this.getCNode().getNext());       
            if(currentNode.getPos() == 1){
                this.setmCount(this.getmCount() + 200);    
            }
        }
    }
    
    public void moveToIdentity(int moveToIdentity) { 
    
    	if(moveToIdentity == 0){
    		while(currentNode.getName() != "Board Walk" ){  
                currentNode = currentNode.getNext();
            }
    	}
    else if(moveToIdentity == 11){
            while(currentNode.getPos() != moveToIdentity ){  
                currentNode = currentNode.getNext();
            }
        }
        else{
            while(currentNode.getPos() != moveToIdentity ){
                currentNode = currentNode.getNext();    
                if(currentNode.getName() == "GO"){              
                    this.setmCount(this.getmCount() + 200);
                }
            }
        }
        
    }
    
    public void roll(){
        if(onDice2 == false){this.setDice1((int)(6*Math.random() + 1));
        	this.onDice2 = true;
        	this.roll();
        }else{
        	this.setDice2((int)(6*Math.random() + 1));      
        	diceTotal = this.getDice1() + this.getDice2();
        	this.rCount = diceTotal;    
        	onDice2 = false;
        }
    }
    
    public void trade(Player trader2, PropertySpace give, PropertySpace take, int cash){
            
        give.setOwnership(trader2);
        take.setOwnership(this);
        
        
        this.propsOwned.remove(give); 
        trader2.propsOwned.add(give); 
        
        trader2.propsOwned.remove(take); 
        this.propsOwned.add(take);
        
        int currentT1MC = this.getmCount();
        int currentT2MC = trader2.getmCount();      
        this.setmCount(currentT1MC-cash);
        trader2.setmCount(currentT2MC+cash);
    }
    
    public boolean hasMonopoly(PropertySpace landed){ 
        boolean has;
        String colorTemp = landed.getColor();       
        char colorChar = colorTemp.charAt(0);
        int counter = 0;
        
        switch(colorChar){
            case 'B':
                for(int i = 0; i<this.propsOwned.size(); i++){ 

                    if(this.propsOwned.get(i).getColor().charAt(0) == colorChar){
                        counter++;
                        }              
                    else{}
                }
                counter -= 1;
                if(counter == 2){
                    has = true;
                }
                else{has = false;}
                break;
            case 'D':
                for(int i = 0; i<this.propsOwned.size(); i++){

                    if(this.propsOwned.get(i).getColor().charAt(0) == colorChar){
                        counter++;
                        }
                    else{}
                }
                counter -= 1;           
                if(counter == 2){
                    has = true;
                }
                else{has = false;}
                break;
            case 'R':
                for(int i = 0; i<this.propsOwned.size(); i++){ 

                    if(this.propsOwned.get(i).getColor().charAt(0) == colorChar){
                        counter++;
                        }
                    else{}
                }
                counter -= 1;
                if(counter == 4){   
                    has = true;
                }
                else{has = false;}
                break;
            case 'U':
                for(int i = 0; i<this.propsOwned.size(); i++){ 
                	if(this.propsOwned.get(i).getColor().charAt(0) == colorChar){
                        counter++;
                        }
                    else{}
                }
                counter -= 1;           
                if(counter == 2){
                    has = true;
                }
                else{has = false;}
                break;
            default: 
                for(int i = 0; i<this.propsOwned.size(); i++){
                    if(this.propsOwned.get(i).getColor().charAt(0) == colorChar){
                        counter++;}else{}                 
                }
                counter -= 1;
                if(counter == 3){   
                    has = true;
                }
                else{has = false;}
                break;
                
            }
        
        return has;
    }
    
    public void mortgage(PropertySpace ownable){        
            ownable.setMortgaged("true");
            this.mCount += (Integer.parseInt(ownable.getPrice()))/2;
    }
    
    public void unMortgage(PropertySpace ownable){
            this.mCount -= (Integer.parseInt(ownable.getPrice()))/2;
            ownable.setMortgaged("false");
    }
    
    public int payRent(Player owner, PropertySpace landed){
        int rent = Integer.parseInt(landed.getRent());
        int nrent = 0;
        
        if(landed.getType() == 'p'){
            int hrent = Integer.parseInt(landed.getOneHouseRent());
        if(landed.isMortgaged() == "false"){
        switch(landed.getHouses()){
        case 0:
            if(this.hasMonopoly(landed) == true){nrent = 2*rent;}
            else{nrent = rent;}
            break;
        case 1:
            nrent = hrent;     
            break;
        case 2:
            nrent = hrent * 3;
            break;
        case 3:
            nrent = hrent * 9;
            break;
        case 4:
            nrent = hrent * 18;
            break;
        case 5:
            nrent = hrent * 36;
            break;
        }
        }
}else{nrent = rent;}
        
        if(landed.getOwnership() != this){     
            this.mCount -= nrent;
            owner.setmCount(owner.getmCount() + nrent);
        }
        
        return nrent;
    }
    
    public void buy(PropertySpace landed){
        this.mCount -= Integer.parseInt(landed.getPrice()); 
        landed.setOwnership(this);
        this.propsOwned.add(landed);
    }
    
    public void buyHouse(PropertySpace landed){
        if(landed.getHouses() < 5){                 
        landed.setHouses(landed.getHouses() + 1);
        this.mCount -= Integer.parseInt(landed.getBuildCost());
        JOptionPane.showMessageDialog(null, "Your house has been bought.", "House bought", JOptionPane.INFORMATION_MESSAGE);}
        else{JOptionPane.showMessageDialog(null, "This property has enough houses!", "Sorry!", JOptionPane.INFORMATION_MESSAGE);}
    }
    
    public void setName(String nName){
        this.name = nName;
    }
    
    public String getName(){
        return name;
    }
    
    public int getdCount(){
        return dCount;
    }
    
    public void setdCount(int ndCount){
        this.dCount = ndCount;
    }
    
    public int getrCount(){
        return rCount;
    }
    
    public void setrCount(int nrCount){
        this.rCount = nrCount;
    }
    
    public int getmCount(){
        return mCount;
    }
    
    public void setmCount(int nmCount){
        this.mCount = nmCount;
    }
    
    public int getDice1(){
        return dice1;
    }
    
    public int getDice2(){
        return dice2;
    }
    
    public void setDice1(int nDice1){
        this.dice1 = nDice1;
    }
    
    public void setDice2(int nDice2){
        this.dice2 = nDice2;
    }

    public SpaceNode getCNode(){
        return currentNode;
    }
    
    public void setCNode(SpaceNode nCNode){
        this.currentNode = nCNode;
    }

    public int getJfCount() {
        return jfCount;
    }

    public void setJfCount(int jfCount) {
        this.jfCount = jfCount;
    }

    public ArrayList<PropertySpace> getPropsOwned() {
        return propsOwned;
    }

    public void setPropsOwned(ArrayList<PropertySpace> propsOwned) {
        this.propsOwned = propsOwned;
    }

	public void setJailTime(int jailTime) {
		this.jailTime = jailTime;
	}

	public int getJailTime() {
		return jailTime;
	}
    
}
