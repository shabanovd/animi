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
package org.animotron.animi.cortex;

import java.util.Arrays;

/**
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 *
 */
public class RetinaZone extends Task {

	float XScale, YScale;
	
	int history[] = null;
	
	int input[];
	int inputSizeX, inputSizeY;
	
	OnOffMatrix matrix;

	protected RetinaZone(CortexZoneSimple sz) {
		super(sz);
		
    	matrix = new OnOffMatrix(null);
	}
	
	public void setInput(
			final float XScale, final float YScale, 
			final int input[], 
			final int inputSizeX, int inputSizeY) {
		
		if (history == null || history.length != sz.cols.length) {
			history = new int[sz.cols.length];
			Arrays.fill(history, 0);
		}
		
    	this.XScale = XScale;
    	this.YScale = YScale;
    	
    	this.input = input;
    	this.inputSizeX = inputSizeX;
    	this.inputSizeY = inputSizeY;
    	
	}

	@Override
	protected void release() {
	}

	private int input(int x, int y) {
		return input[(y * inputSizeX) + x];
	}

	private int history(int x, int y) {
		return history[(y * sz.width) + x];
    }

	private void history(int value, int x, int y) {
		history[(y * sz.width) + x] = value;
    }

	private void output(float value, int x, int y) {
    	sz.cols(value, x, y);
    }
	
	private float gray(final int x, final int y) {
	    int rgb = input(x, y);

	    int R = (rgb >> 16) & 0xFF;
		int G = (rgb >> 8 ) & 0xFF;
		int B = (rgb      ) & 0xFF;

	    //calculate gray
		float gray = (R + G + B) / 3.0f;

		return gray;
	}
	
	private int onOff(
			final int type,
			final float SA, final float SC, final float SP,
			float K_cont) {
		if (SA > Retina.Lelel_min) {
			if (type == 1) {
				//On-centre
				if (SC / SP > K_cont) {
					return 1;
				}
			
			} else if (type == 2) {
				//Off-centre
				if (SP / SC > K_cont) {
					return 1;
				}
			
			} else if (type == 3) {
				if (SC / SP > K_cont || SP / SC > K_cont) {
					return 1;
				}
			}
		}
		return 0;
	}
	
	private int X(int x) {
		return (int)(x * XScale);
	}

	private int Y(int y) {
		return (int)(y * YScale);
	}

	@Override
	public void gpuMethod(int x, int y) {
    	
    	int numInPeref = 0;
    	int numInCenter = 0;
    	int SP = 0;
    	int SC = 0;
    	
    	int xi, yi = 0;
    	
    	int t = 0;
    	for (int mX = 0; mX < matrix.regionSize(); mX++) {
        	for (int mY = 0; mY < matrix.regionSize(); mY++) {

        		t = matrix.getType(mX, mY);
        		//if nothing than do nothing
			    if (t == 0) continue;

		        xi = x - matrix.regionSize() + mX;
		        yi = y - matrix.regionSize() + mY;

		        //periphery
		        if (t == 1) {
		        	try {
				        SP += gray(X(xi), Y(yi));
			        	numInPeref++;
		        	} catch (ArrayIndexOutOfBoundsException e) {
		        		//ignore if gets out
		        	}
		        }
		        //center
		        else if (t == 2) {
		        	try {
			        	numInCenter++;
				        SC += gray(X(xi), Y(yi));
		        	} catch (ArrayIndexOutOfBoundsException e) {
		        		//ignore if gets out
		        	}
		        }
        	}
    	}
    	if (numInCenter == 0 || numInPeref == 0) {
			history(0, x, y);
			output(0, x, y);
			return;
    	}
    	
		float SA = ((SP + SC) / (float)(numInCenter + numInPeref));
		
		float K_cont = Retina.KContr1 + SA * (Retina.KContr2 - Retina.KContr1) / (float)Retina.Level_Bright;
	
		if (K_cont < Retina.KContr3) K_cont = Retina.KContr3;
	
		SC = SC / numInCenter;
		SP = SP / numInPeref;
		
		int type = ((x + y) % 2) + 1;
	
		float value = onOff(type, SA, SC, SP, K_cont);
		
		int oppositeStimuli = onOff(type % 2 + 1, SA, SC, SP, K_cont);

		//if no stimuli, check if opposite was 
		if (value == 0) {
			if (history(x, y) == 1 && oppositeStimuli == 0) {
				value = 0.5f;
			}
		}
		
		output(value, x, y);

		history(oppositeStimuli, x, y);
	}
}
