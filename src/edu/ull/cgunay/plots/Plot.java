
package neuroidnet.ntr.plots;
import neuroidnet.ntr.plots.Grapher;
import neuroidnet.ntr.plots.Range;
import neuroidnet.ntr.plots.Profile;
import neuroidnet.ntr.plots.Plot;

// $Id$
/**
 * Grapher independent description for plots.
 *
 * <p>Created: Mon Apr  8 17:35:11 2002
 * <p>Modified: $Date$
 *
 * @see Grapher
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 */

abstract public class Plot  {

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
     * Delegates the request to the <code>Grapher</code> instance
     * to do the appropriate things according to the subclass.
     *
     * @see Grapher#plot(ProfilePlot)
     * @see Grapher#plot(SpikePlot)
     * @see Grapher#plot(PotentialPlot)
     * @return a <code>String</code> value
     */
    public String plot(Grapher grapher) {
	this.grapher = grapher;	
	return grapher.plot(this);
    }

    // Code for delegation of neuroidnet.ntr.plots.Grapher methods to grapher
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#add(String, String)
     */
    public String add(String param1, String param2) {
	return grapher.add(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#assign(String, String)
     */
    public String assign(String param1, String param2) {
	return grapher.assign(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#profile(Profile, Range)
     */
    public String profile(Profile param1, Range param2) {
	return grapher.profile(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#plot(Plot)
     */
    public String plot(Plot param1) {
	return grapher.plot(param1);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#sub(String, String)
     */
    public String sub(String param1, String param2) {
	return grapher.sub(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#mul(String, String)
     */
    public String mul(String param1, String param2) {
	return grapher.mul(param1, param2);
    }

    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#geq(String, String)
     */
    public String geq(String param1, String param2) {
	return grapher.geq(param1, param2);
    }

    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#div(String, String)
     */
    public String div(String param1, String param2) {
	return grapher.div(param1, param2);
    }
    
    /**
     *
     * @param param1 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#neg(String)
     */
    public String neg(String param1) {
	return grapher.neg(param1);
    }

    /**
     *
     * @param param1 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#exp(String)
     */
    public String exp(String param1) {
	return grapher.exp(param1);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @param param3 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#def_func(String, String[], String)
     */
    public String def_func(String param1, String[] param2, String param3) {
	return grapher.def_func(param1, param2, param3);
    }
    
    /**
     *
     * @param param1 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#range(Range)
     */
    public String range(Range param1) {
	return grapher.range(param1);
    }
    
    /**
     *
     * @param param1 <description>
     * @param param2 <description>
     * @return <description>
     * @see neuroidnet.ntr.plots.Grapher#func(String, String[])
     */
    public String func(String param1, String[] param2) {
	return grapher.func(param1, param2);
    }


}// Plot
