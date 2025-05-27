#!/bin/bash

echo "=== Sistema de Estoque para Mecânica - Build Windows EXE ==="
echo "Este script irá gerar especificamente um executável .exe para Windows"
echo ""

# Verificar se o Java 17+ está instalado
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

# Gerar executável Windows (.exe) - sem opções específicas do Windows se não estiver no Windows
echo ""
echo "6. Gerando executável Windows (.exe)..."

# Detectar se estamos no Windows
if [[ "$OSTYPE" == "cygwin" ]] || [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "win32" ]]; then
    # Estamos no Windows - usar todas as opções
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
else
    # Não estamos no Windows - usar apenas opções básicas
    echo "AVISO: Gerando .exe em plataforma não-Windows. Algumas funcionalidades podem não estar disponíveis."
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
fi

if [ $? -ne 0 ]; then
    echo "ERRO: Falha na geração do executável."
    echo ""
    echo "DICA: Para gerar um .exe completo com todas as funcionalidades,"
    echo "execute este script em um ambiente Windows ou use o script"
    echo "build-exe.sh que detecta automaticamente sua plataforma."
    exit 1
fi

echo ""
echo "=== BUILD CONCLUÍDO COM SUCESSO! ==="
echo ""
echo "O executável foi gerado em: target/dist/EstoqueMecanica-1.0.exe"
echo ""
echo "Características do executável:"
echo "- Não requer Java instalado no computador do cliente"
echo "- Inclui runtime Java customizado (menor tamanho)"
echo "- Compatível com Windows 10 ou superior"
echo ""
echo "Para distribuir:"
echo "1. Copie o arquivo .exe para o computador Windows do cliente"
echo "2. Execute como administrador para instalar"
echo "3. O sistema estará disponível no menu iniciar"
echo "" 