
package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML
    private ComboBox<Country> cmbStato;
    
    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	int anno = Integer.parseInt(txtAnno.getText());
    	if((anno < 1816 || anno > 2016) && txtAnno.getText() != "") {
    		txtResult.setText("Inserire solo anni compresi tra il 1816 e il 2016");
    	}
    	else {
    		String string = model.calcolaRisultato(anno);
    		txtResult.setText(string);
    	}
    }
    
    @FXML
    void handleCerca(ActionEvent event) {
    	Country country = cmbStato.getValue();
    	if(country != null) {
    		String string = model.statiRaggiungibili(country);
    		txtResult.setText(string);
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<Country> country = model.getCountry();
		cmbStato.getItems().addAll(country); // getItems() è l'elenco degli elementi della tendina
    }
}
