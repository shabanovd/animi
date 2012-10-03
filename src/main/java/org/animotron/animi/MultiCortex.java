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
package org.animotron.animi;


/**
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:gazdovsky@gmail.com">Evgeny Gazdovsky</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class MultiCortex {

	public static final int VISUAL_FIELD_WIDTH = 96 * 2;
	public static final int VISUAL_FIELD_HEIGHT = 72 * 2;

    public boolean active = false;
    
    public Retina retina;

    public CortexZoneSimple z_video, z_good, z_bad;
    public CortexZoneComplex z_viscor, z_asscor;

    public CortexZoneSimple [] zones;

    public MultiCortex() {

        System.out.println("z_video");
        z_video = new CortexZoneSimple("Input visual layer", VISUAL_FIELD_WIDTH, VISUAL_FIELD_HEIGHT);

        System.out.println("z_viscor");
        z_viscor = new CortexZoneComplex("Prime visual cortex", VISUAL_FIELD_WIDTH, VISUAL_FIELD_HEIGHT, 2,
                9, 0, 0.3, 0.6, 0.6, 10, 2,
                new Mapping[]{
                        new Mapping(z_video, 15, 0.02)
                }
        );

//        System.out.println("z_good");
//        z_good = new SCortexZone("Zone good", 20, 20);
//        System.out.println("z_bad");
//        z_bad = new SCortexZone("Zone bad", 20, 20);
//
//        System.out.println("z_asscor");
//        z_asscor = new CCortexZone("Associative cortex", 48, 48, 10,
//                9, 0, 0.3, 0.6, 0.6, 10, 2,
//                new Mapping[]{
//                        new Mapping(z_viscor, 20, 0.1),
//                        new Mapping(z_good, 10, 0.01),
//                        new Mapping(z_bad, 10, 0.01)
//                }
//        );
//
//        zones = new SCortexZone[]{z_video, z_viscor, z_asscor, z_good, z_bad};
        zones = new CortexZoneSimple[]{z_video, z_viscor};
        
        retina = new Retina(Retina.WIDTH, Retina.HEIGHT);
        retina.setNextLayer(z_video);

        System.out.println("done.");

    }

    //Такт 1. Активация колонок (узнавание)
    public void cycle1() {
        //Последовательность активации зон коры определяется их номером
        z_viscor.cycle1();
        //z_asscor.cycle1();
    }

    //Такт 2. Запоминание  и переоценка параметров стабильности нейрона
    public void cycle2() {
        //Последовательность активации зон коры определяется их номером
        z_viscor.cycle2();
        //z_asscor.cycle2();
    }
}
