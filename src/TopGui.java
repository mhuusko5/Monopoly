/*
 PROJECT: MONOPOLY
 AUTHOR: MATHEW HUUSKO V
 COURSE: COMPUTER SCIENCE E-FORMAT
 TEACHER: MR. SEA
 */

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class TopGui extends JPanel{

	private static final long serialVersionUID = 8099740733742748227L;
	ArrayList<String> holder;
	JList list;
	Player player;
								
	public TopGui(){	
		holder = new ArrayList<String>();
		
	}
	
	public void refreshList(Player player){
		this.removeAll();
		
		if(player.getPropsOwned().size() < 1 && holder.isEmpty()){		

			String detail = "No property";		
			holder.add(0, detail);
		}else{	
			
		holder = new ArrayList<String>(); //empties arraylist
		
		for(int i = 0; i<player.getPropsOwned().size(); i++){ 

			String prop = player.getPropsOwned().get(i).getName() + " - " + player.getPropsOwned().get(i).getColor();	
			holder.add(i, prop);
		}
		}
		
		if(player.getPropsOwned().size() < 1 && holder.isEmpty()){
			String detail = "No property";		
			holder.add(0, detail);
		}
		
		String name = player.getName();
		JLabel label1 = new JLabel(name);
		label1.setFont(new Font("Serif", Font.BOLD, 24));
	
		JLabel label2 = new JLabel(	"$" + Integer.toString(player.getmCount()));
		label2.setFont(new Font("Serif", Font.BOLD, 18));
		
		this.add(BorderLayout.NORTH, label1);
		this.add(BorderLayout.NORTH, label2);
		

		list = new JList(holder.toArray());
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setName("Properties");
		list.setSelectedIndex (1);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(800, 140));
		this.add(BorderLayout.CENTER, list);
		this.validate();
		this.repaint();
	}
	
	public void valueChanged(ListSelectionEvent e) {
		for(int i = 0; i < player.getPropsOwned().size(); i++){
			if(player.getPropsOwned().get(i).getName() == list.getSelectedValue()){
				String details = "Name: " + player.getPropsOwned().get(i).getName() + "\n" + "Type: " + player.getPropsOwned().get(i).getColor() + "\n" + "Rent: " + player.getPropsOwned().get(i).getRent() + "\n" + "Houses: " + player.getPropsOwned().get(i).getHouses() + "\n\n" + "Mortgaged: " + player.getPropsOwned().get(i).isMortgaged();
				JOptionPane.showMessageDialog(null, player.getPropsOwned().get(i).getName(), details, JOptionPane.INFORMATION_MESSAGE);
			}
			else{}
		}
	}
}

