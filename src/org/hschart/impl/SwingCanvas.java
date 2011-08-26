package org.hschart.impl;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;

import org.hschart.Canvas;

/**
 * Implements <code>Canvas</code> by encapsulating a {@link Graphics2D} and 
 * drawing on it.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public class SwingCanvas implements Canvas
{
    private final Dimension myCanvasDimension;
    
    private final Graphics2D myGraphics; 
    
    /**
     * CTOR
     */
    public SwingCanvas(Graphics2D graphics, Dimension dim)
    {
        myGraphics = graphics;
        myCanvasDimension = dim;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight()
    {
        return (int) myCanvasDimension.getHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth()
    {
        return (int) myCanvasDimension.getWidth();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawRuler(int x, 
                          int y, 
                          int spikeSpace, 
                          int numSpikes)
    {
        GeneralPath path = new GeneralPath();
        path.moveTo(x, y);
        int spikeX = -1;
        for (int i = 1; i <= numSpikes; i++) {
            if (spikeX == -1) {
                spikeX = x + (spikeSpace / 2);
            }
            else {
                spikeX += spikeSpace;
            }
            path.lineTo(spikeX, y);
            path.lineTo(spikeX, y + 2);
            path.moveTo(spikeX, y);
        }
        
        spikeX += spikeSpace;
        path.lineTo(spikeX, y);
        myGraphics.draw(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawText(int x, int y, String title)
    {
        myGraphics.drawString(title, x, y);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void drawTextInRect(int x, int y, int width, String title)
    {
        x += ((width / 2) - (getWidth(title) / 2));
        y -= (getHeight(title) / 2);  
        myGraphics.drawString(title, x, y);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void drawVerticalRuler(int x, int y, int spikeSpace, int numSpikes)
    {
        GeneralPath path = new GeneralPath();
        path.moveTo(x, y);
        int spikeY = y;
        for (int i = 1; i <= numSpikes; i++) {
            if (spikeY == y) {
                spikeY -= (spikeSpace / 2);
            }
            else {
                spikeY -= spikeSpace;
            }
            path.lineTo(x, spikeY);
            path.lineTo(x - 2, spikeY);
            path.moveTo(x, spikeY);
        }
        
        spikeY -= spikeSpace;
        path.lineTo(x, spikeY);
        myGraphics.draw(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawVerticalText(int x, int y, String title)
    {
        myGraphics.rotate((3 * Math.PI / 2), x, y);
        myGraphics.drawString(title, x, y);
        myGraphics.rotate((-3 * Math.PI / 2), x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawVerticalTextInRect(int x, int y, int height, String title)
    {
        // Center the text 
        int offset = ((height / 2) - (getWidth(title) / 2));
        y -= offset;
        x += getHeight(title); 
        myGraphics.rotate((3 * Math.PI / 2), x, y);
        myGraphics.drawString(title, x, y);
        myGraphics.rotate((-3 * Math.PI / 2), x, y);

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getHeight(String s)
    {
        return (int) myGraphics.getFontMetrics()
                               .getLineMetrics(s, myGraphics)
                               .getHeight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getWidth(String s)
    {
        return myGraphics.getFontMetrics().charsWidth(s.toCharArray(), 
                                                      0,
                                                      s.length());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void drawLine(double x1, double y1, double x2, double y2)
    {
        myGraphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
    }
}
