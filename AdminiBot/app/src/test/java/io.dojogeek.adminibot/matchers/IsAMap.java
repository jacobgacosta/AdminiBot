package io.dojogeek.adminibot.matchers;

import org.mockito.ArgumentMatcher;

import java.util.List;
import java.util.Map;

public class IsAMap <T> extends ArgumentMatcher<Map<T, T>> {

    private int size;

    public IsAMap<T> size(int size) {
        this.size = size;
        return this;
    }

    @Override
    public boolean matches(Object argument) {
        return ((List<T>) argument).size() == size;
    }
}
