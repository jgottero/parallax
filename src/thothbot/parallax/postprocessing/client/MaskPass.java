/*
 * Copyright 2012 Alex Usachev, thothbot@gmail.com
 * 
 * This file based on the JavaScript source file of the THREE.JS project, 
 * licensed under MIT License.
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

package thothbot.parallax.postprocessing.client;

import thothbot.parallax.core.client.gl2.enums.GLenum;
import thothbot.parallax.core.client.renderers.WebGLRenderer;
import thothbot.parallax.core.client.textures.RenderTargetTexture;
import thothbot.parallax.core.shared.cameras.Camera;
import thothbot.parallax.core.shared.scenes.Scene;

public class MaskPass extends Pass
{
	private Scene scene;
	
	private Camera camera;
	private boolean clear = true;
	private boolean inverse = false;
	
	public MaskPass( Scene scene, Camera camera ) 
	{
		this.scene = scene;
		this.camera = camera;
		this.setEnabled(true);
	}
		
	@Override
	public void render (WebGLRenderer renderer, RenderTargetTexture writeBuffer, RenderTargetTexture readBuffer, float delta, boolean maskActive) 
	{
		// don't update color or depth
		renderer.getGL().colorMask( false, false, false, false );
		renderer.getGL().depthMask( false );

		// set up stencil

		int writeValue, clearValue;

		if ( this.inverse ) 
		{
			writeValue = 0;
			clearValue = 1;
		} 
		else 
		{
			writeValue = 1;
			clearValue = 0;
		}

		renderer.getGL().enable( GLenum.STENCIL_TEST.getValue() );
		renderer.getGL().stencilOp( GLenum.REPLACE.getValue(), GLenum.REPLACE.getValue(), GLenum.REPLACE.getValue() );
		renderer.getGL().stencilFunc( GLenum.ALWAYS.getValue(), writeValue, 0xffffffff );
		renderer.getGL().clearStencil( clearValue );

		// draw into the stencil buffer
		renderer.render( this.scene, this.camera, readBuffer, this.clear );
		renderer.render( this.scene, this.camera, writeBuffer, this.clear );

		// re-enable update of color and depth
		renderer.getGL().colorMask( true, true, true, true );
		renderer.getGL().depthMask( true );

		// only render where stencil is set to 1
		renderer.getGL().stencilFunc( GLenum.EQUAL.getValue(), 1, 0xffffffff );  // draw if == 1
		renderer.getGL().stencilOp( GLenum.KEEP.getValue(), GLenum.KEEP.getValue(), GLenum.KEEP.getValue() );
	}
	
	@Override
	public boolean isMaskActive()
	{
		return true;
	}
}
