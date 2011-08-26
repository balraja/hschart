package org.hschart.impl;

import org.hschart.Axis;
import org.hschart.Canvas;
import org.hschart.DataPoint;
import org.hschart.GraphableModel;
import org.hschart.ModelIterator;

/**
 * Implements {@link GraphableModel} where datapoints are connected by lines.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public class Lines extends GraphableModel
{
    private final Axis myDomainAxis;
    
    private final Axis myRangeAxis;
    
    /**
     * CTOR
     */
    public Lines(ModelIterator modelIterator, Axis domainAxis, Axis rangeAxis)
    {
        super(modelIterator);
        myDomainAxis = domainAxis;
        myRangeAxis = rangeAxis;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Canvas canvas)
    {
        ModelIterator iterator = getIterator();
        while (iterator.hasNext()) {
            DataPoint dp = iterator.next();
            DataPoint pdp = iterator.getPreviousDataPoint();
            if (pdp != null) {
                double x0 = 
                    myDomainAxis.transformToScreen(pdp.getDomainValue(), canvas);
                double y0 =
                    myRangeAxis.transformToScreen(pdp.getRangeValue(), canvas);
                double x1 = 
                    myDomainAxis.transformToScreen(dp.getDomainValue(), canvas);
                double y1 =
                    myRangeAxis.transformToScreen(dp.getRangeValue(), canvas);
                canvas.drawLine(x0, y0, x1, y1);
            }
        }
    }

}
