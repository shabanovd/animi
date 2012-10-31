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

/**
 * Simple neuron
 * 
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:gazdovsky@gmail.com">Evgeny Gazdovsky</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class NeuronSimple extends Neuron {
	
	int z;
	
	public boolean occupy = false;
	
	public NeuronSimple(int x, int y, int z) {
		super(x,y);
		this.z = z;
	}

	public boolean isOccupy() {
		return occupy;
	}
}