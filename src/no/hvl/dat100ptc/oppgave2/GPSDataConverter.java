package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

//	private static int TIME_STARTINDEX = 11; Vet ikke hva denne er

	public static int toSeconds(String timestr) {
		
		int secs = 0;
		int hr, min, sec;
		sec = Integer.parseInt(timestr.substring(17,19));
		min = Integer.parseInt(timestr.substring(14,16));
		hr = Integer.parseInt(timestr.substring(11,13));
		
		secs += sec + (hr*3600) + (min*60);
		
		return secs;
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {
		int time = toSeconds(timeStr);
		double lat = Double.parseDouble(latitudeStr);
		double lon = Double.parseDouble(longitudeStr);
		double elev = Double.parseDouble(elevationStr);
		GPSPoint gpspoint = new GPSPoint(time, lat, lon, elev);
		
		return gpspoint;
		
	}
	
}
