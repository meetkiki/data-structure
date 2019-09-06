package entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * 有向图的边 --> 旅行
 * @author tao
 */
public class DirectedTrip implements Comparable<DirectedTrip> {
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

    public DirectedTrip(){}

    public DirectedTrip(Town from, Town to, BigDecimal distance) {
        this();
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
        return from.equals(that.from) &&
                to.equals(that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return String.format(" %s -> %s %.2f ", from , to ,distance == null ? 0.0 : distance.doubleValue());
    }
    
    @Override
    public int compareTo(DirectedTrip o) {
        Objects.requireNonNull(this.distance,String.format(" this.distance %s Can not be null !",this));
        Objects.requireNonNull(o.distance,String.format(" to.distance %s Can not be null !",o));
        return Objects.compare(this.distance,o.distance,BigDecimal::compareTo);
    }

    public static DirectedTrip.DirectedTripBuilder builder() {
        return new DirectedTrip.DirectedTripBuilder();
    }

    public static final class DirectedTripBuilder{
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

        public DirectedTripBuilder withFrom(Town from){
            this.from = from;
            return this;
        }

        public DirectedTripBuilder withTo(Town to){
            this.to = to;
            return this;
        }

        public DirectedTripBuilder withDistance(BigDecimal distance){
            this.distance = distance;
            return this;
        }

        public DirectedTrip build(){
            if (from == null){
                throw new IllegalStateException(" from may not be null");
            }
            if (to == null){
                throw new IllegalStateException(" to may not be null");
            }
            return new DirectedTrip(this.from,this.to,this.distance);
        }
    }

}
