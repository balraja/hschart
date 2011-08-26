package org.hschart;

/**
 * An abstract type to denote a simple data point on the chart.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public interface DataPoint
{
    public double getDomainValue();
    
    public double getRangeValue();
}
