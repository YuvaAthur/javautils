
package edu.ull.cgunay.utils.plots;

import edu.ull.cgunay.utils.plots.Plot;
import edu.ull.cgunay.utils.*;

import java.util.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * ErrorBarPlot.java
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

    /**
     * Vectors representing values and their corresponding minimum and
     * maximum limits.
     */
    Collection values, minValues, maxValues, xAxis;

    /**
     * Takes list of <code>ErrorValue</code>s to plot.
     *
     * @param title a <code>String</code> value
     * @param range a <code>Range</code> value
     * @param errorValues a <code>Vector</code> value
     */
    public ErrorBarPlot (String title, Range range, Profile errorValues) {
	super(title, range);

	int size = errorValues.size();

	xAxis = errorValues.keySet();
	values = new Vector(size);
	minValues = new Vector(size);
	maxValues = new Vector(size);

	new UninterruptedIteration() {
	    public void job(Object o) {
		ErrorValue e = (ErrorValue) o;
		values.add(new Double(e.value));
		minValues.add(new Double(e.minValue));
		maxValues.add(new Double(e.maxValue));
	    }
	}.loop(errorValues.values());
    }

    /**
     * @param title a <code>String</code> value
     * @param range a <code>Range</code> value
     * @param values a <code>Vector</code> value, for points on the graph
     * @param minValues a <code>Vector</code> value, for lower limit of the points
     * @param maxValues a <code>Vector</code> value, for upper limit of the points
     */
    public ErrorBarPlot (String title, Range range, Vector xAxis, Vector values,
			 Vector minValues, Vector maxValues) {
	super(title, range);
	this.xAxis = xAxis;
	this.values = values;
	this.minValues = minValues;
	this.maxValues = maxValues;
    }

    /**
     * Creates an "errorbar" type dataset for the spike plot and
     * associates with a single axis.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String recipe(final Grapher grapher) {
	Grapher.ErrorData data = grapher.new ErrorData(label) {
		void init() {
		    addVariable("xAxis", xAxis);
		    addVariable("values", values);
		    addVariable("minValues", minValues);
		    addVariable("maxValues", maxValues);
		}

		public String xExpression() {
		    // don't use the delegation methods anymore!
		    return grapher.variable("xAxis"); 
		}

		public String yExpression() {
		    return grapher.variable("values"); 
		}

		public String minExpression() {
		    return grapher.variable("minValues"); 
		}

		public String maxExpression() {
		    return grapher.variable("maxValues"); 
		}

	    };

	data.init();

	Grapher.Axis axis = grapher.createAxis();
	axis.addData(data);
	axis.setRange(range);

	axes = new LinkedList();
	axes.add(axis);

	return /*preamble(grapher) + */axis.getString();
    }
    
    
}// ErrorBarPlot
