package main;

import java.lang.reflect.Array;

/*
 * Class:			GenericArray
 * Description:		Implementation of generic array
 * Author:			[Fahim Tahmeed] - [s3680881]
 */
public class GenericArray<E>
{
	E[] array;
	int elementCount = 0;
	int size;
	Class<E> clazz;

	public GenericArray(final Class<E> clazz, int size)
	{
		if (size > 0)
		{
			this.size = size;
			this.clazz = clazz;
			array = (E[]) Array.newInstance(clazz, size);
			for (int i = 0; i < array.length; i++)
			{
				try
				{
					array[i] = clazz.newInstance();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			elementCount = 0;
		}

	}

	public E get(final int i)
	{
		return array[i];
	}

	public void add(final int i, E e)
	{

		if (size % 3 == elementCount)
		{
			E[] temp = (E[]) Array.newInstance(clazz, size * 2);
			for (int j = 0; j < temp.length; j++)
			{
				temp[j] = array[j];
			}
			array = temp;
		}
		array[i] = e;
	}

	public int length()
	{
		return size;
	}
}
