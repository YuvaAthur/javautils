
package edu.ull.cgunay.plots;

import java.util.*;
import java.io.*;
import java.lang.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * An entity whose contents can be recorded in a <code>Profile</code>. Allows to be
 * both <code>Observable</code> and <code>Cloneable</code>.
 *
 * <p>Created: Tue Apr  9 15:52:21 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Profile
 */

abstract public class Profilable extends Observable implements Cloneable, Serializable  {

    /**
     * Dummy constructor.
     *
     */
    public Profilable () { }

    /**
     * Returns its clone.
     *
     * @return an <code>Object</code> value
     */
    public Object getClone() {
	try {
	    return clone(); 	    
	} catch (CloneNotSupportedException e) {
	    throw new Error("This shouldn't happen!");
	} // end of try-catch
    }

    /**
     * Return a representation if the entity as a <code>double</code> value.
     * This is used for plotting a primitive value's profile.
     * <p> For most entities conversion to such a simple
     * representation is not possible. In these cases calling this method
     * should result in a fatal error.
     * @return a <code>double</code> value
     */
    abstract public double doubleValue();
}// Profilable
