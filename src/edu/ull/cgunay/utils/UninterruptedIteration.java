
package edu.ull.cgunay.utils;

import java.util.*;

// $Id$
/**
 * Loop until a break without complaining. That is, terminate the loop if a
 * <code>BreakOutOfIterationException</code> occurs without letting the caller know.
 * <p>See parent class <code>Iteration</code> for details on use.
 * <p>Created: Sat Apr 27 15:16:29 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see BreakOutOfIterationException
 */

abstract public class UninterruptedIteration extends Iteration  {
    public UninterruptedIteration () { super(); }

    public UninterruptedIteration (Collection collection) { super(collection); }

    static public void loop(Iterator i, Task t) {
	try {
	    Iteration.loop(i, t); 
	} catch (BreakOutOfIterationException e) {
	    // just return, iteration is terminated	    
	} // end of try-catch
    }

    public static void loop(Object[] array, Task t) {
	try {
	    Iteration.loop(array, t); 
	} catch (BreakOutOfIterationException e) {
	    // just return, iteration is terminated	    
	} // end of try-catch	
    }

    /**
     * Convenience method with <code>Collection</code> parameter, calls another
     * <code>loop()</code> method.
     *
     * @param array an <code>Object[]</code> value
     */
    public void loop(Object[] array) {
	loop(array, this);
    }

    static public void loop(Collection c, Task t) {
	loop(c.iterator(), t);
    }

    public void loop(Collection c) {
	loop(c, this);
    }

    public void loop() {
	loop(collection, this);
    }
    
}// UninterruptedIteration

