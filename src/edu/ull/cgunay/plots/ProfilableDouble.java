
package edu.ull.cgunay.utils.plots;

import java.io.*;
import java.util.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * Encapsulation of a <code>double</code> value as a <code>Profilable</code> entity.
 *
 * <p>Created: Sun Apr 14 22:26:29 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Profilable
 */

public class ProfilableDouble extends Profilable implements Serializable, Comparable {
    double value;

    public ProfilableDouble (double value) {
	this.value = value;
    }

    /**
     * Returns the double value encapsulated. 
     *
     * @return a <code>double</code> value
     */
    public double doubleValue() { return value; } 

    public int compareTo(Object that) {
	return new Double(this.value).compareTo(new Double(((ProfilableDouble)that).value));
    }
}// ProfilableDouble
