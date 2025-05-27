#!/bin/bash

echo "=== Sistema de Estoque para Mecânica - Build Script ==="
echo "Este script irá gerar um executável nativo independente"
echo ""

# Detectar plataforma
OS=$(uname -s)
case "$OS" in
    Darwin*)    PLATFORM="mac";;
    Linux*)     PLATFORM="linux";;
    CYGWIN*|MINGW*|MSYS*) PLATFORM="windows";;
    *)          PLATFORM="unknown";;
esac

echo "Plataforma detectada: $PLATFORM"

# Verificar se o Java 17+ está instalado
echo ""
echo "1. Verificando versão do Java..."
java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
echo "Java version: $java_version"

if [[ ! "$java_version" =~ ^17\. ]] && [[ ! "$java_version" =~ ^[2-9][0-9]\. ]]; then
    echo "ERRO: Java 17 ou superior é necessário para gerar o executável."
    echo "Por favor, instale o JDK 17+ e tente novamente."
    exit 1
fi

# Limpar builds anteriores
echo ""
echo "2. Limpando builds anteriores..."
mvn clean

# Compilar o projeto
echo ""
echo "3. Compilando o projeto..."
mvn compile

if [ $? -ne 0 ]; then
    echo "ERRO: Falha na compilação. Verifique os erros acima."
    exit 1
fi

# Criar JAR com todas as dependências
echo ""
echo "4. Criando JAR executável..."
mvn package

if [ $? -ne 0 ]; then
    echo "ERRO: Falha na criação do JAR. Verifique os erros acima."
    exit 1
fi

# Criar runtime customizado do Java
echo ""
echo "5. Criando runtime customizado do Java..."
jlink --module-path "$JAVA_HOME/jmods" \
      --add-modules java.base,java.desktop,java.logging,java.naming,java.security.jgss,java.instrument,java.management,java.sql,java.xml \
      --output target/java-runtime \
      --compress=2 \
      --no-header-files \
      --no-man-pages

if [ $? -ne 0 ]; then
    echo "ERRO: Falha na criação do runtime Java customizado."
    exit 1
fi

# Configurar opções específicas da plataforma
echo ""
echo "6. Gerando executável nativo para $PLATFORM..."

case "$PLATFORM" in
    "windows")
        # Opções específicas do Windows
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
                 --win-dir-chooser \
                 --win-menu \
                 --win-shortcut \
                 --win-menu-group "Oficina" \
                 --java-options "-Dfile.encoding=UTF-8"
        EXECUTABLE_EXT=".exe"
        ;;
    "mac")
        # Opções específicas do macOS
        jpackage --input target \
                 --name "EstoqueMecanica" \
                 --main-jar "EstoqueMecanica-1.0.jar" \
                 --main-class "com.estoque.App" \
                 --runtime-image target/java-runtime \
                 --dest target/dist \
                 --type dmg \
                 --vendor "Oficina Mecânica" \
                 --app-version "1.0" \
                 --description "Sistema de Gerenciamento de Estoque para Oficinas Mecânicas" \
                 --java-options "-Dfile.encoding=UTF-8"
        EXECUTABLE_EXT=".dmg"
        ;;
    "linux")
        # Opções específicas do Linux
        jpackage --input target \
                 --name "EstoqueMecanica" \
                 --main-jar "EstoqueMecanica-1.0.jar" \
                 --main-class "com.estoque.App" \
                 --runtime-image target/java-runtime \
                 --dest target/dist \
                 --type deb \
                 --vendor "Oficina Mecânica" \
                 --app-version "1.0" \
                 --description "Sistema de Gerenciamento de Estoque para Oficinas Mecânicas" \
                 --java-options "-Dfile.encoding=UTF-8"
        EXECUTABLE_EXT=".deb"
        ;;
    *)
        echo "ERRO: Plataforma não suportada: $OS"
        exit 1
        ;;
esac

if [ $? -ne 0 ]; then
    echo "ERRO: Falha na geração do executável."
    exit 1
fi

echo ""
echo "=== BUILD CONCLUÍDO COM SUCESSO! ==="
echo ""
echo "O executável foi gerado em: target/dist/EstoqueMecanica-1.0$EXECUTABLE_EXT"
echo ""
echo "Características do executável:"
echo "- Não requer Java instalado no computador do cliente"
echo "- Inclui runtime Java customizado (menor tamanho)"

case "$PLATFORM" in
    "windows")
        echo "- Cria atalho no menu iniciar"
        echo "- Cria atalho na área de trabalho"
        echo "- Instalador com opção de escolher diretório"
        ;;
    "mac")
        echo "- Arquivo .dmg para instalação no macOS"
        echo "- Arraste para a pasta Applications"
        ;;
    "linux")
        echo "- Pacote .deb para instalação no Ubuntu/Debian"
        echo "- Instale com: sudo dpkg -i EstoqueMecanica-1.0.deb"
        ;;
esac

echo ""
echo "Para distribuir:"
case "$PLATFORM" in
    "windows")
        echo "1. Copie o arquivo .exe para o computador do cliente"
        echo "2. Execute como administrador para instalar"
        echo "3. O sistema estará disponível no menu iniciar"
        ;;
    "mac")
        echo "1. Copie o arquivo .dmg para o computador do cliente"
        echo "2. Abra o .dmg e arraste o app para Applications"
        echo "3. Execute o app normalmente"
        ;;
    "linux")
        echo "1. Copie o arquivo .deb para o computador do cliente"
        echo "2. Instale com: sudo dpkg -i EstoqueMecanica-1.0.deb"
        echo "3. Execute pelo menu de aplicações"
        ;;
esac
echo "" 