
package edu.ull.cgunay.utils.plots;

import java.util.*;
import java.io.*;
import edu.ull.cgunay.utils.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * Grapher independent description for plots. The plot is converted to 
 * a grapher specific String representation when the <code>plot(Grapher)</code> method 
 * is invoked. 
 * <p>In general the plot is assumed to be a <code>String</code> composed of a preamble,
 * a plot command and some post commands or data.
 * The <code>preamble()</code> method represents the information required before the actual plot
 * command, and the <code>body()</code> method represents the part that will appear as the body of
 * the plot command. The range of the plot that will appear as part of the plot command is
 * kept as the <code>range</code> attribute.
 * <p>As a simple example here is how to plot the y = t + 5 graph: <br>
 * (The x-axis is called "t" by default)
 * <blockquote><pre>
 * class MyPlot extends Plot {
 *   MyPlot(Range range) { super("My plot!", range); }
 *   String body() {
 *     return add("t","5");
 *   }
 * }
 * </pre></blockquote>
 * <p> See the description in the <code>Grapher</code> class on how to display the plot.
 * Other <code>private</code> methods in this class are simply delegated to the
 * associated <code>Grapher</code> instance (if available).
 * <p>Created: Mon Apr  8 17:35:11 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see #plot(Grapher)
 * @see #preamble
 * @see #body
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
     * The list of axes contained in the plot. TODO: maybe should go
     * to the <code>grapher</code> dependent part, the
     * <code>PlotHandle</code>.
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
     * 	Find maximum range by iterating on all plots.
     *
     * @param plots a <code>Collection</code> value
     * @return a <code>Range</code> value
     */
    static Range getMaxRange(Collection plots) {
	final Range maximalRange = new Range(0, 0);

	new UninterruptedIteration() {
	    boolean first = true;
	    public void job(Object o) {
		Plot plot = (Plot)o;
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

    public Plot (/*Grapher grapher,*/ String label, Range range) {
	//this.grapher = grapher;
	this.label = label;
	this.range = range;
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
     * @see Grapher#plot(Plot)
     * @see Grapher#plot(SpikePlot)
     * @return a <code>String</code> value
     */
    public String plot(Grapher grapher) {
	this.grapher = grapher;	
	return grapher.plotToString((SimplePlot)this);
    }

    // Code for delegation of edu.ull.cgunay.utils.plots.Grapher methods to grapher
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#add(String, String)
     */
    protected String add(String param1, String param2) {
	return grapher.add(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#assign(String, String)
     */
    protected String assign(String param1, String param2) {
	return grapher.assign(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#profile(Profile, Range)
     */
    protected String profile(Profile param1, Range param2) {
	return grapher.profile(param1, param2);
    }
        
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#sub(String, String)
     */
    protected String sub(String param1, String param2) {
	return grapher.sub(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#mul(String, String)
     */
    protected String mul(String param1, String param2) {
	return grapher.mul(param1, param2);
    }

    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#geq(String, String)
     */
    protected String geq(String param1, String param2) {
	return grapher.geq(param1, param2);
    }

    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#div(String, String)
     */
    protected String div(String param1, String param2) {
	return grapher.div(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#neg(String)
     */
    protected String neg(String param1) {
	return grapher.neg(param1);
    }

    /**
     *
     * @param param1 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#exp(String)
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
     * @see edu.ull.cgunay.utils.plots.Grapher#def_func(String, String[], String)
     */
    protected String def_func(String param1, String[] param2, String param3) {
	return grapher.def_func(param1, param2, param3);
    }
    
    /**
     *
     * @param param1 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#range(Range)
     */
    protected String range(Range param1) {
	return grapher.range(param1);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see edu.ull.cgunay.utils.plots.Grapher#func(String, String[])
     */
    protected String func(String param1, String[] param2) {
	return grapher.func(param1, param2);
    }

    /**
     *
     * @param param1 a <code>String</code> value
     * @return a <code>String</code> value
     * @see edu.ull.cgunay.utils.plots.Grapher#variable(String)
     */
    protected String variable(String param1) {
	return grapher.variable(param1);
    }

}// Plot
