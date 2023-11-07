package ir.negah.bank.config;

import org.axonframework.config.ConfigurerModule;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۳۰
 * TIME: ۸:۱۸
 */
@Configuration
public class AxonConfig {

/*    @Bean
    public ConfigurerModule processorDefaultConfigurerModule(){
        return configurer -> configurer.eventProcessing(EventProcessingConfigurer::usingSubscribingEventProcessors);
    }*/
}
