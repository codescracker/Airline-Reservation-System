package cs509.client.Search;

import java.util.ArrayList;

import cs509.client.airport.Airport;
import cs509.client.airport.Airports;
import cs509.client.dao.ServerInterface;

public class SearchAirports {
	ArrayList<Airport> airportslist;
	
	public SearchAirports(){
		airportslist = new ArrayList<Airport>();
	}
	
	public void searchAirports(String team){
		
		ServerInterface resSys = new ServerInterface();
		String xmlAirport = resSys.getAirports(team);
		Airports airportlist = new Airports();
		airportlist.addAll(xmlAirport);
		this.airportslist= airportlist;
	}
	
	public ArrayList<Airport> getAirports(){
		return this.airportslist;
	}
	
	public static void main(String[] args){
		
		SearchAirports apsc= new SearchAirports();
		apsc.searchAirports("Team03");
		System.out.println(apsc.getAirports().get(0).code());
		System.out.println(apsc.getAirports().get(0).name());
		System.out.println(apsc.getAirports().get(0).longitude());
		
	}
	
}
