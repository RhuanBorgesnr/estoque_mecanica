package com.estoque.controller;

import com.estoque.model.Peca;
import com.estoque.model.Movimentacao;
import com.estoque.service.EstoqueService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main controller for the inventory management application.
 * Handles the primary user interface interactions.
 */
public class MainController implements Initializable {
    
    @FXML private TextField searchField;
    @FXML private TableView<Peca> pecasTable;
    @FXML private TableColumn<Peca, String> nomeColumn;
    @FXML private TableColumn<Peca, String> descricaoColumn;
    @FXML private TableColumn<Peca, Integer> estoqueColumn;
    @FXML private TableColumn<Peca, Integer> minimoColumn;
    @FXML private TableColumn<Peca, String> localizacaoColumn;
    @FXML private TableColumn<Peca, String> statusColumn;
    
    @FXML private TableView<Movimentacao> movimentacoesTable;
    @FXML private TableColumn<Movimentacao, String> pecaMovColumn;
    @FXML private TableColumn<Movimentacao, String> tipoColumn;
    @FXML private TableColumn<Movimentacao, Integer> quantidadeMovColumn;
    @FXML private TableColumn<Movimentacao, String> dataMovColumn;
    @FXML private TableColumn<Movimentacao, String> observacaoColumn;
    
    @FXML private Label totalPecasLabel;
    @FXML private Label estoqueBaixoLabel;
    @FXML private Label valorTotalLabel;
    
    private EstoqueService estoqueService;
    private ObservableList<Peca> pecasList;
    private ObservableList<Movimentacao> movimentacoesList;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estoqueService = new EstoqueService();
        pecasList = FXCollections.observableArrayList();
        movimentacoesList = FXCollections.observableArrayList();
        
        setupTables();
        loadData();
        setupSearchFilter();
    }
    
    /**
     * Configures the table columns and cell factories.
     */
    private void setupTables() {
        // Setup parts table
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        estoqueColumn.setCellValueFactory(new PropertyValueFactory<>("quantidadeEstoque"));
        minimoColumn.setCellValueFactory(new PropertyValueFactory<>("quantidadeMinima"));
        localizacaoColumn.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        
        statusColumn.setCellValueFactory(cellData -> {
            Peca peca = cellData.getValue();
            String status = peca.isEstoqueBaixo() ? "BAIXO" : "OK";
            return new SimpleStringProperty(status);
        });
        
        // Style low stock rows
        pecasTable.setRowFactory(tv -> {
            TableRow<Peca> row = new TableRow<>();
            row.itemProperty().addListener((obs, oldPeca, newPeca) -> {
                if (newPeca != null && newPeca.isEstoqueBaixo()) {
                    row.setStyle("-fx-background-color: #ffebee;");
                } else {
                    row.setStyle("");
                }
            });
            return row;
        });
        
        pecasTable.setItems(pecasList);
        
        // Setup movements table
        pecaMovColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getPeca().getNome()));
        tipoColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getTipo().getDescricao()));
        quantidadeMovColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        dataMovColumn.setCellValueFactory(cellData -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return new SimpleStringProperty(cellData.getValue().getDataMovimentacao().format(formatter));
        });
        observacaoColumn.setCellValueFactory(new PropertyValueFactory<>("observacao"));
        
        movimentacoesTable.setItems(movimentacoesList);
    }
    
    /**
     * Loads initial data into the tables.
     */
    private void loadData() {
        refreshPecasList();
        refreshMovimentacoesList();
        updateStatistics();
    }
    
    /**
     * Refreshes the parts list from the database.
     */
    private void refreshPecasList() {
        try {
            List<Peca> pecas = estoqueService.listarPecas();
            pecasList.clear();
            pecasList.addAll(pecas);
        } catch (Exception e) {
            showError("Erro ao carregar peças", e.getMessage());
        }
    }
    
    /**
     * Refreshes the movements list from the database.
     */
    private void refreshMovimentacoesList() {
        try {
            List<Movimentacao> movimentacoes = estoqueService.obterMovimentacaoRecente();
            movimentacoesList.clear();
            movimentacoesList.addAll(movimentacoes);
        } catch (Exception e) {
            showError("Erro ao carregar movimentações", e.getMessage());
        }
    }
    
    /**
     * Updates the statistics labels.
     */
    private void updateStatistics() {
        try {
            List<Peca> todasPecas = estoqueService.listarPecas();
            List<Peca> estoqueBaixo = estoqueService.listarEstoqueBaixo();
            
            totalPecasLabel.setText(String.valueOf(todasPecas.size()));
            estoqueBaixoLabel.setText(String.valueOf(estoqueBaixo.size()));
            
            double valorTotal = todasPecas.stream()
                .mapToDouble(p -> (p.getPrecoUnitario() != null ? p.getPrecoUnitario() : 0.0) * p.getQuantidadeEstoque())
                .sum();
            valorTotalLabel.setText(String.format("R$ %.2f", valorTotal));
        } catch (Exception e) {
            showError("Erro ao calcular estatísticas", e.getMessage());
        }
    }
    
    /**
     * Sets up the search filter functionality.
     */
    private void setupSearchFilter() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null || newValue.trim().isEmpty()) {
                refreshPecasList();
            } else {
                try {
                    List<Peca> pecasFiltradas = estoqueService.buscarPecasPorNome(newValue.trim());
                    pecasList.clear();
                    pecasList.addAll(pecasFiltradas);
                } catch (Exception e) {
                    showError("Erro na busca", e.getMessage());
                }
            }
        });
    }
    
    @FXML
    private void onNovaPeca() {
        showPecaDialog(null);
    }
    
    @FXML
    private void onEditarPeca() {
        Peca selectedPeca = pecasTable.getSelectionModel().getSelectedItem();
        if (selectedPeca != null) {
            showPecaDialog(selectedPeca);
        } else {
            showWarning("Nenhuma peça selecionada", "Selecione uma peça para editar.");
        }
    }
    
    @FXML
    private void onExcluirPeca() {
        Peca selectedPeca = pecasTable.getSelectionModel().getSelectedItem();
        if (selectedPeca != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar Exclusão");
            alert.setHeaderText("Excluir Peça");
            alert.setContentText("Tem certeza que deseja excluir a peça: " + selectedPeca.getNome() + "?");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    estoqueService.excluirPeca(selectedPeca.getId());
                    refreshPecasList();
                    updateStatistics();
                    showInfo("Sucesso", "Peça excluída com sucesso!");
                } catch (Exception e) {
                    showError("Erro ao excluir peça", e.getMessage());
                }
            }
        } else {
            showWarning("Nenhuma peça selecionada", "Selecione uma peça para excluir.");
        }
    }
    
    @FXML
    private void onDarBaixa() {
        Peca selectedPeca = pecasTable.getSelectionModel().getSelectedItem();
        if (selectedPeca != null) {
            showMovimentacaoDialog(selectedPeca, "SAIDA");
        } else {
            showWarning("Nenhuma peça selecionada", "Selecione uma peça para dar baixa.");
        }
    }
    
    @FXML
    private void onAdicionarEstoque() {
        Peca selectedPeca = pecasTable.getSelectionModel().getSelectedItem();
        if (selectedPeca != null) {
            showMovimentacaoDialog(selectedPeca, "ENTRADA");
        } else {
            showWarning("Nenhuma peça selecionada", "Selecione uma peça para adicionar estoque.");
        }
    }
    
    @FXML
    private void onVerHistorico() {
        Peca selectedPeca = pecasTable.getSelectionModel().getSelectedItem();
        if (selectedPeca != null) {
            try {
                List<Movimentacao> historico = estoqueService.obterHistoricoMovimentacao(selectedPeca);
                movimentacoesList.clear();
                movimentacoesList.addAll(historico);
            } catch (Exception e) {
                showError("Erro ao carregar histórico", e.getMessage());
            }
        } else {
            showWarning("Nenhuma peça selecionada", "Selecione uma peça para ver o histórico.");
        }
    }
    
    @FXML
    private void onAtualizarListas() {
        loadData();
    }
    
    @FXML
    private void onEstoqueBaixo() {
        try {
            List<Peca> estoqueBaixo = estoqueService.listarEstoqueBaixo();
            pecasList.clear();
            pecasList.addAll(estoqueBaixo);
        } catch (Exception e) {
            showError("Erro ao carregar estoque baixo", e.getMessage());
        }
    }
    
    /**
     * Shows the part dialog for adding or editing.
     */
    private void showPecaDialog(Peca peca) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/peca-dialog.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle(peca == null ? "Nova Peça" : "Editar Peça");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(pecasTable.getScene().getWindow());
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            
            PecaDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPeca(peca);
            
            dialogStage.showAndWait();
            
            if (controller.isConfirmed()) {
                try {
                    Peca pecaToSave = controller.getPeca();
                    if (peca == null) {
                        // New part
                        estoqueService.adicionarPeca(pecaToSave);
                        showInfo("Sucesso", "Peça cadastrada com sucesso!");
                    } else {
                        // Edit existing part
                        estoqueService.atualizarPeca(pecaToSave);
                        showInfo("Sucesso", "Peça atualizada com sucesso!");
                    }
                    
                    refreshPecasList();
                    updateStatistics();
                } catch (Exception e) {
                    showError("Erro ao salvar peça", e.getMessage());
                }
            }
        } catch (IOException e) {
            showError("Erro", "Não foi possível abrir o dialog de peças: " + e.getMessage());
        }
    }
    
    /**
     * Shows the stock movement dialog.
     */
    private void showMovimentacaoDialog(Peca peca, String tipo) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Movimentação de Estoque");
        dialog.setHeaderText(tipo.equals("SAIDA") ? "Dar Baixa no Estoque" : "Adicionar ao Estoque");
        dialog.setContentText("Quantidade:");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                Integer quantidade = Integer.parseInt(result.get());
                String observacao = "Movimentação via sistema";
                String usuario = "Usuário";
                
                boolean sucesso;
                if (tipo.equals("SAIDA")) {
                    sucesso = estoqueService.darBaixaEstoque(peca.getId(), quantidade, observacao, usuario);
                } else {
                    sucesso = estoqueService.adicionarEstoque(peca.getId(), quantidade, observacao, usuario);
                }
                
                if (sucesso) {
                    refreshPecasList();
                    refreshMovimentacoesList();
                    updateStatistics();
                    showInfo("Sucesso", "Movimentação realizada com sucesso!");
                }
            } catch (NumberFormatException e) {
                showError("Erro", "Quantidade deve ser um número válido.");
            } catch (Exception e) {
                showError("Erro na movimentação", e.getMessage());
            }
        }
    }
    
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showWarning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 