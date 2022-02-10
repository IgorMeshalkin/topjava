package ru.javawebinar.topjava.dao;

import java.util.List;

public interface Dao<T> {

    public List<T> getAll();

    public T getById (Integer id);

    public void add (T t);

    public void update (T t);

    public void delete(Integer id);
}
