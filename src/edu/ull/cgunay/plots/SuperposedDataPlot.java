
package edu.ull.cgunay.utils.plots;

import edu.ull.cgunay.utils.plots.Plot;
import edu.ull.cgunay.utils.*;

import java.util.LinkedList;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * Plots multiple datasets on a single axis.
 *
 *
 * <p>Created: Mon Nov 11 15:08:58 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version v2.0, $Revision$ for this file.
 * @see Grapher.Data
 */

public class SuperposedDataPlot extends Plot  {

    Grapher.Data[] datas;

    /**
     * Takes a dataset to plot.
     *
     * @param label a <code>String</code> value
     * @param range a <code>Range</code> value
     * @param data to plot
     */
    public SuperposedDataPlot (Range range, Grapher.Data[] datas) {
	super(null, range);
	this.datas = datas;
    }

    /**
     * Creates an "errorbar" type dataset for the spike plot and
     * associates with a single axis.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String recipe(final Grapher grapher) {

	TaskWithReturn axisFactory = new TaskWithReturn() {
		Grapher.Axis axis;

		public void job(Object o) {
		    Grapher grapher = (Grapher) o;
		    axis = grapher.createAxis();

		    new UninterruptedIteration() {
			public void job(Object o) {
			    axis.addData((Grapher.Data)o);
			}
		    }.loop(datas);
	
		    axis.setRange(range);
		    axis.setTitle(title);
		    axis.setXLabel(xLabel);
		    axis.setYLabel(yLabel);
		}

		public Object getValue() {
		    return axis;
		}
	    };

	axes = new LinkedList();
	axes.add(axisFactory);

	try {
	    axisFactory.job(grapher);

	    Grapher.Axis axis = (Grapher.Axis) axisFactory.getValue();
	    return /*preamble(grapher) + */axis.getString();
	     
	} catch (TaskException e) {
	    throw new Error("fatal: not supposed to happen");
	} // end of try-catch
	
    }
    
    
}// SuperposedDataPlot
