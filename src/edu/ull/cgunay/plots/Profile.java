
package neuroidnet.ntr.plots;

import java.io.*;
import java.util.*;
import java.util.Observable;
import java.util.Observer;

// $Id$
/**
 * Keeps track of the time profile of an entity that changes through time.
 * Saves pairs consisting of a time and an  associated value
 * for the entity.
 * Currently saves a simple <code>double</code> value for each time instant.
 *
 * <p>Created: Mon Apr  8 17:07:04 2002
 * <p>Modified: $Date$
 *
 * @see Grapher;
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public class Profile extends TreeMap implements Observer, Serializable {
    Profilable entity;

    public Profile (Profilable entity, Object time) {
	connectTo(entity, time);
    }

    public void connectTo(Profilable entity, Object time) {
	this.entity = entity;
	entity.addObserver(this);

	// initial entry
	update(entity, time);
    }

    public Iterator iterator(Range range) {
	return
	    subMap(new Double(range.getStart()), new Double(range.getEnd())).values().iterator();
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
