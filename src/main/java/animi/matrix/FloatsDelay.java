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

import java.util.Arrays;

/**
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 *
 */
public class FloatsDelay extends FloatsImpl {
	
	public interface Attenuation {
		float next(int step, float value);
	}
	
	public final static Attenuation oneStepAttenuation = new Attenuation() {
		@Override
		public float next(int step, float value) {
			return step <= 1 ? value : 0f;
		}
	};
	
	public final static Attenuation noAttenuation = new Attenuation() {
		@Override
		public float next(int step, float value) {
			return value;
		}
	};

	Attenuation attenuation;
	
	int[] stepsFromLastSet;
	
	public FloatsDelay(Attenuation attenuation, int ... dims) {
		super(dims);
		
		this.attenuation = attenuation;

		stepsFromLastSet = new int[data.length];
	}
	
	public FloatsDelay(Attenuation attenuation, FloatsImpl source) {
		this(attenuation, source.dimensions.clone());
	}
	
	public FloatsDelay(FloatsDelay source) {
		super(source);
		
		attenuation = source.attenuation;
		
		stepsFromLastSet = new int[source.stepsFromLastSet.length];
		System.arraycopy(source.stepsFromLastSet, 0, stepsFromLastSet, 0, source.stepsFromLastSet.length);
	}

	public void init(Value value) {
		super.init(value);

		//zero steps
		for (int i = 0; i < stepsFromLastSet.length; i++) {
			stepsFromLastSet[i] = 0;
		}
	}

	public void set(Float value, int ... dims) {
		final int index = index(dims);
		if (value > super.getByIndex(index)) {
			super.setByIndex(value, index);
		}
		stepsFromLastSet[index] = 0;
	}
	
	public void setByIndex(Float value, int index) {
		if (value > super.getByIndex(index)) {
			super.setByIndex(value, index);
		}
		stepsFromLastSet[index] = 0;
	}

	public void fill(Float value) {
		super.fill(value);
		Arrays.fill(stepsFromLastSet, 0);
		isSet.clear(0, isSet.size() - 1);
	}
	
	@Override
	public float getByIndex(final int index) {
		return attenuation.next(stepsFromLastSet[index], data[index]);
	}

	@Override
	public float get(final int ... dims) {
		return getByIndex(index(dims));
	}

	public int[] max() {
		return super.max();
	}
	
	public FloatsDelay copy() {
		return new FloatsDelay(this);
	}

	public FloatsProxy sub(int ... dims) {
		return new FloatsProxy(this, dims);
	}

//	public void debug(String comment) {
//		System.out.println(comment);
//		
//		debug(new Floats(data), false);
//	}
	
	public void step(final Floats matrix) {
		for (int index = 0; index < length(); index++) {
			if (matrix.isSet(index)) {
				final float v = matrix.getByIndex(index);
				if (v != 0f || Float.isNaN(v)) {//WORKAROUND: > 0f
					setByIndex(matrix.getByIndex(index), index);
				}
			}
			stepsFromLastSet[index]++;
		}
		isSet.clear(0, isSet.size() - 1);
	}

	@Override
	public void step() {
		throw new IllegalAccessError();
	}
}
