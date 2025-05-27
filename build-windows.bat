@echo off
echo ========================================
echo    GERADOR DE EXECUTAVEL WINDOWS
echo    Sistema de Estoque para Mecanica
echo ========================================
echo.

REM Verificar se Java 17+ está instalado
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Java nao encontrado. Instale Java 17 ou superior.
    pause
    exit /b 1
)

echo [1/5] Limpando projeto...
call mvn clean
if %errorlevel% neq 0 (
    echo ERRO: Falha na limpeza do projeto
    pause
    exit /b 1
)

echo [2/5] Compilando projeto...
call mvn compile
if %errorlevel% neq 0 (
    echo ERRO: Falha na compilacao
    pause
    exit /b 1
)

echo [3/5] Gerando JAR com dependencias...
call mvn package
if %errorlevel% neq 0 (
    echo ERRO: Falha na geracao do JAR
    pause
    exit /b 1
)

echo [4/5] Criando runtime Java customizado...
if exist target\java-runtime rmdir /s /q target\java-runtime
jlink --module-path "%JAVA_HOME%\jmods" ^
      --add-modules java.base,java.desktop,java.logging,java.naming,java.security.jgss,java.instrument,java.management,java.sql,java.xml ^
      --output target\java-runtime ^
      --compress=2 ^
      --no-header-files ^
      --no-man-pages
if %errorlevel% neq 0 (
    echo ERRO: Falha na criacao do runtime Java
    pause
    exit /b 1
)

echo [5/5] Gerando executavel Windows (.exe)...
if exist target\dist rmdir /s /q target\dist
jpackage --input target ^
         --name "EstoqueMecanica" ^
         --main-jar EstoqueMecanica-1.0.jar ^
         --main-class com.estoque.App ^
         --runtime-image target\java-runtime ^
         --dest target\dist ^
         --type exe ^
         --app-version "1.0" ^
         --vendor "Oficina Mecanica" ^
         --description "Sistema de Gerenciamento de Estoque para Oficinas Mecanicas" ^
         --win-dir-chooser ^
         --win-menu ^
         --win-shortcut ^
         --win-menu-group "Oficina" ^
         --java-options "-Dfile.encoding=UTF-8"

if %errorlevel% neq 0 (
    echo ERRO: Falha na geracao do executavel
    pause
    exit /b 1
)

echo.
echo ========================================
echo           SUCESSO!
echo ========================================
echo.
echo Executavel gerado em: target\dist\EstoqueMecanica-1.0.exe
echo.
echo Caracteristicas do executavel:
echo - Tamanho: ~80-120 MB
echo - Independente: Nao precisa de Java instalado
echo - Instalador: Interface nativa do Windows
echo - Atalhos: Menu Iniciar e Desktop
echo.
echo Para distribuir: Copie o arquivo .exe
echo.
pause 