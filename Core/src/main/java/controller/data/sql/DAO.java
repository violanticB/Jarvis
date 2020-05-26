package controller.data.sql;

import java.util.List;

public interface DAO<T> {

    List<T> findAll();
    List<T> findByField(String field, Object fieldValue);
    boolean insert(T t);
    boolean update(T t);
    boolean delete(T t);

}
