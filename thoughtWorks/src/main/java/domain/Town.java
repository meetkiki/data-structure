package domain;

import java.util.Objects;

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
}
