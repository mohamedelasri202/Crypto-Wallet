package Utilitaire;

public enum Priority {
    ECONOMICAL("ECONOMICAL"),
    STANDARD("STANDARD"),
    FAST("FAST");

    private final String displayName;

    Priority(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
