package edu.ull.cgunay.utils.plots;

import edu.ull.cgunay.utils.plots.Plot;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * A simple single axis, single data plot. An <i>x</i> variable is
 * predefined in the dataset (<code>data</code>) corresponding to the
 * x-axis, so that <code>body()</code> method can return an expression
 * based on <i>x</i>. A <code>preamble()</code> method is provided for
 * any other definitions.
 *
 * <p>Created: Wed Nov  6 22:37:04 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

abstract public class SimplePlot extends Plot  {
    public SimplePlot (String label, Range range) {
	super(label, range);
    }

    /**
     * Returns body that appears inside the plot command for the specific plot.
     *
     * @return a <code>String</code> value
     */
    abstract public String body();

    /**
     * Helper function to set grapher before calling body.
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String body(Grapher grapher) {
	this.grapher = grapher;
	return body();
    }

    /**
     * Returns a preamble to appear before the plot command. 
     * By default returns empty string.
     * 
     * @return a <code>String</code> value
     */
    public String preamble() { return ""; }

    /**
     * Returns a preamble to appear before the plot command. 
     * By default returns empty string.
     * 
     * @return a <code>String</code> value
     */
    public String preamble(Grapher grapher) {
	this.grapher = grapher;
	return preamble();
    }

    /**
     * The single axis data of this plot
     */
    Grapher.Data data;

    /**
     * A new way to describe plots in terms of <code>Grapher</code>
     * capabilities.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String recipe(final Grapher grapher) {
	data = grapher.new Data("default", label) {
		public void init() {
		    variables.put("x", range); // Null range accepted
		}

		public String xExpression() {
		    return "x";
		}

		public String yExpression() {
		    /* SHOULD BE: "Grapher.this" instead of "grapher",
		     * but Java is buggy! */
		    return body(grapher); 
		}
	    };

	Grapher.Axis axis = grapher.createAxis();
	axis.addData(data);
	axis.setRange(range);

	return preamble(grapher) + axis.getString();
    }

    
}// SimplePlot
