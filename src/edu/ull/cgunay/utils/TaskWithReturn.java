package Utils;

// $Id$
/**
 * Contains additional method for returning an intermediate value from task.
 *
 * Created: Tue Mar 27 00:13:29 2001
 *
 * @author Cengiz Gunay
 * @version $Revision$ for this file
 * @see Neuroid.SynapseActivityTask
 */

public interface TaskWithReturn extends Task {
    /**
     * Returns a value from the task.
     *
     * @return an <code>Object</code> value
     */
    Object getValue();
}// TaskWithReturn
