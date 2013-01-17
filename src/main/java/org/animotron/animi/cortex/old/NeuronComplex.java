/*
 *  Copyright (C) 2012-2013 The Animo Project
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
package org.animotron.animi.cortex.old;

import java.util.Map;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.animotron.animi.RuntimeParam;
import org.animotron.animi.cortex.CortexZoneSimple;

/**
 * Complex neuron
 * 
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:gazdovsky@gmail.com">Evgeny Gazdovsky</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class NeuronComplex extends Neuron {
	
	@RuntimeParam(name="backProjection")
	public double[] backProjection = new double[] {0, 0, 0, 0, 0, 0, 0};

	@RuntimeParam(name="posActivity")
	public double[] posActivity = new double[] {0, 0, 0, 0, 0, 0, 0};

	public double q = 0;
	
	public CortexZoneSimple zone = null;

	public Map<NeuronComplex, LinkQ> Qs = new FastMap<NeuronComplex, LinkQ>();
	public FastList<LinkQ> a_Qs = new FastList<LinkQ>();

	public NeuronComplex(CortexZoneSimple zone, int x, int y) {
		super(x,y);
		
		this.zone = zone;
	}

	public void init() {
		double count = Qs.values().size();
		for (LinkQ link : Qs.values()) {
			link.q = 1 / count;
		}
	}
}