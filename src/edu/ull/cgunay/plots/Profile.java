
package edu.ull.cgunay.utils.plots;

import java.io.*;
import java.util.*;
import java.util.Observable;
import java.util.Observer;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * Records the time profile of a <code>Profilable</code> entity that changes in time.
 * Saves pairs consisting of a time and an associated value for the entity (its clone).
 * The time is represented as a simple <code>double</code> value.
 *
 * <p>Created: Mon Apr  8 17:07:04 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see ProfilePlot
 * @see Profilable
 */
public class Profile extends TreeMap implements Observer, Serializable {

    /**
     * Constant to be added to range expressions for making the end inlusive.
     *
     */
    static final double overhead = 0.01;

    /**
     * Entity to be observed and recorded.
     *
     */
    protected Profilable entity;

    /**
     * Dummy constructor.
     *
     */
    public Profile () { }

    /**
     * Starts recording <code>entity</code> at given <code>time</code>.
     * Calls <code>connectTo()</code>.
     *
     * @param entity to be observed and recorded
     * @param time an <code>Double</code> value, used as hash key
     * @see #connectTo
     */
    public Profile (Profilable entity, Object time) {
	connectTo(entity, time);
    }

    /**
     * Adds this object to the observer list of the entity and initializes the profile
     * with given time-entity pair.
     * 
     * @param entity a <code>Profilable</code> value
     * @param time an <code>Object</code> value
     */
    public void connectTo(Profilable entity, Object time) {
	this.entity = entity;
	entity.addObserver(this);

	// initial entry
	update(entity, time);
    }

    /**
     * Returns an <code>Iterator</code> for the entity values recorded
     * in the given <code>range</code>. If <code>range</code> is <code>null</code>
     * the iterator is on all values of the entity contained in this <code>Profile</code>.
     *
     * @param range a <code>Range</code> value
     * @return an <code>Iterator</code> value
     * @see Grapher#profile
     */
    public Iterator iterator(Range range) {
	return collection(range).iterator();
    }

    /**
     * Returns an <code>Collection</code> for the entity values recorded
     * in the given <code>range</code>. If <code>range</code> is <code>null</code>
     * the iterator is on all values of the entity contained in this <code>Profile</code>.
     *
     * @param range a <code>Range</code> value
     * @return an <code>Iterator</code> value
     * @see Grapher#profile
     */
    public Collection collection(Range range) {
	if (range == null) 
	    return entrySet();	// if range == null	     

	SortedMap headMap = headMap(new Double(range.getStart() + overhead));
	Double
	    end = new Double(range.getEnd() + overhead),
	    start = (headMap != null) ? (Double)headMap.lastKey() : new Double(range.getStart() + overhead);
	
	return
	    subMap(start, end).entrySet();
    }

    /**
     * Returns the maximum range of this profile, that is a range consisting of 
     * the minimum and maximum time entries.
     *
     * @return a <code>Range</code> value
     */
    public Range getRange() {
	return new Range(((Double)firstKey()).doubleValue(),
			 ((Double)lastKey()).doubleValue() + overhead);
    }

    // implementation of java.util.Observer interface

    /**
     * Called when notified of change of the entity.
     * @param entity that changed
     * @param time time of change
     */
    public void update(Observable entity, Object time) {
	put(time, this.entity.getClone());
    }

    
}// Profile
