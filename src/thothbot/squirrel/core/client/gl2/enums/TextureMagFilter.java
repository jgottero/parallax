/*
 * Copyright 2012 Alex Usachev, thothbot@gmail.com
 * 
 * This file is part of Squirrel project.
 * 
 * Squirrel is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the 
 * Free Software Foundation, either version 3 of the License, or (at your 
 * option) any later version.
 * 
 * Squirrel is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with 
 * Squirrel. If not, see http://www.gnu.org/licenses/.
 */

package thothbot.squirrel.core.client.gl2.enums;

/**
 * GL2 Texture MagFilter flags.
 * 
 * @author thothbot
 *
 */
public enum TextureMagFilter 
{
	NEAREST(GLenum.NEAREST),
	LINEAR(GLenum.LINEAR);

	private final GLenum value;

	private TextureMagFilter(GLenum GLenum) 
	{
		this.value = GLenum;
	}
	
	public GLenum getEnum()
	{
		return this.value;
	}

	/**
	 * Gets the enum's numerical value.
	 */
	public int getValue() 
	{
		return value.getValue();
	}
}
