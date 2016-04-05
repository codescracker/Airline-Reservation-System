package cs509.client.reservation;

import java.text.ParseException;
import java.util.ArrayList;

import cs509.client.Search.SearchFilter;
import cs509.client.dao.ServerInterface;
import cs509.client.flight.Flight;
import cs509.client.flight.Flights;
import cs509.client.flight.Ticket;
import cs509.client.util.Time;

public class Reserve {

	public static void main(String[] args) throws ParseException {
		Time t = new Time();
		SearchFilter filter = new SearchFilter();
		filter.search1or2("WorldPlaneInc", "BOS", "LGA", "2016_05_10","2016_05_11",false);
		ArrayList<Ticket> twogo = filter.getroundtripgo();
		Flight flight = twogo.get(0).getticket().get(0);
		System.out.println(flight.getmCodeDepart());
		System.out.println(flight.getmTimeDepart());
		System.out.println(t.timeDate2RighttimeString(t.timesString2timeDate(flight.getmTimeDepart())));
		
		Reserve reserve = new Reserve();
		reserve.reserveFlight(flight, false);
		
		Ticket ticket = twogo.get(twogo.size()-1);
		reserve.reserveTicket(ticket, false);
		
	}
	
	public void reserveFlight(Flight f, boolean iscoach) throws ParseException{
		ServerInterface resSys = new ServerInterface();
		String flightNumber = f.getmNumber();
		
		if (iscoach==true){
			int seatsReservedStart = f.getmSeatsCoach();

			String xmlReservation = "<Flights>"
					+ "<Flight number=\"" + flightNumber + "\" seating=\"Coach\"/>"
					+ "</Flights>";
			
			
			// Try to lock the database, purchase ticket and unlock database
			resSys.lock("WorldPlaneInc");
			resSys.buyTickets("WorldPlaneInc", xmlReservation);
			resSys.unlock("WorldPlaneInc");
			
			String verifydep= f.getmCodeDepart();
			String verifydeptime = f.getmTimeDepart();
			Time t = new Time();
			String qverifydeptime = t.timeDate2RighttimeString(t.timesString2timeDate(verifydeptime));
			
			// Verify the operation worked
			Flights flights = new Flights();
			String xmlFlights = resSys.getFlights("WorldPlaneInc", verifydep, qverifydeptime);
			flights.addAll(xmlFlights);
			
			// Find the flight number just updated
			int seatsReservedEnd = seatsReservedStart;
			for (Flight flight : flights) {
				String tmpFlightNumber = flight.getmNumber();
				if (tmpFlightNumber.equals(flightNumber)) {
					seatsReservedEnd = flight.getmSeatsCoach();
					break;
				}
			}
			if (seatsReservedEnd == (seatsReservedStart + 1)) {
				System.out.println("Seat Reserved Successfully");
			} else {
				System.out.println("Reservation Failed");
			}
			
			
		}
		else{
			
			int seatsReservedStart = f.getmSeatsFirstclass();

			String xmlReservation = "<Flights>"
					+ "<Flight number=\"" + flightNumber + "\" seating=\"FirstClass\"/>"
					+ "</Flights>";
			
			
			// Try to lock the database, purchase ticket and unlock database
			resSys.lock("WorldPlaneInc");
			resSys.buyTickets("WorldPlaneInc", xmlReservation);
			resSys.unlock("WorldPlaneInc");
			
			String verifydep= f.getmCodeDepart();
			String verifydeptime = f.getmTimeDepart();
			Time t = new Time();
			String qverifydeptime = t.timeDate2RighttimeString(t.timesString2timeDate(verifydeptime));
			
			// Verify the operation worked
			Flights flights = new Flights();
			String xmlFlights = resSys.getFlights("WorldPlaneInc", verifydep, qverifydeptime);
			flights.addAll(xmlFlights);
			
			// Find the flight number just updated
			int seatsReservedEnd = seatsReservedStart;
			for (Flight flight : flights) {
				String tmpFlightNumber = flight.getmNumber();
				if (tmpFlightNumber.equals(flightNumber)) {
					seatsReservedEnd = flight.getmSeatsFirstclass();
					break;
				}
			}
			if (seatsReservedEnd == (seatsReservedStart + 1)) {
				System.out.println("Seat Reserved Successfully");
			} else {
				System.out.println("Reservation Failed");
			}
			
			
		}
		
	}
	
	public void reserveTicket(Ticket t, boolean iscoach) throws ParseException{
		ArrayList<Flight> flist = t.getticket();
		for (int i=0; i <flist.size();i++){
			this.reserveFlight(flist.get(i), iscoach);
		}
		
	}
	
}
