#!/usr/bin/env bash
#
# AI LLM aid Bash script - X Grok
# C lang Scanner - Analisador Léxico
# @danielscarvalho
# c_scanner.sh
# Very basic C tokenizer (bash implementation)
# Usage: ./s_canner.sh program.c

if [ $# -ne 1 ]; then
    echo "Usage: $0 <source_file.c>"
    exit 1
fi

if [ ! -f "$1" ]; then
    echo "Error: File '$1' not found"
    exit 1
fi

file="$1"

# Remove // comments, /* */ comments and strings in a very crude way
# (this is NOT perfect, but good enough for simple examples)
cleaned=$(sed '
    # Remove // comments
    s|//.*$||g
    
    # Remove /* ... */ comments (very naive - single line only)
    s|/\*.*\*/||g
    
    # Replace string literals with placeholder (to avoid splitting them)
    s|"[^"\\]*\\.[^"\\]*"|"STRING"|g
    s|"[^"]*"|"STRING"|g
' "$file")

# Now tokenize - this is the crude but illustrative part
echo "$cleaned" | tr -s ' \t\n\r' '\n' | while IFS= read -r line; do
    # skip empty lines
    [[ -z "$line" ]] && continue
    
    # split on operators and punctuation but keep them
    # We use a pattern with lookaround-like behavior via sed + tr
    echo "$line" | sed 's/\([][(){};,+\-*/%=!<>&|^~?:]\)/ \1 /g' | tr -s ' ' '\n'
done | grep -v '^$' | while IFS= read -r token; do
    # Remove the STRING placeholder if you want real strings (optional)
    if [[ "$token" = "STRING" ]]; then
        echo "<string literal>"
    else
        # Try to classify token roughly
        case "$token" in
            "auto"|"break"|"case"|"char"|"const"|"continue"|"default"|"do"|"double"|"else"|"enum"|"extern"|"float"|"for"|"goto"|"if"|"int"|"long"|"register"|"return"|"short"|"signed"|"sizeof"|"static"|"struct"|"switch"|"typedef"|"union"|"unsigned"|"void"|"volatile"|"while")
                echo "KEYWORD     $token"
                ;;
            "#include"|"#define"|"#ifdef"|"#ifndef"|"#endif"|"#else"|"#elif"|"#pragma"|"#undef")
                echo "PREPROC     $token"
                ;;
            "+"|"-"|"*"|"/"|"%"|"="|"=="|"!="|"<"|">"|"<="|">="|"&&"|"||"|"!"|"~"|"|"|"^"|"&"|"<<"|">>"|"+="|"-="|"*="|"/="|"%="|"<<="|">>="|"&="|"^="|"|=")
                echo "OPERATOR    $token"
                ;;
            "("|")"|"["|"]"|"{"|"}"|";"|","|":"|"?"|"...")
                echo "PUNCTUATION $token"
                ;;
            [0-9]*)
                echo "NUMBER      $token"
                ;;
            0[xX][0-9a-fA-F]+)
                echo "HEX_NUMBER  $token"
                ;;
            [a-zA-Z_][a-zA-Z0-9_]*)
                echo "IDENTIFIER  $token"
                ;;
            *)
                echo "TOKEN       $token"
                ;;
        esac
    fi
done
