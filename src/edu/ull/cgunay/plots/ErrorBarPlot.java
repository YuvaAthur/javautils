
package edu.ull.cgunay.plots;

import edu.ull.cgunay.plots.Plot;
import edu.ull.cgunay.utils.*;

import java.util.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * A simple plot that features a single <code>ErrorDAta</code>.
 *
 *
 * <p>Created: Mon Nov 11 15:08:58 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version v2.0, $Revision$ for this file.
 * @see ErrorValue
 */

public class ErrorBarPlot extends Plot  {

    Profile errorValues;

    /**
     * Takes list of <code>ErrorValue</code>s to plot.
     *
     * @param label a <code>String</code> value
     * @param range a <code>Range</code> value
     * @param errorValues a <code>Vector</code> value
     */
    public ErrorBarPlot (String label, Range range, Profile errorValues) {
	super(label, range);
	this.errorValues = errorValues;
    }

    /**
     * @param label a <code>String</code> value
     * @param range a <code>Range</code> value
     * @param values a <code>Vector</code> value, for points on the graph
     * @param minValues a <code>Vector</code> value, for lower limit of the points
     * @param maxValues a <code>Vector</code> value, for upper limit of the points
     */
    /*public ErrorBarPlot (String label, Range range, Vector xAxis, Vector values,
			 Vector minValues, Vector maxValues) {
	super(label, range);
	this.xAxis = xAxis;
	this.values = values;
	this.minValues = minValues;
	this.maxValues = maxValues;
    }*/

    /**
     * Creates an "errorbar" type dataset for the spike plot and
     * associates with a single axis.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String recipe(final Grapher grapher) {
	Grapher.ErrorData data = grapher.new ErrorData(label, errorValues);

	Grapher.Axis axis = grapher.createAxis();
	axis.addData(data);
	axis.setRange(range);
	axis.setTitle(title);
	axis.setXLabel(xLabel);
	axis.setYLabel(yLabel);

	axes = new LinkedList();
	axes.add(axis);

	return /*preamble(grapher) + */axis.getString();
    }
    
    
}// ErrorBarPlot
