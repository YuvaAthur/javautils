
package edu.ull.cgunay.utils.plots;

import edu.ull.cgunay.utils.*;

import java.io.*;
import java.util.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
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
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version v2.0, and $Revision$ for this file.
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
    public PrintWriter out;

    /**
     * Reader of the output stream of the grapher process.
     */
    BufferedReader msg;

    /**
     * Reader of the error stream of the grapher process.
     */
    BufferedReader err;

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
    protected int windowNumber = 1;

    /**
     * Spawns a grapher process (used from subclasses).
     *
     * @param processName the string to be executed
     * @exception GrapherNotAvailableException cannot connect process or streams
     */
    Grapher(String processName) throws GrapherNotAvailableException {
	try {
	    process = Runtime.getRuntime().exec(processName); 

	    out = new PrintWriter(process.getOutputStream(), true);
	    msg = new BufferedReader(new InputStreamReader(process.getInputStream()));
	    err = new BufferedReader(new InputStreamReader(process.getErrorStream()));

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
		    double delta = val - lastval;

		    // Only add entry if there's any change in value
		    if (delta != 0)
			retval = add(retval,
				     mul(geq("t", "" + entry.getKey()), "" + (val-lastval)));
		    lastval = val;
		}
	    }.getString(profile.collection(range)));
    }

    /**
     * Generic plot that calls <code>Plot.body()</code> as the body.
     * <!-- Needs to be redefined in subclasses and it is an error to call this function here.-->
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     * @see Plot#body
     */
    abstract public String plotToString(SimplePlot plot);/* {
	throw new Error("This method is only defined in subclasses!");
    }*/

    /**
     * Returns a <code>String</code> representation of a spike
     * plot for the <code>Grapher</code>.
     * @param plot a <code>SpikePlot</code> value
     * @return a <code>String</code> value
     * @see GNUPlot#plot(SpikePlot)
     */
    abstract public String plotToString(SpikePlot plot); 

    /**
     * When defined, should call <code>plot.recipe</code>.
     *
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    abstract public String plotToStringAlt(Plot plot);

    /**
     * @param plots a <code>Plot[]</code> value
     * @param out a <code>PrintStream</code> value
     * @return a <code>String</code> value
     * @see #multiPlot(Collection,PrintStream)
     */
    public PlotHandle multiPlot(Plot[] plots, PrintStream out) {
	return multiPlot(Arrays.asList(plots), out);
    }

    /**
     * Multiple plots in the same window, arranged one on top of the other with same range.
     * Implemented separately for each grapher.
     * @param plots a <code>Collection</code> value
     * @param out a <code>PrintStream</code> value
     * @return a <code>String</code> value
     * @see GNUPlot#multiPlot
     * @see MatLab#multiPlot
     */
    abstract public PlotHandle multiPlot(Collection plots, PrintStream out);

    /**
     *
     * @param title a <code>String</code> value
     * @param plots a <code>Plot[]</code> value
     * @param out a <code>PrintStream</code> value
     * @return a <code>PlotHandle</code> value
     * @see #superposedPlot(String,Collection,PrintStream)
     */
    public PlotHandle superposedPlot(final String title, Plot[] plots, final PrintStream out) {
	return superposedPlot(title, Arrays.asList(plots), out);
    }


    /**
     * Multiple datasets on the same axis. 
     *
     * @param title a <code>String</code> value
     * @param plots a <code>Collection</code> value
     * @param out a <code>PrintStream</code> value
     * @return a <code>PlotHandle</code> value
     */
    abstract public PlotHandle superposedPlot(final String title, Collection plots,
					      final PrintStream out);

    /**
     * Factory method fro creating <code>Axis</code> instances. 
     *
     * @return an <code>Axis</code> subclass instance for the given grapher.
     */
    abstract public Axis createAxis();

    /**
     * An object that represent a data axis for the grapher. Describes
     * how to form a graph axis for specific <code>Grapher</code>. To be
     * instantiated by the <code>Grapher</code>.
     */
    abstract protected class Axis extends LinkedList implements HasAxisLabels {
	Range range;
	
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
	 * Label for the y-axis.
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
	 * Adds a new dataset to the axis.
	 *
	 * @param data a <code>Data</code> value
	 */
	void addData(Data data) {
	    this.add(data);
	}

	/**
	 * Grapher specific code for generating the axis with the
	 * given data sets. For each <code>data</code> instantiate all
	 * <code>variables</code>.
	 *
	 * @return a <code>String</code> value
	 */
	abstract String getString();
    }

    /**
     * Hash to hold name->datatype values for a grapher.
     */
    protected Hashtable dataTypes = new Hashtable();

    /**
     * Type of representation for the data. That is spikes, lines,
     * errorbars, etc. To be instantiated by the <code>Grapher</code>
     * as representing its capabilities.
     */
    abstract protected class DataType {
	String name;

	/**
	 * Adds itself to the <code>dataTypes</code> hash.
	 *
	 * @param name key for the datatype
	 */
	DataType(String name, String axisCommand) {
	    this.name = name;
	    this.axisCommand = axisCommand;
	    dataTypes.put(name, this);
	}

	/**
	 * Command to realize axis in grapher, i.e. "stem", "plot", etc.
	 */
	String axisCommand;
	    
	/**
	 * Maybe for additional properties? Used by <code>body()</code>.
	 * @see Grapher.Data#body
	 */
	String propertyName;

	/**
	 * Generates the plot command for the grapher for given data.
	 *
	 * @param data a <code>Data</code> value
	 * @return a <code>String</code> value
	 */
	abstract public String plotCommand(Data data);
    }

    /**
     * Each dataset that can be added to an <code>Axis</code>. To be
     * instantiated by the <code>Plot</code>.
     */
    abstract class Data implements Serializable {
	/**
	 * Variables used by this plot data definition. name->Range or
	 * name->vector pairs.
	 */
	Hashtable variables = new Hashtable();

	/**
	 * Get the Variables value.
	 * @return the Variables value.
	 */
	public Hashtable getVariables() {
	    return variables;
	}

	/**
	 * Finds the <code>DataType</code> associated with requested
	 * <code>dataTypeName</code>.
	 *
	 * @param dataTypeName a <code>String</code> value
	 * @param label a <code>String</code> value
	 */
	public Data(String dataTypeName, String label) {
	    this.label = label;
	    setDataType(dataTypeName);
	    //init();
	}

	/**
	 * Put initialization code in this method.
	 */
	void init() {}

	/**
	 * Add a range variable to <code>variables</code>. <p> TODO:
	 * maybe just take the name ot the member variable and use
	 * reflection to look it up? (like in catacomb)
	 *
	 * @param name a <code>String</code> value
	 * @param range a <code>Range</code> value
	 * @see #variables
	 */
	public void addVariable(String name, Range range) {
	    variables.put(name, range);
	}

	/**
	 * Add a vector variable to <code>variables</code>.
	 *
	 * @param name a <code>String</code> value
	 * @param vector a <code>Collection</code> value
	 * @see #variables
	 */
	public void addVariable(String name, Collection vector) {
	    variables.put(name, vector);
	}

	DataType dataType;

	/**
	 * Get the DataType value.
	 * @return the DataType value.
	 */
	public DataType getDataType() {
	    return dataType;
	}

	/**
	 * Looks up the name from <code>dataTypes</code>.
	 *
	 * @param dataTypeName a <code>String</code> value
	 */
	public void setDataType(String dataTypeName) {
	    dataType = (DataType) dataTypes.get(dataTypeName);
	    if (dataType == null) 
		throw new RuntimeException("Datatype " + dataTypeName + " is not found in " +
					   Grapher.this + ". Not supported or wrong type name. " +
					   "Available types: " + dataTypes.keySet());
	}

	/**
	 * Legend label for the data.
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
	 * Expression to appear on the x-axis part of the plot
	 * command. Description should be in terms of the variables
	 * here and capabilities of the <code>Grapher</code>.
	 *
	 * @return a <code>String</code> value
	 */
	abstract public String xExpression();

	/**
	 * Expression to appear on the x-axis part of the plot
	 * command. Description should be in terms of the variables
	 * here and capabilities of the <code>Grapher</code>.
	 *
	 * @return a <code>String</code> value
	 * @see #variables
	 */
	abstract public String yExpression();
    }

    /**
     * Data fitted for generating plots for profiles. Assumes time for
     * the x-axis.
     * @see Profile
     */
    abstract class ProfileData extends Data {
	public ProfileData(String dataTypeName, String label) {
	    super(dataTypeName,label);
	}

	Range range;
	
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
	    addVariable("t", range);
	}

	/*void init() {
	    
	}*/

	/**
	 * Currently just returns the variable "t" being the default
	 * range variable. Need to put that in the hashtable.
	 *
	 * @return a <code>String</code> value
	 */
	public String xExpression() {
	    return variable("t");
	}
    }

    /**
     * For errorbar plots.
     *
     */
    public class ErrorData extends Data {

	/**
	 * Vectors representing values and their corresponding minimum and
	 * maximum limits.
	 */
	Collection values, minValues, maxValues, xAxis;

	public ErrorData(String label, Profile errorValues) {
	    super("errorbar", label);

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

	    addVariable("xAxis", xAxis);
	    addVariable("values", values);
	    addVariable("minValues", minValues);
	    addVariable("maxValues", maxValues);
	}

	public String xExpression() {
	    return variable("xAxis"); 
	}

	public String yExpression() {
	    return variable("values"); 
	}


	/**
	 * Returns the lower limit expression for each data point.
	 *
	 * @return a <code>String</code> value
	 */
	public String minExpression() {
	    return variable("minValues"); 
	}


	/**
	 * Returns the upper limit expression for each data point.
	 *
	 * @return a <code>String</code> value
	 */
	public String maxExpression() {
	    return variable("maxValues"); 
	}

    }

    // tools

    /**
     * Returns a <code>String</code> that represents the given
     * variable for the <code>Grapher</code>.  By default it is
     * "name".
     *
     * @param name a <code>String</code> value
     * @return a <code>String</code> value
     */
    String variable(String name) {
	return name;
    }

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
     * Returns a <code>String</code> that parenthesizes the parameter
     * for the <code>Grapher</code>.  By default it is "(a)".
     *
     * @param a a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String paren(String a) {
	return "(" + a + ")";
    }

    /**
     * Quotes the given string.
     *
     * @param value a <code>String</code> value
     */
    public String quote(String value) {
	return "\"" + value + "\"";
    }

    /**
     * Properly terminate a grapher command with end-of-line separator.
     *
     * @param line a <code>String</code> value
     */
    public String command(String line) {
	return line + "\n";
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
		boolean first = true;
		public void job(Object o) {
		    if (!first) 
			super.job(", ");
		    first = false;
		    super.job(o);
		}
	    }.getString(Arrays.asList(params));
    }

    /**
     * Displays the plot generated from the given plot description. 
     * <p>TODO: Should return the PlotHandle and call directly Grapher.plot(Plot)
     * @param plot a <code>Plot</code> value
     * @param out a <code>PrintStream</code> for displaying <code>grapher</code>
     * response. If <code>null</code>, <code>System.out</code> is used.
     * @return The response from the grapher
     * @see Plot#plot(Grapher)
     */
    public PlotHandle display(Plot plot, PrintStream out) {
	if (out == null) 
	    out = System.out;

	PlotHandle handle = new PlotHandle(plot, this, windowNumber);

	setWindow(windowNumber);

	// Bad bad hack.. shame on java's incapability of polymorphism
	String plotStr =
	    (plot instanceof SpikePlot) ?
	    plotToString((SpikePlot)plot) : plotToStringAlt(plot);
	    
	this.out.println(plotStr);

	windowNumber++;

	waitForResponse();	
	out.println(response());

	return handle;
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
     */
    public String response() {
	String retval = "";

	try {
	    // Display error and output 
	    retval += "Standard output from the grapher:\n";
	    while (msg.ready()) {
		retval += (char)msg.read();
	    } // end of while (msg.ready())

	    retval += "\nStandard error from the grapher:\n";
	    while (err.ready()) {
		retval += (char)err.read();
	    } // end of while (err.ready())	     
	} catch (IOException e) {
	    throw new RuntimeException("" + e); 
	} // end of try-catch
	
	return retval;
    }


    /**
     * Wait until a response comes from either the output or error
     * streams of the process.
     */
    synchronized public void waitForResponse() {
	int
	    until = 5000,	// max wait time in msecs
	    time = 0,		// counter
	    incr = 200;		// step size (in msecs)
	
	try {
	    while (!err.ready() && !msg.ready() && time < until) {
		time += incr;
		wait(incr);	// Sleep some msecs
	    } // end of while (!err.ready() && !msg.ready())
	    
	} catch (IOException e) {
	    throw new RuntimeException("" + e); 
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

    /**
     * Exports an EPS file of the previously visualized (required) plot.
     *
     * @param handle a <code>PlotHandle</code> value
     * @param filename a <code>String</code> value
     */
    abstract public void writeEPS(PlotHandle handle, String filename);
    
}// Grapher
