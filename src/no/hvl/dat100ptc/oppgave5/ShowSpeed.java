package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowSpeed extends EasyGraphics {
			
	private static int MARGIN = 50;
	private static int BARHEIGHT = 100; 

	private GPSComputer gpscomputer;
	
	public ShowSpeed() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Speed profile", 
				2 * MARGIN + 
				2 * gpscomputer.speeds().length, 2 * MARGIN + BARHEIGHT);
		
		showSpeedProfile(MARGIN + BARHEIGHT);
	}
	
	public void showSpeedProfile(int ybase) {
		
		int x = MARGIN,y;
		
		int average = (int) gpscomputer.averageSpeed();
		
		double[] speed = gpscomputer.speeds(); 
		
		setColor(0,255,0);
		drawLine(MARGIN - 1, ybase - (average * 4), MARGIN + (speed.length * 2) - 1, ybase - (average * 4));
		drawLine(MARGIN, ybase - (average * 4), MARGIN + (speed.length * 2), ybase - (average * 4));
		drawLine(MARGIN + 1, ybase - (average * 4), MARGIN + (speed.length * 2) + 1, ybase - (average * 4));
		
		setColor(0,0,255);
		
		for (int i = 0; i < speed.length; i++) {
			int height = (int) speed[i];
		
			drawLine(x, ybase, x, ybase - (height * 4));
			x += 2;
		}
	}
}
