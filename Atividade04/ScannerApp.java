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