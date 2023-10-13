package kodachi_CSCI201_Assignment1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException; 
import java.io.IOException;
import java.io.PrintWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;


public class Main {
	public static void main(String []args) {
//		get file and check valid format/exists
		Scanner in = new Scanner(System.in);
		boolean valid = false;
		Menu menu = null;
		String fn = "";
		while (!valid) {
			System.out.print("What is the name of the restaurant file? ");
			fn = in.nextLine();
			try {
				FileReader fr = new FileReader(fn);
				BufferedReader br = new BufferedReader(fr);
				Gson gson = new Gson();
				menu = gson.fromJson(br,Menu.class); // Gson documentation, 4 Sep 2023 https://github.com/google/gson/blob/main/UserGuide.md
				if (!menu.valid()) {
					System.out.println("The file is missing one or more parameters. ");
					valid = false;
				}
				else valid = true;
				fr.close();
				br.close();
			} catch (JsonSyntaxException jse) {
				System.out.println("The file " + fn + " is not formatted properly.");
			} catch (FileNotFoundException fnfe) {
				System.out.println("The file " + fn + " could not be found.");
			} catch (IOException ioe) {
				System.out.println("The file " + fn + " could not be found.");
			}
			finally {
				System.out.println("");
			}
		}	
//		get lat/lon
		while (true) {
			try {
//				System.out.println("");
				System.out.print("What is your latitude? ");
				menu.setLat(Double.parseDouble(in.nextLine()));
				break;
			} catch (InputMismatchException ime) {
				System.out.print("Latitude needs to be of type double. \n");
			} catch (NullPointerException npe) {
				System.out.print("Latitude needs to be of type double. \n");
			} catch (NumberFormatException nfe) {
				System.out.print("Latitude needs to be of type double. \n");
			}
		}
		while (true) {
			try {
				System.out.println("");
				System.out.print("What is your longitude? ");
				menu.setLon(Double.parseDouble(in.nextLine()));
				break;
			} catch (InputMismatchException ime) {
				System.out.print("Longitude needs to be of type double. \n");
			}catch (NullPointerException npe) {
				System.out.print("Longitude needs to be of type double. \n");
			} catch (NumberFormatException nfe) {
				System.out.print("Longitude needs to be of type double. \n");
			}
		}
		menu.initDist();
//		Enter the menu phase
		boolean val = true;
		while (true) {
			if (val) {
				menu.printcmds();
			}
			System.out.print("What would you like to do? ");
			String cmd = in.nextLine().trim();
			if (cmd.equals("1")) { // display all restaurants
				System.out.println("");
				menu.displayRestaurants();
			}
			else if (cmd.equals("2")) { // search for restaurant
				System.out.println("");
				System.out.print("What is the name of the restaurant you would like to search for? ");
				String name = in.nextLine();
				menu.searchRestaurants(name);
			}
			else if (cmd.equals("3")) { // search for food
				System.out.println("");
				System.out.print("What menu item you would like to search for? ");
				String name = in.nextLine();
				menu.searchFood(name);
			}
			else if (cmd.equals("4")) { // add new restaurant
				String name;
				String addy;
				double lat;
				double lon;
				List<String> foods = new ArrayList<>();
				while (true) {
					System.out.println("");
					System.out.print("What is the name of the restaurant you would like to add? ");
					name = in.nextLine();
					if (menu.findRestaurant(name) != -1) {
						System.out.println("");
						System.out.println("There is already an entry for " + name + ".");
					}
					else {
						break;
					}
				}
				System.out.println("");
				System.out.print("What is the address for " + name + "? ");
				addy = in.nextLine();
				while (true) {
					try {
						System.out.println("");
						System.out.print("What is the latitude for " + name + "? ");
						lat = Double.parseDouble(in.nextLine());
						break;
					} catch (InputMismatchException ime) {
						System.out.print("Latitude needs to be of type double. \n");
					} catch (NullPointerException npe) {
						System.out.print("Longitude needs to be of type double. \n");
					} catch (NumberFormatException nfe) {
						System.out.print("Longitude needs to be of type double. \n");
					}
				}
				while (true) {
					try {
						System.out.println("");
						System.out.print("What is the longitude for " + name + "? ");
						lon = Double.parseDouble(in.nextLine());
						break;
					} catch (InputMismatchException ime) {
						System.out.print("Longitude needs to be of type double. \n");
					}catch (NullPointerException npe) {
						System.out.print("Longitude needs to be of type double. \n");
					} catch (NumberFormatException nfe) {
						System.out.print("Longitude needs to be of type double. \n");
					}
				}
				while (true) {
					System.out.println("");
					System.out.print("What does " + name + " serve? ");
					String item = in.nextLine();
					foods.add(item);
					String input = "";
					System.out.println("");
					System.out.println("\t1) Yes");
					System.out.println("\t2) No");
					while (true) {
						System.out.print("Does " + name + " serve anything else? ");
						input = in.nextLine().trim();
						if (input.equals("1") || input.equals("2")){
							break;
						}
						System.out.println("That is not a valid option. ");
					}
					if (input.equals("1")) {
						continue;
					}
					else if (input.equals("2")) {
						break;
					}
					
				}
				menu.addRestaurant(name,addy,lat,lon,foods);
			}
			else if (cmd.equals("5")) { // remove restaurant
				int i = menu.toberemoved();
				System.out.println("");
				System.out.print("Which restaurant would you like to remove? ");
				int j = Integer.parseInt(in.nextLine());
				if (j > 0 && j <= i) {
					menu.removeInd(j-1);
				}
				else {
					System.out.println("That is not a valid option.\n");
				}
			}
			else if (cmd.equals("6")) { // sort restaurants
				menu.sortmenu();
				while (true) {
					System.out.print("\nWhat would you like to sort by? ");
					String sorttype = in.nextLine().trim();
					if (sorttype.equals("1") || sorttype.equals("2") || sorttype.equals("3") || sorttype.equals("4")) {
						menu.sortRestaurants(sorttype);
						break;
					}
					else {
						System.out.println("That is not a valid option.");
					}
				}
			}
			else if (cmd.equals("7")) { // give option to save then exit
				System.out.println("");
				System.out.println("\t1) Yes");
				System.out.println("\t2) No");
				System.out.print("Would you like to save your edits? ");
				String exittype = in.nextLine().trim();
				System.out.println("");
				if (exittype.equals("1")) {
					try {
						FileWriter fw = new FileWriter(fn);
						PrintWriter pw = new PrintWriter(fw);
						Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Gson documentation, 4 Sep 2023 https://github.com/google/gson/blob/main/UserGuide.md
						gson.toJson(menu,pw);
						fw.close();
						pw.close();
						System.out.println("Your edits have been saved to " + fn + ".");
					} catch(IOException ioe) {
						System.out.println(ioe.getMessage());
					}
				}
				else if (!exittype.equals("2")) {
					System.out.println("That is not a valid option.");
					continue;
				}
				break;
			}
			else {
				System.out.println("That is not a valid option.\n");
				val = false;
				continue;
			}
			val = true;
		}
		System.out.println("Thanks for using my program!");
		System.out.println("----------------------------");
		in.close();
	}
}
