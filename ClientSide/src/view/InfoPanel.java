package view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {
	
	private JLabel capacity;
	private JLabel available;
	private JLabel numberOfGetAction;
	private JLabel numberOfSwapActions;

	public InfoPanel() {
		
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		capacity = new JLabel();
		capacity.setText("Capacity : ");
		available = new JLabel();
		available.setText("Available : ");
		numberOfGetAction = new JLabel();
		numberOfGetAction.setText("Number Of Get Action :");
		numberOfSwapActions = new JLabel();
		numberOfSwapActions.setText("Number Of Swap Actions :");
		
		this.add(capacity);
		this.add(available);
		this.add(numberOfGetAction);
		this.add(numberOfSwapActions);
	}
	
	public void updateLabels(int capacity,int available,int numberOfGetAction,int numberOfSwapActions)
	{
		this.capacity.setText("capacity = "+capacity);
		this.available.setText("available = "+available);
		this.numberOfGetAction.setText("numberOfGetAction = "+numberOfGetAction);
		this.numberOfSwapActions.setText("numberOfSwapActions = "+numberOfSwapActions);
		
	}

	
}


