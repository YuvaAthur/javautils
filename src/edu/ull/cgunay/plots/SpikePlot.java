
package edu.ull.cgunay.plots;

import edu.ull.cgunay.utils.*;

import java.util.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * Plot of a collection of spike timings.
 *
 *
 * <p>Created: Mon Apr  8 17:50:01 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
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

    public SpikePlot(Vector spikes) {
	this(null, null, spikes);
    }

    /**
     * Creates a "impulse" type dataset for the spike plot and
     * associates with a single axis.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String recipe(final Grapher grapher) {
	Grapher.Data data = grapher.new Data("impulse", label) {
		void init() {
		    addVariable("spikes", spikes);

		    // Create a equidimension vector with ones
		    final Vector ones = new Vector(spikes.size());
		    new UninterruptedIteration() {
			public void job(Object o) {
			    ones.add(new Double(1));
			}
		    }.loop(spikes);
		    addVariable("ones", ones);
		}

		public String xExpression() {
		    // don't use the delegation methods anymore!
		    return grapher.variable("spikes"); 
		}

		public String yExpression() {
		    return grapher.variable("ones"); 
		}
	    };

	data.init();

	Grapher.Axis axis = grapher.createAxis();
	axis.addData(data);
	axis.setRange(range);

	axes = new LinkedList();
	axes.add(axis);

	return /*preamble(grapher) + */axis.getString();
    }
    
}// SpikePlot
