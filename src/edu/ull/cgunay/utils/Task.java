package edu.ull.cgunay.utils;

import java.io.*;
/**
 * Interface to describe a task that takes one argument. It is
 * semantically equivalent to lambda functions of Lisp and Scheme.
 *
 * @see Task#job
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version 1.0
 * @since 1.0
 */
public interface Task extends Serializable {
    /**
     * Method to be called by external task givers.
     * @see Iteration#loop
     * @param o the iterating value
     */
    void job(Object o) throws TaskException;
}
