package org.hschart.impl;

import java.text.DecimalFormat;

import org.hschart.impl.AxisImpl.TickInfo;

/**
 * An implementation of <code>TickInfo</code> for representing a numeric axis.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public class NumberTickInfo implements TickInfo
{
    private final double myTick;
    
    private final DecimalFormat myFormat;

    /**
     * CTOR
     */
    public NumberTickInfo(double tick, DecimalFormat format)
    {
        myTick = tick;
        myFormat = format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String format(double value)
    {
        return myFormat.format(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getTick()
    {
        return myTick;
    }

}
