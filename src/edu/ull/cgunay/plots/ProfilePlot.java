
package edu.ull.cgunay.utils.plots;

// $Id$
// See licensing information at http://www.cacs.louisiana.edu/~cxg9789/LICENSE.txt
/**
 * Plots a time profile of an <code>double</code> entity changing in
 * time. The contents of the <code>Profile</code> should meaningfully
 * define the <code>Profilable.doubleValue()</code>.
 * 
 * <p>Created: Sat Apr 13 14:47:44 2002
 * <p>Modified: $Date$
 *
 * @author <a href="mailto:cengiz@ull.edu">Cengiz Gunay</a>
 * @version v2.0, and $Revision$ for this file.
 * @see Profile
 * @see Profilable#doubleValue
 */
public class ProfilePlot extends Plot  {
    Profile profile;

    public ProfilePlot (String title, Range range, Profile profile) {
	super(title, range);

	this.profile = profile;
	
	if (range == null) 
	    this.range = profile.getRange();
    }

    public ProfilePlot (Profile profile) {
	this(null, null, profile);
    }

    /**
     * Creates a profile string changing with variable "t". 
     * @see Grapher#profile
     * @return a <code>String</code> value
     */
    public String body(Grapher grapher) { 
	return grapher.profile(profile, range); 
    }

    /**
     * Create a <code>ProfileData</code> object, and send to the <code>Axis</code>.
     *
     * @param grapher a <code>Grapher</code> value
     * @return a <code>String</code> value
     */
    public String recipe(final Grapher grapher) {
	Grapher.ProfileData data = grapher.new ProfileData("default", label) {
		public String yExpression() {
		    /* SHOULD BE: "Grapher.this" instead of "grapher",
		     * but Java is buggy! */
		    return ProfilePlot.this.body(grapher); 
		}
	    };

	data.setRange(range);

	Grapher.Axis axis = grapher.createAxis();
	axis.addData(data);
	axis.setRange(range);

	return /*preamble(grapher) + */axis.getString();
    }
    
}// ProfilePlot
