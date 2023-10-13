package kodachi_CSCI201_Assignment1;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Menu {
	@SerializedName("data") // Gson documentation, 4 Sept 2023, https://github.com/google/gson/blob/main/UserGuide.md
	ArrayList<Restaurant> restaurants;
	
	private transient double lat;
	private transient double lon;
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return this.lat;
	}
	public double getLon() {
		return this.lon;
	}
	
	public void initDist() {
		for (Restaurant temp: this.restaurants) {
			temp.setDist(temp.calcDist(this.lat, this.lon));
		}
	}
	
	public boolean valid() {
		if (restaurants.isEmpty()) return false;
		for (Restaurant temp: restaurants) {
			if (!temp.validcheck()) return false;
		}
		return true;
	}
	
	public Menu(ArrayList<Restaurant> list) {
		this.restaurants = list;
	}
	
	public void printcmds() {
		System.out.println("\n\t1) Display all restaurants");
		System.out.println("\t2) Search for a restaurant");
		System.out.println("\t3) Search for a menu item");
		System.out.println("\t4) Add a new restaurant");
		System.out.println("\t5) Remove a restaurant");
		System.out.println("\t6) Sort restaurants");
		System.out.println("\t7) Exit");
	}
	
	public void displayRestaurants() {
		for (Restaurant temp : this.restaurants) {
			this.displayRestaurant(temp);
		}
	}
	
	public void displayRestaurant(Restaurant temp) {
		System.out.println(temp.getname() + ", located " + 
				String.valueOf(temp.getDist()) + " miles away at " + temp.getaddress());
	}
	
	public void searchRestaurants(String name) {
		List<Restaurant> prints = new ArrayList<>();
		String hold = name.toLowerCase().strip();
		for (Restaurant temp: this.restaurants) {
			if (temp.getname().toLowerCase().strip().contains(hold)) {
				prints.add(temp);
			}
		}
		if (prints.isEmpty()) {
			System.out.print("\n" + name + " could not be found.");
		}
		else {
			System.out.println("");
			for (Restaurant temp: prints) {
				this.displayRestaurant(temp);
			}
		}
	}
	
	public int findRestaurant(String name) {
		String hold = name.strip();
		for (Restaurant temp: this.restaurants) {			
			if (hold.equals(temp.getname().strip())) {
				return this.restaurants.indexOf(temp);
			}
		}
		return -1;
	}
	
	public void displayFood(Restaurant temp, List<String> display) {
		if (display.isEmpty()) return;
		String items = "";
		items += display.get(0);
		if (display.size() == 2) {
			items += " and " + display.get(1);
		}
		else {
			for (int i=1;i<display.size();i++) {
				if (i == display.size() - 1) {
					items += ", and " + display.get(i);
				}
				else {
					items += ", " + display.get(i);
				}
			}
		}
		System.out.println(temp.getname() + " serves " + items + ".");
	}
	
	public void searchFood(String name) {
		String hold = name.toLowerCase().strip();
		System.out.println("");
		int count = 0;
		for (Restaurant temp: this.restaurants) {
			List<String> match = new ArrayList<>();
			for (String item: temp.getmenu()) {
				if (item.toLowerCase().strip().contains(hold)){
					match.add(item);
				}
			}
			if (match.isEmpty()) count ++;
			else {
				this.displayFood(temp, match);
			}
			match.clear();
		}
		if (count == this.restaurants.size()) {
			System.out.println("No restaurant nearby serves " + name);
		}
	}
	
	public void addRestaurant(String name,String addy,double lat,double lon, List<String>foods) {
		Restaurant added = new Restaurant(name,addy,lat,lon,foods);
		added.setDist(added.calcDist(this.lat, this.lon)); // problem
		this.restaurants.add(added);
		System.out.println("");
		System.out.println("There is a new entry for: ");
		this.displayRestaurant(added);
		this.displayFood(added, foods);
	}
	
	public int toberemoved() {
		System.out.println("");
		int i = 1;
		for (Restaurant temp: this.restaurants) {
			System.out.println("\t"+String.valueOf(i)+") "+temp.getname());
			i++;
		}
		return i-1;
	}
	
	public void removeInd(int i) {
		String name = this.restaurants.get(i).getname();
		this.restaurants.remove(i);
		System.out.println("");
		System.out.println(name + " is now removed.");
	}
	
	public void sortmenu() {
		System.out.println("");
		System.out.println("\t1) A to Z");
		System.out.println("\t2) Z to A");
		System.out.println("\t3) Closest to Farthest");
		System.out.print("\t4) Farthest to Closest");
	}
	
	public void sortRestaurants(String sorttype) { // comparator syntax documentation, 4 Sept 2023 https://docs.oracle.com/javase/8/docs/api/java/util/Comparator.html
		if (sorttype.equals("1")) {
			Collections.sort(this.restaurants,Comparator.comparing(Restaurant::getname));
			System.out.println("Your restaurants have been sorted from A to Z.");
		}
		else if(sorttype.equals("2")) {
			Collections.sort(this.restaurants,Comparator.comparing(Restaurant::getname).reversed());
			System.out.println("Your restaurants have been sorted from Z to A.");
		}
		else if(sorttype.equals("3")) {
			Collections.sort(this.restaurants,Comparator.comparing(Restaurant::getDist));
			System.out.println("Your restaurants have been sorted from closest to farthest.");
		}
		else if(sorttype.equals("4")) {
			Collections.sort(this.restaurants,Comparator.comparing(Restaurant::getDist).reversed());
			System.out.println("Your restaurants have been sorted from farthest to closest.");
		}
		else {
			System.out.println("That is not a valid option.\n");
		}
	}
}
