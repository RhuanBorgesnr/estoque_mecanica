# 🪟 Guia para Gerar Executável Windows (.exe)

## 📋 Pré-requisitos

### ✅ 1. Java Development Kit (JDK) 17+
- **Download**: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) ou [OpenJDK](https://adoptium.net/)
- **Versão**: 17 ou superior (LTS recomendado)
- **Tipo**: JDK completo (não apenas JRE)

### ✅ 2. Apache Maven
- **Download**: [Maven](https://maven.apache.org/download.cgi)
- **Instalação**: Extrair e adicionar ao PATH

### ✅ 3. Verificar Instalação
```cmd
java -version
mvn -version
jpackage --version
```

## 🚀 Métodos de Geração

### 🎯 Método 1: Script Batch (Recomendado)
```cmd
# No prompt de comando do Windows:
build-windows.bat
```

### 🎯 Método 2: PowerShell (Avançado)
```powershell
# No PowerShell:
.\build-windows.ps1
```

### 🎯 Método 3: Manual (Passo a Passo)
```cmd
# 1. Limpar projeto
mvn clean

# 2. Compilar
mvn compile

# 3. Gerar JAR
mvn package

# 4. Criar runtime Java
jlink --module-path "%JAVA_HOME%\jmods" ^
      --add-modules java.base,java.desktop,java.logging,java.naming,java.security.jgss,java.instrument,java.management,java.sql,java.xml ^
      --output target\java-runtime ^
      --compress=2 ^
      --no-header-files ^
      --no-man-pages

# 5. Gerar executável
jpackage --input target ^
         --name "EstoqueMecanica" ^
         --main-jar EstoqueMecanica-1.0.jar ^
         --main-class com.estoque.App ^
         --runtime-image target\java-runtime ^
         --dest target\dist ^
         --type exe ^
         --app-version "1.0" ^
         --vendor "Oficina Mecânica" ^
         --description "Sistema de Gerenciamento de Estoque para Oficinas Mecânicas" ^
         --win-dir-chooser ^
         --win-menu ^
         --win-shortcut ^
         --win-menu-group "Oficina" ^
         --java-options "-Dfile.encoding=UTF-8"
```

## 📁 Resultado Esperado

### 📦 Arquivo Gerado
```
target/dist/EstoqueMecanica-1.0.exe
```

### 📊 Características
- **Tamanho**: ~80-120 MB
- **Tipo**: Instalador Windows nativo
- **Dependências**: Nenhuma (Java incluído)
- **Compatibilidade**: Windows 10/11

### 🎯 Funcionalidades do Instalador
- ✅ Interface nativa do Windows
- ✅ Escolha do diretório de instalação
- ✅ Atalho no Menu Iniciar
- ✅ Atalho na Área de Trabalho
- ✅ Grupo "Oficina" no menu
- ✅ Desinstalação via Painel de Controle

## 🔧 Solução de Problemas

### ❌ Erro: "jpackage não encontrado"
**Solução**: Instale JDK completo (não JRE)
```cmd
# Verificar se JAVA_HOME está configurado
echo %JAVA_HOME%

# Deve apontar para JDK, exemplo:
# C:\Program Files\Java\jdk-17.0.8
```

### ❌ Erro: "Maven não encontrado"
**Solução**: Adicionar Maven ao PATH
```cmd
# Adicionar ao PATH do sistema:
C:\apache-maven-3.9.5\bin
```

### ❌ Erro: "Falha na compilação"
**Solução**: Verificar dependências
```cmd
mvn dependency:resolve
mvn clean compile
```

### ❌ Erro: "Módulos não encontrados"
**Solução**: Verificar JAVA_HOME
```cmd
# JAVA_HOME deve apontar para JDK com pasta jmods
dir "%JAVA_HOME%\jmods"
```

## 🌐 Alternativas para Cross-Platform

### 🔄 GitHub Actions (Automático)
Crie `.github/workflows/build-windows.yml`:
```yaml
name: Build Windows Executable
on: [push]
jobs:
  build-windows:
    runs-on: windows-latest
    steps:
    - uses: actions/checkout@v3
    - uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    - run: mvn clean package
    - run: .\build-windows.bat
    - uses: actions/upload-artifact@v3
      with:
        name: windows-executable
        path: target/dist/*.exe
```

### 🖥️ Máquina Virtual Windows
- **VirtualBox**: Instalar Windows em VM
- **VMware**: Ambiente Windows virtualizado
- **Docker**: Container Windows (limitado)

## 📋 Checklist Final

- [ ] Java 17+ JDK instalado
- [ ] Maven instalado e no PATH
- [ ] JAVA_HOME configurado
- [ ] jpackage funcionando
- [ ] Projeto compila sem erros
- [ ] Script executado com sucesso
- [ ] Arquivo .exe gerado em `target/dist/`
- [ ] Executável testado em máquina limpa

## 🎯 Distribuição

### 📤 Para Distribuir
1. Copie o arquivo `EstoqueMecanica-1.0.exe`
2. Envie para o cliente
3. Cliente executa o instalador
4. Sistema instalado e funcionando

### 💾 Backup dos Dados
O banco SQLite fica em:
```
%USERPROFILE%\AppData\Local\EstoqueMecanica\database\
```

## 🆘 Suporte

Se encontrar problemas:
1. Verifique os pré-requisitos
2. Execute os comandos manualmente
3. Consulte os logs de erro
4. Teste em ambiente limpo

---

**Nota**: Este processo deve ser executado em um ambiente Windows para gerar o executável .exe. O jpackage não suporta cross-compilation. 