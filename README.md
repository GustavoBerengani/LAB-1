# Relatório - Fase 1 de Compiladores: Análise Léxica / Scanner

- **Faculdade:** PUC-SP
- **Curso:** Ciência da Computação (5º Período)
- **Disciplina:** Compiladores
- **Equipe:** Gustavo Negrão, William da Silva Marques

**Nota:** relatório completo do LAB-1 está neste README.md, entretanto, no repositório estão divididas pastas para cada atividade contendo os arquivos que foram utilizados em cada uma delas.
  
## Atividade 1 – Bash no Terminal Ubuntu
Criamos o script `scanner_simples.sh` para simular o processamento de um *character stream*.

**Código do Script (`scanner_simples.sh`):**
```bash
#!/bin/bash
while true; do
    read -r linha
    # tr -d remove os espaços em branco, tabs e carriage returns
    linha_limpa=$(echo "$linha" | tr -d ' \t\r')
    
    # Só imprime se a linha não estiver vazia após a limpeza
    if [ -n "$linha_limpa" ]; then
        echo "[SCANNER] Linha processada: '$linha_limpa'"
    fi
done
```

## Atividade 2 – Expressões Regulares online (regexr + regex101)

**Evidência:**

<img width="1561" height="879" alt="image" src="https://github.com/user-attachments/assets/024a43bc-4c33-4666-8d1e-2148c41a12c8" />

<img width="1428" height="284" alt="image" src="https://github.com/user-attachments/assets/0135b6c4-4d18-4c5f-89bb-3eebdbc8d676" />

**Explicação:**
As expressões regulares funcionam como as regras de transição de um autômato finito dentro do scanner. Ao definirmos esses padrões, criamos um reconhecedor que varre o fluxo de caracteres e isola cada lexema (identificadores, números ou operadores). Esse processo permite que o analisador léxico ignore espaços em branco e agrupe os caracteres em tokens válidos para as próximas etapas da compilação.

## Atividade 3 – Find/Replace com regex em editores de texto

Nesta etapa, utilizamos o modo Regex do MS VS Code (ativado pelo ícone `.*` na ferramenta de busca e substituição) para processar dados em massa. Isso demonstra a aplicação real das expressões regulares fora do ambiente de compiladores.

**Exercícios Práticos Realizados:**

1. **Remover todos os comentários C (`//` e `/* */`):**
   * **Find:** `\/\/.*|\/\*[\s\S]*?\*\/`
   * **Replace:** *(deixar em branco)*
   * *Ação:* Remove tanto os comentários de linha única quanto os blocos de múltiplas linhas.
   * Antes: 
    <img width="1736" height="965" alt="ReplaceComentáriosEmC" src="https://github.com/user-attachments/assets/4d6a1831-3e8c-45fc-8e36-6d8bf38f151a" />
    
    * Depois:
    <img width="1739" height="917" alt="AposRemoveCometáriosEmC" src="https://github.com/user-attachments/assets/2aaa1d8a-0f6c-46f3-866d-ec04e373a7f5" />

2. **Substituir `=` por `:=` (simulando mudança de linguagem):**
   * **Find:** `(?<![<>=!])=(?![=])`
   * **Replace:** `:=`
   * *Ação:* Usa *lookarounds* para garantir que apenas o operador de atribuição `=` seja alterado, ignorando operadores relacionais como `==`, `>=`, `<=`, ou `!=`.
   * Antes:
     <img width="1721" height="948" alt="SubstituicaoPreIgual" src="https://github.com/user-attachments/assets/04351fce-ee0c-45ee-a473-64edad342a80" />
     
   * Depois:
     <img width="1725" height="881" alt="SubstituicaoPosDoisPontosIgual" src="https://github.com/user-attachments/assets/3c55c0a0-155a-4fd6-88b1-97df553db155" />
     
3. **Limpar espaços extras em um arquivo CSV (mais de 10 mil linhas):**
   * **Find:** `[ \t]+`
   * **Replace:** ` ` *(um único espaço)*
   * *Ação:* Localiza sequências de múltiplos espaços ou tabs e as reduz a um espaço simples, higienizando os dados.
   * Evidência:
     <img width="1126" height="935" alt="image" src="https://github.com/user-attachments/assets/19888447-70cf-489a-af5d-f61c06add17b" />


4. **Tornar um arquivo CSV em um TSV (Tab-Separated Values):**
   * **Find:** `,`
   * **Replace:** `\t`
   * *Ação:* Troca todas as vírgulas por espaçamentos de tabulação.
   * Evidência (pós troca):
     <img width="1107" height="917" alt="image" src="https://github.com/user-attachments/assets/3977c4a5-9316-48be-9825-40a38c4833d6" />

5. **Converter CSV com dados em português para inglês:**
   *Para evitar que a troca de separadores destrua as casas decimais, o processo foi dividido em duas etapas:*
   * **Passo A (Casas Decimais):** * **Find:** `(\d),(\d)`
     * **Replace:** `$1.$2`
     * Evidência (pós troca):
       <img width="1127" height="938" alt="image" src="https://github.com/user-attachments/assets/501abb45-73d0-4f70-86ed-5960f6cc3aa9" />

   * **Passo B (Separadores de Coluna):**
     * **Find:** `,`
     * **Replace:** `;`
     * Evidência (pós troca):
       <img width="1134" height="976" alt="image" src="https://github.com/user-attachments/assets/2f4281db-c71d-4874-8c9f-abe114a2ae93" />

**Conexão com a Teoria:**
O scanner faz exatamente isso em tempo de compilação. Ele lê o fluxo de caracteres de entrada, identifica padrões estruturais através de expressões regulares (como os comentários e operadores) e os descarta ou substitui pelas categorias de tokens apropriadas antes de enviar a estrutura limpa para o analisador sintático.

## Atividade 4 – RegExp em Python e Java

Nesta atividade, implementamos um mini-scanner utilizando as bibliotecas nativas de expressões regulares em Python e Java. O objetivo foi criar um autômato finito em código que retorna os tokens, simulando a tokenização estruturada do compilador.

### Implementação em Python (Google Colab)
O laboratório foi desenvolvido em etapas evolutivas dentro do Google Colab (o arquivo `ScannerAtividade04.ipynb` completo está disponível na pasta desta atividade no repositório).

**Passo 1: Automato Finito Básico (`re.findall`)**
Testamos a extração simples em uma lista de strings.

```python
import re

codigo = "position = initial + rate * 60"
regexp = r'[a-zA-Z_][a-zA-Z0-9_]*|\d+|[=+\-*]' # Definindo o Automato Finito

tokens = re.findall(regexp, codigo) # O Automato ganha vida
print(tokens)
# Saída: ['position', '=', 'initial', '+', 'rate', '*', '60']
```

**Passo 2: Extração de E-mails com `requests`**
Utilizamos a biblioteca `requests` para ler o HTML de uma página web real e aplicamos uma expressão regular para capturar e isolar os endereços de e-mail encontrados.

```python
import re
import requests

# Lendo o conteúdo texto de uma página web
url = "[https://www.pucsp.br/contato](https://www.pucsp.br/contato)"
resposta = requests.get(url)
texto_pagina = resposta.text

# Regex para capturar e-mails
regex_email = r'[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}'

emails_encontrados = re.findall(regex_email, texto_pagina)
# Removendo duplicatas convertendo para set e voltando para lista
emails_unicos = list(set(emails_encontrados))

print(f"E-mails encontrados: {emails_unicos}")
```

**Passo 3: Evolução para a função `tokenize(texto)`**
Evoluímos o scanner para retornar uma lista estruturada de tuplas `(tipo, lexema)`, utilizando grupos nomeados na Regex.

```python
import re

def tokenize(texto):
    # Gramática regular com grupos nomeados para classificar o token
    regexp = r'(?P<ID>[a-zA-Z_][a-zA-Z0-9_]*)|(?P<NUM>\d+)|(?P<OP>[=+\-*])|(?P<SKIP>\s+)'
    
    tokens = []
    for match in re.finditer(regexp, texto):
        tipo = match.lastgroup
        lexema = match.group()
        
        if tipo != 'SKIP': # Ignora espaços em branco
            tokens.append((tipo, lexema))
            
    return tokens

codigo = "position = initial + rate * 60"
print(tokenize(codigo))
# Saída: [('ID', 'position'), ('OP', '='), ('ID', 'initial'), ('OP', '+'), ('ID', 'rate'), ('OP', '*'), ('NUM', '60')]
```

---

### Implementação em Java (`ScannerApp.java`)
A mesma lógica estruturada do tokenizer foi aplicada em Java utilizando as classes `Pattern` e `Matcher`. Os grupos nomeados em Java usam a sintaxe `(?<nome>)` e as barras invertidas precisam ser escapadas nas strings (`\\`). O código-fonte também se encontra na pasta da atividade.

```java
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerApp {
    public static void main(String[] args) {
        String codigo = "position = initial + rate * 60";
        // Gramática regular com grupos nomeados em Java
        String regex = "(?<ID>[a-zA-Z_][a-zA-Z0-9_]*)|(?<NUM>\\d+)|(?<OP>[=+\\-*])|(?<SKIP>\\s+)";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(codigo);
        
        List<String> tokens = new ArrayList<>();
        
        // Loop de varredura do scanner
        while (matcher.find()) {
            if (matcher.group("SKIP") != null) continue; // Ignora os espaços em branco
            
            // Classifica e adiciona à lista no formato de tupla (String)
            if (matcher.group("ID") != null) {
                tokens.add("('ID', '" + matcher.group("ID") + "')");
            } else if (matcher.group("NUM") != null) {
                tokens.add("('NUM', '" + matcher.group("NUM") + "')");
            } else if (matcher.group("OP") != null) {
                tokens.add("('OP', '" + matcher.group("OP") + "')");
            }
        }
        
        System.out.println(tokens);
    }
}
```

     
