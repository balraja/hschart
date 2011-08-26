/**
 * 
 */
package org.hschart;

/**
 * Type for abstracting out the drawing surface. The idea is that by not 
 * exposing ourselves to swing we can alternate graphics libraries eclipse 
 * swt.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public interface Canvas
{    
    /** Returns the width of the given string */
    public int getWidth(String s);
    
    /** Returns the height of string */
    public int getHeight(String s);
    
    /** Draws a ruler on the canvas */
    public void drawRuler(int x, 
                          int y, 
                          int spikeSpace,
                          int numSpikes);
    
    /** Draws a string on the canvas */
    public void drawText(int x, int y, String title);
    
    /** 
     * Draws a string on the canvas in the middle of rectangle , bounded by 
     * width and height of string
     */
    public void drawTextInRect(int x, 
                               int y, 
                               int width,
                               String title);
    
    /** Draws a ruler on the canvas */
    public void drawVerticalRuler(int x, 
                                  int y, 
                                  int spikeSpace,
                                  int numSpikes);
    
    /** Draws a perpendicular string on the canvas */
    public void drawVerticalText(int x, int y, String title);
    
    /** 
     * Draws a perpendicular string on the canvas in the middle of the rectangle
     * bounded by the given height and (actual height of the text acting as
     * width).
     */
    public void drawVerticalTextInRect(int x, 
                                       int y, 
                                       int height,
                                       String title);
    
    /** Draws line between the specified coordinates */
    public void drawLine(double x0, double yo, double x1, double y1);
    
    /** Returns the width of the canvas */
    public int getWidth();
    
    /** Returns the height of canvas */
    public int getHeight();
}
