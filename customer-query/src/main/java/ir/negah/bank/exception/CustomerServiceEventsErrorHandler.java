package ir.negah.bank.exception;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

/**
 * CREATED_BY abbaszadeh
 * DATE: ۲۰۲۳/۱۰/۳۱
 * TIME: ۱۵:۱۲
 */
public class CustomerServiceEventsErrorHandler implements ListenerInvocationErrorHandler {
    @Override
    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
        throw exception;
    }
}
