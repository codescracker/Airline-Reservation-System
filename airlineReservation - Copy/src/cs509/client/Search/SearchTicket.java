package cs509.client.Search;

import java.text.ParseException;
import java.util.ArrayList;

import cs509.client.dao.ServerInterface;
import cs509.client.flight.Flight;
import cs509.client.flight.Flights;
import cs509.client.flight.Ticket;
import cs509.client.util.Time;

public class SearchTicket {
	ArrayList<Ticket> flightlist1 ;
		
	SearchTicket(){
		flightlist1 =  new ArrayList<Ticket>();
	}
	
	
	public static void main(String[] args) throws ParseException{
//		SearchTicket drive= new SearchTicket();		
//		
//		ArrayList<Flight> forphase2= drive.phaseone("Team01","BOS","LGA","2016_05_10"); 
//		ArrayList<ArrayList<Flight>> forphase3= drive.phasetwo(forphase2, "LGA");
//		drive.phasethree(forphase3, "LGA");
//		System.out.println(flightlist1.size());
//		System.out.println(forphase3);
//		System.out.println(flightlist1);
//		System.out.println(flightlist1);
//		System.out.println(forphase2.get(0).getmTimeArrival());
//		Time time = new Time();
//		System.out.println(forphase2);
//		System.out.println(time.timeDate2RighttimeString(time.timesString2timeDate(forphase2.get(0).getmTimeArrival())));
//		drive.act("Team01", "BOS", "LGA", "2016_05_10");
//		ArrayList<ArrayList<Flight>> res= drive.act("Team01", "BOS", "LGA", "2016_05_10");
//		System.out.println(res);
//		System.out.println(res.get(res.size()-1).get(2).getmCodeArrival());
		
//		drive.search1or2("Team01", "BOS", "LGA", "2016_05_10","2016_05_11",false);
//		System.out.println(drive.roundtripgo);
//		System.out.println(drive.roundtripback);
//		System.out.println(drive.roundtripbackstopoverfilter(2));
		
//		ArrayList<Flight> forphase2= drive.phaseone("Team03","BOS","LGA","2016_05_10"); 
//		System.out.println(drive.flightlist1);
//		System.out.println(forphase2);
//		
//		ArrayList<ArrayList<Flight>> forphase3= drive.phasetwo(forphase2, "LGA");
//		System.out.println(drive.flightlist1);
//		System.out.println(forphase3);
//		
//		drive.phasethree(forphase3, "LGA");
//		System.out.println(drive.flightlist1);
//		
//		System.out.println(drive.flightlist1.get(0).getticket().get(0));
//		
//		ArrayList<Ticket> res= drive.act("Team03", "BOS", "LGA", "2016_05_10");
//		System.out.println(res);
//		
//		drive.search1or2("Team03", "BOS", "LGA", "2016_05_10","2016_05_11",false);
//		System.out.println(drive.roundtripgo);
//		System.out.println(drive.roundtripback);
	}
	
	public ArrayList<Ticket> act(String team, String depcode, String arrcode, String date) throws ParseException{
		SearchTicket actor= new SearchTicket();
		ArrayList<Flight> forphase2= actor.phaseone(team,depcode,arrcode,date); 
		ArrayList<ArrayList<Flight>> forphase3= actor.phasetwo(forphase2, arrcode);
		actor.phasethree(forphase3, arrcode);
		return actor.flightlist1;
	}
	
	
	public ArrayList<Flight> phaseone(String team, String depcode,String arrcode, String date){
		ServerInterface server = new ServerInterface();
		String xmlflights = server.getFlights(team,depcode,date);
		Flights flights = new Flights();
		flights.addAll(xmlflights);

		ArrayList<Flight> potentialflights= new ArrayList<Flight>();

		for(int i=0;i<flights.size();i++){
			Flight flight1 = flights.get(i);
			if (flight1.getmCodeArrival().equals(arrcode)){

				Ticket ticket = new Ticket();
				ticket.addFlight(flight1);
				flightlist1.add(ticket);
			}
			else{

				potentialflights.add(flight1);
			}
		}
		return potentialflights;

	}

	public ArrayList<ArrayList<Flight>> phasetwo(ArrayList<Flight> potential1, String arrcode) throws ParseException{
		ArrayList<ArrayList<Flight>> potentialflights2 = new ArrayList<ArrayList<Flight>>();
		Time time = new Time();
		for (int i=0; i<potential1.size();i++){

			Flight potentialflight = potential1.get(i);
			String airportName = potentialflight.getmCodeArrival();
			String date = time.timeDate2RighttimeString(time.timesString2timeDate(potentialflight.getmTimeArrival()));
			ServerInterface server = new ServerInterface();
			String xmlflights = server.getFlights("Team03",airportName,date);
			Flights flights = new Flights();
			flights.addAll(xmlflights);

			for(int j=0;j<flights.size();j++){
				Flight flight = flights.get(j);
				if (time.timeDifference(potentialflight.getmTimeArrival(),flight.getmTimeDepart())>=1 && time.timeDifference(potentialflight.getmTimeArrival(),flight.getmTimeDepart())<=3){
					if (flight.getmCodeArrival().equals(arrcode)){

						Ticket ticket = new Ticket();
						ticket.addFlight(potentialflight);
						ticket.addFlight(flight);
						flightlist1.add(ticket);
					}
					else{
						ArrayList<Flight> flightcontainer= new ArrayList<Flight>();
						flightcontainer.add(potentialflight);
						flightcontainer.add(flight);
						potentialflights2.add(flightcontainer);

					}


				}

			}

		}
		return potentialflights2;
	}
	
	public void phasethree(ArrayList<ArrayList<Flight>> potential2, String arrcode) throws ParseException{
		Time time = new Time();
		
		for (int i=0; i<potential2.size();i++){
			ArrayList<Flight> flightlist2= potential2.get(i);
			Flight potentialflight = flightlist2.get(1);
			String airportName = potentialflight.getmCodeArrival();
			String date = time.timeDate2RighttimeString(time.timesString2timeDate(potentialflight.getmTimeArrival()));
			ServerInterface server = new ServerInterface();
			String xmlflights = server.getFlights("Team03",airportName,date);
			Flights flights = new Flights();
			flights.addAll(xmlflights);

			for(int j=0;j<flights.size();j++){
				Flight flight = flights.get(j);
				if (time.timeDifference(potentialflight.getmTimeArrival(),flight.getmTimeDepart())>=1 && time.timeDifference(potentialflight.getmTimeArrival(),flight.getmTimeDepart())<=3 && flight.getmCodeArrival().equals(arrcode)){

						Ticket ticket = new Ticket();
						ticket.addFlight(flightlist2.get(0));
						ticket.addFlight(flightlist2.get(1));
						ticket.addFlight(flight);
						flightlist1.add(ticket);


				}

			}

		}
	}
	

}