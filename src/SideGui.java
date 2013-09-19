/*
 PROJECT: MONOPOLY
 AUTHOR: MATHEW HUUSKO V
 COURSE: COMPUTER SCIENCE E-FORMAT
 TEACHER: MR. SEA
 */

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class SideGui extends JPanel{
	
	private static final long serialVersionUID = 777841764412963320L;
	ArrayList<String> holder;
	JList list;				
	Player player;
	
	public SideGui(){	
		holder = new ArrayList<String>();
	}
	
	public void refreshList(Player player){
		this.removeAll(); 	
		
		if(player.getPropsOwned().size() < 1 && holder.isEmpty()){		

			String detail = "No property";		
			holder.add(0, detail);
		}else{	
			
		holder = new ArrayList<String>(); 
		
		for(int i = 0; i<player.getPropsOwned().size(); i++){ 

			String prop = player.getPropsOwned().get(i).getName() + " - " + player.getPropsOwned().get(i).getColor();	
			holder.add(i, prop);}
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
		
		list.setLayoutOrientation(JList.VERTICAL);
		list.setName("Properties");
		list.setSelectedIndex (1);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setLayout(new FlowLayout());
		this.setPreferredSize(new Dimension(185, 800));
		this.add(BorderLayout.NORTH, list);
		this.validate();
		this.repaint();
		
	}
}

	


	
