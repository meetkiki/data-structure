package entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 有向图的边--旅行
 */
public class DirectedTrip {
    /**
     * 起点
     */
    private Town from;
    /**
     * 终点
     */
    private Town to;
    /**
     * 旅行的距离
     */
    private BigDecimal distance;

    public DirectedTrip() {}

    public DirectedTrip(Town from, Town to, BigDecimal distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Town getFrom() {
        return from;
    }

    public void setFrom(Town from) {
        this.from = from;
    }

    public Town getTo() {
        return to;
    }

    public void setTo(Town to) {
        this.to = to;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DirectedTrip that = (DirectedTrip) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(distance, that.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, distance);
    }

    @Override
    public String toString() {
        return String.format(" %s -> %s %.2f", from , to ,distance == null ? 0.0 : distance.doubleValue());
    }
}
