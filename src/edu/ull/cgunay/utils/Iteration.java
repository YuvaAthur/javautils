package neuroidnet.utils;

import java.lang.*;
import java.util.*;

// $Id$
/**
 * Various iteration utilities based on the Collections of JAVA 2 SDK.
 * This file contains many alternative methods to
 * essentially do the same thing. The collection on which to iterate can be chosen either at
 * the constructor or at the loop() function for the non-static use. This is left to the
 * user to decide if the class is made to iterate on a single collection or if it is rather
 * a generic iteration facility that can be applied to many collections at different times.
 * <p>On the other hand static methods allow iterating without requiring an instance of the class.
 * In this case, an instance of the <code>Task</code> class has to be provided in calls to the
 * loop function.
 * <p>TODO: Provide examples.
 *
 * <p>Created: Sat Nov 25 04:18:32 2000
 * @see Collection
 * @see Iterator
 * @author Cengiz Gunay
 * @version $Revision$
 */

abstract public class Iteration implements Task {
    /**
     * Collection to be iterated on for non-static loop() function.
     * @see #loop()
     */
    Collection collection;

    /**
     * Dummy.
     *
     */
    public Iteration () {
    }

    /**
     * Sets the collection to be iterated on (for the non-static <code>loop()</code>).
     * @see #loop()
     * @param collection a <code>Collection</code> value
     */
    public Iteration (Collection collection) {
	this.collection = collection;
    }

    /**
     * Iterates on collection set by the constructor.
     * @see #Iteration(Collection)
     */
    public void loop() {
	loop(collection, this);
    }

    /**
     * Iterates on <code>c</code> with the <code>Task</code> given in this class.
     * @param c a <code>Collection</code> value
     * @see Task
     * @see #loop()
     */
    public void loop(Collection c) {
	loop(c, this);
    }

    /**
     * Loop for <code>Iterator</code> values calling <code>Task</code>.
     *
     * @see Task#job
     * @param i an <code>Iterator</code> value
     * @param t a <code>Task</code> value
     */
    public static void loop(Iterator i, Task t) {
	try {
	    for (; i.hasNext() ;) {
		try {
		    t.job(i.next());		     
		} catch (RemoveFromIterationException e) {
		    i.remove();
		} // end of try-catch
	    } // end of for (; i.hasNext() ;)
	} catch (BreakOutOfIterationException e) {
	    // just return, iteration is terminated
	} catch (IterationException e) {
	    throw new RuntimeException("Not supposed to happen");
	} // end of catch
	
    }

    public static void loop(Collection c, Task t) {
	loop(c.iterator(), t);
    }

    /**
     * Loop by sending values to inner class.
     * @deprecated use <code>final</code> modifiers to be able to send and
     * return values from inner classes.
     * @param i an <code>Iterator</code> value
     * @param t a <code>TaskWithParam</code> value
     * @param p an <code>Object[]</code> value
     */
    public static void loop(Iterator i, TaskWithParam t, Object[] p) {
	try {
	    for (; i.hasNext() ;) {
		try {
		    t.job(i.next(), p);
		} catch (RemoveFromIterationException e) {
		    i.remove();
		} // end of try-catch
	    } // end of for (; i.hasNext() ;)
	} catch (BreakOutOfIterationException e) {
	    // just return, iteration is terminated
	} catch (IterationException e) {
	    throw new RuntimeException("Not supposed to happen");
	} // end of try-catch
    }

    public static void loop2(Collection c, TaskWithParam t, Object[] p) {
	loop(c.iterator(), t, p);
    }

}// utils
