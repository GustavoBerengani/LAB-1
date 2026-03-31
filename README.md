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
