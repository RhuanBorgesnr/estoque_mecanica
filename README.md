# Sistema de Estoque para Mecânica

Sistema completo de gerenciamento de estoque desenvolvido especificamente para oficinas mecânicas, com foco na funcionalidade de "dar baixa" quando peças são utilizadas em serviços.

## 🚀 Características Principais

- **Interface Moderna**: JavaFX com design intuitivo e responsivo
- **Banco de Dados Local**: SQLite para funcionamento offline
- **Gestão Completa**: Cadastro, edição, exclusão e busca de peças
- **Controle de Estoque**: Dar baixa, adicionar estoque, alertas de estoque baixo
- **Histórico Completo**: Rastreamento de todas as movimentações
- **Executável Independente**: Não requer Java instalado no cliente

## 🛠️ Tecnologias Utilizadas

- **Java 17** (LTS)
- **JavaFX** para interface gráfica moderna
- **SQLite** para banco de dados local
- **Hibernate ORM** para mapeamento objeto-relacional
- **Maven** para gerenciamento de dependências
- **jpackage** para geração de executável nativo

## 📋 Funcionalidades

### Gestão de Peças
- ✅ Cadastrar novas peças
- ✅ Editar informações das peças
- ✅ Excluir peças do sistema
- ✅ Buscar peças por nome
- ✅ Visualizar detalhes completos

### Controle de Estoque
- ✅ Dar baixa no estoque (uso em serviços)
- ✅ Adicionar estoque (compras/reposição)
- ✅ Ajustar estoque (correções)
- ✅ Alertas de estoque baixo
- ✅ Relatórios de movimentação

### Histórico e Rastreamento
- ✅ Histórico completo de movimentações
- ✅ Data e hora de cada operação
- ✅ Observações personalizadas
- ✅ Controle por usuário

## 🎯 Exemplo de Uso

**Cenário**: Oficina tem 10 pastilhas de freio, mecânico usa 2 em um serviço.

**Processo**:
1. Localizar "Pastilha de Freio" na tabela
2. Selecionar a peça
3. Clicar em "Dar Baixa"
4. Digitar quantidade: "2"
5. Adicionar observação: "Usado no Civic do João"
6. Confirmar operação

**Resultado**: Estoque atualizado para 8 unidades, movimentação registrada no histórico.

## 🚀 Como Executar

### Desenvolvimento

```bash
# Clonar o repositório
git clone [url-do-repositorio]
cd EstoqueMecanica

# Compilar e executar
mvn clean compile
mvn javafx:run
```

### Gerar Executável (.exe)

Para distribuir o sistema sem necessidade de Java no cliente:

#### Windows:
```cmd
build-exe.bat
```

#### Linux/Mac:
```bash
./build-exe.sh
```

O executável será gerado em: `target/dist/EstoqueMecanica-1.0.exe`

**Características do Executável**:
- 📦 Não requer Java instalado
- 🎯 Instalador com interface gráfica
- 🔗 Cria atalhos automáticos
- 📱 Aproximadamente 50-80 MB
- 🛡️ Runtime Java isolado

Veja instruções detalhadas em: [GERAR_EXECUTAVEL.md](GERAR_EXECUTAVEL.md)

## 📊 Estrutura do Banco de Dados

### Tabela: pecas
- `id` - Identificador único
- `nome` - Nome da peça
- `descricao` - Descrição detalhada
- `quantidade_estoque` - Quantidade atual
- `quantidade_minima` - Estoque mínimo
- `localizacao` - Localização física
- `preco_unitario` - Preço por unidade
- `fornecedor` - Nome do fornecedor
- `data_cadastro` - Data de cadastro
- `data_ultima_movimentacao` - Última movimentação

### Tabela: movimentacoes
- `id` - Identificador único
- `peca_id` - Referência à peça
- `tipo` - Tipo de movimentação (ENTRADA, SAIDA, AJUSTE, etc.)
- `quantidade` - Quantidade movimentada
- `observacao` - Observações da operação
- `data_movimentacao` - Data e hora
- `usuario` - Usuário responsável

## 🎨 Interface do Sistema

### Tela Principal
- **Tabela de Peças**: Lista todas as peças com status visual
- **Busca Rápida**: Campo de busca em tempo real
- **Botões de Ação**: Novo, Editar, Excluir, Dar Baixa, Adicionar Estoque
- **Estatísticas**: Total de peças, estoque baixo, valor total

### Dialog de Cadastro
- **Formulário Completo**: Todos os campos da peça
- **Validação**: Campos obrigatórios e formatos
- **Spinners**: Controles numéricos para quantidades
- **Interface Intuitiva**: Labels claros e campos organizados

### Histórico de Movimentações
- **Tabela Detalhada**: Todas as movimentações
- **Filtros**: Por peça, período, tipo
- **Ordenação**: Por data (mais recente primeiro)

## 📁 Estrutura do Projeto

```
EstoqueMecanica/
├── src/main/java/com/estoque/
│   ├── model/              # Entidades (Peca, Movimentacao)
│   ├── dao/                # Acesso a dados (DAOs)
│   ├── service/            # Lógica de negócio
│   ├── controller/         # Controladores JavaFX
│   └── App.java           # Classe principal
├── src/main/resources/
│   ├── fxml/              # Arquivos de interface
│   ├── css/               # Estilos CSS
│   ├── database/          # Banco SQLite
│   └── hibernate.cfg.xml  # Configuração Hibernate
├── build-exe.bat          # Script Windows para gerar .exe
├── build-exe.sh           # Script Linux/Mac para gerar executável
├── GERAR_EXECUTAVEL.md    # Instruções detalhadas
└── pom.xml               # Configuração Maven
```

## 🔧 Configuração

### Pré-requisitos para Desenvolvimento
- JDK 17 ou superior
- Maven 3.6+
- IDE com suporte a JavaFX (IntelliJ IDEA, Eclipse, VS Code)

### Pré-requisitos para Gerar Executável
- JDK 17+ (inclui jpackage)
- Maven configurado
- Windows (para gerar .exe)

### Configuração do Banco
O sistema usa SQLite com configuração automática. Na primeira execução:
1. Banco de dados é criado automaticamente
2. Tabelas são geradas pelo Hibernate
3. Dados de exemplo são inseridos

## 📈 Dados de Exemplo

O sistema inclui 5 peças pré-cadastradas:
- Pastilha de Freio (10 unidades)
- Filtro de Óleo (15 unidades)
- Correia do Alternador (8 unidades)
- Lâmpada H4 (20 unidades)
- Amortecedor Dianteiro (4 unidades)

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 📞 Suporte

Para dúvidas, sugestões ou problemas:
- Abra uma issue no GitHub
- Consulte a documentação em `INSTRUCOES.md`
- Veja o guia de geração de executável em `GERAR_EXECUTAVEL.md`

---

**Desenvolvido para oficinas mecânicas que precisam de controle eficiente de estoque** 🔧⚙️ 