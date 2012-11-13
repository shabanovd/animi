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

import java.io.File;
import java.io.IOException;
import java.io.Writer;

import org.animotron.animi.Params;
import org.animotron.animi.gui.Application;


/**
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:gazdovsky@gmail.com">Evgeny Gazdovsky</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class MultiCortex {

    public boolean active = false;
    
    public long count = 0;
    
    public Retina retina;

    public CortexZoneSimple z_video;
    public CortexZoneComplex z_viscor;
    public CortexZoneComplex z_asscor;
    public CortexZoneComplex z_3rd;
//  @Params
//  public CortexZoneSimple z_good;
//  @Params
//  public CortexZoneSimple z_bad;

    @Params
    public CortexZoneSimple [] zones;

    public MultiCortex() {

        System.out.println("z_video");
        z_video = new CortexZoneSimple("Input visual", this);

        System.out.println("z_viscor");
        z_viscor = new CortexZoneComplex("Prime visual", this, 92, 74,
            new Mapping[]{
                new Mapping(z_video, 300, 5) //20x20 (300)
            }
        );

//        System.out.println("z_good");
//        z_good = new SCortexZone("Zone good", 20, 20);
//        System.out.println("z_bad");
//        z_bad = new SCortexZone("Zone bad", 20, 20);
//
        System.out.println("z_asscor");
        z_asscor = new CortexZoneComplex("Associative", this, 32, 32,
                new Mapping[]{
                        new Mapping(z_viscor, 300, 5)
//                        ,
//                        new Mapping(z_good, 10, 0.01),
//                        new Mapping(z_bad, 10, 0.01)
                }
        );

        System.out.println("z_3rd");
        z_3rd = new CortexZoneComplex("3rd cortex", this, 32, 32,
                new Mapping[]{
                        new Mapping(z_asscor, 300, 5)
//                        ,
//                        new Mapping(z_good, 10, 0.01),
//                        new Mapping(z_bad, 10, 0.01)
                }
        );
//        z_3rd.inhibitory_links = 100;

        zones = new CortexZoneSimple[]{z_video, z_viscor, z_asscor, z_3rd};
        
        retina = new Retina(Retina.WIDTH, Retina.HEIGHT);
        retina.setNextLayer(z_video);

        System.out.println("done.");
    }


	public void init() {
		for (CortexZoneSimple zone : zones) {
			zone.init();
		}
	}

    public void process() {
		for (CortexZoneSimple zone : zones) {
			if (zone instanceof CortexZoneComplex) {
				((CortexZoneComplex) zone).process();
			}
		}
    	count++;
    	
    	Application.count.setText(String.valueOf(count));
    }

    public void prepareForSerialization() {
//		z_video.prepareForSerialization();
		z_viscor.prepareForSerialization();
		z_asscor.prepareForSerialization();
	}
    
    public void save(Writer out) throws IOException {
    	out.write("<cortex>");
		for (CortexZoneSimple zone : zones) {
			if (zone instanceof CortexZoneComplex) {
				((CortexZoneComplex)zone).save(out);
			}
		}
    	out.write("</cortex>");
    }


	public static MultiCortex load(File file) {
		// TODO Auto-generated method stub
		return null;
	}
}
