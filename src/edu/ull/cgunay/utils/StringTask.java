
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
    protected String closing = "";

    public StringTask () { }

    /**
     * Initialize <code>retval</code> with argument.
     *
     * @param initialRetval a <code>String</code> value
     */
    public StringTask (String initialRetval) {
	retval = initialRetval;
    }

    /**
     * Initialize retval and also accept a argument for adding
     * to retval before returning from getValue()
     *
     * @param initialRetval a <code>String</code> value
     * @param closingAddition a <code>String</code> value
     */
    public StringTask (String initialRetval, String closingAddition) {
	this(initialRetval);
	closing = closingAddition;
    }

    // implementation of neuroidnet.utils.TaskWithReturn interface

    /**
     *
     * @return <description>
     */
    public Object getValue()   {
	return retval + closing;
    }
    // implementation of neuroidnet.utils.Task interface

    /**
     * By default adds the <code>String</code> representation of <code>o</code>
     * to <code>retval</code>.
     * @param o the token of this iteration step
     */
    public void job(Object o) {
	retval += "" + o;
    }

    }// StringTask
