package am.nv.cafe.dataaccess.model;

import am.nv.cafe.dataaccess.converter.OrderStatusConverter;
import am.nv.cafe.dataaccess.model.lcp.OrderStatus;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "client_order")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column
    private LocalDateTime date;

    @Column(name = "status_id", nullable = false, length = 1)
    @Convert(converter = OrderStatusConverter.class)
    private OrderStatus status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "table_id")
    private CafeTable table;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public CafeTable getTable() {
        return table;
    }

    public void setTable(CafeTable table) {
        this.table = table;
    }
}