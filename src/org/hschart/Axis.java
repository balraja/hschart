
package org.hschart;

/**
 * Defines the basic signature of a chart's axis.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public interface Axis extends Graphable
{
    /** Returns true, if the axis corresponds to a value axis of the chart */
    public boolean isValueAxis();
    
    /** Returns the range of axis */
    public Range getRange();
     
    /** A method for updating the range of axis */
    public void updateAxis(Range range);
    
    /** Transforms the given value to a coordinate on the screen */
    public double transformToScreen(double value, Canvas canvas);
}
