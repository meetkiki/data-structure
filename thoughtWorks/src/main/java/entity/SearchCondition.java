package entity;

import java.util.function.Predicate;

/**
 * 搜索需要的条件
 */
public class SearchCondition<T> {

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
    private Predicate<T> stopCondition;

    /**
     * 返回数据的条件
     */
    private Predicate<T> returnCondition;

    /**
     * 构造函数
     * @param from  旅行起始地址
     * @param to    旅行终止地址
     */
    public SearchCondition(Town from, Town to) {
        this.from = from;
        this.to = to;
    }

    public Town getFrom() {
        return from;
    }

    public Town getTo() {
        return to;
    }

    public Predicate<T> getStopCondition() {
        return stopCondition;
    }

    public void setStopCondition(Predicate<T> stopCondition) {
        this.stopCondition = stopCondition;
    }

    public Predicate<T> getReturnCondition() {
        return returnCondition;
    }

    public void setReturnCondition(Predicate<T> returnCondition) {
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

        public SearchConditionBuilder withFrom(Town from){
            this.from = from;
            return this;
        }

        public SearchConditionBuilder withTo(Town to){
            this.to = to;
            return this;
        }

        public SearchCondition build(){
            if (from == null){
                throw new IllegalStateException(" from may not be null");
            }
            if (to == null){
                throw new IllegalStateException(" to may not be null");
            }
            return new SearchCondition(this.from,this.to);
        }
    }



}
