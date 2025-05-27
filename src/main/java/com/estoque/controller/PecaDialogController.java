package com.estoque.controller;

import com.estoque.model.Peca;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controller for the part dialog (add/edit parts).
 * Handles the form for creating and editing mechanical parts.
 */
public class PecaDialogController {
    
    @FXML private TextField nomeField;
    @FXML private TextArea descricaoArea;
    @FXML private Spinner<Integer> estoqueSpinner;
    @FXML private Spinner<Integer> minimoSpinner;
    @FXML private TextField localizacaoField;
    @FXML private TextField precoField;
    @FXML private TextField fornecedorField;
    @FXML private Button salvarButton;
    @FXML private Button cancelarButton;
    
    private Peca peca;
    private boolean confirmed = false;
    private Stage dialogStage;
    
    /**
     * Initializes the controller.
     */
    @FXML
    private void initialize() {
        // Configure spinners
        estoqueSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999, 0));
        minimoSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 1));
        
        // Make spinners editable
        estoqueSpinner.setEditable(true);
        minimoSpinner.setEditable(true);
        
        // Add validation to price field (only numbers and decimal point)
        precoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                precoField.setText(oldValue);
            }
        });
    }
    
    /**
     * Sets the dialog stage.
     * @param dialogStage the dialog stage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Sets the part to be edited.
     * @param peca the part to edit, or null for new part
     */
    public void setPeca(Peca peca) {
        this.peca = peca;
        
        if (peca != null) {
            // Edit mode - populate fields
            nomeField.setText(peca.getNome());
            descricaoArea.setText(peca.getDescricao());
            estoqueSpinner.getValueFactory().setValue(peca.getQuantidadeEstoque());
            minimoSpinner.getValueFactory().setValue(peca.getQuantidadeMinima());
            localizacaoField.setText(peca.getLocalizacao());
            precoField.setText(peca.getPrecoUnitario() != null ? peca.getPrecoUnitario().toString() : "");
            fornecedorField.setText(peca.getFornecedor());
        } else {
            // New part mode - clear fields
            nomeField.clear();
            descricaoArea.clear();
            estoqueSpinner.getValueFactory().setValue(0);
            minimoSpinner.getValueFactory().setValue(1);
            localizacaoField.clear();
            precoField.clear();
            fornecedorField.clear();
        }
    }
    
    /**
     * Returns true if the user clicked OK.
     * @return true if confirmed
     */
    public boolean isConfirmed() {
        return confirmed;
    }
    
    /**
     * Returns the configured part.
     * @return the part with form data
     */
    public Peca getPeca() {
        return peca;
    }
    
    /**
     * Handles the save button click.
     */
    @FXML
    private void handleSalvar() {
        if (isInputValid()) {
            if (peca == null) {
                peca = new Peca();
            }
            
            // Update part with form data
            peca.setNome(nomeField.getText().trim());
            peca.setDescricao(descricaoArea.getText().trim());
            peca.setQuantidadeEstoque(estoqueSpinner.getValue());
            peca.setQuantidadeMinima(minimoSpinner.getValue());
            peca.setLocalizacao(localizacaoField.getText().trim());
            
            // Parse price
            String precoText = precoField.getText().trim();
            if (!precoText.isEmpty()) {
                try {
                    peca.setPrecoUnitario(Double.parseDouble(precoText));
                } catch (NumberFormatException e) {
                    peca.setPrecoUnitario(0.0);
                }
            } else {
                peca.setPrecoUnitario(0.0);
            }
            
            peca.setFornecedor(fornecedorField.getText().trim());
            
            confirmed = true;
            dialogStage.close();
        }
    }
    
    /**
     * Handles the cancel button click.
     */
    @FXML
    private void handleCancelar() {
        dialogStage.close();
    }
    
    /**
     * Validates the user input.
     * @return true if input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        
        if (nomeField.getText() == null || nomeField.getText().trim().isEmpty()) {
            errorMessage += "Nome é obrigatório!\n";
        }
        
        if (estoqueSpinner.getValue() < 0) {
            errorMessage += "Quantidade em estoque deve ser maior ou igual a zero!\n";
        }
        
        if (minimoSpinner.getValue() < 0) {
            errorMessage += "Quantidade mínima deve ser maior ou igual a zero!\n";
        }
        
        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Show error dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Dados Inválidos");
            alert.setHeaderText("Por favor, corrija os dados inválidos:");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            
            return false;
        }
    }
} 