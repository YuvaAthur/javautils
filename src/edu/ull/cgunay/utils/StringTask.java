
package edu.ull.cgunay.utils;
import edu.ull.cgunay.utils.Task;
import edu.ull.cgunay.utils.TaskWithReturn;
import java.util.Collection;
// $Id$
/**
 * Iteration task that accumulates a <code>String</code> value. Constructors allow
 * optionally starting and ending <code>String</code>s  to wrap the
 * <code>String</code> created by iterating the <code>Collection</code>
 * given to the <code>getString()</code> method.
 * <p>Example to display contents of a <code>Vector</code> holding
 * <code>double</code> values in parenthesis:
 * <pre>
 * String inParens = new StringTask("(", ")") {
 *   public void job(Object o) {
 *     super.job(o + ", ");	// Add the string representation of object and a comma
 *   }
 * }.getString(doubleVector);	// Iterate on doubleVector
 * </pre>
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
     * Initialize the accumulated <code>String</code> to the argument.
     *
     * @param initialRetval a <code>String</code> value
     */
    public StringTask (String initialRetval) {
	retval = initialRetval;
    }

    /**
     * Wrap the accumulated <code>String</code> with an initial prefix and a final appendix.
     * Initialize <code>retval</code> to <code>initialRetval</code> and
     * <code>closing</code> to <code>closingAddition</code>. The latter is added
     * to <code>retval</code> before being returned from
     * <code>getValue()</code> or <code>getString()</code>.
     * @param initialRetval assigned to <code>retval</code>
     * @param closingAddition assigned to <code>closing</code>
     * @see #closing
     * @see #getValue
     * @see #getString
     */
    public StringTask (String initialRetval, String closingAddition) {
	this(initialRetval);
	closing = closingAddition;
    }

    /**
     * Iterates on the <code>list</code> and returns the accumulated <code>String</code>.
     * Gracefully trims the items if a <code>BreakOutOfIterationException</code> is encountered
     * during iteration.
     * @return a <code>String</code> value
     * @see #getValue()
     * @see UninterruptedIteration
     */
    public String getString(Collection list) {
	UninterruptedIteration.loop(list, this);
	return (String)getValue(); 
    }

    // implementation of edu.ull.cgunay.utils.TaskWithReturn interface

    /**
     * Return the accumulated <code>String</code> appended by the <code>closing</code>.
     * @see #closing
     * @return <description>
     */
    public Object getValue()   {
	return retval + closing;
    }

    // implementation of edu.ull.cgunay.utils.Task interface

    /**
     * Job to be done on each <code>Collection</code> item <code>o</code>.
     * By default adds the <code>String</code> representation of <code>o</code>
     * to the accumulated <code>String</code>.
     * @param o the token of this iteration step
     */
    public void job(Object o) {
	retval += "" + o;
    }

    }// StringTask
