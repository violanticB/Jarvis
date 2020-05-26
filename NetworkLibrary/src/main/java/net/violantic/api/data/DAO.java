package net.violantic.api.data;

import net.violantic.api.data.exception.ExistingRecordException;
import net.violantic.api.data.exception.NonExistentRecordException;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    /**
     * Column fields of T object
     * @return fields
     */
    List<String> getFields();

    /**
     * Find all values of data
     * @return all
     */
    List<T> findAll() throws SQLException;

    /**
     * Find list of data values by field
     * @param field Checked field
     * @param fieldValue Desired value
     * @return list
     */
    List<T> findByField(String field, Object fieldValue) throws SQLException;

    /**
     * Find the first index of queried data
     * @param field Search field
     * @param fieldValue Desired value
     * @return data
     */
    T query(String field, Object fieldValue) throws SQLException;

    /**
     * Insert data into database
     * @param t data
     * @return queried result
     */
    T insert(T t) throws SQLException, ExistingRecordException;

    /**
     * Update data info in database
     * @param t data
     */
    void update(T t, String field, Object value) throws SQLException, NonExistentRecordException;

    /**
     * Delete data and index
     * @param t data
     * @return success
     */
    boolean delete(T t);

}
