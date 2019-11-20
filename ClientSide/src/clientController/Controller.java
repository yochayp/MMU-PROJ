package clientController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import Client.Model;
import view.MMUView;

public class Controller {
	
	private Model model;
	private MMUView view;
	private ActionPerformed actionp;
	private ActionPerformed actionp2;
	public Controller(Model model, MMUView view)
	{
		this.model = model;
		this.view = view;
		
		this.view.setButtonslistener(new ActionPerformed());		
	}
	
	

	class ActionPerformed implements ActionListener{
		
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
		case "load":
			try {
				view.updateServerResponse(model.performLoading())  ;
				break;
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		case "Statistic":
			
				view.updateInfoPanel(model.getStatistics() )  ;
		
			break;
		}
		
	}
	}
}
