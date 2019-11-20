package view;

import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.event.*;

public class Buttons extends JPanel  {

	private JButton load, CacheMemory;

	public Buttons() {
		
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		load = new JButton("Load a Request");
		load.setVerticalTextPosition(AbstractButton.CENTER);
		load.setHorizontalTextPosition(AbstractButton.LEADING); 
		load.setActionCommand("load");

		CacheMemory = new JButton("CacheMemory");
		CacheMemory.setVerticalTextPosition(AbstractButton.BOTTOM);
		CacheMemory.setHorizontalTextPosition(AbstractButton.CENTER);
		CacheMemory.setActionCommand("Statistic");

        add(load);
        add(CacheMemory);

        setSize(1000,300);
        
        }
	
	public JButton getLoadButton()
	{
		return this.load;
	}
	
	public JButton getCacheMemoryButton()
	{
		return this.CacheMemory;
	}
	
	public void setButtonslistener(ActionListener listenforButtons) {
		this.load.addActionListener(listenforButtons);
		this.CacheMemory.addActionListener(listenforButtons);
	}
}