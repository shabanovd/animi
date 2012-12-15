/*
 *  Copyright (C) 2012 The Animo Project
 *  http://animotron.org
 *
 *  This file is part of Animi.
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
package org.animotron.animi.simulator;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.animotron.animi.Params;
import org.animotron.animi.cortex.MultiCortex;
import org.animotron.animi.cortex.Retina;
import org.animotron.animi.gui.Application;
import org.animotron.animi.simulator.figures.Figure;
import org.animotron.animi.simulator.figures.OvalAnime;
import org.animotron.animi.simulator.figures.RectAnime;

/**
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 * @author <a href="mailto:gazdovsky@gmail.com">Evgeny Gazdovsky</a>
 *
 */
public class StimulatorAnime extends AbstractStimulator {
	
    @Params
    public Figure[] figures;
    
    public StimulatorAnime(Application application, MultiCortex cortexs) {
    	super(application, cortexs);
	}

	public void init() {
		Retina retina = mc.retina;
		
        img = new BufferedImage(
        		retina.width()  + (retina.safeZone * 2), 
        		retina.height() + (retina.safeZone * 2),
        		BufferedImage.TYPE_INT_RGB);
		
		int b1 = 40;
		int b2 = 30;
        figures = new Figure[] {
//    		new LineAnime(
//        		15, 0,
//        		new int[][] {
//        				{b1, b1},
//        				{mc.retina.width() - b1, b1},
//        				{mc.retina.width() - b1, mc.retina.height() - b1},
//        				{b1, mc.retina.height() - b1},
//        				{b1, b1}
//        		}
//        	),
//        	new LineAnime(
//        	    15, -0.03,
//        	    new int[][] {
//        	            {b1, b1},
//        	            {mc.retina.width() - b1, b1},
//        	            {mc.retina.width() - b1, mc.retina.height() - b1},
//        	            {b1, mc.retina.height() - b1},
//        	            {b1, b1}
//        	    }
//        	),
//        	new LineAnime(
//        	    15, 0.03,
//        	    new int[][] {
//        	            {mc.retina.width() - b1, b1},
//        	            {mc.retina.width() - b1, mc.retina.height() - b1},
//        	            {b1, mc.retina.height() - b1},
//        	            {b1, b1},
//        	            {mc.retina.width() - b1, b1},
//        	    }
//        	),
//        	new LineAnime(
//        	    15, -0.07,
//        	    new int[][] {
//        	            {mc.retina.width() - b1, mc.retina.height() - b1},
//        	            {b1, mc.retina.height() - b1},
//        	            {b1, b1},
//        	            {mc.retina.width() - b1, b1},
//        	            {mc.retina.width() - b1, mc.retina.height() - b1}
//        	    }
//        	),
//        	new LineAnime(
//        	    15, 0.07,
//        	    new int[][] {
//        	            {b1, mc.retina.height() - b1},
//        	            {b1, b1},
//        	            {mc.retina.width() - b1, b1},
//        	            {mc.retina.width() - b1, mc.retina.height() - b1},
//        	            {b1, mc.retina.height() - b1}
//        	    }
//        	),
        	new OvalAnime(30,
        	    new int[][] {
        	            {b1, b1},
        	            {mc.retina.width() - b1, mc.retina.height() - b1},
        	            {mc.retina.width() - b1, b1},
        	            {b1, mc.retina.height() - b1},
        	            {b1, b1}
        	    }
        	),
        	new OvalAnime(32,
        	    new int[][] {
        	            {b1, b1},
        	            {mc.retina.width() - b1, mc.retina.height() - b1},
        	            {mc.retina.width() - b1, b1},
        	            {b1, mc.retina.height() - b1},
        	            {b1, b1}
        	    }
        	),
        	new RectAnime(
        	    30, 0.05,
        	    new int[][] {
        	            {b2, b2},
        	            {b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, b2},
        	            {b2, b2}
        	    }
        	),
        	new RectAnime(
        	    32, 0.05,
        	    new int[][] {
        	            {b2, b2},
        	            {b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, b2},
        	            {b2, b2}
        	    }
        	),
        	new RectAnime(
        	    40, 0.05,
        	    new int[][] {
        	            {b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, b2},
        	            {b2, b2},
        	            {b2, mc.retina.height() - b2}
        	    }
        	),
        	new RectAnime(
        	    42, 0.05,
        	    new int[][] {
        	            {b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, b2},
        	            {b2, b2},
        	            {b2, mc.retina.height() - b2}
        	    }
        	),
        	new RectAnime(
        	    30, 0,
        	    new int[][] {
        	            {mc.retina.width() - b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, b2},
        	            {b2, b2},
        	            {b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, mc.retina.height() - b2}
        	    }
        	),
        	new RectAnime(
        	    32, 0,
        	    new int[][] {
        	            {mc.retina.width() - b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, b2},
        	            {b2, b2},
        	            {b2, mc.retina.height() - b2},
        	            {mc.retina.width() - b2, mc.retina.height() - b2}
        	    }
        	)
        };
	}

	public void step() {

        for (Figure i : figures) {
        	if (i.isActive()) {
        		i.step();
        	}
        }
        
        Graphics g = img.getGraphics();
        g.clearRect(0, 0, img.getWidth(), img.getHeight());

        for (Figure i : figures) {
        	if (i.isActive()) {
        		i.drawImage(g);
        	}
        }
	}

	public BufferedImage getNextImage() {
		step();
		
		return getImage();
	}
}