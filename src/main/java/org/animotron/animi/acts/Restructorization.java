/*
 *  Copyright (C) 2012 The Animo Project
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

import static org.jocl.CL.*;
import static org.animotron.animi.cortex.MultiCortex.*;

import org.animotron.animi.RuntimeParam;
import org.animotron.animi.cortex.*;
import org.jocl.Pointer;
import org.jocl.Sizeof;
import org.jocl.cl_command_queue;
import org.jocl.cl_event;
import org.jocl.cl_kernel;

/**
 * Активация простых нейронов при узнавании запомненной картины
 * 
 * @author <a href="mailto:aldrd@yahoo.com">Alexey Redozubov</a>
 * @author <a href="mailto:gazdovsky@gmail.com">Evgeny Gazdovsky</a>
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 */
public class Restructorization extends Task {
	
	@RuntimeParam(name = "count")
	public int count = 10000;
	
	public Restructorization(CortexZoneComplex cz) {
		super(cz);
	}

    /**
     * Set up the OpenCL arguments for this task for the given kernel
     * 
     * @param kernel The OpenCL kernel for which the arguments will be set
     */
    protected void setupArguments(cl_kernel kernel) {
        clSetKernelArg(kernel,  0, Sizeof.cl_mem, Pointer.to(cz.cl_cols));
        clSetKernelArg(kernel,  1, Sizeof.cl_int, Pointer.to(new int[] {cz.width}));

    	Mapping m = cz.in_zones[0];

    	clSetKernelArg(kernel,  2, Sizeof.cl_mem, Pointer.to(m.cl_links));
        clSetKernelArg(kernel,  3, Sizeof.cl_mem, Pointer.to(m.cl_senapseOfLinks));
        clSetKernelArg(kernel,  4, Sizeof.cl_int, Pointer.to(new int[] {m.ns_links}));
        clSetKernelArg(kernel,  5, Sizeof.cl_float, Pointer.to(new float[] {sz.ny}));
        clSetKernelArg(kernel,  6, Sizeof.cl_mem, Pointer.to(sz.cl_colsNy));
        
        clSetKernelArg(kernel,  7, Sizeof.cl_mem, Pointer.to(m.frZone.cl_cols));
        clSetKernelArg(kernel,  8, Sizeof.cl_int, Pointer.to(new int[] {m.frZone.width}));
    }

	@Override
	protected void processColors(float[] array) {
//		if (MODE == RUN) {
//			cycles++;
//			if (cycles > 100) {
//				cz.refreshImage();
//				cycles = 0;
//			}
//		}
//
//		if (MODE == STEP) {
//			cz.refreshImage();
//		}
    }

	/**
     * Will execute this task with the given kernel on the given 
     * command queue
     * 
     * @param kernel The kernel
     * @param commandQueue The command queue
     */
    public void execute(cl_kernel kernel, cl_command_queue commandQueue) {
        setupArguments(kernel);
        
        cl_event events[] = new cl_event[] { new cl_event() };
        
        long globalWorkSize[] = new long[2];
        globalWorkSize[0] = sz.width;
        globalWorkSize[1] = sz.height;

        clEnqueueNDRangeKernel(
            commandQueue, 
            kernel, 2, null, 
            globalWorkSize, null, 0, null, events[0]);
        
        clWaitForEvents(1, events);
        
//        Utils.printBenchmarkInfo("Calc", events[0]);

        // Read the contents of the cols memory object
        Mapping m = cz.in_zones[0];

//        float result[] = new float[m.linksWeight.length];
        Pointer target = Pointer.to(m.linksWeight);
        clEnqueueReadBuffer(
            commandQueue, m.cl_links, 
            CL_TRUE, 0, m.linksWeight.length * Sizeof.cl_float, 
            target, 0, null, events[0]);

        clWaitForEvents(1, events);

        target = Pointer.to(sz.colsNy);
        clEnqueueReadBuffer(
            commandQueue, sz.cl_colsNy, 
            CL_TRUE, 0, sz.colsNy.length * Sizeof.cl_float, 
            target, 0, null, events[0]);

        clWaitForEvents(1, events);
        
//        Utils.printBenchmarkInfo("Reading", events[0]);

//        m.linksWeight = result;

//        System.out.println(Arrays.toString(sz.colsNy));
        processColors(sz.cols);
        
        release();
        
        clReleaseEvent(events[0]);
    }
	
    protected void release() {
//		clReleaseMemObject(_cols);
    }

//    @Override
//    public void process(final CortexZoneSimple layer, final int x, final int y) {
//    	
//		final NeuronComplex cn = layer.col[x][y];
//		
//		final double activity = cn.activity[0];
//		if (activity == 0)
//			return;
//		
//		double factor = ny / Math.pow(2, layer.count / count);
//		double sumQ2 = 0;
//
//		for (LinkQ link : cn.Qs.values()) {
//			
//			link.q += activity * link.synapse.activity[link.delay] * factor;
//
//			sumQ2 += link.q * link.q;
//		}
//			
//		double norm = Math.sqrt(sumQ2);
//		for (LinkQ link : cn.Qs.values()) {
//			link.q = link.q / norm;
//		}
		
		//inhibitory restructorization & normlization
//		sumQ2 = 0;
//		for (Link link : cn.s_inhibitoryLinks) {
//			link.w += cn.activity * link.synapse.activity * inhibitoryNy;
//
//			sumQ2 += link.w * link.w;
//		}
//
//		norm = Math.sqrt(sumQ2);
//		for (Link link : cn.s_inhibitoryLinks) {
//			link.w = link.w / norm;
//		}
//    }
}