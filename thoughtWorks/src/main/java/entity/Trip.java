package entity;

import utils.CommonUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 一段旅行实体类
 *      主要用于计算路线距离
 * @author tao
 */
public class Trip {

    /**
     * 旅行的路线
     */
    private final LinkedList<DirectedTrip> trips;
    /**
     * 旅行的距离
     */
    private BigDecimal distance;

    /**
     * 起点
     */
    private Town from;
    /**
     * 终点
     */
    private Town to;

    /**
     * 旅行的次数
     */
    private int count = 0;

    /**
     * 无参构造
     */
    public Trip(){
        this.trips = new LinkedList<>();
        this.distance = BigDecimal.ZERO;
    }

    /**
     * 根据起点创建旅行
     * @param from
     */
    public Trip(Town from) {
        this();
        this.from = from;
    }

    /**
     * 根据路线构造旅行
     * @param trip
     */
    public Trip(Trip trip) {
        this(trip.getTrips());
    }

    /**
     * 根据路段队列构造旅行
     * @param trips
     */
    public Trip(Collection<DirectedTrip> trips) {
        this();
        if (!CommonUtils.isEmpty(trips)){
            this.trips.addAll(trips);
            // 设置起点和终点
            this.from = CommonUtils.findFirst(trips).getFrom();
            this.to = CommonUtils.findFirst(trips).getTo();
            // 旅行的次数
            this.count += this.trips.size();
        }
    }


    public Collection<DirectedTrip> getTrips() {
        return trips;
    }

    /**
     * 添加路线村庄
     * @param directedTrip 旅行的一段路线
     * @return
     */
    public Trip addTrip(DirectedTrip directedTrip){
        // 更改目标指向
        this.to = directedTrip.getTo();
        this.trips.add(directedTrip);
        // 旅行的次数
        this.incrementAndGetCount();
        return this;
    }

    /**
     * 旅行的次数
     * @return
     */
    private int incrementAndGetCount(){
        return ++this.count;
    }

    /**
     * 获得具体的距离
     * @return
     */
    public BigDecimal sumDist(){
        this.distance = BigDecimal.ZERO;
        this.trips.stream()
                .filter(Objects::nonNull)
                .forEach((t) -> distance.add(t.getDistance()));
        return distance;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return String.format(" Trip from %s - > to %s trips %s , distance %.2f ",
                from, to, trips,distance == null ? 0.0 : distance.doubleValue());
    }
}
