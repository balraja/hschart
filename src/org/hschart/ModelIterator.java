package org.hschart;

import java.util.Iterator;

/**
 * A simple iterator over the data points in the model, with an additional 
 * facility to query for the values of previous data point.
 *  
 * @author subbiahb
 * @version $Id:$
 */
public interface ModelIterator extends Iterator<DataPoint>
{
    /** Returns the DataPoint at the previous location */
    public DataPoint getPreviousDataPoint();
}
