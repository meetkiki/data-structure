package entity;

import java.util.Objects;

/**
 * 小镇实体
 * @author tao
 */
public final class Town {

    /**
     * 小镇的标示
     */
    private final String sign;

    public Town(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Town town = (Town) o;
        return Objects.equals(sign, town.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sign);
    }

    @Override
    public String toString() {
        return String.format("Town %s",sign);
    }

    public static Town.TownBuilder builder() {
        return new Town.TownBuilder();
    }

    public static final class TownBuilder{
        /**
         * 小镇的标示
         */
        private String sign;

        public TownBuilder withFrom(String sign){
            this.sign = sign;
            return this;
        }

        public Town build(){
            if (sign == null){
                throw new IllegalStateException(" sign may not be null");
            }
            return new Town(this.sign);
        }
    }
}
