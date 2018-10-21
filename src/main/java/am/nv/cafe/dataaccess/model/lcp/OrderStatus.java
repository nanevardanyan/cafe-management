package am.nv.cafe.dataaccess.model.lcp;

public enum OrderStatus {

    OPEN(1, "Open"),
    CLOSED(2, "Closed"),
    CANCELED(3, "Canceled");

    OrderStatus(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static OrderStatus valueOf(int value) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.value == value) {
                return status;
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
