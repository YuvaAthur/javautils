
package edu.ull.cgunay.utils;

import java.util.*;

// $Id$
/**
 * Allows comparing instances of these sets according to alphabetical
 * comparison technique, assuming elements implement the
 * <code>Comparable</code> interface.
 *
 *
 * <p>Created: Fri Oct 18 17:30:21 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Comparable
 */

public class ComparableSortedSet extends TreeSet  {
    public ComparableSortedSet () {
	super();
    }

    public ComparableSortedSet (SortedSet set) {
	super(set);
    }

    public ComparableSortedSet (Collection set) {
	super(set);
    }

    public ComparableSortedSet (Comparator c) {
	super(c);
    }
    
    // implementation of java.lang.Comparable interface

    /**
     * Compares both sets.
     * @param thatSet set to compare
     * @return -1,0,1 according to this being less, equal, or greater to that
     */
    public int compareTo(Object thatSet) {

	final Iterator otherIterator = ((TreeSet)thatSet).iterator();
	
	try {
	    new Iteration() {
		public void job(Object o) throws BreakOutOfIterationException {
		    Comparable c = (Comparable) o;
		    Comparable c2 = (Comparable) otherIterator.next();
		    if (!c.equals(c2)) 
			throw new BreakOutOfIterationException(new Integer(c.compareTo(c2)));
		}
	    }.loop(this);
	} catch (BreakOutOfIterationException broken) {
	    return ((Integer)broken.getValue()).intValue();
	    
	} catch (NoSuchElementException e) {
	    return 1; // This set is larger (being so far equal) therefore is greater.
	} // end of catch

	// otherwise equal
	if (size() == ((Set)thatSet).size()) 
	    return 0;		// Equal..
	else 
	    return -1;		// That is larger...
    }
}
