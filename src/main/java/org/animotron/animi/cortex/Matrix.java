package org.animotron.animi.cortex;

public interface Matrix<T extends Number> {

	public interface Value<K> {
		public K get();
	}

	public void init(Value<T> value);

	public int length();

	public int dimensions();

	public int dimension(int index);

	public T getByIndex(int index);

	public void setByIndex(T value, int index);

	public T get(int... dims);

	public void set(T value, int... dims);

	public void fill(T value);

	public int[] max();

	public Matrix<T> copy();

	public MatrixProxy<T> sub(int... dims);

	public void debug(String comment);
}