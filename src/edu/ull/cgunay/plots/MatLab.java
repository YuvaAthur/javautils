
package neuroidnet.ntr.plots;

import neuroidnet.utils.*;

import java.io.*;
import java.util.*;

// $Id$
/**
 * MatLab.java
 *
 *
 * <p>Created: Fri Apr 12 22:18:46 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

public class MatLab extends Grapher  {
    public MatLab () throws GrapherNotAvailableException {	
	super("matlab");
    }

    /**
     */
    public String plot(final SpikePlot plot) {
	// converts the given array into matlab style array string (should be made a function)
	TaskWithReturn stringTask = new StringTask("[ ", " ]") {
		Range range = plot.getRange();

		public void job(Object o) {
		    double time = ((Double)o).doubleValue();
		    if (time > range.getStart() && time <= range.getEnd()) 
			super.job(time + " "); // gets added to retval
		}
	    };

	Iteration.loop(plot.getSpikes(), stringTask);

	return 
	    assign("t", (String)stringTask.getValue()) + ";\n" +
	    "stem(t, ones(size(t,2)), 'filled');\n" +
	    "legend('" + getTitle(plot) + "');\n";

    }

    public String plot(Plot plot) {

	return
	    assign("t", range(plot.getRange())) + ";\n" +
	    "plot (t, " + plot.body() + ");\n" +
	    "legend('" + getTitle(plot) + "');\n";
    }

    String getTitle(Plot plot) {
	String title = plot.getTitle();
	return (title != null) ? title : "";
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
	return
	    "function r = " + func(name, params) + "\n" +
	    "r = " + body + "\n";
    }

    public String range(Range range) {
	double start = range.getStart(), end = range.getEnd();
	return start + ":" + (end - start) / points  + ":" + end;
    }

    public void close() {
	grapherOut.println("exit");
	grapherOut.flush();
    }
    
}// MatLab
