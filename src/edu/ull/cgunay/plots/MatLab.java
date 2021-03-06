
package edu.ull.cgunay.plots;

import edu.ull.cgunay.utils.*;

import java.io.*;
import java.util.*;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * <code>Grapher</code> implementation for plotting with the
 * <code>MatLab</code> program.
 * <p>See description in <code>Grapher</code> for the usage.
 *
 * <p>Created: Fri Apr 12 22:18:46 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Grapher
 */

public class MatLab extends Grapher {
    /**
     * Spawns matlab interpreter and defines datatypes. <p>TODO: the
     * datatypes should anonymously be defined in this constructor for
     * simplicity rather than being defined as one-time use inner
     * classes. Doesn't make sense.
     *
     * @exception GrapherNotAvailableException if an error occurs
     * @see Grapher.DataType
     */
    public MatLab () throws GrapherNotAvailableException {
	super("matlab");
	
	// Define datatypes (TODO: )
	new DefaultDataType();
	new ImpulseDataType();
	new ErrorDataType();
    }
    
    /**
     * If multiple datasets are plotted on same axis, cycle through these
     * (only required for <i>stupid</i> Matlab). Also associate plot
     * handles for each data set to be able to properly form a legend
     * string.
     */
    class PatternIterator {

	/**
	 * Possible patterns.
	 */
	String[] lineColorSpecs = { "-", "--g", "-.r", ".m" };

	/**
	 * Form unique plot handle variables for properly forming
	 * legend identifiers.
	 */
	int handleId;

	/**
	 * To iterate on <code>lineColorSpecs</code>.
	 */
	Iterator it;

	/**
	 * Reset iterators for at the beginning of each axis.
	 */
	public void newAxis() {
	    it = Arrays.asList(lineColorSpecs).iterator();
	    handleId = 0;
	}

	/**
	 * Get the next pattern string.
	 *
	 * @return a <code>String</code> value
	 */
	public String nextPattern() {
	    try {
		return quote((String) it.next());		 
	    } catch (NoSuchElementException e) {
		// Restart at the end of the list
		it = Arrays.asList(lineColorSpecs).iterator();
		return quote((String) it.next());
	    } // end of try-catch
	}

	/**
	 * Describe <code>nextHandle</code> method here.
	 *
	 * @return a <code>String</code> value
	 */
	public String nextHandle() {
	    return "handle" + (handleId++);
	}
    }

    PatternIterator patterns = new PatternIterator();

    abstract class MatLabDataType extends DataType {
	String dataHandle;

	public String getDataHandle() {
	    return dataHandle;
	}

	String axisHandle;

	/**
	 * Get the AxisHandle value.
	 * @return the AxisHandle value.
	 */
	public String getAxisHandle() {
	    return axisHandle;
	}

	/**
	 * Set the AxisHandle value.
	 * @param newAxisHandle The new AxisHandle value.
	 */
	public void setAxisHandle(String newAxisHandle) {
	    this.axisHandle = newAxisHandle;
	}

	MatLabDataType(String name, String commandName) {
	    super(name, commandName);
	}

    }

    /**
     * Simple plot command type.
     *
     */
    class DefaultDataType extends MatLabDataType { 
	DefaultDataType() {
	    super("default", "plot");
	}
	
	/**
	 * Process the <code>data</code> for MatLab.
	 *
	 * @param data a <code>Data</code> value
	 * @return a <code>String</code> value
	 */
	public String plotCommand(Data data) {
	    dataHandle = patterns.nextHandle();
	    return assign(dataHandle,
			  axisCommand + paren(data.xExpression() + ", " +
					      data.yExpression() + ", " +
					       patterns.nextPattern()));
	}
    }
    
    class ImpulseDataType extends MatLabDataType {
	
	ImpulseDataType() {
	    super("impulse", "stem");
	}

	/**
	 * TODO: Unify this with <code>DefaultDataType</code>.
	 *
	 * @param data a <code>Grapher.Data</code> value
	 * @return a <code>String</code> value
	 */
	public String plotCommand(Data data) {
	    dataHandle = patterns.nextHandle();
	    return assign(dataHandle,
			  axisCommand + paren(data.xExpression() + ", " +
					       data.yExpression() + ", " +
					       patterns.nextPattern()));
	}
    }

    class ErrorDataType extends MatLabDataType {
	
	ErrorDataType() {
	    super("errorbar", "errorbar");
	}

	/**
	 * TODO: Unify this with <code>DefaultDataType</code>.
	 *
	 * @param data a <code>Grapher.Data</code> value
	 * @return a <code>String</code> value
	 */
	public String plotCommand(Data data) {
	    String vectorDHandle = patterns.nextHandle();
	    dataHandle = vectorDHandle + "(2)"; // legend handle is the second one!
	    return assign(vectorDHandle,
			  axisCommand + paren(data.xExpression() + ", "
					      + data.yExpression() + ", "
					      + sub(data.yExpression(),
						    ((ErrorData)data).minExpression()) + ", "
					      + sub(((ErrorData)data).maxExpression(),
						    data.yExpression()) + ", "
					      + patterns.nextPattern()));
	}
    }

    /**
     * @deprecated 
     * @see #plotToStringAlt(Plot)
     */
    public String plotToString(final SpikePlot plot) {
	return null;
	// First converts the given array into matlab style array string
	// (should be made a function)
	/*return 
	    assign("t", new StringTask("[ ", " ]") {
		    Range range = plot.getRange();

		    public void job(Object o) {
			double time = ((Double)o).doubleValue();
			if (time >= range.getStart() && time <= range.getEnd()) 
			    super.job(time + " "); // gets added to retval
		    }
		}.getString(plot.getSpikes())) + ";\n" +
	    "stem(t, ones(size(t,2)), 'filled');\n" +
	    labelString(plot) + titleString(plot) +
	    xLabelString(plot) + yLabelString(plot);*/
    }

    /**
     * Matlab specific plotting command. Calls Plot.body().
     * @deprecated
     * @see #plotToStringAlt
     * @see SimplePlot#body
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    public String plotToString(SimplePlot plot) {
	return null;
	/*Range range = plot.getRange();

	return
	    plot.preamble() +
	    assign("t", "[" + ((range!=null) ? range(range) : "")+ "]") + ";\n" +
	    "plot (x, " + plot.body() + ");\n" +
	    labelString(plot) + titleString(plot) +
	    xLabelString(plot) + yLabelString(plot);*/
    }

    /**
     * Convenience method to get instance of <code>Axis</code> defined
     * <i>here</i>.
     */
    public Grapher.Axis createAxis() {
	return new Axis();
    }

    /**
     * Overriding original <code>Axis</code> definition.
     *
     */
    class Axis extends Grapher.Axis {
	/**
	 * Shortcut to the single data. Why do we need this?
	 */
	Data data;

	/**
	 * Matlab style plot description for given sets of data. 
	 *
	 * @return a <code>String</code> value
	 */
	String getString() {
	    patterns.newAxis();
	    if (size() == 1) {	// Single plot
		Data data = (Data) getFirst();
		return 
		    getDataPreamble(data) + 
		    data.dataType.plotCommand(data) +
		    labelString(data) + titleString(this) +
		    xLabelString(this) + yLabelString(this);

	    } else if (size() > 1) { // superposed plot
		return
		    new StringTask(command("hold on"), command("hold off")) {
			String labelString = "";
			String labelHandles = "[";
			public void job(Object o) {
			    Data data = (Data) o;
			    String dataCommands =
				getDataPreamble(data) +
				data.dataType.plotCommand(data);
			    
			    // Set the font size if requested
			    /*if (fontSize != 0) {
				String[] params = {((MatLabDataType)data.getDataType()).getDataHandle(),
						   quote("FontSize"), "" + fontSize};
				dataCommands += command(func("set", params));
			    }*/
				
			    labelString += ( !first ? ", " : "" ) + quote(data.getLabel());
			    labelHandles +=
				( !first ? "; " : "" ) +
				((MatLabDataType)data.getDataType()).getDataHandle();
			    super.job(dataCommands);
			}
			
			public String getString(Collection c) {
			    return
				super.getString(c) +
				command("legend" + paren(labelHandles + "], " + labelString +
							 ", 0")); // best-position
			}
		    }.getString(this) + titleString(this) +
		    xLabelString(this) + yLabelString(this);
	    } // end of if (size() > 1)
	    
	    throw new Error("Undefined");
	}

	/**
	 * Handle preamble of single data set.
	 *
	 * @param data a <code>Data</code> value
	 * @return a <code>String</code> value
	 */
	String getDataPreamble(Data data) {
	    // Loop on all variables defined in the data
	    return 
		new StringTask() {
		    public void job(Object o) {
			// for each range variable, declare the range
			// for each vector variable initialize a variable
			Map.Entry entry = (Map.Entry) o;
			
			if (entry.getValue() instanceof Range) {
			    Range range = (Range) entry.getValue();
			    super.job(assign((String) entry.getKey(),
					     "[" + ((range!=null) ? range(range) : "")+ "]"));
			} else if (entry.getValue() instanceof Collection) {
			    Collection vector = (Collection) entry.getValue();
			    super.job(assign((String) entry.getKey(),
					     // make a matlab vector (TODO: generalize this)
					     new StringTask("[", "]", ",").getString(vector)));
			} else {
			    new Error("Undefined type for variable " + entry.getKey() +
				      " in dataset.");
			} // end of else
			
		    }
		}.getString(data.getVariables().entrySet()) +
		data.preamble(); // preamble at the end
	}
	}

    /**
     * Convenience method to get instance of <code>MultiAxes</code> defined
     * <i>here</i>.
     */
    public Grapher.MultiAxes createMultiAxes(Grapher.Axis[] axes) {
	return new MultiAxes(axes);
    }

    /**
     * Convenience method to get instance of <code>MultiAxes</code> defined
     * <i>here</i>.
     */
    public Grapher.MultiAxes createMultiAxes(List axes) {
	return new MultiAxes(axes);
    }


    class MultiAxes extends Grapher.MultiAxes {

	MultiAxes(Grapher.Axis[] axes) {
	    super(axes);
	}

	MultiAxes(List axes) {
	    super(axes);
	}

	/**
	 * Iterates over all contained <code>Axis</code>'s to create
	 * the multiaxes figure.
	 *
	 * @return a <code>String</code> value
	 */
	String getString() {
	    return
		new StringTask("", "") {	
		    int thisPlot = 1;
		    int noOfPlots = axes.length;

		    public void job(Object o) {
			Grapher.Axis axis = (Grapher.Axis)o;
			// Remove all but last bottom labels in MatLab graphs
			// (overwrites each other)
			if (thisPlot != noOfPlots) 
			    axis.setXLabel(null); 
			
			super.job("subplot(" + noOfPlots + ", 1, " + thisPlot++ + ");\n" +
				  axis.getString());
		    }
		}.getString(axes); 
	}
    }

    /**
     * Alternative to <code>plotToString</code>, supposed to replace it
     * soon.
     * <p>TODO: Should it stay in Grapher?
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    public String plotToStringAlt(Plot plot) {
	return plot.recipe(this);
    }

    /**
     * Returns a proper dataset label statement in MatLab if label
     * available.
     *
     * @param plot a <code>Plot</code> value
     * @return Label setting statement or "" if no label exists.
     */
    String labelString(Data plot) {
	String label = plot.getLabel();
	return (label != null) ? command("legend" + paren(quote(label))) : "";
    }

    /**
     * Returns a proper title statement in MatLab if available.
     *
     * @param plot a <code>Plot</code> value
     * @return MatLab statement or "" if no title exists.
     */
    String titleString(HasAxisLabels plot) {
	String title = plot.getTitle();
	int fontSize = plot.getFontSize();
	String setFontSize = (fontSize != 0 ? "," + quote("FontSize") + "," + fontSize : "");
	return (title != null) ? command("title" + paren(quote(title) + setFontSize)) : "";
    }

    /**
     * Returns a proper statement in MatLab to set the x-axis label if
     * available.
     *
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    String xLabelString(HasAxisLabels plot) {
	String label = plot.getXLabel();
	int fontSize = plot.getFontSize();
	String setFontSize = (fontSize != 0 ? "," + quote("FontSize") + "," + fontSize : "");
	return
	    (label != null) ? command("xlabel" + paren(quote(label) + setFontSize)) : "";
    }

    /**
     * Returns a proper statement in MatLab to set the y-axis label if
     * available.
     *
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    String yLabelString(HasAxisLabels plot) {
	String label = plot.getYLabel();
	int fontSize = plot.getFontSize();
	String setFontSize = (fontSize != 0 ? "," + quote("FontSize") + "," + fontSize : "");
	return (label != null) ? command("ylabel" + paren(quote(label) + setFontSize)) : "";
    }

    /**
     * Initial implementation of a collection of separate plots appear
     * on one figure aligend vertically with matching ranges.
     *
     * @param label a <code>String</code> value
     * @param plots a <code>Collection</code> value
     * @return a <code>String</code> value
     */
    public PlotHandle multiPlot(Collection plots, final PrintStream out) {
	//if (out == null) 
	//    out = System.out;

	PlotHandle handle = new PlotHandle(plots, this, windowNumber);

	setWindow(windowNumber);

	// go into multiplot mode
	// count number of plots, -> divide view into subplots
	final int noOfPlots = plots.size();
	final Range maximalRange = Plot.getMaxRange(plots);

	// iterate on each plot and plot them with size prefix
	new UninterruptedIteration() {
	    int thisPlot = 1;
	    public void job(Object o) {
		Plot plot = (Plot)o;

		// Set range
		plot.setRange(maximalRange);

		// Select subplot
		MatLab.this.out.println("subplot(" + noOfPlots + ", 1, " + thisPlot + ");");
		thisPlot++;
		// Bad bad hack.. shame on java's incapability of polymorphism
		String plotStr =
		    (plot instanceof SpikePlot) ?
		    plotToString((SpikePlot)plot) : plotToString((SimplePlot)plot);
		MatLab.this.out.println(plotStr);
		waitForResponse();	
		if (out == null) 
		    System.out.println(response());
		else
		    out.println(response());

		/* set title (NO SINGLE TITLE REQUIRED)
		   if (thisPlot == 2 && title != null) 
		   MatLab.this.out.println("title('" + title + "')");*/
	    }
	}.loop(plots);
	// leave multiplot mode

	windowNumber++;

	return handle;
    }

    /**
     * Initial implementation of a superposed collection of plots appearing
     * on one figure.
     * <p> Note: SpikePlots currently not allowed.
     * <p>TODO: return a Plot from this.
     * @param label a <code>String</code> value
     * @param plots a <code>Collection</code> value
     * @return a <code>String</code> value
     */
    public PlotHandle superposedPlot(final String title, final Collection plots,
				     final PrintStream out) {
	PlotHandle handle = new PlotHandle(plots, this, windowNumber);

	setWindow(windowNumber);

	// go into superposed plot mode
	//out.println("hold on;");

	final Range maximalRange = Plot.getMaxRange(plots);

	// iterate on each plot to get plotStr
	String plotStr = (String) 
	    new TaskWithReturn() {
		boolean first = true;
		String preambleStr = "";
		String plotStr =
		    assign("t", "[" + ((maximalRange!=null) ? range(maximalRange) : "")+ "]") +
		    "plot (";
		String labelStr = "legend(";
		String otherLabels = "";

		public void job(Object o) {
		    SimplePlot plot = (SimplePlot)o;

		    // Set range
		    plot.setRange(maximalRange);
		
		    preambleStr += plot.preamble();

		    plotStr += (!first ? ", " : "") + "t, " + plot.body();
		    String label = plot.getLabel();
		    labelStr += (!first ? ", " : "") + (label != null ? "'" + label + "'" : "");
		    // Another bad hack:
		    otherLabels = xLabelString(plot) + yLabelString(plot);
		    first = false;
		}
		public Object getValue() {
		    UninterruptedIteration.loop(plots, this);
		    plotStr += ");\n";
		    labelStr += ");\n";
		    return preambleStr + plotStr + labelStr + otherLabels;
		}
	    }.getValue();


	// set title 
	if (title != null) 
	    plotStr += "title('" + title + "')";

	// leave superposed plot mode
	//out.println("hold off;");

	this.out.println(plotStr);

	// DEBUG
	System.out.println(plotStr);

	waitForResponse();	
	if (out == null) 
	    System.out.println(response());
	else
	    out.println(response());

	windowNumber++;

	return handle;
    }

    

    // Tool methods

    /**
     * Returns a <code>String</code> that is a scalar product for MatLab which 
     * is "(a) .* (b)".
     *
     * @param a a <code>String</code> value
     * @param b a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String mul(String a, String b) {
	return paren(a) + ".*" + paren(b);
    }

    /**
     * Creates a file in the current directory named with the function for matlab.
     */
    public String def_func(String name, String[] params, String body) {
	try {
	    PrintWriter out = new PrintWriter(new FileOutputStream(name + ".m", false));
	    out.println("function r = " + func(name, params) + "\n" +
			"r = " + body + ";\n");
	    out.close();
	} catch (IOException e) {
	    throw new Error("Cannot produce MatLab function file " + name +
			    ".m in current directory: " + e);
	} // end of try-catch
	
	return "";
    }

    /**
     * Add a proper MatLab termination to the superior definition.
     *
     * @param var a <code>String</code> value
     * @param value a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String assign(String var, String value) {
	return command(super.assign(var, value));
    }

    /**
     * Returns range in the matlab format "a:(b-a)/points:b"
     *
     * @param range a <code>Range</code> value
     * @return a <code>String</code> value
     */
    public String range(Range range) {
	double start = range.getStart(), end = range.getEnd();
	return start + ":" + (end - start) / points  + ":" + end;
    }

    /**
     * Exits matlab process.
     *
     */
    public void close() {
	out.println("exit");
	out.flush();
    }

    /**
     * Matlab style 'quoting'.
     *
     * @param value a <code>String</code> value
     */
    public String quote(String value) {
	return "'" + value + "'";
    }

    /**
     * Add end-of-line separator for matlab.
     *
     * @param line a <code>String</code> value
     */
    public String command(String line) {
	return line + ";\n";
    }

    /**
     * Opens new matlab figure.
     *
     * @param windowNumber an <code>int</code> value
     */
    public void setWindow(int windowNumber) {
	out.println("figure;"); // Opens a new figure
    }

    public void writeEPS(PlotHandle handle, String filename) {
	out.println("print -depsc2 -f" + handle.getWindowNumber()
		    + " " + filename);
	waitForResponse();
	System.out.println(response());
    }

    public void writeEPSbw(PlotHandle handle, String filename) {
	out.println("print -deps2 -f" + handle.getWindowNumber()
		    + " " + filename);
	waitForResponse();
	System.out.println(response());
    }
    
    }// MatLab
