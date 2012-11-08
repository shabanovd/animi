/*
 *  Copyright (C) 2012 The Animo Project
 *  http://animotron.org
 *
 *  This file is part of Animotron.
 *
 *  Animotron is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of
 *  the License, or (at your option) any later version.
 *
 *  Animotron is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of
 *  the GNU Affero General Public License along with Animotron.
 *  If not, see <http://www.gnu.org/licenses/>.
 */
package org.animotron.animi.cortex;

import org.animotron.animi.InitParam;
import org.animotron.animi.Utils;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Simple cortex zone
 * 
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:gazdovsky@gmail.com">Evgeny Gazdovsky</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class CortexZoneSimple implements Layer {

    String name;
    MultiCortex mc;
    
    /** State of complex neurons (outputs cortical columns) **/
    public NeuronComplex[][] col;
    
	@InitParam(name="width")
	public int width = 192;
	@InitParam(name="height")
	public int height = 144;

    CortexZoneSimple() {
    	name = null;
    	mc = null;
    }

    public CortexZoneSimple(String name, MultiCortex mc) {
        this.name = name;
        this.mc = mc;
    }
    
    public void init() {
        col = new NeuronComplex[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                col[x][y] = new NeuronComplex(x,y);
            }
        }
    }

    public BufferedImage getImage() {
        int c;
    	
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                c = col[x][y].backProjection > 0 ? Color.WHITE.getRGB() : Color.BLACK.getRGB();
                image.setRGB(x, y, Utils.create_rgb(255, c, c, c));
            }
        }
        return image;
    }


	@Override
	public Object whatAt(Point point) {
		return col[point.x][point.y];
	}

    public String getImageName() {
    	return toString();
    }

    public String toString() {
    	return name;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

	@Override
	public void process() {
	}

	@Override
	public void set(int x, int y, double b) {
		col[x][y].activity = b;
		col[x][y].backProjection = b;
		col[x][y].posActivity = b;
	}

	@Override
	public void focusGained(Point point) {
	}

	@Override
	public void focusLost(Point point) {
	}

	@Override
	public void closed(Point point) {
	}

	public NeuronComplex getCol(int x, int y) {
		return col[x][y];
	}
}