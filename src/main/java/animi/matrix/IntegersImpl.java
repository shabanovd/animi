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
package animi.matrix;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.BitSet;

/**
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 *
 */
public class IntegersImpl implements Integers {
	
	int[] dimensions;
	
	int[] data;
	
	BitSet isSet;
	
	public IntegersImpl(int ... dims) {
		dimensions = new int[dims.length];
		System.arraycopy(dims, 0, dimensions, 0, dims.length);
		
		int length = 1;
		for (int i = 0; i < dims.length; i++) {
			length *= dims[i];
		}
		
		data = new int[length];
	}
	
	public IntegersImpl(IntegersImpl source) {
		dimensions = new int[source.dimensions.length];
		System.arraycopy(source.dimensions, 0, dimensions, 0, source.dimensions.length);
		
		data = new int[source.data.length];
		System.arraycopy(source.data, 0, data, 0, source.data.length);
	}

	@Override
	public void init(Value value) {
		for (int i = 0; i < data.length; i++) {
			data[i] = value.get();
		}
	}

	@Override
	public void step() {
		throw new IllegalAccessError();
	}
	
	protected int index(int ... dims) {
		if (dims.length != dimensions.length) {
			throw new IndexOutOfBoundsException("Matrix have "+dimensions.length+" dimensions, but get "+dims.length+".");
		}
		
		for (int i = 0; i < dims.length; i++) {
			if (dims[i] >= dimensions[i]) {
				throw new IndexOutOfBoundsException("Matrix's "+(i+1)+" dimension have "+dimensions[i]+" elements, but requested "+dims[i]+" element.");
			}
		}
		
		int index = 0;
		for (int i = dims.length - 1; i > 0 ; i--) {
			index = (index + dims[i]) * dimensions[i-1];
		}
		return index + dims[0];
	}
	
	/* (non-Javadoc)
	 * @see org.animotron.animi.cortex.Matrix#length()
	 */
	@Override
	public int length() {
		return data.length;
	}

	@Override
	public int dimensions() {
		return dimensions.length;
	}

	@Override
	public int dimension(int index) {
		return dimensions[index];
	}

	@Override
	public int getByIndex(int index) {
		return data[index];
	}

	@Override
	public void setByIndex(int value, int index) {
		data[index] = value;
	}

	@Override
	public int get(int ... dims) {
		return data[index(dims)];
	}

	public boolean isSet(final int ... dims) {
		return isSet(index(dims));
	}
	
	public boolean isSet(final int index) {
		return isSet.get(index);
	}

	@Override
	public void set(int value, int ... dims) {
		data[index(dims)] = value;
	}
	
	/* (non-Javadoc)
	 * @see org.animotron.animi.cortex.Matrix#fill(float)
	 */
	@Override
	public void fill(int value) {
		Arrays.fill(data, value);
	}
	
	/* (non-Javadoc)
	 * @see org.animotron.animi.cortex.Matrix#max()
	 */
	@Override
	public int[] max() {
    	int maxPos = -1;
    	float max = 0;
    	for (int pos = 0; pos < data.length; pos++) {
    		if (data[pos] > max) {
    			maxPos = pos;
    			max = data[pos];
    		}
    	}
    	
    	if (maxPos == -1) {
    		return null;
//    		throw new IllegalArgumentException("Maximum value can't be found.");
    	}
    	
    	int[] dims = new int[dimensions.length];
    	
//		System.out.print("Max "+maxPos+" ");

    	int prev = 0, factor = 1;
    	for (int i = dimensions.length - 1; i > 0; i--) {
    		factor = 1;
    		for (int z = 0; z < i; z++) {
    			factor *= dimensions[z];
    		}

			dims[i] = prev = (int)Math.floor(maxPos / factor);
			maxPos -= (prev * factor);
    	}
    	dims[0] = maxPos;
    	//maxPos must be zero
    	
//    	System.out.println(Arrays.toString(dims));;
    	return dims;
	}
	
	/* (non-Javadoc)
	 * @see org.animotron.animi.cortex.Matrix#maximum()
	 */
	@Override
	public int maximum() {
    	int max = 0;
    	for (int pos = 0; pos < data.length; pos++) {
    		if (data[pos] > max) {
    			max = data[pos];
    		}
    	}
    	return max;
	}

	/* (non-Javadoc)
	 * @see org.animotron.animi.cortex.Matrix#copy()
	 */
	@Override
	public IntegersImpl copy() {
		return new IntegersImpl(this);
//		System.arraycopy(source.data, 0, data, 0, data.length);
	}

	@Override
	public IntegersProxy sub(int ... dims) {
		return new IntegersProxy(this, dims);
	}

	/* (non-Javadoc)
	 * @see org.animotron.animi.cortex.Matrix#debug(java.lang.String)
	 */
	@Override
	public void debug(String comment) {
		System.out.println(comment);
		
		int[] pos = new int[dimensions.length];
		Arrays.fill(pos, 0);
		
		int value;
		
		DecimalFormat df = new DecimalFormat("0.00000");
		
		System.out.print(Arrays.toString(pos));
		System.out.print(" ");
		System.out.print(df.format(get(pos)));
		System.out.print(" ");
		
		boolean print = false;

    	for (int i = 1; i < data.length; i++) {
    		for (int dim = dimensions.length - 1; dim >= 0; dim--) {
        		value = ++pos[dim];
        		
        		if (value >= dimensions[dim]) {
        			pos[dim] = 0;
        			
        			if (dim == dimensions.length - 1) {
        				print = true;
        			}
        			continue;
        		}
        		break;
    		}
    		
    		if (print) {
    			print = false;
				System.out.println();
				System.out.print(Arrays.toString(pos));
				System.out.print(" ");
    		}

			System.out.print(df.format(get(pos)));
			System.out.print(" ");
		}
    	System.out.println();
	}
}
