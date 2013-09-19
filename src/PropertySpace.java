/*
 PROJECT: MONOPOLY
 AUTHOR: MATHEW HUUSKO V
 COURSE: COMPUTER SCIENCE E-FORMAT
 TEACHER: MR. SEA
 */

public class PropertySpace extends SpaceNode{

	private String buildCost;
	private String oneHouseRent;
	private Player ownership = null;	
	private String mortgaged = "false";
	private String rent;
	private String price;
	private int houses = 0;
	private String color;
	
		//Extends from SpaceNode, adding attributes unique to buyable spaces
		public PropertySpace(char type, String name, String color, String price, String buildCost, String rent, String oneHouseRent, int pos){
			super(type, name, pos);
			this.price = price;
			this.buildCost = buildCost;			
			this.oneHouseRent = oneHouseRent;
			this.rent = rent;
			this.color = color;
		
		}

		public PropertySpace() {} //blank constructor for use in special cases in other classes

		//getters/setters
		public void setOwnership(Player ownership) {
			this.ownership = ownership;
		}

		public Player getOwnership() {
			return ownership;
		}

		public void setMortgaged(String mortgaged) {
			this.mortgaged = mortgaged;
		}

		public String isMortgaged() {
			return mortgaged;
		}

		public void setRent(String rent) {
			this.rent = rent;
		}

		public String getRent() {
			return rent;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getPrice() {
			return price;
		}

		public void setHouses(int houses) {
			this.houses = houses;
		}

		public int getHouses() {
			return houses;
		}
		

		public void setBuildCost(String buildCost) {
			this.buildCost = buildCost;
		}

		public String getBuildCost() {
			return buildCost;
		}

		public void setOneHouseRent(String oneHouseRent) {
			this.oneHouseRent = oneHouseRent;
		}

		public String getOneHouseRent() {
			return oneHouseRent;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getColor() {
			return color;
		}

}
