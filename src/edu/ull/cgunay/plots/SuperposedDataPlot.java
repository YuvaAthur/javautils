
package edu.ull.cgunay.plots;

import edu.ull.cgunay.plots.Plot;
import edu.ull.cgunay.utils.*;

import java.util.List;
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
     * Takes a <code>List</code> of datasets to plot.
     *
     * @param range a <code>Range</code> value
     * @param datas a <code>List</code> value
     */
    public SuperposedDataPlot (Range range, List datas) {
	super(null, range);
	this.datas = (Grapher.Data[]) datas.toArray(new Grapher.Data[0]);
    }

    /**
     * Combines given datasets to be displayed on a single axis.
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
		    axis.setFontSize(fontSize);
		}

		public Object getValue() {
		    return axis;
		}
	    };

	// should be kept in plothandle!
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
