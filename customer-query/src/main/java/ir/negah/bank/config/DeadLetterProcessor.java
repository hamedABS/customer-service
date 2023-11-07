package ir.negah.bank.config;

import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.deadletter.SequencedDeadLetterProcessor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۱/۱
 * TIME: ۱۵:۰۸
 */
@Component
public class DeadLetterProcessor {

    private EventProcessingConfiguration processingConfiguration;
    private ExecutorService executorService;

    public DeadLetterProcessor(EventProcessingConfiguration processingConfiguration) {
        this.processingConfiguration = processingConfiguration;
        this.executorService = Executors.newFixedThreadPool(5);
    }

    public CompletableFuture<Boolean> processorAnyFor(String processingGroup) {
        SequencedDeadLetterProcessor<EventMessage<?>> letterProcessor = processingConfiguration.sequencedDeadLetterProcessor(processingGroup).orElseThrow(() -> new IllegalArgumentException(
                "No DLQ for processing group [" + processingGroup + " ]"
        ));
        return CompletableFuture.supplyAsync(letterProcessor::processAny, executorService);
    }

    public void retryAnySequence(String processingGroup) {
        processingConfiguration.sequencedDeadLetterProcessor(processingGroup)
                .ifPresent(SequencedDeadLetterProcessor::processAny);
    }
}
