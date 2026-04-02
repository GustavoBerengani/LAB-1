# Relatório - Fase 1 de Compiladores: Análise Léxica / Scanner

- **Faculdade:** PUC-SP
- **Curso:** Ciência da Computação (5º Período)
- **Matéria:** Compiladores
- **Equipe:** Gustavo Negrão de Souza Berengani Ramos e William da Silva Marques

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

### Implementação em Java (`ScannerApp.java`)
A mesma lógica estruturada do tokenizer foi aplicada em Java utilizando as classes `Pattern` e `Matcher` com a sintaxe de grupos nomeados `(?<nome>)`. Para cumprir o "Desafio Final" do laboratório, o código foi aprimorado para simular perfeitamente a saída do Analisador Léxico ilustrada na Figura 1.7 do "Livro do Dragão". Para isso, implementamos uma Tabela de Símbolos utilizando a estrutura `LinkedHashMap`, responsável por registrar novos identificadores e atribuir a eles um índice na memória. A saída gerada (`<id, 1><=><id, 2><+><id, 3><*><60>`) espelha com exatidão o comportamento de um compilador real na etapa de escaneamento. O código-fonte também se encontra na pasta da atividade.

```java
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerApp {
    public static void main(String[] args) {
        String codigo = "position = initial + rate * 60";
        // Gramática regular com grupos nomeados em Java
        String regex = "(?<ID>[a-zA-Z_][a-zA-Z0-9_]*)|(?<NUM>\\d+)|(?<OP>[=+\\-*])|(?<SKIP>\\s+)";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(codigo);
        
        // Tabela de Símbolos simulada para guardar os IDs e seus índices
        Map<String, Integer> tabelaSimbolos = new LinkedHashMap<>();
        int idCounter = 1;
        
        StringBuilder saidaDragonBook = new StringBuilder();
        
        // Loop de varredura do scanner
        while (matcher.find()) {
            if (matcher.group("SKIP") != null) continue; // Ignora espaços em branco
            
            if (matcher.group("ID") != null) {
                String lexema = matcher.group("ID");
                // Se o ID ainda não está na tabela de símbolos, registra com um novo número
                if (!tabelaSimbolos.containsKey(lexema)) {
                    tabelaSimbolos.put(lexema, idCounter++);
                }
                // Imprime no formato <id, índice> SEM espaço no final
                saidaDragonBook.append("<id, ").append(tabelaSimbolos.get(lexema)).append(">");
            } 
            else if (matcher.group("NUM") != null) {
                // Números são impressos diretamente entre <> SEM espaço no final
                saidaDragonBook.append("<").append(matcher.group("NUM")).append(">");
            } 
            else if (matcher.group("OP") != null) {
                // Operadores são impressos diretamente entre <> SEM espaço no final
                saidaDragonBook.append("<").append(matcher.group("OP")).append(">");
            }
        }
        
        System.out.println("Saída exata (Figura 1.7 do Dragon Book):");
        System.out.println(saidaDragonBook.toString());
        
        System.out.println("\nEstado final da Tabela de Símbolos:");
        for (Map.Entry<String, Integer> entry : tabelaSimbolos.entrySet()) {
            System.out.println("Índice " + entry.getValue() + " -> " + entry.getKey());
        }
    }
}
```

## Atividade 5 – Modelagem de Autômatos Finitos (JFLAP)

Nesta etapa final, utilizamos o software **JFLAP** para construir os modelos matemáticos (Autômatos Finitos) que dão suporte lógico ao nosso scanner. Em vez de uma máquina única e complexa, optamos por modelar os três pilares fundamentais da nossa gramática léxica de forma isolada, garantindo a especialização de cada reconhecedor.

Os arquivos fonte (`.jff`) de cada autômato estão disponíveis na pasta `Atividade05`.

### 1. Autômato para Identificadores (ID)
Responsável por reconhecer nomes de variáveis e funções seguindo o padrão `[a-zA-Z_][a-zA-Z0-9_]*`. 
* **Lógica:** Inicia obrigatoriamente com uma letra ou underscore (transição de `q0` para `q1`) e aceita qualquer combinação subsequente de alfanuméricos em um laço de repetição no estado final.
* **Tokens validados do exemplo:** `position`, `initial`, `rate`.

**Evidência:**
<img width="1455" height="961" alt="image" src="https://github.com/user-attachments/assets/1c4c3988-b327-493e-9323-905a8b897bae" />

### 2. Autômato para Números (NUM)
Focado no reconhecimento de constantes numéricas inteiras (`\d+`).
* **Lógica:** Requer ao menos um dígito para atingir o estado de aceitação, permitindo a leitura de múltiplos dígitos adicionais.
* **Token validado do exemplo:** `60`.

**Evidência:**
<img width="1457" height="923" alt="image" src="https://github.com/user-attachments/assets/7d3a6aa2-7961-492d-822a-5ad12590e492" />

### 3. Autômato para Operadores e Atribuição (OP)
Um reconhecedor de transição direta para os símbolos reservados da linguagem.
* **Lógica:** Ramificações simples que levam a estados de aceitação imediatos ao ler os caracteres mapeados.
* **Tokens validados do exemplo:** `=`, `+`, `*`.

**Evidência:**
<img width="1910" height="977" alt="image" src="https://github.com/user-attachments/assets/6b5743f8-3029-4507-a24c-923927902712" />


### Metodologia de Teste
Para validar a string completa do "Livro do Dragão" (*position = initial + rate * 60*), realizamos testes individuais de cada lexema em seus respectivos autômatos através da função **Input > Step with Closure** do JFLAP. 

Essa separação prova que o scanner é capaz de decompor a sentença complexa em unidades atômicas (tokens) e classificá-las corretamente antes de qualquer análise sintática, cumprindo o objetivo principal desta fase do compilador.

## Atividade 6 – OpenAI Tokenizer × Tokens de Compilador

Nesta atividade, comparamos a abordagem clássica de Análise Léxica com a tokenização estatística utilizada por Grandes Modelos de Linguagem (LLMs), utilizando a ferramenta [OpenAI Tokenizer](https://platform.openai.com/tokenizer).

### 1. Por que o tokenizer da OpenAI quebra `position` em `pos` + `ition`?

**Observação da Equipe:** Durante os testes, notamos que nos modelos mais recentes (família GPT-4o e o1), a palavra `position` já é mapeada como um token único. A quebra em `pos` + `ition` mencionada no roteiro ocorre ao rodar o texto nos modelos legados (GPT-3). 

<img width="846" height="598" alt="image" src="https://github.com/user-attachments/assets/d55adfd4-e469-4a4d-80c1-3802bdcac9ba" />

**Explicação do fenômeno:** Diferente de um compilador clássico, o tokenizer da OpenAI não conhece as regras da linguagem C ou Java. Ele utiliza um algoritmo de compressão de dados chamado **BPE (Byte Pair Encoding)**. O BPE constrói seus tokens baseando-se puramente na **frequência estatística** com que conjuntos de caracteres aparecem no seu banco de dados de treinamento. 

Nos modelos antigos, o algoritmo percebeu que o prefixo `pos` e o sufixo `ition` apareciam frequentemente em milhares de palavras diferentes, então ele otimizava o dicionário quebrando a palavra ao meio. Ele não se importa se `position` é uma variável válida no código; ele foca na forma mais eficiente de compactar o texto. Modelos mais novos possuem vocabulários muito maiores, o que permite armazenar a palavra inteira como um único token.

### 2. Diferença conceitual: Token Léxico vs. Token de LLM (BPE)

* **Token Léxico (Compiladores):** É **baseado em regras (Determinístico)**. Segue rigorosamente a gramática regular da linguagem. Cada token tem um significado estrutural para o compilador (ex: "Identificador", "Operador"). Se o texto violar a regra do Autômato Finito, gera erro.
* **Token de LLM (BPE):** É **baseado em dados (Estatístico/Probabilístico)**. Representa fragmentos de texto (sub-palavras ou letras) estatisticamente comuns na internet. Não possui categoria estrutural ou semântica para a etapa de tokenização; é apenas um fragmento mapeado para um número (ID) para que a Rede Neural processe probabilidades.

### 3. Discussão Final: A Necessidade de Precisão
**"Por que o scanner de compilador precisa ser preciso e seguir a gramática, enquanto o da OpenAI não?"**

Essa diferença reflete o **objetivo final** de cada sistema:
* **Compiladores exigem rigor absoluto:** O código-fonte será traduzido para instruções de máquina exatas. Não existe margem para ambiguidade; um caractere fora da regra quebra a lógica do software. O scanner é o primeiro validador, barrando tudo que não pertencer à gramática.
* **LLMs lidam com Linguagem Natural:** A comunicação humana (e *prompts*) é flexível, ambígua e sujeita a erros ortográficos ou neologismos. Se a OpenAI usasse um scanner rígido de compilador, a IA não conseguiria interpretar palavras não catalogadas. A tokenização por sub-palavras (BPE) confere flexibilidade ao modelo, permitindo que ele "deduza" partes de palavras desconhecidas sem gerar um erro fatal.

## Atividade 7 – Tokenizador de Linguagem Natural (Português)

Nesta atividade, expandimos o conceito de Análise Léxica para o Processamento de Linguagem Natural (PLN). O objetivo foi criar um scanner capaz de ler uma obra literária inteira e separar o texto em um vetor de tokens (palavras e pontuações isoladas), respeitando as particularidades da gramática da língua portuguesa.

A obra escolhida no Project Gutenberg foi **A Pata da Gazela**, de José de Alencar, salva localmente como o arquivo `a_pata_da_gazella.txt`.

### Expressão Regular Utilizada
Para capturar adequadamente o texto, desenvolvemos a seguinte regex otimizada:
`\w+(?:-\w+)*|[^\w\s]`

**Explicação da regra:**
1.  **`\w+(?:-\w+)*`**: Captura caracteres de palavra (letras e números). O grupo não-capturador permite que hífens seguidos de mais letras sejam mantidos no mesmo token, garantindo a integridade de palavras compostas ou com ênclise (ex: "sentou-se", "vê-lo", "guarda-chuva").
2.  **`|`**: Operador lógico OU.
3.  **`[^\w\s]`**: Captura qualquer caractere que NÃO seja uma palavra (`\w`) e NÃO seja um espaço em branco (`\s`). Isso isola efetivamente os sinais de pontuação (vírgulas, pontos, exclamações) como tokens individuais.

---

### Implementação em Python (`scanner.py`)
O script Python utiliza a biblioteca `re` com suporte nativo a Unicode. O uso do encoding `utf-8-sig` é fundamental para ignorar automaticamente o caractere BOM (Byte Order Mark) comum em arquivos do Project Gutenberg.

```python
import re

def tokenize(file_path):
    with open(file_path, 'r', encoding='utf-8-sig') as file:
        texto = file.read()
    
    # Expressão regular:
    # \w+(?:-\w+)* -> Captura palavras e palavras compostas por hífen
    # [^\w\s]      -> Captura qualquer caractere que não seja letra/número ou espaço (pontuação)
    padrao = r"\w+(?:-\w+)*|[^\w\s]"
    
    tokens = re.findall(padrao, texto)
    return tokens

# Exemplo de uso
lista_tokens = tokenize('a_pata_da_gazella.txt')
print(lista_tokens[:20]) # Exibe os primeiros 20 tokens
```

---

### Implementação em Java (`ScannerLivro.java`)
A versão em Java foi implementada para espelhar o comportamento do Python. Um detalhe técnico importante foi o uso da flag `Pattern.UNICODE_CHARACTER_CLASS`, sem a qual o Java não reconheceria caracteres acentuados (como 'ç' ou 'ã') dentro da classe `\w`, causando a quebra indevida de palavras em português.

```java
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Atividade 7 - Analisador Léxico (Scanner)
 * Este programa lê um arquivo de texto e o divide em tokens (palavras e pontuação)
 * seguindo a lógica de um compilador básico.
 */
public class ScannerLivro {

    public static void main(String[] args) {
        // Nome do arquivo baixado do Project Gutenberg
        String nomeArquivo = "a_pata_da_gazella.txt";

        try {
            List<String> tokens = tokenize(nomeArquivo);

            // Exibe os primeiros 20 tokens para validar o resultado
            System.out.println("Saída (vetor de strings):");
            System.out.println(tokens.subList(0, Math.min(tokens.size(), 20)));

            System.out.println("\nTotal de tokens processados: " + tokens.size());

        } catch (IOException e) {
            System.err.println("Erro: Certifique-se de que o arquivo '" + nomeArquivo + "' está na mesma pasta.");
        }
    }

    /**
     * Realiza a quebra do texto em tokens conforme a gramática
     */
    public static List<String> tokenize(String filePath) throws IOException {
        // Lê o conteúdo do arquivo em UTF-8
        String texto = Files.readString(Paths.get(filePath), StandardCharsets.UTF_8);

        // Remove o caractere invisível BOM (\ufeff) se existir
        if (texto.startsWith("\uFEFF")) {
            texto = texto.substring(1);
        }

        List<String> tokens = new ArrayList<>();

        /* * Regex explicada:
         * \\w+(?:-\\w+)* -> Palavras alfanuméricas e palavras com hífen (ex: "sentou-se")
         * |              -> OU
         * [^\\w\\s]      -> Qualquer caractere que não seja letra/número ou espaço (pontuação)
         */
        String padraoRegex = "\\w+(?:-\\w+)*|[^\\w\\s]";

        Pattern pattern = Pattern.compile(padraoRegex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(texto);

        // Percorre o texto encontrando os tokens
        while (matcher.find()) {
            tokens.add(matcher.group());
        }

        return tokens;
    }
}
```
