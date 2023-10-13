package kodachi_CSCI201_Assignment1;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
//import java.util.Arrays;

public class Restaurant {
	@SerializedName("name") // Gson documentation, 4 Sept 2023, https://github.com/google/gson/blob/main/UserGuide.md
	private String name;
	@SerializedName("address")
	private String address;
	@SerializedName("latitude")
	private double latitude;
	@SerializedName("longitude")
	private double longitude;
	@SerializedName("menu")
//	private String [] menu;
	private List<String> menu = new ArrayList<>();
	
	private transient double dist;
	
	public Restaurant(String name,String addy,double lat,double lon,List<String> menu) {
		this.name = name;
		this.address = addy;
		this.latitude = lat;
		this.longitude = lon;
		this.menu = menu;
	}
	
	public String getname() {
		return this.name;
	}
	public String getaddress() {
		return this.address;
	}
	public double getlatitude() {
		return this.latitude;
	}
	public double getlongitude() {
		return this.longitude;
	}
	public List<String> getmenu() {
		return this.menu;
	}
	public double getDist() {
		return this.dist;
	}
	
	public void setname(String name) {
		this.name = name;
	}
	public void setaddress(String address) {
		this.address = address;
	}
	public void setlatitude(double latitude) {
		this.latitude = latitude;
	}
	public void setlongitude(double longitude) {
		this.longitude = longitude;
	}
	public void setmenu(List<String> menu) {
		this.menu = menu;
	}
	public void setDist(double dist) {
		this.dist = dist;
	}
	
	public boolean validcheck() {
		if (this.name == null) return false;
		if (this.address == null) return false;
		if (this.latitude == 0.0) return false;
		if (this.longitude == 0.0) return false;
		if (this.menu.isEmpty()) return false;
		return true;
	}
	
	public double calcDist(double lat, double lon) {
		double lat1 = Math.toRadians(lat);
		double lat2 = Math.toRadians(this.latitude);
		double lon1 = Math.toRadians(lon);
		double lon2 = Math.toRadians(this.longitude);
		double distance = 3963.0 * Math.acos((Math.sin(lat1) * Math.sin(lat2)) 
		                              + Math.cos(lat1) * Math.cos(lat2) 
		                              * Math.cos(lon2-lon1));
		return (Math.round(distance * 10.0) / 10.0);
	}
}
