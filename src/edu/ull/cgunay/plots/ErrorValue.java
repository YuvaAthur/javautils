
package edu.ull.cgunay.utils.plots;

import java.io.Serializable;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * Simple encapsulation of a <code>double</code> value with minimum
 * and maximum error.
 *
 *
 * <p>Created: Mon Nov 11 18:00:22 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version v2.0, $Revision$ for this file.
 */

public class ErrorValue extends Profilable implements Serializable {
    public double value;
    public double minValue;
    public double maxValue;

    public ErrorValue (double value, double minValue, double maxValue) {
	this.value = value;
	this.minValue = minValue;
	this.maxValue = maxValue;
    }

    public double doubleValue() {
	throw new Error("undefined!");
    }
    
}// ErrorValue
