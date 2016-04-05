package cs509.client.Search;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import cs509.client.flight.Ticket;
import cs509.client.util.Time;

public class SearchFilter {
	
	ArrayList<Ticket> oneway;
	ArrayList<Ticket> roundtripgo;
	ArrayList<Ticket> roundtripback;
	
	public static void main(String[] args) throws ParseException {
		SearchFilter filter = new SearchFilter();
		filter.search1or2("Team03", "BOS", "LGA", "2016_05_10","2016_05_11",false);
		ArrayList<Ticket> one = filter.getoneway();
		System.out.println(one);
		ArrayList<Ticket> twogo = filter.getroundtripgo();
		System.out.println(twogo);
		ArrayList<Ticket> twoback = filter.getroundtripback();
		System.out.println(twoback);
		
		
		System.out.println(twoback.get(0).getcoachPrice());
		System.out.println(twoback.get(1).getcoachPrice());
		System.out.println(twoback.get(4).gettotalarriTime());
		System.out.println(twoback.get(4).gettotaldepTime());
	
//		
//		Collections.sort(twoback, Ticket.CompareTicketCoachPrice);
		filter.sortbyCoachPrice(twoback);
		System.out.println(twoback.get(0).getcoachPrice());
		System.out.println(twoback.get(1).getcoachPrice());
		System.out.println(twoback.get(2).getcoachPrice());
		System.out.println(twoback.get(3).getcoachPrice());
		
		
//		Collections.sort(twoback, Ticket.CompareTicketFirstPrice);
		filter.sortbyFirstPrice(twoback);
		System.out.println(twoback.get(0).getFirstPrice());
		System.out.println(twoback.get(1).getFirstPrice());
		System.out.println(twoback.get(2).getFirstPrice());
		System.out.println(twoback.get(3).getFirstPrice());
		
//		Collections.sort(twoback, Ticket.CompareFlighttime);
		filter.sortbyFightTime(twoback);
		System.out.println(twoback.get(0).getFlightime());
		System.out.println(twoback.get(1).getFlightime());
		System.out.println(twoback.get(2).getFlightime());
		System.out.println(twoback.get(3).getFlightime());
		
//		System.out.println(filter.roundtripbackstopoverfilter(0));
//		System.out.println(filter.roundtripbackstopoverfilter(1));
//		System.out.println(filter.roundtripbackstopoverfilter(2));
//		
//		System.out.println(filter.roundtripgostopoverfilter(0));
//		System.out.println(filter.roundtripgostopoverfilter(1));
//		System.out.println(filter.roundtripgostopoverfilter(2));
		
		Time t = new Time();
		System.out.println(twoback.get(0).gettotaldepTime());
		System.out.println(t.convertGMT2localTDep(twoback.get(0)));
		
		System.out.println(twoback.get(0).gettotalarriTime());
		System.out.println(t.convertGMT2localTArr(twoback.get(0)));
	}
	
	public void search1or2(String team, String depcode, String arrcode, String godate, String backdate, boolean isoneway) throws ParseException{
		SearchTicket drive= new SearchTicket();
		
		if(isoneway==true){
			oneway= drive.act(team, depcode, arrcode, godate);
		}
		else{
			roundtripgo=drive.act(team, depcode, arrcode, godate);
			roundtripback=drive.act(team,arrcode,depcode,backdate);
		}
		
	}
	
	public ArrayList<Ticket> getoneway(){
		return oneway;
	}
	
	public ArrayList<Ticket> getroundtripgo(){
		return roundtripgo;
	}
	
	public ArrayList<Ticket> getroundtripback(){
		return roundtripback;
	}
	
	public ArrayList<Ticket> onewaystopoverfilter(int stopover){
		ArrayList<Ticket> res = new ArrayList<Ticket>();
		if (stopover ==0){
			for(int i=0; i <oneway.size();i++){
				if (oneway.get(i).getticket().size()==1){
					res.add(oneway.get(i));
				}
			}
		}
		
		
		if (stopover ==1){
			for(int i=0; i <oneway.size();i++){
				if (oneway.get(i).getticket().size()==2){
					res.add(oneway.get(i));
				}
			}
		}
		
		if (stopover ==2){
			for(int i=0; i <oneway.size();i++){
				if (oneway.get(i).getticket().size()==3){
					res.add(oneway.get(i));
				}
			}
		}
		
		return res;
	}
	
	
	public ArrayList<Ticket> roundtripgostopoverfilter(int stopover){
		ArrayList<Ticket> res = new ArrayList<Ticket>();
		if (stopover ==0){
			for(int i=0; i <roundtripgo.size();i++){
				if (roundtripgo.get(i).getticket().size()==1){
					res.add(roundtripgo.get(i));
				}
			}
		}
		
		
		if (stopover ==1){
			for(int i=0; i <roundtripgo.size();i++){
				if (roundtripgo.get(i).getticket().size()==2){
					res.add(roundtripgo.get(i));
				}
			}
		}
		
		if (stopover ==2){
			for(int i=0; i <roundtripgo.size();i++){
				if (roundtripgo.get(i).getticket().size()==3){
					res.add(roundtripgo.get(i));
				}
			}
		}
		
		return res;
	}
	
	public ArrayList<Ticket> roundtripbackstopoverfilter(int stopover){
		ArrayList<Ticket> res = new ArrayList<Ticket>();
		if (stopover ==0){
			for(int i=0; i <roundtripback.size();i++){
				if (roundtripback.get(i).getticket().size()==1){
					res.add(roundtripback.get(i));
				}
			}
		}
		
		
		if (stopover ==1){
			for(int i=0; i <roundtripback.size();i++){
				if (roundtripback.get(i).getticket().size()==2){
					res.add(roundtripback.get(i));
				}
			}
		}
		
		if (stopover ==2){
			for(int i=0; i <roundtripback.size();i++){
				if (roundtripback.get(i).getticket().size()==3){
					res.add(roundtripback.get(i));
				}
			}
		}
		
		return res;
	}
	

	public void sortbyCoachPrice(ArrayList<Ticket> ticketlist){
		Collections.sort(ticketlist, Ticket.CompareTicketCoachPrice);
	}
	
	public void sortbyFirstPrice(ArrayList<Ticket> ticketlist){
		Collections.sort(ticketlist, Ticket.CompareTicketFirstPrice);
	}
	
	public void sortbyFightTime(ArrayList<Ticket> ticketlist){
		Collections.sort(ticketlist, Ticket.CompareFlighttime);
	}
	

}
