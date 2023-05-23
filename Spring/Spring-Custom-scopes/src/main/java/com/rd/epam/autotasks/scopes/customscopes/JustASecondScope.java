package com.rd.epam.autotasks.scopes.customscopes;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JustASecondScope implements Scope {
    private final Map<String, Object> scopedObjects = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, LocalDateTime> dateOfObjects = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Runnable> destructionCallbacks = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {

        if(!scopedObjects.containsKey(s) || dateOfObjects.get(s).plusSeconds(1).isBefore(LocalDateTime.now())) {
            scopedObjects.put(s, objectFactory.getObject());
            dateOfObjects.put(s, LocalDateTime.now());
        }
        return scopedObjects.get(s);
    }

    @Override
    public Object remove(String s) {
        destructionCallbacks.remove(s);
        dateOfObjects.remove(s);
        return scopedObjects.remove(s);
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {
        destructionCallbacks.put(s, runnable);
    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return "justASecond";
    }
}
