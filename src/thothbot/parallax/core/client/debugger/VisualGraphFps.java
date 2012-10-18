/*
 * Copyright 2012 Alex Usachev, thothbot@gmail.com
 * 
 * This file is part of Parallax project.
 * 
 * Parallax is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 * 
 * Parallax is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with 
 * Parallax. If not, see http://www.gnu.org/licenses/.
 */

package thothbot.parallax.core.client.debugger;

import com.google.gwt.core.client.Duration;

public final class VisualGraphFps extends VisualGraphAbstract
{

	private double prevTime = Duration.currentTimeMillis();
	private double frames = 0;
	private double fps = 0, fpsMin = Double.POSITIVE_INFINITY, fpsMax = 0;
	
	@Override
	protected String getType() { return "fps"; }

	@Override
	protected void update(double time) 
	{
		frames ++;

		if ( time > prevTime + 1000 ) 
		{
			fps = Math.round( ( frames * 1000 ) / ( time - prevTime ) );
			fpsMin = Math.min( fpsMin, fps );
			fpsMax = Math.max( fpsMax, fps );

			text.setInnerText( fps + " FPS" );
			textMin.setInnerText(fpsMin + "");
			textMax.setInnerText(fpsMax + "");

			updateGraph( graph, Math.min( 30, 30 - ( fps / 100 ) * 30 ) );

			prevTime = time;
			frames = 0;
		}
	}
}