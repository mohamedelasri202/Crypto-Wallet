package Utilitaire;

public enum Priority {
    ECONOMIQUE("Economique"),
    STANDARD("Standard"),
    RAPIDE("Rapide");

    private final String displayName;

    Priority(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
