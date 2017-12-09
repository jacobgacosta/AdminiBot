package io.dojogeek.adminibot.matchers;

import org.mockito.ArgumentMatcher;

import java.util.List;

public class IsAList <T> extends ArgumentMatcher<List<T>> {

    private int size;

    public IsAList<T> size(int size) {
        this.size = size;
        return this;
    }

    @Override
    public boolean matches(Object argument) {
        return ((List<T>) argument).size() == size;
    }
}
