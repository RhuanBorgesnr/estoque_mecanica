# ⚡ Instruções Rápidas - Executável Windows

## 🎯 Objetivo
Gerar `EstoqueMecanica-1.0.exe` para distribuição em Windows.

## 📋 Pré-requisitos (5 minutos)
1. **Java JDK 17+**: https://adoptium.net/
2. **Maven**: https://maven.apache.org/download.cgi
3. **Windows 10/11**

## 🚀 Gerar Executável (2 comandos)

### Método 1 - Script Automático
```cmd
build-windows.bat
```

### Método 2 - Manual
```cmd
mvn clean package
build-windows.bat
```

## 📦 Resultado
- **Arquivo**: `target\dist\EstoqueMecanica-1.0.exe`
- **Tamanho**: ~100 MB
- **Tipo**: Instalador Windows

## ✅ Pronto para Distribuir
- ✅ Não precisa Java instalado
- ✅ Interface nativa Windows
- ✅ Atalhos automáticos
- ✅ Banco SQLite incluído

## 🔧 Se der erro:
```cmd
java -version
mvn -version
jpackage --version
```

Todos devem funcionar. Se não:
- Reinstale JDK 17+
- Adicione ao PATH
- Reinicie terminal

## 🌐 Alternativa: GitHub Actions
1. Fork este projeto no GitHub
2. Push para seu repositório
3. Baixe o .exe gerado automaticamente

---
**Tempo total**: ~10 minutos 