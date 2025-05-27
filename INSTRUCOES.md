# Sistema de Estoque para Mecânica - Instruções de Uso

## 🎯 Objetivo do Sistema

Este sistema foi desenvolvido especificamente para oficinas mecânicas que precisam controlar o estoque de peças automotivas de forma simples e eficiente.

## 🚀 Como Executar

### Opção 1: Script Automático (Recomendado)
```bash
./run.sh
```

### Opção 2: Comandos Maven
```bash
# Compilar
mvn clean compile

# Executar
mvn javafx:run
```

## 📋 Funcionalidades Principais

### 1. Dar Baixa no Estoque
**Cenário**: O mecânico usou 2 pastilhas de freio em um serviço.

**Passos**:
1. Abra o sistema
2. Localize "Pastilha de Freio" na lista (ou use a busca)
3. Selecione a peça clicando nela
4. Clique no botão "Dar Baixa"
5. Digite "2" na caixa de diálogo
6. Confirme

**Resultado**: O estoque será reduzido de 10 para 8 unidades e a movimentação será registrada.

### 2. Adicionar Estoque
**Cenário**: Chegaram 5 novas pastilhas de freio do fornecedor.

**Passos**:
1. Selecione "Pastilha de Freio"
2. Clique em "Adicionar Estoque"
3. Digite "5"
4. Confirme

**Resultado**: O estoque aumentará para 13 unidades.

### 3. Verificar Estoque Baixo
- Clique no botão "Estoque Baixo"
- Peças com estoque abaixo do mínimo aparecerão destacadas em vermelho
- O contador na parte inferior mostra quantas peças estão com estoque baixo

### 4. Buscar Peças
- Use o campo de busca no topo
- Digite parte do nome da peça
- A lista será filtrada automaticamente

### 5. Ver Histórico
- Selecione uma peça
- Clique em "Histórico de Movimentações" no menu Relatórios
- Veja todas as entradas e saídas da peça

## 📊 Interface do Sistema

### Tela Principal
- **Tabela Superior**: Lista todas as peças com estoque atual
- **Tabela Inferior**: Mostra as últimas movimentações
- **Barra Inferior**: Estatísticas (total de peças, estoque baixo, valor total)

### Cores e Indicadores
- **Vermelho**: Peças com estoque baixo
- **Verde**: Status OK
- **Azul**: Linha selecionada

## 🔧 Dados de Exemplo

Na primeira execução, o sistema criará automaticamente:
- Pastilha de Freio (10 unidades, mínimo 5)
- Filtro de Óleo (15 unidades, mínimo 3)
- Correia do Alternador (8 unidades, mínimo 2)
- Lâmpada H4 (20 unidades, mínimo 5)
- Amortecedor Dianteiro (4 unidades, mínimo 2)

## 💡 Dicas de Uso

### Para Oficinas Pequenas
- Use o sistema diariamente para dar baixa nas peças utilizadas
- Verifique o estoque baixo semanalmente
- Mantenha os dados de fornecedor atualizados

### Para Oficinas Médias/Grandes
- Treine todos os mecânicos para usar o sistema
- Estabeleça rotinas de verificação de estoque
- Use o histórico para análise de consumo

## 🎯 Fluxo de Trabalho Recomendado

### Início do Dia
1. Abra o sistema
2. Verifique peças com estoque baixo
3. Faça pedidos se necessário

### Durante o Serviço
1. Ao usar uma peça, dê baixa imediatamente
2. Anote a quantidade exata utilizada
3. Confirme a movimentação

### Final do Dia
1. Revise as movimentações do dia
2. Verifique se todas as baixas foram registradas
3. Planeje compras para o próximo dia

## 🔍 Solução de Problemas Comuns

### "Estoque Insuficiente"
- Verifique se a quantidade está correta
- Confirme o estoque atual da peça
- Se necessário, faça um ajuste de estoque

### Peça Não Encontrada
- Use a busca por nome
- Verifique se a peça foi cadastrada
- Considere cadastrar uma nova peça

### Sistema Lento
- Feche e reabra a aplicação
- Verifique se há muitas movimentações na tela
- Use o botão "Atualizar" para recarregar os dados

## 📈 Relatórios Disponíveis

### Estatísticas em Tempo Real
- Total de peças cadastradas
- Quantidade de peças com estoque baixo
- Valor total do estoque

### Histórico de Movimentações
- Por peça específica
- Últimas 50 movimentações
- Data, hora e tipo de movimentação

## 🎨 Personalização

### Localização das Peças
- Use códigos como "A1", "B2", "C3" para prateleiras
- Seja consistente na nomenclatura
- Facilita a localização física das peças

### Fornecedores
- Mantenha dados atualizados
- Use nomes completos das empresas
- Facilita contato para reposição

---

**💡 Lembre-se**: O sistema é uma ferramenta para ajudar no controle. O sucesso depende do uso consistente e correto por toda a equipe da oficina. 