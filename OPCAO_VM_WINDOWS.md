# 🖥️ Opção: Máquina Virtual Windows

## 🎯 Para Quem Não Tem Windows

Se você usa Mac/Linux mas quer gerar o .exe localmente.

## 🚀 Configuração Rápida

### 1. VirtualBox (Gratuito)
```bash
# 1. Baixar VirtualBox
https://www.virtualbox.org/

# 2. Baixar Windows 10/11 ISO
https://www.microsoft.com/software-download/windows11

# 3. Criar VM com:
- RAM: 4GB mínimo
- HD: 50GB mínimo
- Processador: 2 cores
```

### 2. Instalar no Windows da VM
```cmd
# 1. Java JDK 17
https://adoptium.net/

# 2. Maven
https://maven.apache.org/download.cgi

# 3. Git (para clonar projeto)
https://git-scm.com/download/win
```

### 3. Gerar o .exe na VM
```cmd
# Clonar projeto
git clone https://github.com/SEU_USUARIO/EstoqueMecanica.git
cd EstoqueMecanica

# Gerar executável
build-windows.bat
```

## ⚡ Alternativas Mais Rápidas

### AWS/Azure Windows VM
- **Vantagem**: Mais rápido que VM local
- **Custo**: ~$0.50/hora
- **Tempo**: 30 minutos total

### GitHub Codespaces
- **Vantagem**: Ambiente pronto
- **Limitação**: Não suporta jpackage Windows

## 🎯 Recomendação

**Para uso único**: GitHub Actions (gratuito, automático)
**Para desenvolvimento contínuo**: VM local ou cloud

---

**Tempo**: 2-3 horas configuração inicial 