package com.estoque;

import com.estoque.dao.HibernateUtil;
import com.estoque.model.Peca;
import com.estoque.service.EstoqueService;
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
        
        // Initialize with sample data if database is empty
        initializeSampleData();
        
        primaryStage.show();
    }
    
    @Override
    public void stop() throws Exception {
        // Close Hibernate session factory
        HibernateUtil.shutdown();
        super.stop();
    }
    
    /**
     * Initializes the database with sample data if it's empty.
     */
    private void initializeSampleData() {
        try {
            EstoqueService estoqueService = new EstoqueService();
            
            // Check if database has data
            if (estoqueService.listarPecas().isEmpty()) {
                // Add sample parts
                Peca pastilhaFreio = new Peca(
                    "Pastilha de Freio",
                    "Pastilha de freio dianteira universal",
                    10,
                    5,
                    "Prateleira A1",
                    25.50,
                    "AutoPeças Ltda"
                );
                
                Peca filtroOleo = new Peca(
                    "Filtro de Óleo",
                    "Filtro de óleo para motores 1.0 a 2.0",
                    15,
                    3,
                    "Prateleira B2",
                    18.90,
                    "Filtros Brasil"
                );
                
                Peca correiaAlternador = new Peca(
                    "Correia do Alternador",
                    "Correia do alternador para veículos populares",
                    8,
                    2,
                    "Prateleira C1",
                    35.00,
                    "Correias & Cia"
                );
                
                Peca lampada = new Peca(
                    "Lâmpada H4",
                    "Lâmpada de farol H4 12V 60/55W",
                    20,
                    5,
                    "Gaveta D3",
                    12.50,
                    "Iluminação Auto"
                );
                
                Peca amortecedor = new Peca(
                    "Amortecedor Dianteiro",
                    "Amortecedor dianteiro para carros compactos",
                    4,
                    2,
                    "Estoque Pesado",
                    120.00,
                    "Suspensão Total"
                );
                
                // Save sample parts
                estoqueService.adicionarPeca(pastilhaFreio);
                estoqueService.adicionarPeca(filtroOleo);
                estoqueService.adicionarPeca(correiaAlternador);
                estoqueService.adicionarPeca(lampada);
                estoqueService.adicionarPeca(amortecedor);
                
                System.out.println("Dados de exemplo inseridos com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao inicializar dados de exemplo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}