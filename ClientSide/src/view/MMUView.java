package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import Client.DataModel;
import Client.Request;
import javafx.beans.property.SetProperty;

public class MMUView<T> extends JFrame {
	
	
	private Buttons buttons;
	private ServerResponses responses;
	private InfoPanel infoPanel ;
	
	public MMUView ()
	{
		
		this.setTitle("CacheUnitUI");
		responses = new ServerResponses("");
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.buttons = new Buttons();
		this.infoPanel = new InfoPanel();
		//Display the window.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
		
			public void run() {
				
			createAndShowGUI();
			}
		});

		
	}
	
	private  void createAndShowGUI() {
		
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout(15,60));
	
		contentPane.add(buttons,BorderLayout.NORTH);
		contentPane.add(infoPanel,BorderLayout.WEST);
		contentPane.add(responses,BorderLayout.SOUTH);
		
		infoPanel.updateLabels(0,0,0,0);
	
		this.pack();
		this.setVisible(true);
		}
	
	public JButton getLoadButton()
	{
		return this.buttons.getLoadButton();
	}
	
	public JButton getCacheUnitButtons()
	{
		return this.buttons.getCacheMemoryButton();
	}
	
	public void setButtonslistener(ActionListener listenforButtons) {
		this.buttons.setButtonslistener(listenforButtons);
	}
	
	public void updateServerResponse(Request<DataModel<T>[]> response)
	{
		this.responses.setResponse( responseToString(response));
	}
	public void updateInfoPanel(Request<DataModel<T>[]> response)
	{
		this.infoPanel.updateLabels(
				Math.toIntExact(response.getBody()[0].getDataModelId()),
				Math.toIntExact(response.getBody()[1].getDataModelId()), 
				Math.toIntExact(response.getBody()[2].getDataModelId()), 
				Math.toIntExact(response.getBody()[3].getDataModelId()));
	}
	public StringBuilder responseToString(Request<DataModel<T>[]> response)
	{
		;
		StringBuilder sb = new StringBuilder(response.getAction());
		if(response.getAction().equals("Failed")) sb.append(",  not all data models found.\n");
		switch(response.getAction())
		{
		case "succeeded":
		case "Failed":
		{
			sb.append(System.getProperty("line.separator"));
			sb.append(" loaded data models info :\n\n");
			
			for(DataModel<T> datamodel : response.getBody())
			{
				if(datamodel!=null) 
				{
			
				sb.append("data model id :"+datamodel.getDataModelId()+" ,  contenet :"+datamodel.getContent()+".");
				sb.append(System.getProperty("line.separator"));
				}
				}
		}
		}
			return sb;
	}
}