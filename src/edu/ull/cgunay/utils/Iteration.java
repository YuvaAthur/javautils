package edu.ull.cgunay.utils;

import java.lang.*;
import java.util.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * <p>Allows iterating on the <code>Collection</code>s of JAVA 2 SDK with a high level interface.
 * In summary, a given <code>Task</code> is executed for all items
 * in a given <code>Collection</code> when an appropriate
 * <code>loop()</code> method is invoked. 
 *
 * <p>This scheme allows
 * generalizing commonly used iterations in template classes.
 * This is a vast improvement allowing object oriented
 * control over the primitive looping constructs 
 * <code>for</code> and <code>while</code>.
 * 
 * <p>This file contains alternative methods for iteration.
 * The collection on which to iterate can be chosen either at
 * the constructor or at the <code>loop()</code> function for the non-static use.
 * It is left to the
 * user to decide if the class is made to iterate on a single collection or if it is rather
 * a generic iteration facility that can be applied to many collections at different times.
 *
 * <p>On the other hand, static methods allow iterating without
 * requiring an instance of this class. However, in this case an
 * instance of the <code>Task</code> class has to be provided in calls to the
 * <code>loop(Collection,Task)</code> method.
 * See the examples below for details on use.
 *
 * <p><b>Note:</b> The <code>loop()</code> methods of this class throws
 * <code>BreakOutOfIterationException</code> to enable the user to have control
 * on the iteration. If this is undesired, the subclass <code>UninterruptedIteration</code>
 * should be used instead.
 *
 * <p>Example of static use:
 * <blockquote><pre>
 * Iteration.loop(collection, new Task() {
 *   public void job(Object o) {
 *     // do something on o here
 *   }
 * });
 * </pre></blockquote>
 * <p>Example of non-static repeatedly use of same task on different collections:
 * <blockquote><pre>
 * new Iteration() {
 *   public void job(Object o) {
 *     // do something on o here
 *   }
 * }.loop(collection);
 * </pre></blockquote>
 * <p>Example of non-static repeatedly use of same task with same collection:
 * <blockquote><pre>
 * new Iteration(collection) {
 *   public void job(Object o) {
 *     // do something on o here
 *   }
 * }.loop();
 * </pre></blockquote>
 * <p>Created: Sat Nov 25 04:18:32 2000
 * @author Cengiz Gunay
 * @version $Revision$
 * @see Collection
 * @see Iterator
 * @see Task
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
     * @param collection a <code>Collection</code> value
     * @see #loop()
     */
    public Iteration (Collection collection) {
	this.collection = collection;
    }

    /**
     * Iterates on collection set by the constructor.
     * @exception BreakOutOfIterationException if received from given <code>Task</code> instance
     * @see #Iteration(Collection)
     */
    public void loop() throws BreakOutOfIterationException {
	loop(collection, this);
    }

    /**
     * Iterates on <code>c</code> with the <code>Task</code> given in this class.
     * @param c a <code>Collection</code> value
     * @exception BreakOutOfIterationException if received from given <code>Task</code> instance
     * @see Task
     * @see #loop()
     */
    public void loop(Collection c) throws BreakOutOfIterationException {
	loop(c, this);
    }

    /**
     * Loop on <code>Iterator</code> values calling <code>Task</code>.
     *
     * @param i an <code>Iterator</code> value
     * @param t a <code>Task</code> value
     * @exception BreakOutOfIterationException if received from given <code>Task</code> instance
     * @see Task#job
     */
    public static void loop(Iterator i, Task t) throws BreakOutOfIterationException {
	try {
	    while (i.hasNext()) {
		try {
		    t.job(i.next());		     
		} catch (RemoveFromIterationException e) {
		    i.remove();
		} // end of try-catch
	    } // end of while (i.hasNext())
	} catch (BreakOutOfIterationException e) {
	    throw e;
	} catch (TaskException e) {
	    throw new RuntimeException("Not supposed to happen");
	} // end of catch
    }

    /**
     * Loop on <code>Object[]</code> values calling <code>Task</code>.
     *
     * @param array an <code>Object[]</code> value
     * @param t a <code>Task</code> value
     * @exception BreakOutOfIterationException if an error occurs
     */
    public static void loop(Object[] array, Task t) throws BreakOutOfIterationException {
	try {
	    int size = array.length;
	    for (int i = 0; i < size; i++) {
		try {
		    t.job(array[i]); 
		} catch (RemoveFromIterationException e) {
		    throw new Error("Not supported for arrays. Use Collections.");
		} // end of try-catch
	    } // end of while (i.hasNext())
	} catch (BreakOutOfIterationException e) {
	    throw e;
	} catch (TaskException e) {
	    throw new RuntimeException("Not supposed to happen");
	} // end of catch
    }

    /**
     * Convenience method with <code>Collection</code> parameter, calls another
     * <code>loop()</code> method.
     *
     * @param array an <code>Object[]</code> value
     * @exception BreakOutOfIterationException if an error occurs
     */
    public void loop(Object[] array) throws BreakOutOfIterationException {
	loop(array, this);
    }

    /**
     * Convenience method with <code>Collection</code> parameter, calls another
     * <code>loop()</code> method.
     *
     * @param c a <code>Collection</code> value
     * @param t a <code>Task</code> value
     * @exception BreakOutOfIterationException if received from given <code>Task</code> instance
     * @see #loop(Iterator,Task)
     */
    public static void loop(Collection c, Task t) throws BreakOutOfIterationException {
	loop(c.iterator(), t);
    }

    /*
     * Loop by sending values to inner class.
     * @deprecated use <code>final</code> modifiers to be able to send and
     * return values from inner classes.
     * @param i an <code>Iterator</code> value
     * @param t a <code>TaskWithParam</code> value
     * @param p an <code>Object[]</code> value
    
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
	} catch (TaskException e) {
	    throw new RuntimeException("Not supposed to happen");
	} // end of try-catch
    }

    public static void loop(Collection c, TaskWithParam t, Object[] p) {
	loop(c.iterator(), t, p);
    }
*/
}// utils
