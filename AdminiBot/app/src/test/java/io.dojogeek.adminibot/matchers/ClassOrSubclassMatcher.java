package io.dojogeek.adminibot.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ClassOrSubclassMatcher <T> extends BaseMatcher<Class<T>> {

    private final Class<T> targetClass;

    public ClassOrSubclassMatcher(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public boolean matches(Object obj) {
        if (obj != null) {
            if (obj instanceof Class) {
                return targetClass.isAssignableFrom((Class<T>) obj);
            }
        }
        return false;
    }

    @Override
    public void describeTo(Description desc) {
        desc.appendText("Matches a class or subclass");
    }
}
