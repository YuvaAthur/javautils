
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
     * Creates a new <code>SuperposedDataPlot</code> instance for
     * internal use by subclasses.
     *
     * @param range a <code>Range</code> value
     */
    SuperposedDataPlot (Range range) {
	super(null, range);
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
     * Creates an <code>Grapher.Axis</code> from given
     * <code>AxisTemplate</code> when a <code>Grapher</code> is
     * available.
     *
     * <p>TODO: Inconsistent; Data's should be fabricated at a later
     * time, as well!
     */
    class AxisFactory implements TaskWithReturn {
	/**
	 * <code>Axis</code> to be created.
	 */
	Grapher.Axis axis;

	/**
	 * Datas to be added to the <code>Axis</code>.
	 * @see Grapher.Data
	 */
	Grapher.Data[] datas;

	/**
	 * Holds axis decorations.
	 */
	HasAxisLabels decor;

	/**
	 * Creates a new <code>AxisFactory</code> instance.
	 *
	 * @param datas a <code>Collection</code> of
	 * <code>Grapher.Data</code>s
	 */
	AxisFactory(Grapher.Data[] datas, HasAxisLabels decor) {
	    this.datas = datas;
	    this.decor = decor;
	}

	/**
	 * Creates a new <code>AxisFactory</code> instance that will
	 * create a <code>Grapher.Axis</code> from an
	 * <code>AxisTemplate</code>.
	 *
	 * @param template an <code>AxisTemplate</code> value
	 */
	AxisFactory(AxisTemplate template) {
	    this.datas = template.getDatas();
	    this.decor = template;
	}

	/**
	 * Create the axis, add all datas to it, and decorate it.
	 *
	 * @param o a <code>Grapher</code> object
	 */
	public void job(Object o) {
	    Grapher grapher = (Grapher) o;
	    axis = grapher.createAxis();

	    new UninterruptedIteration() {
		public void job(Object o) {
		    axis.addData((Grapher.Data)o);
		}
	    }.loop(datas);
	
	    axis.setRange(decor.getRange());
	    axis.setTitle(decor.getTitle());
	    axis.setXLabel(decor.getXLabel());
	    axis.setYLabel(decor.getYLabel());
	    axis.setFontSize(decor.getFontSize());
	}

	public Object getValue() {
	    return axis;
	}
    }

    /**
     * Combines given datasets to be displayed on a single axis.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String recipe(final Grapher grapher) {

	TaskWithReturn axisFactory = new AxisFactory(datas, this);

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
