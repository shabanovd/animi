package org.animotron.animi.gui;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.ds.openimaj.OpenImajDriver;

/**
 * Simply implementation of JPanel allowing users to render pictures taken with
 * webcam.
 * 
 * @author Bartosz Firyn (SarXos)
 */
public class WebcamPanel extends JPanel implements WebcamListener {

	private static final long serialVersionUID = 5792962512394656227L;

	private int frequency = 65; // Hz

	private class Repainter extends Thread {

		public Repainter() {
			setDaemon(true);
		}

		@Override
		public void run() {
			super.run();

			while (webcam.isOpen()) {

				image = webcam.getImage();

				try {
					if (paused) {
						synchronized (this) {
							this.wait();
						}
					}
					Thread.sleep(1000 / frequency);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				WebcamPanel.this.repaint();
			}
		}
	}

	private Webcam webcam = null;
	private BufferedImage image = null;
	private Repainter repainter = null;

	public WebcamPanel() {
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				Webcam.setDriver(new OpenImajDriver());
				webcam = Webcam.getDefault();
				if (webcam == null) {
					System.out.println("No webcams found...");
					System.exit(1);
				}
                webcam.setViewSize(new Dimension(640, 480));
				webcam.addWebcamListener(WebcamPanel.this);

				if (!webcam.isOpen()) {
					webcam.open();
				}
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (image == null) {
			return;
		}

		g.drawImage(image, 0, 0, null);
	}

	@Override
	public void webcamOpen(WebcamEvent we) {
		if (repainter == null) {
			repainter = new Repainter();
		}
		repainter.start();
		setPreferredSize(webcam.getViewSize());
	}

	@Override
	public void webcamClosed(WebcamEvent we) {
		try {
			repainter.join();
			repainter = null;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private volatile boolean paused = false;

	/**
	 * Pause rendering.
	 */
	public void pause() {
		if (paused) {
			return;
		}
		paused = true;
		System.out.println("paused");
	}

	/**
	 * Resume rendering.
	 */
	public void resume() {
		if (!paused) {
			return;
		}
		synchronized (repainter) {
			repainter.notifyAll();
		}
		paused = false;
		System.out.println("resumed");
	}
	
	public void stop() {
		webcam.close();
	}

	/**
	 * @return Rendering frequency (in Hz or FPS).
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * Set rendering frequency (in Hz or FPS). Min is 1 and max is 100.
	 * 
	 * @param frequency
	 */
	public void setFrequency(int frequency) {
		if (frequency > 100) {
			frequency = 100;
		}
		if (frequency < 1) {
			frequency = 1;
		}
		this.frequency = frequency;
	}

}