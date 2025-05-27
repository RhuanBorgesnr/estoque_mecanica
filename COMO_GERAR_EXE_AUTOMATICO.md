# 🤖 Gerar .exe Automaticamente com GitHub Actions

## 🎯 Vantagem: Não Precisa de Windows!

O GitHub Actions pode gerar o .exe automaticamente em ambiente Windows na nuvem.

## 🚀 Como Usar (5 minutos)

### 1. Fazer Fork do Projeto
1. Vá para: https://github.com/SEU_USUARIO/EstoqueMecanica
2. Clique em "Fork"
3. Agora você tem uma cópia do projeto

### 2. Ativar GitHub Actions
1. No seu fork, vá em "Actions"
2. Clique "I understand my workflows, enable them"
3. O workflow já está configurado!

### 3. Disparar a Geração
**Opção A - Push qualquer mudança:**
```bash
git add .
git commit -m "Gerar executável"
git push
```

**Opção B - Executar manualmente:**
1. Vá em "Actions" → "Build Windows Executable"
2. Clique "Run workflow"
3. Clique "Run workflow" novamente

### 4. Baixar o .exe
1. Aguarde ~10 minutos (build automático)
2. Vá em "Actions" → último workflow
3. Baixe "EstoqueMecanica-Windows-Executable"
4. ✅ Arquivo .exe pronto!

## 📦 O que o GitHub Actions Faz

```yaml
# Ambiente Windows na nuvem
runs-on: windows-latest

# Instala Java 17 automaticamente
- uses: actions/setup-java@v4
  with:
    java-version: '17'

# Compila o projeto
- run: mvn clean compile

# Gera o JAR
- run: mvn package

# Cria runtime Java customizado
- run: jlink --module-path "$env:JAVA_HOME\jmods" ...

# Gera o executável .exe
- run: jpackage --input target --name "EstoqueMecanica" ...
```

## ✅ Resultado Final

- **Arquivo**: `EstoqueMecanica-1.0.exe`
- **Tamanho**: ~100 MB
- **Características**:
  - ✅ Instalador Windows profissional
  - ✅ Java incluído (cliente não precisa instalar)
  - ✅ Atalhos automáticos (Desktop + Menu Iniciar)
  - ✅ Desinstalação via Painel de Controle
  - ✅ Interface nativa Windows

## 🔄 Para Atualizações Futuras

1. Faça mudanças no código
2. Push para GitHub
3. Novo .exe gerado automaticamente
4. Distribua a nova versão

---

**Tempo total**: 5 minutos de configuração + 10 minutos de build automático 