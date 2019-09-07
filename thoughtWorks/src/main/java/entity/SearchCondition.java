package entity;

import org.junit.Assert;

import java.util.function.Predicate;

/**
 * 搜索需要的条件
 */
public class SearchCondition {

    /**
     * 旅行起始地址
     */
    private final Town from;

    /**
     * 旅行终止地址
     */
    private final Town to;

    /**
     * 停止搜索的条件
     */
    private Predicate<Trip> stopCondition;

    /**
     * 返回数据的条件
     */
    private Predicate<Trip> returnCondition;

    /**
     * 构造函数
     * @param from  旅行起始地址
     * @param to    旅行终止地址
     */
    public SearchCondition(Town from, Town to) {
        this(from,to,null,null);
    }

    public SearchCondition(Town from, Town to, Predicate<Trip> stopCondition, Predicate<Trip> returnCondition) {
        Assert.assertNotNull(from);
        Assert.assertNotNull(to);
        this.from = from;
        this.to = to;
        this.stopCondition = stopCondition;
        this.returnCondition = returnCondition;
    }

    public Town getFrom() {
        return from;
    }

    public Town getTo() {
        return to;
    }

    public Predicate<Trip> getStopCondition() {
        return stopCondition;
    }

    public void setStopCondition(Predicate<Trip> stopCondition) {
        this.stopCondition = stopCondition;
    }

    public Predicate<Trip> getReturnCondition() {
        return returnCondition;
    }

    public void setReturnCondition(Predicate<Trip> returnCondition) {
        this.returnCondition = returnCondition;
    }

    public static SearchCondition.SearchConditionBuilder builder() {
        return new SearchCondition.SearchConditionBuilder();
    }

    public static final class SearchConditionBuilder{

        /**
         * 旅行起始地址
         */
        private Town from;

        /**
         * 旅行终止地址
         */
        private Town to;

        /**
         * 停止搜索的条件
         */
        private Predicate<Trip> stopCondition;

        /**
         * 返回数据的条件
         */
        private Predicate<Trip> returnCondition;

        public SearchConditionBuilder withFrom(Town from){
            this.from = from;
            return this;
        }

        public SearchConditionBuilder withTo(Town to){
            this.to = to;
            return this;
        }

        public SearchConditionBuilder withStopCondition(Predicate<Trip> stopCondition){
            this.stopCondition = stopCondition;
            return this;
        }

        public SearchConditionBuilder withReturnCondition(Predicate<Trip> returnCondition){
            this.returnCondition = returnCondition;
            return this;
        }

        public SearchCondition build(){
            if (from == null){
                throw new IllegalStateException(" from may not be null");
            }
            if (to == null){
                throw new IllegalStateException(" to may not be null");
            }
            return new SearchCondition(this.from,this.to,this.stopCondition,this.returnCondition);
        }
    }



}
