
package edu.ull.cgunay.utils.plots;

// $Id$
/**
 * Encapsulation of a <code>double</code> value as a <code>Profilable</code> entity.
 *
 * <p>Created: Sun Apr 14 22:26:29 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public class ProfilableDouble extends Profilable  {
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
    
}// ProfilableDouble
