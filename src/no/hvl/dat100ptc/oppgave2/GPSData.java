package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.*;


public class GPSData {
	
	private GPSPoint[] gpspoints;
	protected int antall = 0;
	
	public GPSData(int antall) {
		this.gpspoints = new GPSPoint[antall];
		this.antall = 0;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	protected boolean insertGPS(GPSPoint gpspoint) {
		if (antall < gpspoints.length) {
			gpspoints[antall] = gpspoint;
			antall++;
			return true;
		}
		return false;
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {
		GPSPoint gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);
		return insertGPS(gpspoint);
	}

	public void print() {
		System.out.println("====== GPS Data - START ======");
		for (int i = 0; i < gpspoints.length; i++) {
			System.out.print(gpspoints[i]);
		}
		System.out.println("====== GPS Data - SLUTT ======");
	}
}
