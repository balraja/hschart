package org.hschart;

import java.awt.Dimension;

/**
 * Factory for making {@link ScreenImage} for the given dimension.
 * 
 * @author subbiahb
 * @version $Id:$
 */
public interface ScreenImageFactory
{
     /** Returns a {@link ScreenImage} corresponding to the given dimension */
     public ScreenImage makeScreenImage(Dimension d);
}
