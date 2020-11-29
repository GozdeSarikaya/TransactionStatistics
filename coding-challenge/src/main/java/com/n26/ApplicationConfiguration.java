package com.n26;

import com.n26.exception.CustomExceptionHandler;
import com.n26.service.StatisticsService;
import com.n26.service.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.validation.Validator;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public TransactionService transactionService() {
        return new TransactionService();
    }

    @Bean
    public StatisticsService statisticsService() {
        return new StatisticsService();
    }

    @Bean
    public DateTimeFormatter dateTimeFormatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ").withZone(ZoneId.of("UTC"));
    }

    @Primary
    @Bean
    public CustomExceptionHandler handlerExceptionResolver (){
        return new CustomExceptionHandler ();
    }

}
