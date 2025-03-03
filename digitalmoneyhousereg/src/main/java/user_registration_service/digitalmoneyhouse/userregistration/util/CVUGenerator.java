package user_registration_service.digitalmoneyhouse.userregistration.util;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CVUGenerator {

    public String generateCVU() {
        Random random = new Random();
        StringBuilder cvu = new StringBuilder();
        for (int i = 0; i < 22; i++) {
            cvu.append(random.nextInt(10)); // Genera un número aleatorio del 0 al 9
        }
        return cvu.toString();
    }
}

