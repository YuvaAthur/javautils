
package neuroidnet.ntr.plots;

import java.util.Vector;

// $Id$
/**
 * SpikePlot.java
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

	// if not specified, take the max range
	if (range == null) {
	    this.range = new Range(((Double)spikes.firstElement()).doubleValue(),
				   ((Double)spikes.lastElement()).doubleValue());
	} // end of if (range == null)
	
    }

    /**
     * Delegates the request to the <code>Grapher</code> instance
     * to do the appropriate things according to this subclass.
     * @see Grapher#plot(ProfilePlot)
     * @see Grapher#plot(SpikePlot)
     * @see Grapher#plot(PotentialPlot)
     * @return a <code>String</code> value
     */
    public String plot(Grapher grapher) {
	this.grapher = grapher;	
	return grapher.plot(this);
    }

    /**
     * Not applicable.
     *
     * @return a <code>String</code> value
     */
    public String body() { return null; }
    
}// SpikePlot
