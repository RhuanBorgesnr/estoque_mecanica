# 🎯 RESUMO: Como Gerar EstoqueMecanica.exe

## ✅ Você Tem Windows?

### 🚀 SIM - Execute Agora (2 minutos)
```cmd
# Opção 1: Script automático
build-windows.bat

# Opção 2: PowerShell
.\build-windows.ps1
```

**Pré-requisitos:**
- Java JDK 17+: https://adoptium.net/
- Maven: https://maven.apache.org/download.cgi

---

### 🌐 NÃO - Use GitHub Actions (5 minutos)

1. **Fork** este projeto no GitHub
2. Vá em **Actions** → **Build Windows Executable**
3. Clique **Run workflow**
4. Aguarde 10 minutos
5. **Baixe** o .exe gerado

**Vantagem**: Totalmente automático, sem instalar nada!

---

## 📦 Resultado Final

### 🎯 Características do .exe
- **Tamanho**: ~100 MB
- **Instalador**: Interface nativa Windows
- **Java**: Incluído (cliente não precisa instalar)
- **Atalhos**: Desktop + Menu Iniciar automáticos
- **Desinstalação**: Via Painel de Controle
- **Atualizações**: Fáceis no futuro

### 🏢 Experiência Profissional
- ✅ Instalação com wizard Windows
- ✅ Escolha do diretório de instalação
- ✅ Criação automática de atalhos
- ✅ Registro no sistema Windows
- ✅ Desinstalação limpa

## 🔄 Para Distribuição

1. **Gere** o .exe usando uma das opções acima
2. **Teste** em máquina Windows limpa
3. **Distribua** o arquivo único .exe
4. **Cliente** executa e instala normalmente

## ⚡ Recomendação Rápida

**Tem Windows?** → `build-windows.bat`
**Não tem Windows?** → GitHub Actions
**Desenvolvimento contínuo?** → VM Windows

---

**🎯 Objetivo alcançado**: Executável .exe profissional com jpackage! 