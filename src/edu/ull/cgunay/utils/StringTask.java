
package neuroidnet.utils;
import neuroidnet.utils.Task;
import neuroidnet.utils.TaskWithReturn;
import java.util.Collection;
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

    /**
     * A piece of string to be added before the accumulated string
     * <code>retval</code> is returned. Empty string by default.
     *
     */
    protected String closing = "";

    /**
     * Dummy constructor.
     *
     */
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
     * Initialize retval and also accept an argument for <code>closing</code> that is added
     * to retval before returning from <code>getValue()</code> or <code>getString()</code>.
     * @see #closing
     * @see #getValue
     * @see #getString
     * @param initialRetval assigned to <code>retval</code>
     * @param closingAddition assigned to <code>closing</code>
     */
    public StringTask (String initialRetval, String closingAddition) {
	this(initialRetval);
	closing = closingAddition;
    }

    /**
     * Runs the iteration and returns a <code>String</code> value.
     * @see #getValue()
     * @return a <code>String</code> value
     */
    public String getString(Collection list) {
	Iteration.loop(list, this);
	return (String)getValue(); 
    }

    // implementation of neuroidnet.utils.TaskWithReturn interface

    /**
     * Adds a closing string value.
     * @see #closing
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
