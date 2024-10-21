package no.hvl.dat100ptc.oppgave6;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;
import no.hvl.dat100ptc.oppgave5.ShowRoute;

public class CycleComputer extends EasyGraphics {

	private static int SPACE = 10;
	private static int MARGIN = 20;

	// FIXME: take into account number of measurements / gps points
	private static int TEXTDISTANCE = 20;
	private static int ROUTEMAPXSIZE = 800;
	private static int ROUTEMAPYSIZE = 400;
	private static int HEIGHTSIZE = 200;
	private static int TEXTWIDTH = 200;

	private GPSComputer gpscomp;
	private GPSPoint[] gpspoints;

	private int N = 0;

	private int x = 0;

	private double distance = 0;
	private int time = 0;
	private double elevation = 0;
	private double totalkcal = 0;
	private double minlon, minlat, maxlon, maxlat;

	private double xstep, ystep;

	public CycleComputer() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");

		gpscomp = new GPSComputer(filename);
		gpspoints = gpscomp.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		N = gpspoints.length; // number of gps points

		minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
		minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));

		xstep = xstep();
		ystep = ystep();

		makeWindow("Cycle Computer", 2 * MARGIN + ROUTEMAPXSIZE, 2 * MARGIN + ROUTEMAPYSIZE + HEIGHTSIZE + SPACE);

		bikeRoute();

	}

	// main method to visualise route, position, and current speed/time
	public void bikeRoute() {

		for (int i = 0; i < N - 1; i++) {

			setColor(255, 255, 255);
			fillRectangle(20, 0, 200, 100);

			double[] speeds = gpscomp.speeds();

			distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);

			time += gpspoints[i + 1].getTime() - gpspoints[i].getTime();

			if (gpspoints[i].getElevation() < gpspoints[i + 1].getElevation()) {
				elevation += gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();
			}

			totalkcal += gpscomp.kcal(80, gpspoints[i + 1].getTime() - gpspoints[i].getTime(), speeds[i]);

			setColor(0, 0, 0);
			drawString("Time spent       : " + GPSUtils.formatTime(time), TEXTDISTANCE, 10);
			drawString("Distance cycled  : " + GPSUtils.formatDouble(distance / 1000) + " km", TEXTDISTANCE, 25);
			drawString("Elevation gained : " + GPSUtils.formatDouble(elevation) + " m", TEXTDISTANCE, 40);
			drawString("Max speed        : " + GPSUtils.formatDouble(gpscomp.maxSpeed() * 3.6) + " km/t", TEXTDISTANCE,
					55);
			drawString("speed            : " + GPSUtils.formatDouble(speeds[i] * 3.6) + " km/t", TEXTDISTANCE, 70);
			drawString("Energy           : " + GPSUtils.formatDouble(totalkcal) + " kcal", TEXTDISTANCE, 85);
			pause(20);

			showCurrent(i);
			showPosition(i);
			showHeight(600, i);

			x += 2;

		}

	}

	public double xstep() {
		return ROUTEMAPXSIZE / (Math.abs(maxlon - minlon));
	}

	public double ystep() {
		return ROUTEMAPYSIZE / (Math.abs(maxlat - minlat));

	}

	// show current speed and time (i'th GPS point)
	public void showCurrent(int i) {

		setColor(0, 255, 0);
		// Finner start x og y
		int x1 = MARGIN + (int) ((gpspoints[i].getLongitude() - minlon) * xstep);
		int y1 = (60 + ROUTEMAPYSIZE) - (int) ((gpspoints[i].getLatitude() - minlat) * ystep);
		// Finner slutt x og y
		int x2 = MARGIN + (int) ((gpspoints[i + 1].getLongitude() - minlon) * xstep);
		int y2 = (60 + ROUTEMAPYSIZE) - (int) ((gpspoints[i + 1].getLatitude() - minlat) * ystep);
		drawLine(x1, y1, x2, y2);

	}

	// show current height (i'th GPS point)
	public void showHeight(int ybase, int i) {

		setColor(0, 0, 255);
		int height = (int) gpspoints[i].getElevation();

		drawLine(x, ybase, x, ybase - height);

	}

	// show current position (i'th GPS point)
	public void showPosition(int i) {

		int length = gpspoints.length;
		int[] x = new int[length];
		int[] y = new int[length];

		x[i] = MARGIN + (int) ((gpspoints[i].getLongitude() - minlon) * xstep);
		y[i] = (60 + ROUTEMAPYSIZE) - (int) ((gpspoints[i].getLatitude() - minlat) * ystep);
		if (gpspoints[i + 1].getElevation() > gpspoints[i].getElevation()) {
			setColor(255, 0, 0);
			fillCircle(x[i], y[i], 5);
		} else if (gpspoints[i + 1].getElevation() < gpspoints[i].getElevation()) {
			setColor(0, 255, 0);
			fillCircle(x[i], y[i], 5);
		} else {
			setColor(0, 0, 255);
			fillCircle(x[i], y[i], 5);
		}

	}
}
