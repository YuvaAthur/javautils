
package neuroidnet.ntr.plots;

// $Id$
/**
 * ProfilableDouble.java
 *
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

    public double doubleValue() { return value; } 
    
}// ProfilableDouble
