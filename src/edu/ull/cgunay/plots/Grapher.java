
package edu.ull.cgunay.utils.plots;

import edu.ull.cgunay.utils.*;

import java.io.*;
import java.util.*;

// $Id$
/**
 * <p>Represents the active Grapher process and its capabilities. The methods
 * defined in this class are generic or abstract if it is not possible to generalize them. 
 * 
 * <p>Returns string representations for various operations according to the
 * grapher platform. Each of the classes extending this class 
 * specifies such a platform.
 * 
 * <p>In order to create a plot the usage is as follows:
 * <code><blockquote>
 * Grapher g = new ...();<br>
 * Plot p = new ...();<br>
 * g.display(p);
 * </blockquote> </code>
 *
 * <p>Created: Mon Apr  8 17:12:23 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Plot
 */

abstract public class Grapher  {

    /**
     * The runtime process of the spawned grapher program.
     * @see GNUPlot
     * @see MatLab
     */
    Process process;

    /**
     * The output stream that is connected to the grapher process.
     *
     */
    public PrintWriter grapherOut;

    /**
     * Reader of the output stream of the grapher process.
     */
    BufferedReader grapherMsg;

    /**
     * Reader of the error stream of the grapher process.
     */
    BufferedReader grapherErr;

    /**
     * Number of points on the graph, default is 100.
     *
     */
    int points = 100;
    
    /**
     * Get the value of points.
     * @return value of points.
     */
    public int getPoints() {
	return points;
    }
    
    /**
     * Set the value of points.
     * @param v  Value to assign to points.
     */
    public void setPoints(int  v) {
	this.points = v;
    }

    /**
     * The number of the window in which the plot will appear.
     *
     */
    private int windowNumber = 1;

    /**
     * Spawns a grapher process (used from subclasses).
     *
     * @param processName the string to be executed
     * @exception GrapherNotAvailableException cannot connect process or streams
     */
    Grapher(String processName) throws GrapherNotAvailableException {
	try {
	    process = Runtime.getRuntime().exec(processName); 

	    grapherOut = new PrintWriter(process.getOutputStream(), true);
	    grapherMsg = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    grapherErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

	} catch (IOException e) {
	    throw new GrapherNotAvailableException("" + e);	    
	} // end of try-catch
    }

    /**
     * Returns a string representation of a <code>Profile</code> for
     * a given <code>Range</code>. Most of the time it should be in the form of
     * ((t>t1)*v1 + (t>t2)*(v2-v1) + ... ) where t is the variable denoting time,
     * and (t1,v1) is a pair found in the profile. See specific implementations for 
     * more information. It is an error if the <code>Profilable</code> entity does not
     * meaningfully define its <code>doubleValue()</code> method, that is converted
     * to a simple <code>double</code> value.
     *
     * @param profile a <code>Profile</code> value
     * @param range a <code>Range</code> value
     * @return a <code>String</code> value
     * @see Profilable#doubleValue
     */
    public String profile(Profile profile, Range range) {
	return paren(new StringTask("0") {
		double lastval = 0;

		public void job(Object o) {
		    Map.Entry entry = (Map.Entry)o;
		    double val = ((Profilable)entry.getValue()).doubleValue();
		    
		    retval = add(retval, mul(geq("t", "" + entry.getKey()), "" + (val-lastval)));
		    lastval = val;
		}
	    }.getString(profile.collection(range)));
    }

    /**
     * Generic plot that calls <code>Plot.body()</code> as the body.
     * Needs to be redefined in subclasses and it is an error to call this function here.
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     * @see Plot#body
     */
    public String plot(Plot plot) {
	throw new Error("This function should not be called");
    }

    /**
     * @param title a <code>String</code> value
     * @param plots a <code>Plot[]</code> value
     * @return a <code>String</code> value
     * @see #multiPlot(String,Collection)
     */
    public String multiPlot(String title, Plot[] plots) {
	return multiPlot(title, Arrays.asList(plots));
    }

    /**
     * Multiple plots in the same window, arranged one on top of the other.
     * Implemented separately for each grapher.
     * @param title a <code>String</code> value
     * @param plots a <code>Collection</code> value
     * @return a <code>String</code> value
     * @see GNUPlot#multiPlot
     * @see MatLab#multiPlot
     */
    abstract public String multiPlot(String title, Collection plots);

    /**
     * Returns a <code>String</code> representation of a spike
     * plot for the <code>Grapher</code>.
     * @param plot a <code>SpikePlot</code> value
     * @return a <code>String</code> value
     * @see GNUPlot#plot(SpikePlot)
     */
    abstract public String plot(SpikePlot plot); 

    // tools

    /**
     * Returns a <code>String</code> that adds two parameters for the <code>Grapher</code>.
     * By default it is "(a) + (b)".
     * @param a a <code>String</code> value
     * @param b a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String add(String a, String b) {
	return paren(a) + "+" + paren(b);
    }

    /**
     * Returns a <code>String</code> that subtracts two parameters for the <code>Grapher</code>.
     * by default it is <code>add((a), neg(b))</code>.
     * @param a a <code>String</code> value
     * @param b a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String sub(String a, String b) {
	return add(a,neg(b));
    }

    /**
     * Returns a <code>String</code> that multiplies two parameters for the <code>Grapher</code>.
     * By default it is "(a) * (b)".
     *
     * @param a a <code>String</code> value
     * @param b a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String mul(String a, String b) {
	return paren(a) + "*" + paren(b);
    }

    /**
     * Returns a <code>String</code> that is a greater or equal conditional
     * expression for the <code>Grapher</code>.
     * By default it is "(a) >= (b)".
     *
     * @param a a <code>String</code> value
     * @param b a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String geq(String a, String b) {
	return paren(a) + ">=" + paren(b);
    }

    /**
     * Returns a <code>String</code> that divides two parameters for the <code>Grapher</code>.
     * By default it is "(a) / (b)".
     *
     * @param a a <code>String</code> value
     * @param b a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String div(String a, String b) {
	return paren(a) + "/" + paren(b);
    }

    /**
     * Returns a <code>String</code> that negates the parameter for the <code>Grapher</code>.
     * By default it is "-(a)".
     *
     * @param a a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String neg(String a) {
	return "-" + paren(a);
    }

    /**
     * Returns a <code>String</code> that takes the exponential of the
     * parameter for the <code>Grapher</code>.
     * By default it is "exp(a)".
     *
     * @param a a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String exp(String a) {
	String params[] = { a };
	return func("exp", params);
    }

    /**
     * Returns a <code>String</code> that parenthesizes the parameter for the <code>Grapher</code>.
     * By default it is "(a)".
     *
     * @param a a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String paren(String a) {
	return "(" + a + ")";
    }

    /**
     * Returns a <code>String</code> that assigns a value to a variable
     * for the <code>Grapher</code>.
     *
     * @param var a <code>String</code> value
     * @param value a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String assign(String var, String value) {
	return var + "=" + value;
    }

    /**
     * Returns a <code>String</code> that defines a function
     * for the <code>Grapher</code>.
     *
     * @param name a <code>String</code> value, name of the function
     * @param params a <code>String[]</code> value, parameter names of the function
     * @param body a <code>String</code> value, the body of the function
     * @return a <code>String</code> value
     */
    public abstract String def_func(String name, String[] params, String body);

    /**
     * Returns a <code>String</code> that gives a range 
     * for the <code>Grapher</code>. By default it is "start:end".
     *
     * @param range a <code>Range</code> value
     * @return a <code>String</code> value
     */
    public String range(Range range) {
	return range.getStart() + ":" + range.getEnd();
    }

    /**
     * Returns a <code>String</code> that calls a predefined function with given arguments
     * for the <code>Grapher</code>. By default it is "func(par1,par2,...)".
     *
     * @param name a <code>String</code> value, name of the function
     * @param params a <code>String[]</code> value, array of parameter values
     * @return a <code>String</code> value
     */
    public String func(String name, String[] params) {
	return new StringTask(name + "(", ")") {
		public void job(Object o) {
		    if (!this.retval.equals("")) 
			this.retval += ", ";
		    super.job(o);
		}
	    }.getString(Arrays.asList(params));
    }

    /**
     * Displays the plot generated from the given plot description. 
     * @param plot a <code>Plot</code> value
     * @return The response from the grapher
     * @exception GrapherNotAvailableException if the grapher is not available
     * @see Plot#plot(Grapher)
     */
    public String display(Plot plot) throws GrapherNotAvailableException {
	if (grapherOut == null) 
	    throw new GrapherNotAvailableException(); 

	setWindow(windowNumber++);
	grapherOut.println(plot.plot(this));

	waitForResponse();	
	return response();
    }

    /**
     * Sets the current plot window according to grapher platform.
     *
     * @param windowNumber an <code>int</code> value
     */
    abstract public void setWindow(int windowNumber);

    /**
     * Returns the standard and error outputs of the process if available.
     *
     * @return a <code>String</code> value
     * @exception GrapherNotAvailableException if the grapher is not available
     */
    public String response() throws GrapherNotAvailableException {
	String retval = "";

	try {
	    // Display error and output 
	    retval += "Standard output from the grapher:\n";
	    while (grapherMsg.ready()) {
		retval += (char)grapherMsg.read();
	    } // end of while (grapherMsg.ready())

	    retval += "\nStandard error from the grapher:\n";
	    while (grapherErr.ready()) {
		retval += (char)grapherErr.read();
	    } // end of while (grapherErr.ready())	     
	} catch (IOException e) {
	    throw new GrapherNotAvailableException("" + e); 
	} // end of try-catch
	
	return retval;
    }


    /**
     * Wait until a response comes from either the output or error streams of the process.
     *
     * @exception GrapherNotAvailableException if an error occurs
     */
    synchronized public void waitForResponse() throws GrapherNotAvailableException {
	try {
	    while (!grapherErr.ready() && !grapherMsg.ready()) {
		wait(200);	// Sleep some msecs
	    } // end of while (!grapherErr.ready() && !grapherMsg.ready())
	    
	} catch (IOException e) {
	    throw new GrapherNotAvailableException("" + e); 	    
	} catch (InterruptedException e) {
	    // do nothing
	} // end of catch
	
    }

    /**
     * Kills the grapher process.
     *
     */
    public void close() {
	process.destroy();
    }
    
}// Grapher
