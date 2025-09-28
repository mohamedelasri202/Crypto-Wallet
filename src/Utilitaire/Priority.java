package Utilitaire;

public enum Priority {
    ECONOMICAL("ECONOMICAL"),
    STANDARD("STANDARD"),
    RAPIDE("FAST");

    private final String displayName;

    Priority(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
