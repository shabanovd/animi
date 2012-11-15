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
package org.animotron.animi.acts.old;

import org.animotron.animi.cortex.*;

/**
 * Активация простых нейронов при узнавании запомненной картины
 * 
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:gazdovsky@gmail.com">Evgeny Gazdovsky</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class Subtraction { //implements Act<CortexZoneComplex> {

	public Subtraction() {}

//    @Override
    public static NeuronComplex[][] process(CortexZoneComplex layer, final int x, final int y) {

    	
    	// Ac = Sum ( A(in)j * qj )
    	// A(in)j = qj * Ac / Sum(q^2)
    	
    	CortexZoneSimple zone = layer.in_zones[0].zone;
    	NeuronComplex[][] ms = new NeuronComplex[zone.width][zone.height];
    	for (int _x = 0; _x < zone.width; _x++) {
        	for (int _y = 0; _y < zone.height; _y++) {
        		ms[_x][_y] = new NeuronComplex(zone.col[_x][_y]);
        	}
    	}
    	
    	NeuronComplex cn = layer.col[x][y];
    	if (cn.activity > 0) {
    		double Q2 = 0;
    		for (LinkQ link : cn.Qs.values()) {
    			Q2 += link.q * link.q;
    		}
    		
    		for (LinkQ link : cn.Qs.values()) {
    			NeuronComplex in = ms[link.synapse.x][link.synapse.y];
    			in.activity = cn.activity * link.q * Q2;
    			in.posActivity = in.activity;
    		}
    		
    	}
    	return ms;
    }
}