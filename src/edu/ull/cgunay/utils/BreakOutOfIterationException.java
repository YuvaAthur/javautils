
package edu.ull.cgunay.utils;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * Thrown in iterators when the iteration needs to be terminated.
 *
 * <p>Created: Sat Apr 20 14:42:05 2002
 * <p>Modified: $Date$
 *
 * @see Iteration
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public class BreakOutOfIterationException extends TaskException  {
    Object currentObject;

    public BreakOutOfIterationException () {
	
    }

    /**
     * Breaks the iteration and records the object which caused it.
     * Retrieve it method <code>getValue()</code>.
     *
     * @param currentObject an <code>Object</code> value
     * @see #getValue
     */
    public BreakOutOfIterationException (Object currentObject) {
	this.currentObject = currentObject;
    }

    public Object getValue() {
	return currentObject;
    }
    
}// BreakOutOfIterationException
