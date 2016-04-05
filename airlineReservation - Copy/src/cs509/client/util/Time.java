package cs509.client.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cs509.client.Search.SearchAirports;
import cs509.client.airport.Airport;
import cs509.client.flight.Ticket;

public class Time {
	public Date timesString2timeDate(String timestring) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy MMM dd HH:mm");
		Date date = formatter.parse(timestring);
		return date;
	}
	
	public String timeDate2RighttimeString(Date timedate){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(timedate);
		String my_new_str = format.replaceAll("-", "_");
		return my_new_str;
	}
	
	public long timeDifference(String time1,String time2) throws ParseException{
		Date date1 = timesString2timeDate(time1);
		Date date2 = timesString2timeDate(time2);
		
		return (date2.getTime()-date1.getTime())/ (60 * 60 * 1000);
	}
	
	
	public Date addhours(Date oldtime,int hour){
		Calendar cal = Calendar.getInstance();
		cal.setTime(oldtime);
		// mutate the value
		cal.add(Calendar.HOUR, hour);
		// convert back to Date
		Date newtime = cal.getTime();
		return newtime;
		
	}
	
	public double getlongtitude(String airportcode){
		
		SearchAirports apsc= new SearchAirports();
		apsc.searchAirports("Team03");
		ArrayList<Airport> airportslist =  apsc.getAirports();
		double error = -200;
		
		for(int i=0; i <airportslist.size();i++){
			if(airportslist.get(i).code().equals(airportcode)){
				return airportslist.get(i).longitude();
			}
			
		}
		return error;
		
	}
	
	public int TzoneDff(double longtitude){
		if(longtitude>=0){
			double base = Math.floor(longtitude/15);
			double remain = longtitude%15;
			if (remain>7.5){
				return (int) (base+1);
			}
			else{
				return (int) base;
			}
			
		}
		else{
			double newlt=Math.abs(longtitude);
			double base = Math.floor(newlt/15);
			double remain = newlt%15;
			if (remain>7.5){
				return (int) -(base+1);
			}
			else{
				return (int) -base;
			}
		}
	}
	
	public Date convertGMT2localTDep(Ticket t) throws ParseException{
		Date dep = t.gettotaldepTime();
		String depcode = t.totalDepcode();
		
		
		double deplt = this.getlongtitude(depcode);
		int depTZ = this.TzoneDff(deplt);
		Date depLt = this.addhours(dep, depTZ);
		return depLt;
		
	}
	
	public Date convertGMT2localTArr(Ticket t) throws ParseException{
		Date arr = t.gettotalarriTime();
		String arrcode = t.totalArrcode();
		
		double arrlt = this.getlongtitude(arrcode);
		int arrTZ = this.TzoneDff(arrlt);
		Date arrLt = this.addhours(arr, arrTZ);
		return arrLt;
		
	}
	
	
	public static void main(String[] arg) throws ParseException{
		Time time = new Time();
		String dateInString1 = "2016 May 10 19:57 GMT";	
		String dateInString2 = "2016 May 12 12:57 GMT";	
		Date haha = time.timesString2timeDate(dateInString1);
		System.out.println(haha);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(haha);
		// mutate the value
		cal.add(Calendar.HOUR, -1);
		// convert back to Date
		Date newhaha = cal.getTime();
		System.out.println(newhaha);
		
		Date newtime= time.addhours(haha, 2);
		System.out.println(newtime);
		
		long diff = time.timeDifference(dateInString1, dateInString2);
		String test = time.timeDate2RighttimeString(time.timesString2timeDate(dateInString2));
		System.out.println(diff);
		System.out.println(test);
		
		double longtitude = time.getlongtitude("LGA");
		System.out.println(longtitude);
		
		System.out.println(time.TzoneDff(-88.2));
	}
}
