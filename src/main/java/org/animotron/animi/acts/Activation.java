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

/**
 * 
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class Activation extends Task {
	
//	@Params
	private ActivationHebbian positive;
	@Params
	private ActivationAntiHebbian negative;
	
	public Activation(CortexZoneComplex cz) {
		super(cz);
		
		positive = new ActivationHebbian(cz);
		negative = new ActivationAntiHebbian(cz);
	}

	@Override
    protected void release() {
		positive.release();
		negative.release();
    }
	
	public void gpuMethod(int x, int y) {
		positive.gpuMethod(x, y);
		negative.gpuMethod(x, y);
		
		MatrixProxy pack = cz.packageCols.sub(x, y);
		WinnerGetsAll._(cz, pack, false);
//		cz.packageCols.copy(pack, x, y);
		
		for (int i = 0; i < cz.package_size; i++) {
			if (pack.get(i) > 0) {
				cz.cols.set(pack.get(i), x, y);
				break;
			}
		}
	}
}
