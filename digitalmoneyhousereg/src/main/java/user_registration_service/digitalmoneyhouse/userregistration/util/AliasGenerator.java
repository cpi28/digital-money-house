package user_registration_service.digitalmoneyhouse.userregistration.util;


import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@Component
public class AliasGenerator {

    private static final String ALIAS_FILE_PATH = "src/main/resources/aliases.txt";
    private final List<String> words;
    private final Random random = new Random();

    public AliasGenerator() throws IOException {
        this.words = Files.readAllLines(Paths.get(ALIAS_FILE_PATH));
    }

    public String generateAlias() {
        if (words.size() < 3) throw new IllegalStateException("El archivo de alias debe contener al menos 3 palabras.");
        return words.get(random.nextInt(words.size())) + "." +
                words.get(random.nextInt(words.size())) + "." +
                words.get(random.nextInt(words.size()));
    }
}

