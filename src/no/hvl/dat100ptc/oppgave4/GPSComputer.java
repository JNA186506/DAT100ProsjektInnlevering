package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

import no.hvl.dat100ptc.TODO;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	public double totalDistance() {

		double distance = 0;
		
		for (int i = 0; i < gpspoints.length - 1; i++) {
			distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
		}
		
		return distance;
		
//		double distance = 0;
//		GPSPoint previousPoint = gpspoints[0];
//		for (GPSPoint currentPoint : gpspoints) {
//			distance += GPSUtils.distance(currentPoint, previousPoint);
//			previousPoint = currentPoint;
//		}
	}

	public double totalElevation() {

		double elevation = 0;

		for (int i = 0; i < gpspoints.length - 1; i++) {
			if (gpspoints[i].getElevation() < gpspoints[i + 1].getElevation()) {
				elevation += gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();
			}
		}

		return elevation;
	}

	public int totalTime() {
		return gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();
	}
		

	public double[] speeds() {

		double[] speeds = new double[gpspoints.length - 1];
		
		for (int i = 0; i < gpspoints.length - 1; i++) {
			speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}
		
		return speeds;
	}
	
	public double maxSpeed() {
		
		double maxSpeed = 0;
		
		for (int i = 0; i < speeds().length - 1; i++) {
			if (speeds()[i] > maxSpeed) {
				maxSpeed = speeds()[i];
			}
		}
		return maxSpeed;
	}

	public double averageSpeed() { // TODO det er et eller annet galt med denne
		
		double[] speeds = speeds();
		double average = 0;
		
		for (int i = 0; i < speeds.length; i++) {
			average += speeds[i];
		}
		return average / speeds.length;
		
	}


	// conversion factor m/s to miles per hour (mps)
	public static final double MS = 2.23;

	public double kcal(double weight, int secs, double speed) {

		double met = 0;		
		double speedmph = speed * MS;
		
		switch ((int) Math.floor(speedmph)) {
		case 10: case 11:
			met = 4.0;
			break;
		case 12: case 13:
			met = 8.0;
			break;
		case 14: case 15:
			met = 10.0;
			break;
		case 16: case 17: case 18: case 19:
			met = 12.0;
			break;
		default:
			met = (speedmph >= 20) ? 16.0 : 4.0;
			break;
		}
		
		return met * weight * (secs / 3600.0);
		
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;
		for (int i = 0; i < gpspoints.length - 1; i++) {
			totalkcal += kcal(weight, gpspoints[i + 1].getTime() - gpspoints[i].getTime(), speeds()[i]);
		}
		
		return totalkcal;
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {
		String SEP = "==============================================";
		System.out.println(SEP);
		System.out.println("Total time     : " + GPSUtils.formatTime(totalTime()));
		System.out.println("Total distance : " + GPSUtils.formatDouble((totalDistance() / 1000)) + " km");
		System.out.println("Total elevation: " + GPSUtils.formatDouble(totalElevation()) + " m");
		System.out.println("Max speed      : " + GPSUtils.formatDouble((maxSpeed()) * 3.6) + " km/t");
		System.out.println("Average speed  : " + GPSUtils.formatDouble((averageSpeed()) * 3.6) + " km/t");
		System.out.println("Energy         : " + GPSUtils.formatDouble(totalKcal(WEIGHT)) + " kcal");
		System.out.println(SEP);
	}

}
