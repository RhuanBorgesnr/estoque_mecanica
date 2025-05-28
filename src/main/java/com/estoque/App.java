package com.estoque;

import com.estoque.dao.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the inventory management system.
 * Initializes JavaFX and loads the primary interface.
 */
public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        
        // Configure stage
        primaryStage.setTitle("Sistema de Estoque - Mecânica");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        
        primaryStage.show();
    }
    
    @Override
    public void stop() throws Exception {
        // Close Hibernate session factory
        HibernateUtil.shutdown();
        super.stop();
    }
    

    
    public static void main(String[] args) {
        launch(args);
    }
}