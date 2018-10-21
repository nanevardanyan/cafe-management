package am.nv.cafe.dataaccess.model.lcp;

public enum ProductInOrderStatus {

    ACTIVE(1, "Active"),
    CANCELED(2, "Canceled");

    ProductInOrderStatus(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public static ProductInOrderStatus valueOf(int value) {
        for (ProductInOrderStatus status : ProductInOrderStatus.values()) {
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
