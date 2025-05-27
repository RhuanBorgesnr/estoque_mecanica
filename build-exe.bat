@echo off
echo === Sistema de Estoque para Mecanica - Build Script ===
echo Este script ira gerar um executavel .exe independente
echo.

REM Verificar se o Java 17+ esta instalado
echo 1. Verificando versao do Java...
java -version
if %errorlevel% neq 0 (
    echo ERRO: Java nao encontrado. Por favor, instale o JDK 17+ e tente novamente.
    pause
    exit /b 1
)

REM Limpar builds anteriores
echo.
echo 2. Limpando builds anteriores...
call mvn clean

REM Compilar o projeto
echo.
echo 3. Compilando o projeto...
call mvn compile
if %errorlevel% neq 0 (
    echo ERRO: Falha na compilacao. Verifique os erros acima.
    pause
    exit /b 1
)

REM Criar JAR com todas as dependencias
echo.
echo 4. Criando JAR executavel...
call mvn package
if %errorlevel% neq 0 (
    echo ERRO: Falha na criacao do JAR. Verifique os erros acima.
    pause
    exit /b 1
)

REM Criar runtime customizado do Java
echo.
echo 5. Criando runtime customizado do Java...
jlink --module-path "%JAVA_HOME%\jmods" --add-modules java.base,java.desktop,java.logging,java.naming,java.security.jgss,java.instrument,java.management,java.sql,java.xml --output target\java-runtime --compress=2 --no-header-files --no-man-pages
if %errorlevel% neq 0 (
    echo ERRO: Falha na criacao do runtime Java customizado.
    pause
    exit /b 1
)

REM Gerar executavel com jpackage
echo.
echo 6. Gerando executavel nativo (.exe)...
jpackage --input target --name "EstoqueMecanica" --main-jar "EstoqueMecanica-1.0.jar" --main-class "com.estoque.App" --runtime-image target\java-runtime --dest target\dist --type exe --vendor "Oficina Mecanica" --app-version "1.0" --description "Sistema de Gerenciamento de Estoque para Oficinas Mecanicas" --win-dir-chooser --win-menu --win-shortcut --win-menu-group "Oficina" --java-options "-Dfile.encoding=UTF-8"
if %errorlevel% neq 0 (
    echo ERRO: Falha na geracao do executavel.
    pause
    exit /b 1
)

echo.
echo === BUILD CONCLUIDO COM SUCESSO! ===
echo.
echo O executavel foi gerado em: target\dist\EstoqueMecanica-1.0.exe
echo.
echo Caracteristicas do executavel:
echo - Nao requer Java instalado no computador do cliente
echo - Inclui runtime Java customizado (menor tamanho)
echo - Cria atalho no menu iniciar
echo - Cria atalho na area de trabalho
echo - Instalador com opcao de escolher diretorio
echo.
echo Para distribuir:
echo 1. Copie o arquivo .exe para o computador do cliente
echo 2. Execute como administrador para instalar
echo 3. O sistema estara disponivel no menu iniciar
echo.
pause 