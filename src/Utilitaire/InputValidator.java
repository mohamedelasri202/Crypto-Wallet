package Utilitaire;

public class InputValidator {

    private static final String BASE58_CHARS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    public static boolean isValidAddress(String address) {
        if (address == null || address.isEmpty()) {
            return false;
        }


        if (address.startsWith("0x")) {
            return address.matches("0x[0-9a-fA-F]{40}");
        }


        if (address.startsWith("1")) {
            return address.matches("1[" + BASE58_CHARS + "]{40}");
        }


        if (address.startsWith("3")) {
            return address.matches("3[" + BASE58_CHARS + "]{40}");
        }


        if (address.startsWith("bc1")) {
            return address.matches("bc1[" + BASE58_CHARS + "]{39}");
        }


        return false;
    }
}
