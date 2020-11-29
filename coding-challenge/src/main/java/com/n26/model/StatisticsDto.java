package com.n26.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class StatisticsDto {

    //private LocalDateTime date;

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @Digits(integer=Integer.MAX_VALUE, fraction=3)
    private BigDecimal sum=BigDecimal.valueOf(0,2);

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @Digits(integer=Integer.MAX_VALUE, fraction=2)
    private BigDecimal avg=BigDecimal.valueOf(0,2);

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @Digits(integer=Integer.MAX_VALUE, fraction=2)
    private BigDecimal max=BigDecimal.valueOf(0,2);

    @JsonFormat(shape=JsonFormat.Shape.STRING)
    @Digits(integer=Integer.MAX_VALUE, fraction=2)
    private BigDecimal min=BigDecimal.valueOf(0,2);

    private long count;

    /*
    public StatisticsDto() {
        super();
    }

    public StatisticsDto(Double sum, Double avg, Double max, Double min, Long count) {
        super();
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }*/

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
