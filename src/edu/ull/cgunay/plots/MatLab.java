
package neuroidnet.ntr.plots;

import neuroidnet.utils.*;

import java.io.*;

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
	    "plot(t, ones(size(t,2)), 'filled');\n";
    }

    public String plot(Plot plot) {
	return "plot (" + range(plot.getRange()) + ", " + plot.body() + ");\n";
    }

    /**
     */
    public String def_func(String name, String[] params, String body) {
	return
	    "function r = " + func(name, params) + "\n" +
	    "r = " + body + "\n";
    }

    public void close() {
	grapherOut.println("exit");
	grapherOut.flush();
    }
    
}// MatLab
