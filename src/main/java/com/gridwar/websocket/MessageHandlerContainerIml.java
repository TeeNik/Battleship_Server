package com.gridwar.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Service
public class MessageHandlerContainerIml implements MessageHandlerContainer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageHandlerContainerIml.class);
    private final Map<Class<?>, MessageHandler<?>> handlerMap = new HashMap<>();

    @Override
    public void handle(@NotNull Message message, @NotNull String sessionId) throws HandleException {

        final MessageHandler<?> messageHandler = handlerMap.get(message.getClass());
        if (messageHandler == null) {
            throw new HandleException("no handler for message of " + message.getClass().getName() + " type");
        }
        messageHandler.handleMessage(message, sessionId);
        LOGGER.trace("message handled: type =[" + message.getClass().getName() + ']');
    }

    @Override
    public <T extends Message> void registerHandler(@NotNull Class<T> clazz, MessageHandler<T> handler) {
        handlerMap.put(clazz, handler);
    }
}