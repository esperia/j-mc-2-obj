/*******************************************************************************
 * Copyright (c) 2012
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 ******************************************************************************/
package org.jmc;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Material file class.
 * This class describes the file which contains a library of all materials in the
 * model. It is saved into a .MTL file that is linked from within the .OBJ file. 
 * @author danijel
 *
 */
public class MTLFile {
	
	/**
	 * A small enum for describing the sides of a cube.
	 * @author danijel
	 *
	 */
	public enum Side
	{
		TOP,
		BOTTOM,
		LEFT,
		RIGHT,
		FRONT,
		BACK
	}
	
	/**
	 * Reference to a colors object.
	 */
	private Colors colors;
	
	/**
	 * Main constructor.
	 */
	public MTLFile()
	{
		colors=new Colors();
	}
	
	/**
	 * Returns the material ID for a given side of a block.
	 * @param id ID of the block
	 * @param side side of the block
	 * @return material ID
	 */
	public int getMaterialId(int id, Side side)
	{
		if(colors.getColor(id)!=null)
		{
			return id;
		}
		return -1;
	}
	
	/**
	 * Returns a name for a material with the given ID.
	 * @param id material ID
	 * @return name of material
	 */
	public String getMaterial(int id)
	{
		if(colors.getColor(id)!=null)
			return "material-"+id;
		else return "unknown";
	}
	
	/**
	 * Prints a header with the name of the MTL file. This is used by the OBJ class
	 * to print a header in the OBJ file.
	 * @param out OBJ file writer
	 */
	public void header(PrintWriter out)
	{
		out.println("mtllib minecraft.mtl");
		out.println();
	}
	
	/**
	 * Saves the MTL file.
	 * @param file destination of the file
	 * @throws IOException exception if an error occurs during writing
	 */
	public void saveMTLFile(File file) throws IOException
	{		
		PrintWriter writer=new PrintWriter(new FileWriter(file));
		Locale l=null;
		
		writer.println("newmtl unknown");
		writer.println("Kd 1 0 1");
		writer.println();
		
		Color c;
		for(int i=0; i<256; i++)
		{
			c=colors.getColor(i);
			if(c!=null)
			{
				float r=c.getRed()/256.0f;
				float g=c.getGreen()/256.0f;				
				float b=c.getBlue()/256.0f;
				writer.println("newmtl material-"+i);
				writer.format(l,"Kd %2.2f %2.2f %2.2f",r,g,b);
				writer.println();
				writer.println();
			}
		}
		
		writer.close();
	}

}