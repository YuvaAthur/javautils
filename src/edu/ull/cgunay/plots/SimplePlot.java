package edu.ull.cgunay.plots;

import edu.ull.cgunay.plots.Plot;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * A simple single axis, single data plot. An <i>t</i> variable is
 * predefined in the dataset (<code>data</code>) corresponding to the
 * x-axis, so that <code>body()</code> method can return an expression
 * based on <i>t</i>. A <code>preamble()</code> method is provided for
 * any other definitions.
 * <p>In general the plot is assumed to be a <code>String</code> composed of a preamble,
 * a plot command and some post commands or data.
 * The <code>preamble()</code> method represents the information required before the actual plot
 * command, and the <code>body()</code> method represents the part that will appear as the body of
 * the plot command. The range of the plot that will appear as part of the plot command is
 * kept as the <code>range</code> attribute.
 * <p>As a simple example here is how to plot the y = t + 5 graph: <br>
 * 
 * <blockquote><pre>
 * class MyPlot extends SimplePlot {
 *   MyPlot(Range range) { super("My plot!", range); }
 *   String body() {
 *     return add("t","5");
 *   }
 * }
 * </pre></blockquote>
 *
 * <p>Created: Wed Nov  6 22:37:04 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see #preamble
 * @see #body
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
     * The single axis of this plot.
     */
    Grapher.Axis axis;

    /**
     * Prepare the <code>axis</code> and <code>data</code> variables
     * with given <code>grapher</code>.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public void prepare(final Grapher grapher) {
	data = grapher.new Data("default", label) {
		public void init() {
		    variables.put("t", range); // Null range accepted
		}

		public String preamble() {
		    return SimplePlot.this.preamble(grapher);
		}

		public String xExpression() {
		    return "t";
		}

		public String yExpression() {
		    /* SHOULD BE: "Grapher.this" instead of "grapher",
		     * but Java is buggy! */
		    return body(grapher); 
		}
	    };

	// TODO: Is this the right place for this?
	data.init();

	axis = grapher.createAxis();
	axis.addData(data);

	HasAxisLabels decor = this;
	axis.setRange(decor.getRange());
	axis.setTitle(decor.getTitle());
	axis.setXLabel(decor.getXLabel());
	axis.setYLabel(decor.getYLabel());
	axis.setFontSize(decor.getFontSize());
    }

    /**
     * A new way to describe plots in terms of <code>Grapher</code>
     * capabilities.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String recipe(Grapher grapher) {
	prepare(grapher);

	return preamble(grapher) + axis.getString();
    }

    
}// SimplePlot
