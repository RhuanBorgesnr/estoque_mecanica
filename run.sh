#!/bin/bash

echo "=== Sistema de Gerenciamento de Estoque - Mecânica ==="
echo "Iniciando aplicação..."

# Verifica se o Java está instalado
if ! command -v java &> /dev/null; then
    echo "❌ Java não encontrado. Por favor, instale Java 17 ou superior."
    exit 1
fi

# Verifica se o Maven está instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven não encontrado. Por favor, instale Maven 3.6 ou superior."
    exit 1
fi

# Compila o projeto se necessário
if [ ! -d "target" ]; then
    echo "📦 Compilando projeto..."
    mvn clean compile
fi

# Executa a aplicação
echo "🚀 Executando aplicação..."
mvn javafx:run

echo "✅ Aplicação finalizada." 