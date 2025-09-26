package Utilitaire;

public class InputValidator {


    public static boolean isValidAddress(String address) {
        if (address == null || address.isEmpty()) {
            return false;
        }


        if (address.startsWith("0x")) {
            return address.matches("0x[0-9a-fA-F]{40}");
        }


        if (address.startsWith("1")) {
            return address.matches("1[" +
                    "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{25,33}");
        }

        if (address.startsWith("3")) {
            return address.matches("3[" +
                    "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz]{25,33}");
        }


        if (address.startsWith("bc1")) {
            return address.matches("bc1[qpzry9x8gf2tvdw0s3jn54khce6mua7l]{39,59}");
        }


        return false;
    }

}


