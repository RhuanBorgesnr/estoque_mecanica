# Script PowerShell para gerar executável Windows
# Sistema de Estoque para Mecânica

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   GERADOR DE EXECUTÁVEL WINDOWS" -ForegroundColor Cyan
Write-Host "   Sistema de Estoque para Mecânica" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Função para verificar se comando existe
function Test-Command($cmdname) {
    return [bool](Get-Command -Name $cmdname -ErrorAction SilentlyContinue)
}

# Verificar Java
Write-Host "[VERIFICAÇÃO] Verificando Java..." -ForegroundColor Yellow
if (-not (Test-Command "java")) {
    Write-Host "ERRO: Java não encontrado. Instale Java 17 ou superior." -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

$javaVersion = java -version 2>&1 | Select-String "version" | ForEach-Object { $_.ToString() }
Write-Host "Java encontrado: $javaVersion" -ForegroundColor Green

# Verificar Maven
if (-not (Test-Command "mvn")) {
    Write-Host "ERRO: Maven não encontrado. Instale Apache Maven." -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

# Verificar jpackage
if (-not (Test-Command "jpackage")) {
    Write-Host "ERRO: jpackage não encontrado. Use Java 17+ com JDK completo." -ForegroundColor Red
    Read-Host "Pressione Enter para sair"
    exit 1
}

try {
    # Passo 1: Limpeza
    Write-Host "[1/5] Limpando projeto..." -ForegroundColor Yellow
    mvn clean
    if ($LASTEXITCODE -ne 0) { throw "Falha na limpeza do projeto" }

    # Passo 2: Compilação
    Write-Host "[2/5] Compilando projeto..." -ForegroundColor Yellow
    mvn compile
    if ($LASTEXITCODE -ne 0) { throw "Falha na compilação" }

    # Passo 3: Empacotamento
    Write-Host "[3/5] Gerando JAR com dependências..." -ForegroundColor Yellow
    mvn package
    if ($LASTEXITCODE -ne 0) { throw "Falha na geração do JAR" }

    # Passo 4: Runtime Java
    Write-Host "[4/5] Criando runtime Java customizado..." -ForegroundColor Yellow
    if (Test-Path "target\java-runtime") {
        Remove-Item "target\java-runtime" -Recurse -Force
    }
    
    $jlinkCmd = @(
        "jlink"
        "--module-path", "`"$env:JAVA_HOME\jmods`""
        "--add-modules", "java.base,java.desktop,java.logging,java.naming,java.security.jgss,java.instrument,java.management,java.sql,java.xml"
        "--output", "target\java-runtime"
        "--compress=2"
        "--no-header-files"
        "--no-man-pages"
    )
    
    & $jlinkCmd[0] $jlinkCmd[1..($jlinkCmd.Length-1)]
    if ($LASTEXITCODE -ne 0) { throw "Falha na criação do runtime Java" }

    # Passo 5: Executável
    Write-Host "[5/5] Gerando executável Windows (.exe)..." -ForegroundColor Yellow
    if (Test-Path "target\dist") {
        Remove-Item "target\dist" -Recurse -Force
    }
    
    $jpackageCmd = @(
        "jpackage"
        "--input", "target"
        "--name", "EstoqueMecanica"
        "--main-jar", "EstoqueMecanica-1.0.jar"
        "--main-class", "com.estoque.App"
        "--runtime-image", "target\java-runtime"
        "--dest", "target\dist"
        "--type", "exe"
        "--app-version", "1.0"
        "--vendor", "Oficina Mecânica"
        "--description", "Sistema de Gerenciamento de Estoque para Oficinas Mecânicas"
        "--win-dir-chooser"
        "--win-menu"
        "--win-shortcut"
        "--win-menu-group", "Oficina"
        "--java-options", "-Dfile.encoding=UTF-8"
    )
    
    & $jpackageCmd[0] $jpackageCmd[1..($jpackageCmd.Length-1)]
    if ($LASTEXITCODE -ne 0) { throw "Falha na geração do executável" }

    # Sucesso
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "           SUCESSO!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    
    $exePath = "target\dist\EstoqueMecanica-1.0.exe"
    if (Test-Path $exePath) {
        $fileSize = [math]::Round((Get-Item $exePath).Length / 1MB, 1)
        Write-Host "Executável gerado: $exePath" -ForegroundColor Green
        Write-Host "Tamanho: $fileSize MB" -ForegroundColor Green
    }
    
    Write-Host ""
    Write-Host "Características do executável:" -ForegroundColor Cyan
    Write-Host "- Independente: Não precisa de Java instalado" -ForegroundColor White
    Write-Host "- Instalador: Interface nativa do Windows" -ForegroundColor White
    Write-Host "- Atalhos: Menu Iniciar e Desktop" -ForegroundColor White
    Write-Host "- Banco SQLite: Incluído e configurado" -ForegroundColor White
    Write-Host ""
    Write-Host "Para distribuir: Copie o arquivo .exe" -ForegroundColor Yellow

} catch {
    Write-Host ""
    Write-Host "ERRO: $_" -ForegroundColor Red
    Write-Host ""
} finally {
    Read-Host "Pressione Enter para sair"
} 