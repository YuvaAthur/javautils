package Utils;

import java.lang.*;
import java.util.*;

/**
 * Utils.java
 * Various Java utilities mainly for iteration.
 *
 * Created: Sat Nov 25 04:18:32 2000
 *
 * @author Cengiz Gunay
 * @version
 */

public class Iteration  {
    public Iteration () {
	
    }

    /**
     * Loop for <code>Iterator</code> values calling <code>Task</code>.
     *
     * @see Task#job
     * @param i an <code>Iterator</code> value
     * @param t a <code>Task</code> value
     */
    public static void loop(Iterator i, Task t) {
	for (; i.hasNext() ;) {
	    t.job(i.next());
	} // end of for (; i.hasNext() ;)
    }

    public static void loop(Iterator i, TaskWithParam t, Object[] p) {
	for (; i.hasNext() ;) {
	    t.job(i.next(), p);
	} // end of for (; i.hasNext() ;)
    }

    public static void loop2(AbstractList a, TaskWithParam t, Object[] p) {
	for (Iterator i = a.iterator(); i.hasNext() ;) {
	    t.job(i.next(), p);
	} // end of for (; i.hasNext() ;)
    }

}// Utils
