/*
 *  Copyright (C) 2012-2013 The Animo Project
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
import org.animotron.animi.cortex.Retina;
import org.animotron.animi.gui.Application;
import org.animotron.animi.simulator.figures.Figure;
import org.animotron.animi.simulator.figures.RectAnime;

/**
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 * @author <a href="mailto:gazdovsky@gmail.com">Evgeny Gazdovsky</a>
 *
 */
public class StimulatorAnime extends AbstractStimulator {
	
    @Params
    public Figure[] figures;
    
    public StimulatorAnime(Application application) {
    	super(application);
	}

	public void init() {
		Retina retina = app.cortexs.retina;
		
        img = new BufferedImage(
        		retina.worldWidth(), 
        		retina.worldHeight(),
        		BufferedImage.TYPE_INT_RGB);
		
//		int b1 = 40;
//		int b2 = 30;
        
        figures = new Figure[2];

		int step = 42;
        int[][] steps = new int[step*4 + 2][];

        int j = 0;
        for (int i = 0; i < (step); i++) {
        	steps[j++] = new int[] {-retina.worldStep(), 0};
        }
        for (int i = 0; i < step; i++) {
        	steps[j++] = new int[] {+retina.worldStep(), 0};
        }
        
        //move out and wait...
    	steps[j++] = new int[] {-retina.worldStep()*100, 0};
        for (int i = 0; i < (step*2); i++) {
        	steps[j++] = new int[] {0, 0};
        }
    	steps[j++] = new int[] {retina.worldStep()*100, 0};

    	figures[0] = new RectAnime(
    			(int)(img.getWidth() * 0.5) + retina.worldStep() * step / 2, (int)(img.getHeight() * -0.2),
    			(int)(img.getWidth() * 2), (int)(img.getHeight() *  2),
	    	    true,
	    	    steps,
    			true
	    	);

        steps = new int[step*4 + 2][];
        j = 0;
        
        //move out and wait...
    	steps[j++] = new int[] {0, -retina.worldStep()*100};
        for (int i = 0; i < (step*2); i++) {
        	steps[j++] = new int[] {0, 0};
        }
    	steps[j++] = new int[] {0, retina.worldStep()*100};

    	
    	for (int i = 0; i < (step); i++) {
        	steps[j++] = new int[] {0, -retina.worldStep()};
        }
        for (int i = 0; i < step; i++) {
        	steps[j++] = new int[] {0, +retina.worldStep()};
        }

        figures[1] = new RectAnime(
    			(int)(img.getWidth() * -0.5), (int)(img.getHeight() * 0.5) + retina.worldStep() * step / 2,
    			(int)(img.getWidth() *  2), (int)(img.getHeight() * 2),
	    	    true,
	    	    steps,
    			true
	    	);
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
