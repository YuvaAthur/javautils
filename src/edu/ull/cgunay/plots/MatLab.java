
package edu.ull.cgunay.utils.plots;

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

public class MatLab extends Grapher  {
    /**
     * Spawns matlab interpreter and defines datatypes.
     *
     * @exception GrapherNotAvailableException if an error occurs
     * @see Grapher.DataType
     */
    public MatLab () throws GrapherNotAvailableException {	
	super("matlab");
	
	// Define datatypes
	new DefaultDataType();
	//new DataType("errorbar", "errorbar");
    }

    class DefaultDataType extends DataType { 
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
	    // for each range variable, declare the range
	    // for each vector variable initialize a variable
	    return "plot" + paren( data.xExpression() + ", " + data.yExpression());
	}
    }

    /**
     * @see #plotToString(Plot)
     */
    public String plotToString(final SpikePlot plot) {
	// First converts the given array into matlab style array string
	// (should be made a function)
	return 
	    assign("t", new StringTask("[ ", " ]") {
		    Range range = plot.getRange();

		    public void job(Object o) {
			double time = ((Double)o).doubleValue();
			if (time >= range.getStart() && time <= range.getEnd()) 
			    super.job(time + " "); // gets added to retval
		    }
		}.getString(plot.getSpikes())) + ";\n" +
	    "stem(t, ones(size(t,2)), 'filled');\n" +
	    getLabel(plot) + getTitle(plot) +
	    getXLabel(plot) + getYLabel(plot);

    }

    /**
     * Matlab specific plotting command. Calls Plot.body().
     *
     * @see Plot#body
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    public String plotToString(SimplePlot plot) {
	Range range = plot.getRange();

	return
	    plot.preamble() +
	    assign("t", "[" + ((range!=null) ? range(range) : "")+ "]") + ";\n" +
	    "plot (x, " + plot.body() + ");\n" +
	    getLabel(plot) + getTitle(plot) +
	    getXLabel(plot) + getYLabel(plot);
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
	    data = (Data)getFirst();
	    if (size() == 1)	// Single plot
		return // TODO: "t" should be done by looking at the variables
		    getDataPreamble(data) + ";\n" + 
		    //assign("t", "[" + ((range!=null) ? range(range) : "")+ "]") + ";\n" +
		    data.dataType.plotCommand(data) + ";\n " ;
	    else if (size() > 1) { // Multiplot
		// not implemented yet
		throw new Error("Multiplots not implemented!");
	    } // end of if (size() > 1)
	    
	    throw new Error("Multiplots: undefined");
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
			    Map.Entry entry = (Map.Entry) o;
			
			    if (entry.getValue() instanceof Range) {
				Range range = (Range) entry.getValue();
				super.job(assign((String) entry.getKey(),
						 "[" + ((range!=null) ? range(range) : "")+ "]"));
			    } else if (entry.getValue() instanceof Vector) {
				Vector vector = (Vector) entry.getValue();
				super.job(assign((String) entry.getKey(),
						 // make a matlab vector (TODO: generalize this)
						 new StringTask("[", "]").getString(vector)));
			    } else {
				new Error("Undefined type for variable " + entry.getKey() +
					  " in dataset.");
			    } // end of else
			
			}
		    }.getString(data.getVariables().entrySet());
	    }
	}

    /**
     * Alternative to <code>plotToString</code>, supposed to replace it
     * soon.
     *
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    public String plotToStringAlt(Plot plot) {
	//Range range = plot.getRange();

	return plot.recipe(this);
    }

    

    /**
     * Returns a proper dataset label statement in MatLab if label
     * available.
     *
     * @param plot a <code>Plot</code> value
     * @return Label setting statement or "" if no label exists.
     */
    String getLabel(Plot plot) {
	String label = plot.getLabel();
	return (label != null) ? "legend('" + label + "');\n" : "";
    }

    /**
     * Returns a proper title statement in MatLab if available.
     *
     * @param plot a <code>Plot</code> value
     * @return MatLab statement or "" if no title exists.
     */
    String getTitle(HasAxisLabels plot) {
	String title = plot.getTitle();
	return (title != null) ? "title('" + title + "');\n" : "";
    }

    /**
     * Returns a proper statement in MatLab to set the x-axis label if
     * available.
     *
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    String getXLabel(HasAxisLabels plot) {
	String label = plot.getXLabel();
	return (label != null) ? "xlabel('" + label  + "');\n" : "";
    }

    /**
     * Returns a proper statement in MatLab to set the y-axis label if
     * available.
     *
     * @param plot a <code>Plot</code> value
     * @return a <code>String</code> value
     */
    String getYLabel(HasAxisLabels plot) {
	String label = plot.getYLabel();
	return (label != null) ? "ylabel('" + label  + "');\n" : "";
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
		    ";\n" + "plot (";
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
		    otherLabels = getXLabel(plot) + getYLabel(plot);
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
			    ".m in current directory.");
	} // end of try-catch
	
	return "";
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
    
    }// MatLab
