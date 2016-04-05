package cs509.client.flight;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import cs509.client.util.Time;

public class Ticket {
	ArrayList<Flight> flightslist;
	double totalFirstPrice;
	double totalCoachPrice;
	double totalFlightTime;

	public Ticket(){
		flightslist = new ArrayList<Flight>();
		totalFirstPrice=0;
		totalCoachPrice=0;
		totalFlightTime=0;
	}
	
	public void addFlight(Flight newflight){
		flightslist.add(newflight);
		totalFirstPrice= totalFirstPrice + Double.parseDouble(newflight.getmPriceFirstclass().substring(1));
		totalCoachPrice= totalCoachPrice + Double.parseDouble(newflight.getmPriceCoach().substring(1));
		totalFlightTime= totalFlightTime + Double.parseDouble(newflight.getmFlightTime());
	}
	
	public static Comparator<Ticket> CompareTicketFirstPrice = new Comparator<Ticket>() {

		public int compare(Ticket t1, Ticket t2) {

		   double totalFirstPrice1 = t1.getFirstPrice();
		   double totalFirstPrice2 = t2.getFirstPrice();
		   double diff = totalFirstPrice1-totalFirstPrice2;
		   
           /*For ascending order*/
		   
		   if ( diff > 0 ){
			   return 1;
			   }
		   else if (diff < 0){
			   return -1;}
		   else {
			   return 0;}

		   /*For descending order*/
		   //rollno2-rollno1;
		   }
	};
		
	public static Comparator<Ticket> CompareTicketCoachPrice = new Comparator<Ticket>() {

		public int compare(Ticket t1, Ticket t2) {

		   double totalCoachPrice1 = t1.getcoachPrice();
		   double totalCoachPrice2 = t2.getcoachPrice();
		   double diff = totalCoachPrice1-totalCoachPrice2;
		   
           /*For ascending order*/
		   
		   if ( diff > 0 ){
			   return 1;
			   }
		   else if (diff < 0){
			   return -1;}
		   else {
			   return 0;}

		   /*For descending order*/
		   //rollno2-rollno1;
		   }
	};
	
	public static Comparator<Ticket> CompareFlighttime = new Comparator<Ticket>() {

		public int compare(Ticket t1, Ticket t2) {

		   double totalFlighttime1 = t1.getFlightime();
		   double totalFlighttime2 = t2.getFlightime();
		   double diff = totalFlighttime1-totalFlighttime2;
		   
           /*For ascending order*/
		   
		   if ( diff > 0 ){
			   return 1;
			   }
		   else if (diff < 0){
			   return -1;}
		   else {
			   return 0;}

		   /*For descending order*/
		   //rollno2-rollno1;
		   }
	};
	
	
		
	public double getFirstPrice(){

		return totalFirstPrice;
	}
	
	
	public double getcoachPrice(){
		return totalCoachPrice;
	}
	
	
	public double getFlightime(){
		return totalFlightTime;
	}
	
	public Date gettotaldepTime() throws ParseException{
		Time time = new Time();
		return time.timesString2timeDate(flightslist.get(0).getmTimeDepart());
	}
	
	public Date gettotalarriTime() throws ParseException{
		Time time = new Time();
		return time.timesString2timeDate(flightslist.get(flightslist.size()-1).getmTimeArrival());
	}
	
	public String totalDepcode(){
		return flightslist.get(0).getmCodeDepart();
		
	}
	
	public String totalArrcode(){
		return flightslist.get(flightslist.size()-1).getmCodeArrival();
	}
	
	public ArrayList<Flight> getticket(){
		return flightslist;
	}
}
