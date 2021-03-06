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
package animi.acts;

import java.util.Random;


import animi.RuntimeParam;
import animi.cortex.*;
import animi.matrix.*;

/**
 * Delta rule. http://en.wikipedia.org/wiki/Delta_rule
 * 
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class LearningHebbianOnMemory extends Task {
	
	@RuntimeParam(name = "ny")
	public float ny = 0.1f;
	
	public float factor;
	
	public LearningHebbianOnMemory(LayerWLearning cz) {
		super(cz);
		
		factor = ny;
	}

	private static float adjust(
			final Floats in, 
			final Floats posW, 
			final float activity, 
			final float factor) {
		
		float sumQ2 = 0.0f;
		for (int index = 0; index < posW.length(); index++) {

			final float q = posW.getByIndex(index) + in.getByIndex(index) * activity * factor;

    		posW.setByIndex(q, index);
			
    		sumQ2 += q * q;
		}
	    return sumQ2;
	}

	public static void learn(
			final Floats in, 
			final Floats posW, 
			final float activity,
			final float factor) {
		
		if (activity > 0) {
			final float sumQ2 = adjust(in, posW, activity, factor);
			
			Mth.normalization(posW, sumQ2);
		}
	}
	
	@Override
	public boolean prepare() {
//		System.out.println("***************");
		return true;
	}
	
	Random rnd = new Random();

	@Override
	public void gpuMethod(final int x, final int y, final int z) {
		
//		if (cz.toLearning.get(x,y,z) <= 0f) return;
		
		final Mapping m0 = cz.in_zones[0];
		final Mapping remember = cz.in_zones[1];
		
		final LayerWLearning layer = (LayerWLearning) cz.in_zones[0].frZone();
		final Mapping memoryMapping = layer.in_zones[0];
		
		if (m0.senapsesCode().get(x,y,z) >= 0f) {
			return;
		}
		
		ArrayOfIntegersProxy senapses = m0.senapses().sub(x,y,z);
		
		int count = 0;
		for (int index = 0; index < senapses.length(); index++) {
			final int[] pos = senapses.getByIndex(index);
			if (memoryMapping.senapsesCode().get(pos[0], pos[1], pos[2]) >= 0) {
				count++;
			}
		}
		if (count < senapses.length() - 1)
			return;
		
		final float act = 1f;
//		final float act = m.toZone().neurons.get(x, y, z);
//		if (act <= 0) return;
		
//		m.senapsesCode().set((float)cz.app.getStimulator().getCode(), x,y,z);
		
//		System.out.println("["+x+","+y+","+z+"] "+act);

		for (int i = 1; i < 100; i++) {
			for (int index = 0; index < senapses.length(); index++) {
				final int[] pos = senapses.getByIndex(index);
	
				final Floats in = memoryMapping.senapseWeight().sub(pos[0], pos[1], pos[2]);
				final Floats weights = remember.senapseWeight().sub(x, y, z);
			
				LearningHebbianOnMemory.learn(
					in, 
					weights,
					act,
					1f / (float)(i)
				);
				
//				weights.debug("*** "+index);
			}
		}
		
		m0.senapsesCode().set(1f, x,y,z);
	}

	@Override
    protected void release() {
    }
}