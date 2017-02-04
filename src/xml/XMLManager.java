package xml;

import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class XMLManager extends Application{
	 // kind of data files to look for
    public static final String DATA_FILE_EXTENSION = "*.xml";

    // it is generally accepted behavior that the chooser remembers where user left it last
    private FileChooser myChooser = makeChooser(DATA_FILE_EXTENSION);
    private File dataFile;


    @Override
    public void start (Stage primaryStage) throws Exception {
        dataFile = myChooser.showOpenDialog(primaryStage);
        if (dataFile != null) {
            try {
	            System.out.println(new XMLParser().getSimulation(dataFile));
            }
            catch (XMLException e) {
                Alert a = new Alert(AlertType.ERROR);
                a.setContentText(String.format("ERROR reading file %s", dataFile.getPath()));
                a.showAndWait();
            }
        }
        else {
            // nothing selected, so quit the application
            Platform.exit();
        }
    }
    
    public Simulation getSimulation(){
    	return new XMLParser().getSimulation(dataFile);
    }

    // set some sensible defaults when the FileChooser is created
    private FileChooser makeChooser (String extensionAccepted) {
        FileChooser result = new FileChooser();
        result.setTitle("Open Data File");
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir")));
        result.getExtensionFilters().setAll(new ExtensionFilter("Text Files", extensionAccepted));
        return result;
    }
}
