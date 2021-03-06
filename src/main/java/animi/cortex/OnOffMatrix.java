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
package animi.cortex;

/**
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 *
 */
public class OnOffMatrix {
	
	//Параметры преобразования сетчатки в сигналы полей с он-центом и офф-центром

	//Радиус сенсорного поля
    public static int radius = 10;//диаметр 16 (20)
    //Радиус центра сенсорного поля
    public static int centeRadius = 3;//диаметр 4 (5)

    //Кол-во элементов в центре и переферии сенсорного поля
    int numCenter = 0;
    int numsPeriphery = 0;
    
    int regionSize = 0;
    int[] matrix;

    public OnOffMatrix() {
    	initialize();
    };
    
    public void initialize() {
//    	radius = 4;
//    			
//    	regionSize = 8;
//    	
//    	numCenter = 9;
//    	numsPeriphery = 20;
//    	
//        matrix = new int[][] {
//        {0, 0, 0, 0, 0, 0, 0, 0},
//        {0, 0, 0, 1, 0, 0, 0, 0},
//        {0, 1, 1, 1, 1, 1, 0, 0},
//        {0, 1, 2, 2, 2, 1, 0, 0},
//        {1, 1, 2, 2, 2, 1, 1, 0},
//        {0, 1, 2, 2, 2, 1, 0, 0},
//        {0, 1, 1, 1, 1, 1, 0, 0},
//        {0, 0, 0, 1, 0, 0, 0, 0}
//        };

    	regionSize = 2 * radius + 1;
        
        matrix = new int[regionSize * regionSize];

        int radius2 = radius * radius;
        int centeRadius2 = centeRadius * centeRadius;

        int R2;
        int dx, dy;

        //Разметка квадратного массива двумя кругами (центром и переферией сенсорного поля)
        for (int x = 0; x < regionSize; x++) {
        	for (int y = 0; y < regionSize; y++) {
        		
        		int pos = (y * regionSize) + x;

                dx = radius - x;
                dy = radius - y;
                R2 = dx * dx + dy * dy;

                if (R2 > radius2)
                    matrix[pos] = 0;
                else {
                    if (R2 > centeRadius2) {
                        matrix[pos] = 1;
                        numsPeriphery++;
                    } else {
                        matrix[pos] = 2;
                        numCenter++;
                    }
                }
//                System.out.print(" "+matrix[pos]);
        	}
//        	System.out.println();
        }
	}
    
    public int regionSize() {
    	return regionSize;
    }

	public int getType(int x, int y) {
		return matrix[(y * regionSize) + x];
	}

	public int numInCenter() {
		return numCenter;
	}

	public int numInPeref() {
		return numsPeriphery;
	}

	public int radius() {
		return radius;
	}
}
