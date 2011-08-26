package org.hschart.impl;

import org.hschart.Axis;
import org.hschart.Canvas;
import org.hschart.Range;

/**
 * Defines an implementation of chart <code>Axis</code>
 * 
 * @author subbiahb
 * @version $Id:$
 */
public class AxisImpl implements Axis
{
    /** Constant to denote the space between the subsequent tick units */
    private static final int SPACE = 20;
    
    /** Constant to denote the max possible height */
    private static final int STR_HEIGHT = 15;
    
    /** The offset at which the tic strings are to be drawn */
    private static final int TIC_STR_OFFSET = 10;
    
    /** The offset at which the tic strings are to be drawn */
    private static final int LINE_OFFSET_FROM_TIC_STR = 12;
    
    /** Constant to denote the space at which other axis line is drawn */
    private static final int OTHER_AXIS_OFFSET = 
        ((2 * STR_HEIGHT) + TIC_STR_OFFSET + LINE_OFFSET_FROM_TIC_STR);
    
    /** Set to true if this axis is domain axis */
    private final boolean myIsValueAxis;

    /** The information corresponding to this axis tick size */
    private final TickInfo myTickInfo;
    
    /** The title of the axis */
    private final String myTitle;
    
    /** The range of the axis */
    private Range myRange;
    
    /** 
     * Type that is used for getting information corresponding to the tick
     * unit of a axis.
     */
    public static interface TickInfo
    {
        /** Returns the tick unit */
        public double getTick();
                
        /** Returns the formatted value of the string */
        public String format(double value);
    }

    /**
     * CTOR
     */
    public AxisImpl(boolean isValueAxis, 
                    String title,
                    TickInfo tickInfo,
                    Range range)
    {
        myIsValueAxis = isValueAxis;
        myTitle = title;
        myTickInfo = tickInfo;
        myRange = range;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValueAxis()
    {
        return myIsValueAxis;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Range getRange()
    {
        return myRange;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAxis(Range range)
    {
        if (myRange == null || !myRange.contains(range)) {
            myRange = myRange.merge(range);
        }
    }
    
    /** Draws axis horizontally */
    private void drawHozizontalAxisAt(int x, int y, Canvas canvas)
    {
        int ticStringWidth = 
            canvas.getWidth(myTickInfo.format(myRange.getEnd()));
        double width = canvas.getWidth() - x;
        int numSpikes = 
           (int) Math.floor(width / (ticStringWidth + SPACE));
        
        // Draw the tic strings.
        double tics = myRange.getDifference() / myTickInfo.getTick();
        double increment = tics / (numSpikes + 1);
        increment *= myTickInfo.getTick();
        int ticX = x + (SPACE / 2);
        y -= TIC_STR_OFFSET;
        double value = myRange.getStart() + increment;
        for (int i = 0; i < numSpikes; i++) {
            String ticString = myTickInfo.format(value);
            canvas.drawTextInRect(ticX, y, ticStringWidth, ticString);
            ticX += (ticStringWidth + SPACE);
            value += increment;
        }

        y -= (STR_HEIGHT + LINE_OFFSET_FROM_TIC_STR);
        canvas.drawRuler(x, 
                         y, 
                         ticStringWidth + SPACE, 
                         numSpikes);

    }
    
    /** Draws axis horizontally */
    private void drawVerticalAxisAt(int x, int y, Canvas canvas)
    {
        int ticStringWidth = 
            canvas.getWidth(myTickInfo.format(myRange.getEnd()));
        
        int height = Math.min(y, canvas.getHeight());
        int numSpikes = 
           (int) Math.floor(height / (ticStringWidth + SPACE));
        
        // Draw the tic strings.
        double tics = myRange.getDifference() / myTickInfo.getTick();
        double increment = tics / (numSpikes + 1);
        increment *= myTickInfo.getTick();
        int ticY = y - (SPACE / 2);
        x += TIC_STR_OFFSET;
        double value = myRange.getStart() + increment;
        for (int i = 0; i < numSpikes; i++) {
            String ticString = myTickInfo.format(value);
            canvas.drawVerticalTextInRect(x, ticY, ticStringWidth, ticString);
            ticY -= (ticStringWidth + SPACE);
            value += increment;
        }

        x += (STR_HEIGHT + LINE_OFFSET_FROM_TIC_STR);
        canvas.drawVerticalRuler(x, 
                                 y, 
                                 ticStringWidth + SPACE, 
                                 numSpikes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void draw(Canvas canvas)
    {
        if (myRange == null) {
            throw new IllegalArgumentException("Tick Range is null");
        }
        int titleX = 0;
        int titleY = canvas.getHeight();
        
        // Center the text 
        if (myIsValueAxis) {
            canvas.drawVerticalTextInRect(
                titleX, titleY, canvas.getHeight(), myTitle);
            titleX += STR_HEIGHT;
            drawVerticalAxisAt(titleX, 
                               titleY - OTHER_AXIS_OFFSET, 
                               canvas);  
        }
        else {
            canvas.drawTextInRect(
                titleX, titleY, canvas.getWidth(), myTitle);
            //titleY -= canvas.getHeight(myTitle); 
            titleY -= STR_HEIGHT;
            drawHozizontalAxisAt(titleX + OTHER_AXIS_OFFSET, 
                                 titleY, 
                                 canvas);
        }   
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public double transformToScreen(double value, Canvas canvas)
    {
        assert(value >= myRange.getStart() && value <= myRange.getEnd());
        double screenDim = 
            myIsValueAxis ? canvas.getHeight() : canvas.getWidth();
        screenDim -= OTHER_AXIS_OFFSET;
        double valueRatio = (value - myRange.getStart())
                                / myRange.getDifference();
        double pointOnScn =  valueRatio * screenDim;
        if (myIsValueAxis) {
            pointOnScn = screenDim - pointOnScn;
        }
        else {
            pointOnScn += OTHER_AXIS_OFFSET;
        }
        return pointOnScn;
    }
}
