package edu.ull.cgunay.plots;

/**
 * Features methods to access axis labels.
 * <p>TODO: Change the name to <code>AxisProperties</code>
 * <p>Created: Thu Nov  7 16:24:43 2002
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version 2.0
 */

public interface HasAxisLabels {
    Range getRange();
    String getTitle();
    String getXLabel();
    String getYLabel();
    int getFontSize();
}// HasAxisLabels
