package ru.javawebinar.topjava.dao;

import java.util.List;
import java.util.Objects;

public interface Dao {

    public List<Objects> getAll();

    public void delete(Integer id);
}
