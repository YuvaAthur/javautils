
package edu.ull.cgunay.utils.plots;

// $Id$
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
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see #plot(Grapher)
 * @see #preamble
 * @see #body
 * @see #range
 * @see Grapher
 */
abstract public class Plot  {

    /**
     * <code>Grapher</code> instance associated with the plot (if available).
     *
     */
    protected Grapher grapher;
    
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
     * Plot title.
     *
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

    public Plot (/*Grapher grapher,*/ String title, Range range) {
	//this.grapher = grapher;
	this.title = title;
	this.range = range;
    }

    /**
     * Returns body that appears inside the plot command for the specific plot.
     *
     * @return a <code>String</code> value
     */
    abstract public String body();

    /**
     * Helper function to set grapher before calling body.
     * @see MembranePotentialPlot
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String body(Grapher grapher) {
	this.grapher = grapher;
	return body();
    }

    /**
     * Returns a preamble to appear before the plot command. 
     * By default returns empty string.
     * 
     * @return a <code>String</code> value
     */
    public String preamble() { return ""; }

    /**
     * Returns a preamble to appear before the plot command. 
     * By default returns empty string.
     * 
     * @return a <code>String</code> value
     */
    public String preamble(Grapher grapher) {
	this.grapher = grapher;
	return preamble();
    }
    
    /**
     * Returns a <code>String</code> representation of the plot
     * for the given grapher. Delegates the request to the
     * <code>Grapher</code> instance.
     * @deprecated Grapher handles things by directly calling <code>Grapher.plotToString()</code>
     * and using the <code>PlotHandle</code>
     * @see Grapher#plot(Plot)
     * @see Grapher#plot(SpikePlot)
     * @return a <code>String</code> value
     */
    public String plot(Grapher grapher) {
	this.grapher = grapher;	
	return grapher.plotToString(this);
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


}// Plot
