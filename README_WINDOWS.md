# 🪟 Sistema de Estoque para Mecânica - Windows

## 🎯 Como Gerar o Executável .exe

### 📋 Pré-requisitos (Instalar no Windows)

1. **Java JDK 17+**
   - Download: https://adoptium.net/
   - Escolha: "JDK 17 LTS" para Windows x64
   - Instale e configure JAVA_HOME

2. **Apache Maven**
   - Download: https://maven.apache.org/download.cgi
   - Extraia e adicione ao PATH do Windows

3. **Verificar Instalação**
   ```cmd
   java -version
   mvn -version
   jpackage --version
   ```

### 🚀 Gerar Executável

**Opção 1 - Script Automático:**
```cmd
build-windows.bat
```

**Opção 2 - PowerShell:**
```powershell
.\build-windows.ps1
```

### 📦 Resultado

Arquivo gerado: `target\dist\EstoqueMecanica-1.0.exe`

- ✅ Tamanho: ~100 MB
- ✅ Independente (não precisa Java)
- ✅ Instalador Windows nativo
- ✅ Atalhos automáticos

## 🎯 Funcionalidades do Sistema

### 📊 Gerenciamento de Estoque
- **Cadastro de Peças**: Nome, descrição, quantidade, localização
- **Controle de Estoque**: Quantidade atual e mínima
- **Dar Baixa**: Reduzir estoque quando usar peças
- **Adicionar Estoque**: Aumentar quando receber peças
- **Busca**: Localizar peças rapidamente

### 📈 Relatórios e Alertas
- **Estoque Baixo**: Alertas automáticos
- **Movimentações**: Histórico completo
- **Estatísticas**: Total de peças e valor

### 💾 Banco de Dados
- **SQLite**: Banco local, sem servidor
- **Backup**: Dados salvos automaticamente
- **Portabilidade**: Arquivo único

## 🔧 Exemplo de Uso

**Cenário**: Oficina tem 10 pastilhas de freio, mecânico usa 2

1. Abrir sistema
2. Localizar "Pastilha de Freio" na tabela
3. Selecionar a peça
4. Clicar "Dar Baixa"
5. Digitar "2" na quantidade
6. Confirmar operação
7. ✅ Estoque atualizado para 8 unidades
8. ✅ Movimentação registrada automaticamente

## 🛠️ Solução de Problemas

### ❌ "Java não encontrado"
- Instale JDK 17+ (não JRE)
- Configure JAVA_HOME
- Adicione Java ao PATH

### ❌ "Maven não encontrado"
- Baixe Maven
- Extraia para C:\apache-maven-x.x.x
- Adicione C:\apache-maven-x.x.x\bin ao PATH

### ❌ "jpackage não encontrado"
- Use JDK completo (não OpenJRE)
- Verifique se JAVA_HOME aponta para JDK

### ❌ Erro na compilação
```cmd
mvn clean
mvn dependency:resolve
mvn compile
```

## 📁 Estrutura do Projeto

```
EstoqueMecanica/
├── src/main/java/com/estoque/
│   ├── model/          # Entidades (Peca, Movimentacao)
│   ├── dao/            # Acesso ao banco (PecaDAO, MovimentacaoDAO)
│   ├── service/        # Lógica de negócio (EstoqueService)
│   ├── controller/     # Controladores JavaFX
│   └── App.java        # Classe principal
├── src/main/resources/
│   ├── fxml/           # Interfaces (main.fxml, peca-dialog.fxml)
│   ├── css/            # Estilos (styles.css)
│   └── hibernate.cfg.xml # Configuração do banco
├── build-windows.bat   # Script para gerar .exe
├── build-windows.ps1   # Script PowerShell
└── pom.xml            # Configuração Maven
```

## 🌐 Alternativas para Gerar .exe

### 🔄 GitHub Actions (Automático)
Se não tiver Windows, use GitHub Actions:

1. Faça fork do projeto
2. Crie `.github/workflows/build.yml`
3. Push para GitHub
4. Baixe o .exe gerado automaticamente

### 🖥️ Máquina Virtual
- VirtualBox + Windows 10/11
- VMware + Windows
- AWS/Azure Windows VM

## 📋 Checklist de Distribuição

- [ ] Executável gerado com sucesso
- [ ] Testado em máquina limpa
- [ ] Banco SQLite incluído
- [ ] Interface funcionando
- [ ] Todas as funcionalidades testadas
- [ ] Arquivo .exe pronto para distribuição

## 🎯 Próximos Passos

1. **Gerar .exe** usando os scripts fornecidos
2. **Testar** em máquina Windows limpa
3. **Distribuir** para as oficinas
4. **Treinar** usuários no sistema
5. **Suporte** para dúvidas e melhorias

---

## 📞 Suporte

Para problemas técnicos:
1. Verifique os pré-requisitos
2. Execute comandos manualmente
3. Consulte logs de erro
4. Teste em ambiente limpo

**Nota**: O executável .exe deve ser gerado em ambiente Windows. O jpackage não suporta cross-compilation. 