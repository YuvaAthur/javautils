
package edu.ull.cgunay.utils;

// $Id$
/**
 * A general steppable simulation abstraction.
 *
 *
 * <p>Created: Sat Apr 27 18:37:05 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

abstract public class Simulation  {
    public Simulation () { }

    /**
     * Step function of the simulator. 
     *
     */
    abstract public void step();

    /**
     * Initialization function. Empty by default.
     *
     */
    public void init() { }
    
}// Simulation
