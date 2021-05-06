package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.File;


public class GUI extends Application {
    File inputFile= null;
    File outputFile= null;
    AES aes_encrypt;
    Blowfish blowfish_encypt;
    TripleDES des_encrypt;
    @Override
    public void start(Stage primaryStage) throws Exception {
        aes_encrypt =new AES();
        blowfish_encypt = new Blowfish();
        des_encrypt = new TripleDES();

// Setting the title and the scene for the JavaFX application

        primaryStage.setTitle("Password-Based File Crypter");
        Pane sp = new Pane();
        sp.setBackground(new Background(new BackgroundFill(Paint.valueOf("#404040"),null,null)));
        Scene s= new Scene(sp,900,500);
        primaryStage.setScene(s);
        primaryStage.setResizable(false);
        Font text_font = new Font(15);

        Label generic_label = new Label("Password-Based File Crypter");
        // Centering the title
        generic_label.layoutXProperty().bind(sp.widthProperty().subtract(generic_label.widthProperty()).divide(2));
        generic_label.setLayoutY(20);
        generic_label.setFont(Font.font("Arial", FontWeight.BOLD,24));
        generic_label.setStyle("-fx-text-fill: #FFFFFF;");
        sp.getChildren().add(generic_label);

// Open file button - used to select an input file for encryption/decryption.

        Button b = new Button("Open File");
        b.setMinSize(185,30);
        b.setFont(text_font);
        b.setLayoutX(50);
        b.setLayoutY(100);
        b.setStyle("-fx-text-fill: #000000;");
        sp.getChildren().add(b);

// Input file path text field - used to display the selected input file

        TextField FilePath = new TextField();
        FilePath.setMinSize(600,30);
        FilePath.setLayoutX(250);
        FilePath.setLayoutY(100);
        FilePath.setEditable(false);
        sp.getChildren().add(FilePath);

// Password field label

        generic_label = new Label("Enter Password:");
        generic_label.setLayoutX(50);
        generic_label.setLayoutY(150);
        generic_label.setFont(text_font);
        generic_label.setStyle("-fx-text-fill: #ffffff;");
        sp.getChildren().add(generic_label);

// Password field

        PasswordField pw = new PasswordField();
        pw.setMinSize(600,30);
        pw.setLayoutX(250);
        pw.setLayoutY(150);
        sp.getChildren().add(pw);

// Output file text field - used to set a file name for the output file

        TextField output_file = new TextField();
        output_file.setMinSize(600,30);
        output_file.setLayoutX(250);
        output_file.setLayoutY(250);
        sp.getChildren().add(output_file);

// Error label - used to display success message once a file is encrypted/decrypted or error messages

        Label error = new Label("");
        // Centering the error label
        error.layoutXProperty().bind(sp.widthProperty().subtract(error.widthProperty()).divide(2));
        error.setLayoutY(450);
        error.setFont(text_font);
        error.setStyle("-fx-text-fill: #ffffff;");
        sp.getChildren().add(error);

// Encryption Algorithm drop down label and list

        generic_label = new Label("Select Encryption Algoritm:");
        generic_label.setLayoutX(50);
        generic_label.setLayoutY(200);
        generic_label.setFont(text_font);
        generic_label.setStyle("-fx-text-fill: #ffffff;");
        sp.getChildren().add(generic_label);

        ObservableList<String> enc_types = FXCollections.observableArrayList("AES - Highest Security","Blowfish - Medium Security","3DES - Lowest Security");
        ComboBox<String> types = new ComboBox <String> (enc_types);
        types.setMinSize(600,30);
        types.setPromptText("Select Encryption Algorithm:");
        types.setLayoutX(250);
        types.setLayoutY(200);
        SingleSelectionModel <String> selectionModel = types.getSelectionModel();
        sp.getChildren().add(types);

// Output file name text label

        generic_label = new Label("Output File Name:");
        generic_label.setLayoutX(50);
        generic_label.setLayoutY(250);
        generic_label.setFont(text_font);
        generic_label.setStyle("-fx-text-fill: #ffffff;");
        sp.getChildren().add(generic_label);

// Output file location button

        Button b2 = new Button("Output File Location:");
        b2.setMinSize(185,30);
        b2.setFont(text_font);
        b2.setLayoutX(50);
        b2.setLayoutY(300);
        b2.setStyle("-fx-text-fill: #000000;");
        sp.getChildren().add(b2);

// Output path text field - used to show the output path once it's selected

        TextField outputPath = new TextField();
        outputPath.setMinSize(600,30);
        outputPath.setLayoutX(250);
        outputPath.setLayoutY(300);
        outputPath.setEditable(false);
        sp.getChildren().add(outputPath);

// Encrypt button

        Button encrypt = new Button("Encrypt");
        encrypt.setMinSize(100,30);
        encrypt.setFont(text_font);
        encrypt.setLayoutX(250);
        encrypt.setLayoutY(350);
        encrypt.setStyle("-fx-background-color: #008000; -fx-text-fill: #ffffff;");
        sp.getChildren().add(encrypt);

// Decrypt button

        Button decrypt = new Button("Decrypt");
        decrypt.setMinSize(100,30);
        decrypt.setFont(text_font);
        decrypt.setLayoutX(750);
        decrypt.setLayoutY(350);
        decrypt.setStyle("-fx-background-color: #E90000; -fx-text-fill: #ffffff;");
        sp.getChildren().add(decrypt);

// File Chooser - Selecting input file for encryption

        FileChooser file_opener = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        file_opener.getExtensionFilters().add(extFilter);
        file_opener.setTitle("Select input file for encryption/decryption");

        b.setOnAction(actionEvent -> {
			error.setText("");
        	try {
            inputFile = file_opener.showOpenDialog(primaryStage);
            FilePath.setText(inputFile.getAbsolutePath());}
        	catch (Exception e) {
        		error.setText("No file was selected. Select a file again.");
        	}
        });

// Directory Chooser - Selecting output path for encrypted file

        DirectoryChooser directoryOutputPath = new DirectoryChooser();
        directoryOutputPath.setTitle("Select output path for your encrypted/decrypted file");

        b2.setOnAction(actionEvent -> {
        	error.setText("");
        	try {
            outputFile = directoryOutputPath.showDialog(primaryStage);
            outputPath.setText(outputFile.getAbsolutePath());
        	} catch (Exception e) {
        		error.setText("No output directory was selected.");
        	}
        });

// Encrypt button action event - Encrypts the input file and outputs encrypted file to the chosen directory

        encrypt.setOnAction(actionEvent -> {
            error.setText("");
            if(inputFile!=null && outputFile!=null && !pw.getText().isEmpty() && !output_file.getText().isEmpty())
            {
            
            	// AES encryption
                if(selectionModel.getSelectedIndex()==0)
                {
                	File f = new File(outputPath.getText()+"\\"+output_file.getText()+".txt");
                    try {
                        aes_encrypt.encrypt(inputFile,f,pw.getText());
                        error.setText("File Encrypted Successfully");
                    } catch (Exception e) {
                        f.delete();
                        error.setText("Encryption Error");
                    }
                }

                // Blowfish encryption
                else if(selectionModel.getSelectedIndex()==1)
                {
                    File f = new File(outputPath.getText()+"\\"+output_file.getText()+".txt");
                    try{
                        blowfish_encypt.encrypt(inputFile,f,pw.getText());
                        error.setText("File Encrypted Successfully");
                    }
                    catch (Exception e)
                    {
                        f.delete();
                        error.setText("Encryption Error");
                    }
                }

                // 3DES encryption
                else if(selectionModel.getSelectedIndex()==2)
                {
                	File f = new File(outputPath.getText()+"\\"+output_file.getText()+".txt");
                    try {
                        des_encrypt.encrypt(inputFile,f,pw.getText());
                        error.setText("File Encrypted Successfully");
                    } catch (Exception e) {
                        f.delete();
                        error.setText("Encryption Error");
                    }
                }
            }
            else
            {
                error.setText("Error in fields. Please check the details");
            }
        });

// Decrypt button action event - Decrypts the input file and outputs decrypted file to the chosen directory

        decrypt.setOnAction(actionEvent -> {
            error.setText("");
            if(inputFile!=null && outputFile!=null && !pw.getText().isEmpty() && !output_file.getText().isEmpty())
            {
            	// AES decryption
                if(selectionModel.getSelectedIndex()==0)
                {
                	File f = new File(outputPath.getText()+"\\"+output_file.getText()+".txt");
                    try {
                        aes_encrypt.decrypt(inputFile,f,pw.getText());
                        error.setText("File Decrypted Successfully");
                    } catch (Exception e) {
                        f.delete();
                        error.setText("Decryption Error");
                    }
                }

                // Blowfish decryption
                else if(selectionModel.getSelectedIndex()==1)
                {
                	File f = new File(outputPath.getText()+"\\"+output_file.getText()+".txt");
                    try {
                        blowfish_encypt.decrypt(inputFile,f,pw.getText());
                        error.setText("File Decrypted Successfully");
                    } catch (Exception e) {
                        f.delete();
                        error.setText("Decryption Error");
                    }
                }

                // 3DES decryption
                else if(selectionModel.getSelectedIndex()==2)
                {
                	File f = new File(outputPath.getText()+"\\"+output_file.getText()+".txt");
                    try {
                        des_encrypt.decrypt(inputFile,f,pw.getText());
                        error.setText("File Decrypted Successfully");
                    } catch (Exception e) {
                        f.delete();
                        error.setText("Decryption Error");
                    }
                }
            }
            else
            {
                error.setText("Error in fields. Please check the details");
            }
        });

        // Show content to the primary stage (The user interface window)
        primaryStage.show();
    }

// Launch the JavaFX Application
    public static void main(String[] args) {
        launch(args);
    }
}
