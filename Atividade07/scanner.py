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