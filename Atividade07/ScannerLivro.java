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