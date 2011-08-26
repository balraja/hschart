package org.hschart.impl;

import java.awt.Dimension;

import org.hschart.Axis;
import org.hschart.Canvas;
import org.hschart.Chart;
import org.hschart.ChartHost;
import org.hschart.DataModel;
import org.hschart.GraphableModel;
import org.hschart.ModelIterator;
import org.hschart.ModelUpdate;
import org.hschart.Range;
import org.hschart.ScreenImage;
import org.hschart.ScreenImageFactory;

/**
 * A draft implementation of simple line chart, wherein we connect the points
 * via lines.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public class LineChart implements Chart
{
    /** The domain axis */
    private final Axis myDomainAxis;
    
    /** The range axis */
    private final Axis myRangeAxis;
    
    /** 
     * The increment to be added to the current domain range of the model,
     * when updating the domain axis after an update.
     */
    private final double myDomainAxisIncrement;

    /** 
     * The increment to be added to the current value range of the model,
     * when updating the range axis after an update.
     */
    private final double myRangeAxisIncrement;
    
    /**
     * The underlying data model of the chart.
     */
    private final DataModel myModel;
    
    /** Chart's dimension */
    private Dimension myDimension;
    
    /** The factory to be used for generating screen images */
    private final ScreenImageFactory myScreenImageFactory;
    
    /**
     * The actual component on which our chart is drawn.
     */
    private final ChartHost myChartHost;
    
    /** This represents the image on screen */
    private ScreenImage myScreenImage;
       
    /**
     * CTOR
     */
    public LineChart(Axis domainAxis, 
                     Axis rangeAxis, 
                     double domainAxisIncrement,
                     double rangeAxisIncrement,
                     DataModel model,
                     Dimension dimension,
                     ScreenImageFactory screenImageFactory,
                     ChartHost chartHost)
    {
        myDomainAxis = domainAxis;
        myRangeAxis = rangeAxis;
        myDomainAxisIncrement = domainAxisIncrement;
        myRangeAxisIncrement = rangeAxisIncrement;
        myModel = model;
        myChartHost = chartHost;
        
        myDimension = dimension;
        myScreenImageFactory = screenImageFactory;
        myScreenImage = myScreenImageFactory.makeScreenImage(myDimension);
        updateScreenImage(true, myModel.iterator());
    }
    
    /** Draws the chart on the in memory screen buffer */
    private void updateScreenImage(boolean shouldDrawAxis, 
                                   ModelIterator iterator)
    {
        Canvas canvas = myScreenImage.getCanvas();
        if (shouldDrawAxis) {
            myDomainAxis.draw(canvas);
            myRangeAxis.draw(canvas);
        }
        GraphableModel graphableData = 
            new Lines(iterator, myDomainAxis, myRangeAxis);
        graphableData.draw(canvas);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateChart(ModelUpdate update)
    {
        myModel.handleUpdate(update);
        boolean shouldRedraw = false;
        if (!myDomainAxis.getRange().contains(myModel.getDomainRange())) {
            shouldRedraw = true;
            Range range = myModel.getDomainRange();
            myDomainAxis.updateAxis(
                new Range(range.getStart(), 
                          range.getEnd() + myDomainAxisIncrement));
        }
        if (!myRangeAxis.getRange().contains(myModel.getValueRange())) {
            shouldRedraw = true;
            Range range = myModel.getValueRange();
            myRangeAxis.updateAxis(
                new Range(range.getStart(), 
                          range.getEnd() + myRangeAxisIncrement));
        }
        
        if (shouldRedraw) {
            myScreenImage = myScreenImageFactory.makeScreenImage(myDimension);
        }
        updateScreenImage(shouldRedraw, 
                          shouldRedraw ? myModel.iterator()
                                       : ((SimpleDataModel) myModel)
                                           .lastUpdateIterator());
        myChartHost.redraw();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ScreenImage getImageOnScreen()
    {
        return myScreenImage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeDimension(Dimension dim)
    {
        myDimension = dim;
        myScreenImage = myScreenImageFactory.makeScreenImage(myDimension);
        updateScreenImage(true, myModel.iterator());
        myChartHost.redraw();
    }

}
