
package edu.ull.cgunay.utils.plots;

import java.util.*;

// $Id$
/**
 * Plot of a collection of spike timings.
 *
 *
 * <p>Created: Mon Apr  8 17:50:01 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public class SpikePlot extends Plot  {

    /**
     * Values of the spikes to be plotted.
     */
    Vector spikes;
    
    /**
     * Get the value of spikes.
     * @return value of spikes.
     */
    public Vector getSpikes() {
	return spikes;
    }
    
    /**
     * Set the value of spikes.
     * @param v  Value to assign to spikes.
     */
    public void setSpikes(Vector  v) {
	this.spikes = v;
    }
    

    public SpikePlot (String title, Range range, Vector spikes) {
	super(title, range);

	this.spikes = spikes;

	try {
	    // if not specified, take the max range
	    if (range == null) 
		this.range = new Range(((Double)spikes.firstElement()).doubleValue(),
				       ((Double)spikes.lastElement()).doubleValue() + 0.001);

	} catch (NoSuchElementException e) {
	    // do nothing, keep null range
	} // end of try-catch
	
    }

    /**
     * Not applicable.
     * <p>TODO: Should be '-' to be compliant with multiplot propositions.
     * Need to define a vector(?) method to dump in the data instead of 
     * treating <code>SpikePlot</code>s specially.
     * @return a <code>String</code> value
     */
    public String body() { return null; }
    
}// SpikePlot
