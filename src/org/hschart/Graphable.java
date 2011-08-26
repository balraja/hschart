/**
 * 
 */
package org.hschart;

/**
 * Defines the type that can be drawn on a canvas.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public interface Graphable
{
    /** Subclasses should override this method for drawing on canvas */
    public void draw(Canvas canvas);
}
