
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
		    if (time >= range.getStart() && time <= range.getEnd()) 
			super.job(time + " "); // gets added to retval
		}
	    };

	Iteration.loop(plot.getSpikes(), stringTask);

	return 
	    assign("t", (String)stringTask.getValue()) + ";\n" +
	    "stem(t, ones(size(t,2)), 'filled');\n" +
	    "legend('" + getTitle(plot) + "');\n";

    }

    /**
     * Matlab specific plotting command. Calls Plot.body().
     *
     * @see Plot#body
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    public String plot(Plot plot) {
	Range range = plot.getRange();

	return
	    plot.preamble() +
	    assign("t", "[" + ((range!=null) ? range(range) : "")+ "]") + ";\n" +
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
     * Returns a <code>String</code> that is a scalar product for MatLab which 
     * is "(a) .* (b)".
     *
     * @param a a <code>String</code> value
     * @param b a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String mul(String a, String b) {
	return paren(a) + ".*" + paren(b);
    }

    /**
     * Creates a file in the current directory named with the function.
     */
    public String def_func(String name, String[] params, String body) {
	try {
	    PrintWriter out = new PrintWriter(new FileOutputStream(name + ".m", false));
	    out.println("function r = " + func(name, params) + "\n" +
			"r = " + body + ";\n");
	    out.close();
	} catch (IOException e) {
	    throw new Error("Cannot produce MatLab function file " + name +
			    ".m in current directory.");
	} // end of try-catch
	
	return "";
    }

    public String range(Range range) {
	double start = range.getStart(), end = range.getEnd();
	return start + ":" + (end - start) / points  + ":" + end;
    }

    public void close() {
	grapherOut.println("exit");
	grapherOut.flush();
    }

    public void setWindow(int windowNumber) {
	grapherOut.println("figure;"); // Opens a new figure
    }
    
}// MatLab
