
package neuroidnet.utils;
import neuroidnet.utils.Task;
import neuroidnet.utils.TaskWithReturn;

// $Id$
/**
 * Iteration task that keeps a <code>String</code> value.
 *
 *
 * <p>Created: Tue Apr  9 13:40:54 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public class StringTask implements TaskWithReturn {
    /**
     * Variable holding the <code>String</code> accumulated
     * during iteration.
     *
     */
    protected String retval = "";

    public StringTask () { }

    /**
     * Initialize <code>retval</code> with argument.
     *
     * @param initialRetval a <code>String</code> value
     */
    public StringTask (String initialRetval) {
	retval = initialRetval;
    }

    // implementation of neuroidnet.utils.TaskWithReturn interface

    /**
     *
     * @return <description>
     */
    public Object getValue()   {
	return retval;
    }
    // implementation of neuroidnet.utils.Task interface

    /**
     * By default adds the <code>String</code> representation of <code>o</code>
     * to <code>retval</code>.
     * @param o the token of this iteration step
     */
    public void job(Object o) {
	retval += (String)o;
    }

    }// StringTask
