package no.hvl.dat100ptc.oppgave3;


import java.util.Locale;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {
		double min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;
		
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		
		double[] latitudes = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			latitudes[i] = gpspoints[i].getLatitude();
		}
		
		return latitudes;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {
		
		double[] longitudes = new double[gpspoints.length];
		
		for (int i = 0; i < gpspoints.length; i++) {
			longitudes[i] = gpspoints[i].getLongitude();
		}
		
		return longitudes;
	}

	private static final int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;

		latitude1 = Math.toRadians(gpspoint1.getLatitude());
		latitude2 = Math.toRadians(gpspoint2.getLatitude());
		
		longitude1 = Math.toRadians(gpspoint1.getLongitude());
		longitude2 = Math.toRadians(gpspoint2.getLongitude());

		double a = compute_a(latitude1, latitude2, (latitude2 - latitude1), (longitude2 - longitude1));
		return d = R * compute_c(a);
	}

	private static double compute_a(double phi1, double phi2, double deltaphi, double deltalambda) {
		return Math.pow((Math.sin(deltaphi)/2),2) + Math.cos(phi1) * Math.cos(phi2) * Math.pow((Math.sin(deltalambda)/2),2);
	}

	private static double compute_c(double a) {
		return 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	}

	
	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		return speed = distance(gpspoint1, gpspoint2) / secs;
		

	}

	public static String formatTime(int secs) {

		String timestr;
		
		return timestr = "  " + String.format("%02d:%02d:%02d", secs / 3600, (secs / 60) % 60, secs % 60);
		
	}
	
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;
		return str = String.format(Locale.US,"%" + TEXTWIDTH + ".2f", d);

		
		
	}
}
