
package neuroidnet.ntr.plots;

import neuroidnet.utils.*;

import java.io.*;
import java.util.*;

// $Id$
/**
 * <code>Grapher</code> implementation for the plotting with the
 * <code>gnuplot</code> program.
 *
 *
 * <p>Created: Mon Apr  8 17:53:58 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public class GNUPlot extends Grapher {

    /**
     * Creates a new <code>GNUPlot</code> instance.
     * @exception GrapherNotAvailableException if the cannot instantiate the grapher process
     */
    public GNUPlot () throws GrapherNotAvailableException {	
	super("gnuplot -display :0 -");
    }

    // overriding methods from neuroidnet.ntr.plots.Grapher 

    /**
     */
    public String plot(final SpikePlot plot) {
	String retval =
	    "plot [" + assign("x", range(plot.getRange())) + "] " +
	    "'-'" + getTitle(plot) + " with impulses" + "\n";

	TaskWithReturn stringTask = new StringTask() {
		Range range = plot.getRange();

		public void job(Object o) {
		    double time = ((Double)o).doubleValue();
		    if (time > range.getStart() && time <= range.getEnd()) {
			super.job(time + " 1\n"); 
		    } // end of if 
		}
	    };

	Iteration.loop(plot.getSpikes(), stringTask);

	return retval + stringTask.getValue() + "EOF\n";
    }

    public String plot(Plot plot) {

	Range range = plot.getRange();
	return
	    "plot " + ((range!=null) ? "[" + assign("t", range(range)) + "] " : "") +
	    plot.body() + getTitle(plot) + "\n";
    }

    String getTitle(Plot plot) {
	String title = plot.getTitle();
	return (title != null) ? " title \"" + title + "\"" : "";
    }

    public String multiPlot(String title, Collection plots) {
	// set title
	// go into multiplot mode
	// count number of plots, -> divide view into subplots
	// find maximum range by iterating on all plots
	// iterate on each plot and plot them with size prefix
	// leave multiplot mode
	return null;
    }
    
    /**
     */
    public String def_func(String name, String[] params, String body) {
	return func(name, params) + "=" + body + "\n";
    }

    public void close() {
	grapherOut.println("exit");
	grapherOut.flush();
    }

    }// GNUPlot
