package org.hschart;

/**
 * Type that abstracts out the in memory representation of what chart looks
 * like on screen.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public interface ScreenImage
{
    /** The canvas to be used for drawing on the in memory image */
    public Canvas getCanvas();
}
