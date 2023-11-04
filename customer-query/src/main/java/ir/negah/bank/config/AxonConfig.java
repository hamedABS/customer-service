package ir.negah.bank.config;

import ir.negah.bank.exception.CustomerServiceEventsErrorHandler;
import ir.negah.bank.service.RetryConstrainedEnqueuePolicy;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.eventhandling.deadletter.legacyjpa.JpaSequencedDeadLetterQueue;
import org.axonframework.springboot.util.legacyjpa.ContainerManagedEntityManagerProvider;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.legacyjpa.JpaTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.common.legacyjpa.EntityManagerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.LockModeType;


/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۸
 * TIME: ۱۱:۴۹
 */
@Configuration
public class AxonConfig {

    @Bean
    public TokenStore myTokenStore(Serializer serializer) {
        return JpaTokenStore.builder()
                .entityManagerProvider(primaryEntityManagerProvider())
                .serializer(serializer)
                .loadingLockMode(LockModeType.NONE)
                .build();
    }


    @Bean
    public EntityManagerProvider primaryEntityManagerProvider() {
        return new ContainerManagedEntityManagerProvider();
    }
    
/*    @Bean
    public ConfigurerModule processingGroupErrorHandlingConfigurerModule() {
        return configurer -> configurer.eventProcessing(
                processingConfigurer -> processingConfigurer
                        .registerListenerInvocationErrorHandler(
                                "customer",
                                conf -> new CustomerServiceEventsErrorHandler()
                        )
        );
    }*/

    @Bean
    public ConfigurerModule deadLetterQueueConfigurerModule() {
        // Replace "my-processing-group" for the processing group you want to configure the DLQ on.
        return configurer -> configurer.eventProcessing().registerDeadLetterQueue(
                "customer",
                config -> JpaSequencedDeadLetterQueue.builder()
                        .processingGroup("customer")
                        .maxSequences(256)
                        .maxSequenceSize(256)
                        .entityManagerProvider(config.getComponent(EntityManagerProvider.class))
                        .transactionManager(config.getComponent(TransactionManager.class))
                        .serializer(config.serializer())
                        .build()
        );
    }

    @Bean
    public ConfigurerModule enqueuePolicyConfigurerModule() {
        return configurer -> configurer.eventProcessing()
                .registerDefaultDeadLetterPolicy(
                        config -> new RetryConstrainedEnqueuePolicy()
                );
    }

}
