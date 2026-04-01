# Relatório - Fase 1 de Compiladores: Análise Léxica / Scanner

**Faculdade:** PUC-SP
**Curso:** Ciência da Computação (5º Período)
**Disciplina:** Compiladores
**Equipe:** Gustavo Negrão, William da Silva Marques
---

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
   * Antes:*
    <img width="1736" height="965" alt="ReplaceComentáriosEmC" src="https://github.com/user-attachments/assets/4d6a1831-3e8c-45fc-8e36-6d8bf38f151a" />
    * Depois:*
    <img width="1739" height="917" alt="AposRemoveCometáriosEmC" src="https://github.com/user-attachments/assets/2aaa1d8a-0f6c-46f3-866d-ec04e373a7f5" />

2. **Substituir `=` por `:=` (simulando mudança de linguagem):**
   * **Find:** `(?<![<>=!])=(?![=])`
   * **Replace:** `:=`
   * *Ação:* Usa *lookarounds* para garantir que apenas o operador de atribuição `=` seja alterado, ignorando operadores relacionais como `==`, `>=`, `<=`, ou `!=`.
   * Antes:*
     <img width="1721" height="948" alt="SubstituicaoPreIgual" src="https://github.com/user-attachments/assets/04351fce-ee0c-45ee-a473-64edad342a80" />
   * Depois:*
     <img width="1725" height="881" alt="SubstituicaoPosDoisPontosIgual" src="https://github.com/user-attachments/assets/3c55c0a0-155a-4fd6-88b1-97df553db155" />

     
