package com.n26.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public class StatisticsDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal sum;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal avg;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal max;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal min;

    private long count;

    public StatisticsDto() {
        this.sum = BigDecimal.valueOf(0, 2);
        this.avg = BigDecimal.valueOf(0, 2);
        this.max = BigDecimal.valueOf(0, 2);
        this.min = BigDecimal.valueOf(0, 2);
        this.count = 0;
    }

    public StatisticsDto(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }

}
