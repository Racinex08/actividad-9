import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

  class PasswordValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println("Ingrese una contraseña para validar (o 'exit' para salir):");
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("exit")) {
            executorService.execute(new PasswordValidatorThread(input));
            System.out.println("Ingrese otra contraseña para validar (o 'exit' para salir):");
            input = scanner.nextLine();
        }

        executorService.shutdown();
    }

    static class PasswordValidatorThread implements Runnable {
        private final String password;

        PasswordValidatorThread(String password) {
            this.password = password;
        }


        public void run() {
            if (validatePassword(password)) {
                System.out.println("La contraseña \"" + password + "\" es válida.");
            } else {
                System.out.println("La contraseña \"" + password + "\" no es válida.");
            }
        }

        private boolean validatePassword(String password) {
            // Longitud mínima de 8 caracteres
            if (password.length() < 8) {
                return false;
            }

            // Al menos un número
            if (!password.matches(".*\\d.*")) {
                return false;
            }

            // Al menos 2 letras mayúsculas
            if (password.replaceAll("[^A-Z]", "").length() < 2) {
                return false;
            }

            // Al menos 3 letras minúsculas
            if (password.replaceAll("[^a-z]", "").length() < 3) {
                return false;
            }

            // Al menos un caracter especial
            Pattern specialCharsPattern = Pattern.compile("[^a-zA-Z0-9]");
            Matcher matcher = specialCharsPattern.matcher(password);
            return matcher.find();
        }
    }
}