package driver;
import Client.Model;
import clientController.Controller;
import view.MMUView;

public class Driver {


	public static void main(String[] args) {

	
	
			MMUView view = new MMUView();
			Model model = new Model();

			Controller controller= new Controller(model, view);
			
			
	}

}
