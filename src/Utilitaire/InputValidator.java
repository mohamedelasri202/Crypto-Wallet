package Utilitaire;

public class InputValidator {

    private static final String BASE58_CHARS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";

    public static boolean isValidAddress(String address) {
        if (address == null || address.isEmpty()) {
            return false;
        }

        // Ethereum (0x...)
        if (address.startsWith("0x")) {
            return address.matches("0x[0-9a-fA-F]{40}");
        }

        // Bitcoin Legacy (1...)
        if (address.startsWith("1")) {
            return address.matches("1[" + BASE58_CHARS + "]{40}");
        }

        // Bitcoin P2SH (3...)
        if (address.startsWith("3")) {
            return address.matches("3[" + BASE58_CHARS + "]{40}");
        }

        // Bitcoin bc1 (your generator: 39 BASE58 chars after bc1)
        if (address.startsWith("bc1")) {
            return address.matches("bc1[" + BASE58_CHARS + "]{39}");
        }

        // Unknown format
        return false;
    }
}
