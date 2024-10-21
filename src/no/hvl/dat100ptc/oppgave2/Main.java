package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.*;

public class Main {
	
	public static void main(String[] args) {
		GPSData gpsData = new GPSData(2);
		GPSPoint point1 = new GPSPoint(1,2.0,3.0,4);
		GPSPoint point2 = new GPSPoint(2,3.0,4,5.0);
		
		gpsData.insertGPS(point1);
		gpsData.insertGPS(point2);
		
		gpsData.print();
	}
}
