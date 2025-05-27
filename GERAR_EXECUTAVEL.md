# Como Gerar o Executável do Sistema de Estoque

Este documento explica como gerar um executável independente do sistema que não requer Java instalado no computador do cliente.

## Pré-requisitos

1. **JDK 17 ou superior** instalado no computador de desenvolvimento
2. **Maven** instalado e configurado
3. **Sistema Operacional**: Windows, macOS ou Linux

## Verificar Instalação

```bash
# Verificar Java
java -version

# Verificar Maven
mvn -version

# Verificar jpackage (incluído no JDK 17+)
jpackage --version
```

## Métodos de Geração

### Método 1: Script Automático com Detecção de Plataforma (Recomendado)

O script detecta automaticamente sua plataforma e gera o executável apropriado:

#### No Windows:
```cmd
# Execute o script batch
build-exe.bat
```

#### No Linux/Mac:
```bash
# Executar o script (detecta automaticamente a plataforma)
./build-exe.sh
```

**Resultados por plataforma**:
- **Windows**: Gera `EstoqueMecanica-1.0.exe` (instalador completo)
- **macOS**: Gera `EstoqueMecanica-1.0.dmg` (imagem de disco)
- **Linux**: Gera `EstoqueMecanica-1.0.deb` (pacote Debian)

### Método 2: Script Específico para Windows

Para gerar especificamente um .exe (mesmo em outras plataformas):

```bash
# Gerar .exe independente da plataforma atual
./build-windows-exe.sh
```

### Método 3: Comandos Manuais

```bash
# 1. Limpar projeto
mvn clean

# 2. Compilar
mvn compile

# 3. Criar JAR com dependências
mvn package

# 4. Criar runtime Java customizado
jlink --module-path "$JAVA_HOME/jmods" \
      --add-modules java.base,java.desktop,java.logging,java.naming,java.security.jgss,java.instrument,java.management,java.sql,java.xml \
      --output target/java-runtime \
      --compress=2 \
      --no-header-files \
      --no-man-pages

# 5. Gerar executável (exemplo para Windows)
jpackage --input target \
         --name "EstoqueMecanica" \
         --main-jar "EstoqueMecanica-1.0.jar" \
         --main-class "com.estoque.App" \
         --runtime-image target/java-runtime \
         --dest target/dist \
         --type exe \
         --vendor "Oficina Mecânica" \
         --app-version "1.0" \
         --description "Sistema de Gerenciamento de Estoque para Oficinas Mecânicas" \
         --java-options "-Dfile.encoding=UTF-8"
```

## Resultado por Plataforma

### Windows
- **Arquivo**: `target/dist/EstoqueMecanica-1.0.exe`
- **Tamanho**: ~50-80 MB
- **Características**: Instalador completo com atalhos

### macOS
- **Arquivo**: `target/dist/EstoqueMecanica-1.0.dmg`
- **Tamanho**: ~50-80 MB
- **Características**: Imagem de disco para arrastar para Applications

### Linux
- **Arquivo**: `target/dist/EstoqueMecanica-1.0.deb`
- **Tamanho**: ~50-80 MB
- **Características**: Pacote Debian para instalação

## Características Gerais do Executável

✅ **Independente**: Não requer Java instalado no cliente  
✅ **Runtime Incluído**: Java customizado embutido  
✅ **Banco de dados**: SQLite incluído e configurado  
✅ **Multiplataforma**: Funciona em Windows, macOS e Linux  

## Distribuição

### Para Windows:

1. **Copie** o arquivo `EstoqueMecanica-1.0.exe` para o computador do cliente
2. **Execute** como administrador para instalar
3. **Aceite** as permissões solicitadas
4. **Escolha** o diretório de instalação (opcional)
5. **Aguarde** a instalação concluir
6. **Execute** o sistema pelo menu iniciar ou atalho da área de trabalho

### Para macOS:

1. **Copie** o arquivo `EstoqueMecanica-1.0.dmg` para o Mac do cliente
2. **Abra** o arquivo .dmg
3. **Arraste** o aplicativo para a pasta Applications
4. **Execute** o app normalmente

### Para Linux:

1. **Copie** o arquivo `EstoqueMecanica-1.0.deb` para o computador do cliente
2. **Instale** com: `sudo dpkg -i EstoqueMecanica-1.0.deb`
3. **Execute** pelo menu de aplicações ou terminal

## Requisitos do Cliente

### Windows:
- **Sistema Operacional**: Windows 10 ou superior
- **Memória RAM**: Mínimo 4GB (recomendado 8GB)
- **Espaço em Disco**: 200MB livres
- **Permissões**: Administrador para instalação

### macOS:
- **Sistema Operacional**: macOS 10.14 ou superior
- **Memória RAM**: Mínimo 4GB (recomendado 8GB)
- **Espaço em Disco**: 200MB livres

### Linux:
- **Sistema Operacional**: Ubuntu 18.04+ ou Debian 10+
- **Memória RAM**: Mínimo 4GB (recomendado 8GB)
- **Espaço em Disco**: 200MB livres

## Solução de Problemas

### Erro: "Java não encontrado"
- Instale o JDK 17+ no computador de desenvolvimento
- Configure a variável `JAVA_HOME`

### Erro: "jpackage não encontrado"
- Verifique se está usando JDK 17+ (não JRE)
- jpackage está incluído apenas no JDK

### Erro: "Option [--win-dir-chooser] is not valid on this platform"
- Use o script `build-exe.sh` que detecta automaticamente a plataforma
- Ou use `build-windows-exe.sh` para gerar especificamente um .exe

### Erro: "Maven não encontrado"
- Instale o Apache Maven
- Configure a variável `PATH`

### Executável muito grande
- O tamanho é normal (50-80MB) pois inclui o runtime Java
- Para reduzir, remova módulos desnecessários do jlink

### Erro na instalação no cliente
- Execute como administrador (Windows)
- Verifique se o sistema está atualizado
- Desative temporariamente o antivírus

## Scripts Disponíveis

1. **`build-exe.sh`**: Detecta automaticamente a plataforma e gera o executável apropriado
2. **`build-exe.bat`**: Versão Windows do script automático
3. **`build-windows-exe.sh`**: Gera especificamente um .exe (funciona em qualquer plataforma)

## Vantagens do Executável Nativo

1. **Facilidade**: Cliente não precisa instalar Java
2. **Profissional**: Aparência de software comercial
3. **Segurança**: Runtime isolado e controlado
4. **Performance**: Inicialização mais rápida
5. **Distribuição**: Arquivo único para distribuir
6. **Multiplataforma**: Suporte a Windows, macOS e Linux

## Atualizações

Para gerar uma nova versão:

1. Atualize o código fonte
2. Incremente a versão no `pom.xml`
3. Execute novamente o script de build apropriado
4. Distribua o novo executável

---

**Nota**: O jpackage gera executáveis nativos para a plataforma onde é executado. Para gerar executáveis para outras plataformas, você precisa executar o build na plataforma de destino. 