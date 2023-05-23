package com.rd.epam.autotasks.scopes.customscopes;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThreadScope implements Scope {

    private final Map<String, Map<String, Object>> scopedObjects = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Map<String, Runnable>> destructionCallbacks = Collections.synchronizedMap(new HashMap<>());

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        String threadName = Thread.currentThread().getName();
        Map<String, Object> objects;

        if(scopedObjects.containsKey(threadName) && scopedObjects.get(threadName).containsKey(s)) {
            return scopedObjects.get(Thread.currentThread().getName()).get(s);
        } else if(scopedObjects.containsKey(threadName) && !scopedObjects.get(threadName).containsKey(s)) {
            objects = scopedObjects.get(threadName);
        } else {
            objects = Collections.synchronizedMap(new HashMap<>());
        }

        objects.put(s, objectFactory.getObject());
        scopedObjects.put(threadName, objects);
        return scopedObjects.get(Thread.currentThread().getName()).get(s);
    }

    @Override
    public Object remove(String s) {
        destructionCallbacks.get(Thread.currentThread().getName()).remove(s);
        return scopedObjects.get(Thread.currentThread().getName()).remove(s);
    }

    @Override
    public void registerDestructionCallback(String s, Runnable runnable) {
        Map<String, Runnable> callbacks =  destructionCallbacks.get(Thread.currentThread().getName());
        callbacks.put(s, runnable);
        destructionCallbacks.put(Thread.currentThread().getName(), callbacks);
    }

    @Override
    public Object resolveContextualObject(String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return "thread";
    }
}
