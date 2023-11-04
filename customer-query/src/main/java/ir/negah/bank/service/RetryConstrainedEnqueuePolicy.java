package ir.negah.bank.service;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.MetaData;
import org.axonframework.messaging.deadletter.DeadLetter;
import org.axonframework.messaging.deadletter.Decisions;
import org.axonframework.messaging.deadletter.EnqueueDecision;
import org.axonframework.messaging.deadletter.EnqueuePolicy;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۱/۱
 * TIME: ۱۷:۲۷
 */
public class RetryConstrainedEnqueuePolicy implements EnqueuePolicy<EventMessage<?>> {
    @Override
    public EnqueueDecision<EventMessage<?>> decide(DeadLetter<? extends EventMessage<?>> letter, Throwable cause) {
        int retries = (int) letter.diagnostics().getOrDefault("retries", 0);

        String type = letter.cause().get().type();
//        if (type.contains("DataIntegrityViolationException")){
            return Decisions.evict();
//        }
//        if (retries == 0) {
//            return Decisions.enqueue(cause, l -> MetaData.with("retries", 0));
//        } else if (retries > 5) {
//            return Decisions.evict();
//        }

//        return Decisions.requeue(cause, deadLetter -> letter.diagnostics().and("retries", retries + 1));
    }
}
