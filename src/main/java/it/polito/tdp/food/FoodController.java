/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Edge;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    	
    	String porzione = boxPorzioni.getValue();

    	
    	if(porzione!=null) {
    		
    		try {
    			int passi=Integer.parseInt(txtPassi.getText());
    			
    			List<String> result= model.init(passi, porzione);
    			
    			for(String si: result) {
                	txtResult.appendText(si+"\n");

        		}
    			
    		} catch (NumberFormatException e){
    			txtResult.appendText("Perfavore inserisci un numero valido di passi!\n");
    		}
    		
    	} else {
    		txtResult.appendText("Perfavore seleziona una porzione!\n");
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...\n");
    	
    	String porzione = boxPorzioni.getValue();
    	
    	if(porzione!=null) {
    		
    		List<Edge> result = model.getCorrelate(porzione);
    		
    		for(Edge ei: result) {
            	txtResult.appendText(ei.getP2()+" "+ei.getPeso()+"\n");

    		}
    		
    	} else {
    		txtResult.appendText("Perfavore seleziona una porzione!\n");
    	}
    	
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	
    	try {
        	int num=Integer.parseInt(txtCalorie.getText());
        	
        	model.creaGrafo(num);
        	
        	boxPorzioni.getItems().clear();
        	boxPorzioni.getItems().addAll(model.getVertici());
        	
        	txtResult.appendText("Grafo creato!\n");
        	txtResult.appendText("Vertici: "+model.numVertici()+"\n");
        	txtResult.appendText("Archi: "+model.numArchi()+"\n");
        	
        	
        	

    	} catch(NumberFormatException e) {
    		txtResult.appendText("Perfavore inserisci un numero\n");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
