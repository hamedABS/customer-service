package ir.negah.bank.config;

import org.axonframework.common.legacyjpa.EntityManagerProvider;
import org.axonframework.common.transaction.TransactionManager;
import org.axonframework.config.ConfigurerModule;
import org.axonframework.eventhandling.deadletter.legacyjpa.JpaSequencedDeadLetterQueue;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.legacyjpa.JpaTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.springboot.util.legacyjpa.ContainerManagedEntityManagerProvider;
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

    @Bean
    public ConfigurerModule deadLetterQueueConfigurerModule() {
        return configurer -> configurer.eventProcessing().registerDeadLetterQueue(
                "customer",
                config -> JpaSequencedDeadLetterQueue.builder()
                        .processingGroup("customer")
                        .entityManagerProvider(config.getComponent(EntityManagerProvider.class))
                        .transactionManager(config.getComponent(TransactionManager.class))
                        .serializer(config.eventSerializer())
                        .build()
        );
    }

    @Bean
    public ConfigurerModule enqueuePolicyConfigurerModule() {
        return configurer -> configurer.eventProcessing().registerDeadLetterPolicy(
                "customer", config -> new RetryConstrainedEnqueuePolicy()
        );
    }

}
