
package neuroidnet.ntr.plots;

import java.util.*;
import java.lang.*;

// $Id$
/**
 * Profilable.java
 *
 *
 * <p>Created: Tue Apr  9 15:52:21 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public class Profilable extends Observable implements Cloneable  {
    public Profilable () {	
    }

    public Object getClone() {
	try {
	    return clone(); 	    
	} catch (CloneNotSupportedException e) {
	    throw new Error("This shouldn't happen!");
	} // end of try-catch
    }
    
}// Profilable
