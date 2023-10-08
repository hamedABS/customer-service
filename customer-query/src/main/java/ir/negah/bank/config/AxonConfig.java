package ir.negah.bank.config;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.jpa.JpaTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.springboot.util.jpa.ContainerManagedEntityManagerProvider;
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
}
