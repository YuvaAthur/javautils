
package edu.ull.cgunay.plots;

import edu.ull.cgunay.plots.Plot;
import edu.ull.cgunay.utils.*;

import java.util.LinkedList;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * Plots a single dataset in a single axis.
 *
 *
 * <p>Created: Mon Nov 11 15:08:58 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version v2.0, $Revision$ for this file.
 * @see Grapher.Data
 */

public class SingleDataPlot extends Plot  {

    Grapher.Data data;

    /**
     * Takes a dataset to plot.
     *
     * @param label a <code>String</code> value
     * @param range a <code>Range</code> value
     * @param data to plot
     */
    public SingleDataPlot (Range range, Grapher.Data data) {
	super(data.getLabel(), range);
	this.data = data;
    }

    /**
     * Creates an "errorbar" type dataset for the spike plot and
     * associates with a single axis.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String recipe(final Grapher grapher) {
	Grapher.Axis axis = grapher.createAxis();
	axis.addData(data);
	axis.setRange(range);
	axis.setTitle(title);
	axis.setXLabel(xLabel);
	axis.setYLabel(yLabel);
	axis.setFontSize(fontSize);

	axes = new LinkedList();
	axes.add(axis);

	return /*preamble(grapher) + */axis.getString();
    }
    
    
}// SingleDataPlot
