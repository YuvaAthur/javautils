
package edu.ull.cgunay.plots;

import java.util.*;
import java.io.*;
import edu.ull.cgunay.utils.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * Grapher independent description for plots. The plot is converted to 
 * a grapher specific String representation when the <code>plot(Grapher)</code> method 
 * is invoked. 
 * <p> See the description in the <code>Grapher</code> class on how to display the plot.
 * Other <code>private</code> methods in this class are simply delegated to the
 * associated <code>Grapher</code> instance (if available).
 * <p>Created: Mon Apr  8 17:35:11 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see SimplePlot
 * @see #plot(Grapher)
 * @see #range
 * @see Grapher
 */
abstract public class Plot implements Serializable, HasAxisLabels {

    /**
     * <code>Grapher</code> instance associated with the plot (if available).
     *
     */
    transient protected Grapher grapher;
    
    /**
     * Get the value of grapher.
     * @return value of grapher.
     */
    public Grapher getGrapher() {
	return grapher;
    }
    
    /**
     * Set the value of grapher.
     * @param v  Value to assign to grapher.
     */
    public void setGrapher(Grapher  v) {
	this.grapher = v;
    }    

    /**
     * The list of <code>Grapher.Axis</code>'s contained in the
     * plot. 
     * <p>TODO: maybe should go to the <code>grapher</code>
     * dependent part, the <code>PlotHandle</code>.
     */
    List axes;

    /**
     * Plot title.
     */
    String title;
    
    /**
     * Get the value of title.
     * @return value of title.
     */
    public String getTitle() {
	return title;
    }
    
    /**
     * Set the value of title.
     * @param v  Value to assign to title.
     */
    public void setTitle(String  v) {
	this.title = v;
    }

    /**
     * Font size for plot legends. 0 means default size.
     */
    int fontSize = 0;

    /**
     * Get the FontSize value.
     * @return the FontSize value.
     */
    public int getFontSize() {
	return fontSize;
    }

    /**
     * Set the FontSize value.
     * @param newFontSize The new FontSize value.
     */
    public void setFontSize(int newFontSize) {
	this.fontSize = newFontSize;
    }

    /**
     * Give title or label as part of description.
     *
     * @return a <code>String</code> value
     */
    public String toString() {
	String id = title != null ? title : label;
	return getClass().getName() + ": " + id;
    }

    /**
     * Label of the dataset if it is the only one in this plot.
     */
    String label;
    
    /**
     * Get the value of label.
     * @return value of label.
     */
    public String getLabel() {
	return label;
    }
    
    /**
     * Set the value of label.
     * @param v  Value to assign to label.
     */
    public void setLabel(String  v) {
	this.label = v;
    }

    /**
     * Label for the x-axis.
     */
    String xLabel;
    
    /**
     * Get the value of xLabel.
     * @return value of xLabel.
     */
    public String getXLabel() {
	return xLabel;
    }
    
    /**
     * Set the value of xLabel.
     * @param v  Value to assign to xLabel.
     */
    public void setXLabel(String  v) {
	this.xLabel = v;
    }

    /**
     * Label for the x-axis.
     */
    String yLabel;
    
    /**
     * Get the value of yLabel.
     * @return value of yLabel.
     */
    public String getYLabel() {
	return yLabel;
    }
    
    /**
     * Set the value of yLabel.
     * @param v  Value to assign to yLabel.
     */
    public void setYLabel(String  v) {
	this.yLabel = v;
    }
    

    /**
     * <code>Range</code> of the plot.
     *
     */
    protected Range range;
    
    /**
     * Get the value of range.
     * @return value of range.
     */
    public Range getRange() {
	return range;
    }
    
    /**
     * Set the value of range.
     * @param v  Value to assign to range.
     */
    public void setRange(Range  v) {
	this.range = v;
    }

    /**
     * 	Find maximum range by iterating on all objects implementing
     * 	<code>HasAxisLabels</code>.
     *
     * @param plots a <code>Collection</code> of <code>HasAxisLabels</code> objects
     * @return the maximal <code>Range</code> in the list
     */
    static Range getMaxRange(HasAxisLabels[] plots) {
	final Range maximalRange = new Range(0, 0);

	new UninterruptedIteration() {
	    boolean first = true;
	    public void job(Object o) {
		HasAxisLabels plot = (HasAxisLabels)o;
		Range plotRange = plot.getRange(); 

		if (plotRange == null) 
		    return;
		
		if (first) {
		    maximalRange.setStart(plotRange.getStart());
		    maximalRange.setEnd(plotRange.getEnd());
		    first = false;
		} else 
		    maximalRange.add(plotRange);
	    }
	}.loop(plots);

	return maximalRange;
    }

    static Range getMaxRange(Collection plots) {
	return getMaxRange((HasAxisLabels[]) plots.toArray(new HasAxisLabels[0]));
    }

    public Plot (/*Grapher grapher,*/ String label, Range range) {
	//this.grapher = grapher;
	this.label = label;
	this.range = range;
    }

    /**
     * Calls other constructors with <code>null</code> params.
     *
     */
    public Plot () {
	this(null, null);
    }

    /**
     * A new way to describe plots in terms of <code>Grapher</code>
     * capabilities.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    abstract public String recipe(final Grapher grapher);
    
    /**
     * Returns a <code>String</code> representation of the plot for
     * the given grapher. Delegates the request to the
     * <code>Grapher</code> instance.
     * @deprecated Grapher handles things by directly calling <code>Grapher.plotToString()</code>
     * and using the <code>PlotHandle</code>
     * @return a <code>String</code> value
     */
    public String plot(Grapher grapher) {
	this.grapher = grapher;	
	return grapher.plotToString((SimplePlot)this);
    }

    // Code for delegation of edu.ull.cgunay.plots.Grapher methods to grapher
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#add(String, String)
     */
    protected String add(String param1, String param2) {
	return grapher.add(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#assign(String, String)
     */
    protected String assign(String param1, String param2) {
	return grapher.assign(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#profile(Profile, Range)
     */
    protected String profile(Profile param1, Range param2) {
	return grapher.profile(param1, param2);
    }
        
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#sub(String, String)
     */
    protected String sub(String param1, String param2) {
	return grapher.sub(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#mul(String, String)
     */
    protected String mul(String param1, String param2) {
	return grapher.mul(param1, param2);
    }

    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#geq(String, String)
     */
    protected String geq(String param1, String param2) {
	return grapher.geq(param1, param2);
    }

    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#div(String, String)
     */
    protected String div(String param1, String param2) {
	return grapher.div(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#neg(String)
     */
    protected String neg(String param1) {
	return grapher.neg(param1);
    }

    /**
     *
     * @param param1 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#exp(String)
     */
    protected String exp(String param1) {
	return grapher.exp(param1);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @param param3 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#def_func(String, String[], String)
     */
    protected String def_func(String param1, String[] param2, String param3) {
	return grapher.def_func(param1, param2, param3);
    }
    
    /**
     *
     * @param param1 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#range(Range)
     */
    protected String range(Range param1) {
	return grapher.range(param1);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.plots.Grapher#func(String, String[])
     */
    protected String func(String param1, String[] param2) {
	return grapher.func(param1, param2);
    }

    /**
     *
     * @param param1 a <code>String</code> value
     * @return a <code>String</code> value
     * @see edu.ull.cgunay.plots.Grapher#variable(String)
     */
    protected String variable(String param1) {
	return grapher.variable(param1);
    }

}// Plot
