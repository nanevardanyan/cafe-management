package am.nv.cafe.dataaccess.model.lcp;

public enum UserProfile {

    MANAGER(1, "Manager"),
    WAITER(2, "Waiter");

    UserProfile(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static UserProfile valueOf(int value) {
        for (UserProfile profile : UserProfile.values()) {
            if (profile.value == value) {
                return profile;
            }
        }
        return null;
    }

    private final int value;
    private final String label;

    public int getValue() {
        return value;
    }

    public String getLabelKey() {
        return label;
    }
}
