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

import org.animotron.animi.Params;
import org.animotron.animi.cortex.*;
import org.jocl.cl_command_queue;
import org.jocl.cl_kernel;

/**
 * 
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class Activation extends Task {
	
//	@Params
	private HebbianActivation positive;
	@Params
	private AntiHebbianActivation negative;
	
	public Activation(CortexZoneComplex cz) {
		super(cz);
		
		positive = new HebbianActivation(cz);
		negative = new AntiHebbianActivation(cz);
	}

	@Override
    protected void setupArguments(cl_kernel kernel) {
	}
    
	@Override
    protected void enqueueReads(cl_command_queue commandQueue) {
    }

	@Override
    protected void release() {
		positive.release();
		negative.release();
    }
	
	public void gpuMethod(int x, int y) {
		positive.gpuMethod(x, y);
		negative.gpuMethod(x, y);
	}
}