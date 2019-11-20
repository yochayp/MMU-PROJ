package view;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ServerResponses extends JPanel {

	private JTextArea response;

	public ServerResponses(String response) {
		
		this.response = new JTextArea(20,40);
		this.response.setText(response);
		
		add(this.response);
	}
	
    public ServerResponses() {
		
		this.response = new JTextArea();
	}
	
    public void setResponse(StringBuilder response)
    {
    	this.response.setText(response.toString()); 
    }
}
