
package edu.ull.cgunay.plots;

import edu.ull.cgunay.utils.*;

import java.util.List;
import java.util.LinkedList;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * A plot that can contain multiple axes, each containing multiple datasets. See
 * <code>SuperposedDataPlot</code> for an example of a plot with a
 * <em>single</em> axis plot with <em>multiple</em> data.
 *
 * <p> An object can be created by either providing
 * Grapher-independent <code>AxisTemplate</code>s or -dependent
 * <code>Axis</code>'s.
 *
 * <p>Created: Tue May 6 18:17:51 2003
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Grapher.Axis
 * @see AxisTemplate
 */

public class MultiDataPlot extends SuperposedDataPlot {

    /**
     * Describe variable <code>axes</code> here.
     *
     */
    AxisTemplate[] axisTemplates;

    /**
     * Creates a new <code>MultiDataPlot</code> instance from
     * <code>AxisTemplate</code>s. The plot object does not contain
     * any properties, each axis should have its own set of
     * properties.
     *
     * <p> By default arranges all plots vertically and aligns ranges
     * according to the maximal range.
     *
     * @param axisTemplates an <code>AxisTemplate[]</code> value
     * @see HasAxisLabels
     */
    public MultiDataPlot(AxisTemplate[] axisTemplates) {
	super(null); 
	this.axisTemplates = axisTemplates;
    }


    /**
     * Creates a new <code>MultiDataPlot</code> instance from
     * <code>AxisTemplate</code>s. Adjusts all vertically aligned
     * axisTemplates to conform to the same range.
     *
     * @param axisTemplates an <code>AxisTemplate[]</code> value
     * @param range a <code>Range</code> value to impose on child axes.
     */
    public MultiDataPlot(AxisTemplate[] axisTemplates, Range range) {
	super(range); 
	this.axisTemplates = axisTemplates;
    }

    /**
     * List of <code>Grapher.Axis</code>'s.
     */
    List axes;

    /**
     * Creates a new <code>MultiDataPlot</code> instance from a
     * <code>List</code> of <code>Grapher.Axis</code>.
     *
     * @param axes a <code>List</code> value
     */
    public MultiDataPlot(List axes) {
	super(null); 
	this.axes = axes;
    }

    /**
     * Renders given <code>AxisTemplate</code>s to create
     * <code>Grapher.Axis</code>'s with the given
     * <code>Grapher</code>. Adjusts all axes to a given or maximal
     * range and creates a <code>Grapher.MultiAxes</code> object.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String recipe(Grapher grapher) {
	if (axes == null) {
	    axes = new LinkedList(); // TODO: There is one defined already in Plot?

	    // TODO: Even the following can go to Grapher?
	    new UninterruptedIteration() {
		public void job(Object o) {
		    AxisFactory factory = new AxisFactory((AxisTemplate) o);
		    factory.job(grapher);
		    axes.add((Grapher.Axis) factory.getValue());
		}
	    }.loop(axisTemplates);
	} // end of if (axes == null)

	// Find the maximal range if no range is specified
	if (range == null) 
	    range = Plot.getMaxRange(axes);

	// Adjust ranges of all axes
	new UninterruptedIteration() {
	    public void job(Object o) {
		((Grapher.Axis)o).setRange(range);
	    }
	}.loop(axes);

	return grapher.createMultiAxes(axes).getString();
    }    
}
