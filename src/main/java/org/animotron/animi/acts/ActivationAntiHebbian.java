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
package org.animotron.animi.acts;

import org.animotron.animi.RuntimeParam;
import org.animotron.animi.cortex.*;

/**
 * 
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class ActivationAntiHebbian extends Task {
	
	@RuntimeParam(name = "k")
	public float k = 1f;

	public ActivationAntiHebbian(CortexZoneComplex cz) {
		super(cz);
	}

	private float activity(final Mapping m, final int x, final int y, final int p) {
		float sum = 0.0f;
	    for(int l = 0; l < m.ns_links; l++) {
	    	final int xi = m.linksSenapse.get(x, y, l, 0);
	    	final int yi = m.linksSenapse.get(x, y, l, 1);
	        
	    	if (xi >= 0 && xi < m.frZone.width && yi >= 0 && yi < m.frZone.height) {
	    		sum += m.frZone.cols.get(xi, yi) * m.inhibitoryWeight.get(x, y, p, l);
	        }
	    }
	    
	    return sum * k;
	}

	public void gpuMethod(final int x, final int y) {
		
		final Mapping m = cz.in_zones[0];
		
		for (int p = 0; p < cz.package_size; p++) {
			
			final float activity = cz.colNeurons.get(x, y, p) - activity(m, x, y, p);
	
			cz.colNeurons.set(activity < 0 ? 0 : activity, x, y, p);
		}
		
//		if (Float.isNaN(activity)) {
//			activity(m, x, y);
//		}
//		
//		if (activity != 0)
//			System.out.println(""+x+" - "+y+" = "+activity);
	}

	@Override
    protected void release() {
    }
	
}
