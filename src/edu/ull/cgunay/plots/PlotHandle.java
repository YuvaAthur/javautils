
package edu.ull.cgunay.utils.plots;

import java.util.*;
import edu.ull.cgunay.utils.*;

// $Id$
/**
 * Entity that associates a plot and a grapher at display time. This is the handle
 * that represents the visual representation of the plot. It contains pointers to the plot,
 * the grapher, and the associated window number in the grapher.
 *
 * <p>Created: Sun Apr 28 12:54:13 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Plot
 * @see Grapher
 * @see Grapher#windowNumber
 */
public class PlotHandle  {
    /**
     * The description of the <code>Plot</code> encapsulated.
     *
     */
    Plot plot;

    /**
     * The description of the <code>Plot</code> collection encapsulated.
     *
     */
    Collection plots;
    
    /**
     * Get the value of plot.
     * @return value of plot.
     */
    public Plot getPlot() {
	return plot;
    }
        
    /**
     * The <code>Grapher</code> that displayed the <code>Plot</code>.
     *
     */
    final Grapher grapher;
    
    /**
     * Get the value of grapher.
     * @return value of grapher.
     */
    public Grapher getGrapher() {
	return grapher;
    }

    /**
     * The <code>windowNumber</code> in the associated <code>Grapher</code>.
     * @see Grapher#windowNumber
     */
    final int windowNumber;
    
    /**
     * Get the value of <code>windowNumber</code>.
     * @return value of <code>windowNumber</code>.
     */
    public int getWindowNumber() {
	return windowNumber;
    }

    /**
     * Association of a plot with the grapher.
     *
     * @param plot a <code>Plot</code> value
     * @param grapher a <code>Grapher</code> value
     * @param windowNumber an <code>int</code> value
     */
    public PlotHandle (Plot plot, Grapher grapher, int windowNumber) {
	this.plot = plot;
	this.grapher = grapher;
	this.windowNumber = windowNumber;

	plot.setGrapher(grapher);
    }

    /**
     * Association of a plot group with the grapher.
     *
     * @param plots a <code>Collection</code> value
     * @param grapher a <code>Grapher</code> value
     * @param windowNumber an <code>int</code> value
     * @see Grapher#multiPlot
     */
    public PlotHandle (Collection plots, final Grapher grapher, int windowNumber) {
	this.plots = plots;
	this.grapher = grapher;
	this.windowNumber = windowNumber;

	new UninterruptedIteration() {
	    public void job(Object o) {
		Plot plot = (Plot) o;
		plot.setGrapher(grapher); 
	    }
	}.loop(plots);
    }

    /**
     * Exports an EPS file of the current plot with the grapher.
     * Calls Grapher.writeEPS()
     * @param filename a <code>String</code> value
     * @see Grapher#writeEPS
     */
    public void writeEPS(String filename) {
	grapher.writeEPS(this, filename);
    }
    
}// PlotHandle
