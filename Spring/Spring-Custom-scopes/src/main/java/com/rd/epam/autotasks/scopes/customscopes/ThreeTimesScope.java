package com.rd.epam.autotasks.scopes.customscopes;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ThreeTimesScope implements Scope {

    private final Map<String, Object> scopedObjects = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Runnable> destructionCallbacks = Collections.synchronizedMap(new HashMap<>());
    int timesAccessed = 0;

    @Override
    public Object get(String s, ObjectFactory<?> objectFactory) {
        if(!scopedObjects.containsKey(s)) {
            scopedObjects.put(s, objectFactory.getObject());
            timesAccessed = 0;
        }
        if(timesAccessed > 2) {
            scopedObjects.put(s, objectFactory.getObject());
            timesAccessed = 0;
        }
        timesAccessed++;
        return scopedObjects.get(s);
    }

    @Override
    public Object remove(String s) {
        destructionCallbacks.remove(s);
        timesAccessed = 0;
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
        return "threeTimes";
    }
}
