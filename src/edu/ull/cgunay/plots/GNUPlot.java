
package edu.ull.cgunay.plots;

import edu.ull.cgunay.utils.*;

import java.io.*;
import java.util.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * <code>Grapher</code> implementation for plotting with the
 * <code>gnuplot</code> program.
 * <p>See description in <code>Grapher</code> for the usage.
 *
 *
 * <p>Created: Mon Apr  8 17:53:58 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Grapher
 */

public class GNUPlot extends Grapher {

    /**
     * Creates a new <code>GNUPlot</code> instance.
     * @exception GrapherNotAvailableException if the cannot instantiate the grapher process
     */
    public GNUPlot () throws GrapherNotAvailableException {	
	super("gnuplot -display :0 -");
	out.println("set grid");	// Sets grid mode
    }

    // overriding methods from edu.ull.cgunay.plots.Grapher 

    /**
     */
    public String plotToString(final SpikePlot plot) {
	Range range = plot.getRange();

	return
	    "plot " + ((range!=null) ? "[" + assign("x", range(range)) + "] " : "") +
	    "'-'" + getLabel(plot) + " with impulses" + "\n" +
	    new StringTask("", "EOF\n") {
		Range range = plot.getRange();

		public void job(Object o) {
		    double time = ((Double)o).doubleValue();
		    if (time >= range.getStart() && time <= range.getEnd()) {
			super.job(time + " 1\n"); 
		    } // end of if 
		}
	    }.getString(plot.getSpikes());
    }

    /**
     * gnuplot specific plotting command, calls plot.body().
     * 
     * @see Plot#body
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    public String plotToString(Plot plot) {
	Range range = plot.getRange();
	return
	    plot.preamble() +
	    "plot " + ((range!=null) ? "[" + assign("t", range(range)) + "] " : "") +
	    plot.body() + getLabel(plot) + "\n";
    }

    String getLabel(Plot plot) {
	String label = plot.getLabel();
	return (label != null) ? " title \"" + label + "\"" : "";
    }

    /**
     * Not implemented yet!
     *
     * @param title a <code>String</code> value
     * @param plots a <code>Collection</code> value
     * @return a <code>String</code> value
     */
    public PlotHandle multiPlot(Collection plots, PrintStream out) {
	// set title
	// go into multiplot mode
	// count number of plots, -> divide view into subplots
	// find maximum range by iterating on all plots
	// iterate on each plot and plot them with size prefix
	// leave multiplot mode
	return null;
    }
    
    /**
     * gnuplot style function definition.
     */
    public String def_func(String name, String[] params, String body) {
	return func(name, params) + "=" + body + "\n";
    }

    /**
     * Exits from gnuplot.
     *
     */
    public void close() {
	out.println("exit");
    }

    public void setWindow(int windowNumber) {
	out.println("set terminal x11 " + windowNumber);
    }

    /**
     * Sets terminal type to postscript eps and directs the output to given filename.
     *
     * @param handle a <code>PlotHandle</code> value
     * @param filename a <code>String</code> value
     */
    public void writeEPS(PlotHandle handle, String filename) {
	out.println("set terminal postscript eps\n" +
			   "set output \"" + filename + "\"\n" +
			   plotToString(handle.getPlot()));
	waitForResponse();
	System.out.println(response());
    }

    }// GNUPlot
