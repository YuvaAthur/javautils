
package edu.ull.cgunay.plots;

import java.util.List;
import java.util.LinkedList;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 *  A template, from which a real <code>Grapher.Axis</code> object can
 *  be instantiated when a <code>grapher</code> is available.
 *
 *
 * <p>Created: Tue May  6 17:34:07 2003
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version $Revision$ for this file.
 * @see Grapher.Axis
 * @see Grapher
 */

public class AxisTemplate extends LinkedList implements HasAxisLabels {

    /**
     * Datas to be superposed in this axis.
     */
    Grapher.Data[] datas;

    /**
     * Gets the value of datas
     *
     * @return the value of datas
     */
    public Grapher.Data[] getDatas()  {
	return this.datas;
    }

    /**
     * Sets the value of datas
     *
     * @param argDatas Value to assign to this.datas
     */
    public void setDatas(Grapher.Data[] argDatas) {
	this.datas = argDatas;
    }


    /**
     * Takes a dataset to plot.
     *
     * @param datas to plot
     */
    public AxisTemplate (Grapher.Data[] datas) {
	this.datas = datas;
    }

    /**
     * Takes a <code>List</code> of datasets to plot.
     *
     * @param datas a <code>List</code> value
     */
    public AxisTemplate (List datas) {
	this.datas = (Grapher.Data[]) datas.toArray(new Grapher.Data[0]);
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
    
}// AxisTemplate
