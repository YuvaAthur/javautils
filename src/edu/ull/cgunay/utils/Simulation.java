
package edu.ull.cgunay.utils;

import java.io.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * A general steppable simulation abstraction.
 * <p>TODO: Shouldn't it be an interface?
 *
 * <p>Created: Sat Apr 27 18:37:05 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public interface Simulation extends Serializable {
    /**
     * Step function of the simulator. 
     *
     */
    void step();

    /**
     * Initialization function. Empty by default.
     *
     */
    void init();
    
}// Simulation
