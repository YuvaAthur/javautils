package Utils;
import java.io.*;

/**
 * Interface to describe a task that takes one additional argument other than the object.
 * @see TaskWithParam#job
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version 1.0
 * @since 1.0
 */

public interface TaskWithParam extends Serializable {
    /**
     * Method to be called by external task givers.
     * @see Iteration#loop
     * @param o the iterating value
     * @param p an <code>Object</code> value determined by the call to Utils.loop 
     */
    void job(Object o, Object[] p);
}
