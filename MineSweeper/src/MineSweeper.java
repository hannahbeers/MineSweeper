
/**
 * MineSweeper program sets up and loops games of MineSweeper based off of
 * the user-entered size of the map. Detects when the user wins or loses.
 * 
 * @author Hannah Beers
 *
 */

////////////////////ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
//Title:           MineSweeper
//Files:           (a list of all source files used by that program)
//Course:          333
//
//Author:          Hannah Beers
//Email:           hlbeers@wisc.edu
//Lecturer's Name: Professor Renault
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

// imports
import java.util.Random;
import java.util.Scanner;

// public class MineSweeper runs MineSweeper game

public class MineSweeper {
	// declarations of global variables
	public static int number;
	public static int height;
	public static int width;
	public static int numNearbyMines = 0;
	public static boolean playingGame = true;
	public static boolean correctNumber = false;
	public static boolean correctNumber2 = true;
	public static int row;
	public static int column;
	public static boolean endGame = false;
	public static boolean isInteger = false;
	public static boolean isInteger2 = true;
	public static Random randGen = new Random(Config.SEED);
	public static boolean gameOver = false;
	public static int numOfMines = 0;
	
  /**
   * This is the main method for Mine Sweeper game!
   * This method contains the within game and play again loops and calls
   * the various supporting methods.
   *  
   * @param args (unused)
   */
  public static void main(String[] args) {
	  Scanner scan = new Scanner(System.in); 
	  
	  // resets loops
	  correctNumber = false;
	  correctNumber2 = true;
	  isInteger = false;
	  isInteger2 = true;
	  
	  // welcome message
	  System.out.println("Welcome to Mine Sweeper!");
	  
while(endGame == false) {
	
		// determines width by calling promptUser
		correctNumber = false;
		isInteger = false;
		String prompt = "What width of map would you like (" + Config.MIN_SIZE + " - " + Config.MAX_SIZE + "): ";
		System.out.print(prompt);
		width = promptUser(scan, prompt, Config.MIN_SIZE, Config.MAX_SIZE);
	
		// determines height by calling promptUser
		correctNumber = false;
		isInteger = false;
		prompt = "What height of map would you like (" + Config.MIN_SIZE + " - " + Config.MAX_SIZE + "): ";
		System.out.print(prompt);
		height = promptUser(scan, prompt, Config.MIN_SIZE, Config.MAX_SIZE);
		
		// creates map based off of width and height
		char[][] map = new char[height][width];
		
		// erases map
		eraseMap(map);
		
		// initializes new boolean mine array
		boolean[][] mines = new boolean[height][width];

		// prints number of mines on map
		numOfMines = placeMines(mines, randGen);
		System.out.println("\nMines: " + numOfMines);

		// prints map
		printMap(map);
		
		while(gameOver == false){
		
		// prompts user for the row to sweep
		correctNumber2 = false;
		isInteger2 = false;
		prompt = "row: ";
		System.out.print(prompt);
		row = promptUser(scan, prompt, 1, height);
		
		// prompts user for the column to sweep
		correctNumber2 = false;
		isInteger2 = false;
		prompt = "column: ";
		System.out.print(prompt);
	    column = promptUser(scan, prompt, 1, width);
	    
	    // if swept spot has a mine, updates map with SWEPT_MINE
	    if(mines[row-1][column-1] == true) {
	    showMines(map, mines);
	    map[row-1][column-1] = Config.SWEPT_MINE;
	    printMap(map);
	    System.out.println("Sorry, you lost.");
	    gameOver = true;
	    }
	    
	    // sweeps location
	    else{
	    	int temp = sweepLocation(map, mines, (row-1), (column-1));
	    if(temp == 0){
	    sweepAllNeighbors(map, mines, (row-1), (column-1));
	    }
	    if(allSafeLocationsSwept(map, mines) == false) {
	    System.out.println("\nMines: " + numOfMines);
	    printMap(map);
	    }
	    }
	    
	    
	    // else determine nearby mines and display on map
	    if((allSafeLocationsSwept(map, mines) == true)){
	    	// sets uncovered mines to show
	    	for(int r=0; r<map.length; r++) {
		        for(int col=0; col<map[r].length; col++) {
		        	   if(map[r][col] == Config.UNSWEPT)
		        		   map[r][col] = Config.HIDDEN_MINE;
		        }
	    	}
	    printMap(map);
	    	System.out.println("You Win!");
	    	gameOver = true;
	    } 
	
} // end of gameOver loop
	    
	    // asks the user if they want to play again
	    System.out.print("Would you like to play again (y/n)? ");
	    String again = scan.next();
	    again = again.trim();
	    again = again.substring(0,1);
	    if(again.equalsIgnoreCase("y")) {
	    	endGame = false;
	    	gameOver = false;
	    }
	    else {
	    System.out.println("Thank you for playing Mine Sweeper!");
	    endGame = true;
	    }
		
}
  }


  /**
   * This method prompts the user for a number, verifies that it is between min
   * and max, inclusive, before returning the number.  
   * 
   * If the number entered is not between min and max then the user is shown 
   * an error message and given another opportunity to enter a number.
   * If min is 1 and max is 5 the error message is:
   *      Expected a number from 1 to 5.  
   * 
   * If the user enters characters, words or anything other than a valid int then 
   * the user is shown the same message.  The entering of characters other
   * than a valid int is detected using Scanner's methods (hasNextInt) and
   * does not use exception handling.
   * 
   * Do not use constants in this method, only use the min and max passed
   * in parameters for all comparisons and messages.
   * Do not create an instance of Scanner in this method, pass the reference
   * to the Scanner in main, to this method.
   * The entire prompt should be passed in and printed out.
   *
   * @param in  The reference to the instance of Scanner created in main.
   * @param prompt  The text prompt that is shown once to the user.
   * @param min  The minimum value that the user must enter.
   * @param max  The maximum value that the user must enter.
   * @return The integer that the user entered that is between min and max, 
   *          inclusive.
   */
  public static int promptUser(Scanner in, String prompt, int min, int max) {
	  // prompts for width/height and checks if within min and max
	 
while(correctNumber == false) {
	while(isInteger == false) {
	  if(in.hasNextInt() == false) {
		  in.nextLine();
    	  System.out.println("Expected a number from " + min + " to " + max + ".");
    	 
	 }
	  else {  
		  number = in.nextInt();
		  isInteger = true;
      }
	}
	  
      if(number>Config.MAX_SIZE || number<Config.MIN_SIZE){
    	  correctNumber = false;
    	  in.nextLine();
    	  System.out.println("Expected a number from " + min + " to " + max + ".");
    	  isInteger = false;
    	  
      }
      else {
    	  correctNumber = true;
          } 

} // end of while loop


//prompts for row/column and checks if within min and max

while(correctNumber2 == false) {
	while(isInteger2 == false) {
	  if(in.hasNextInt() == false) {
	  System.out.println("Expected a number from " + min + " to " + max + ".");
	  in.next();
	  in.nextLine();
	 }
	  else {  
		  number = in.nextInt();
		  isInteger2 = true;
      }
	}
	  
      if(number>max || number<1){
    	  correctNumber2 = false;
    	  in.nextLine();
    	  System.out.println("Expected a number from " + min + " to " + max + ".");
    	  isInteger2 = false;
      }
      else {
    	  correctNumber2 = true;
          } 

} // end of while loop
	  return number;
  }
  

  /**
   * This initializes the map char array passed in such that all
   * elements have the Config.UNSWEPT character.
   * Within this method only use the actual size of the array. Don't
   * assume the size of the array.
   * This method does not print out anything. This method does not
   * return anything.
   * 
   * @param map An allocated array. After this method call all elements
   *      in the array have the same character, Config.UNSWEPT. 
   */
  public static void eraseMap(char[][] map) {
	  for(int i=0; i<map.length; i++) {
	        for(int j=0; j<map[i].length; j++) {
	            map[i][j] = Config.UNSWEPT;
	        }
	    }
  }    

  /**
   * This prints out a version of the map without the row and column numbers.
   * A map with width 4 and height 6 would look like the following: 
   *  . . . .
   *  . . . .
   *  . . . .
   *  . . . .
   *  . . . .
   *  . . . .
   * For each location in the map a space is printed followed by the 
   * character in the map location.
   * @param map The map to print out.
   */
  public static void simplePrintMap(char[][] map) {
	  for(int i=0; i<map.length; i++) {
	        for(int j=0; j<map[i].length; j++) {
	            System.out.print(" " + (map[i][j]));
	        }
	        System.out.print("\n");
	    }
  }

  /**
   * This prints out the map. This shows numbers of the columns
   * and rows on the top and left side, respectively. 
   * map[0][0] is row 1, column 1 when shown to the user.
   * The first column, last column and every multiple of 5 are shown.
   * 
   * To print out a 2 digit number with a leading space if the number
   * is less than 10, you may use:
   *     System.out.printf("%2d", 1); 
   * 
   * @param map The map to print out.
   */
  public static void printMap(char[][] map) {

	  // prints horizontal counter if greater than 5
	  int count = 2;
	  if(width > 5) {
		  System.out.print("   1");
		  for(int i = 0; i < width-2; i++) {
				if(count % 5 == 0 && count != width) {
					if(count < 10)
					{
						System.out.print(" ");
					}
		        		System.out.print(count);
			        count++;
		        }
		        	else {
		        		System.out.print("--");
			        count++;
		        	}
	      }
		  if(width < 10) {
			  System.out.print(" ");
		  }
		  System.out.print(width);
	  }
	  
	// prints horizontal counter if less than or equal to 5
	  else {
		  System.out.print("   1");
		  for(int i=1; i < width-1; i++) {
			  if(width % 2 == 0) {
			      System.out.print("-");
			  }
			  System.out.print("-");
			  if(width % 2 != 0) {
			     System.out.print("-");
			  }
		  }
		  System.out.print(" " + width);
	  }
	  System.out.print("\n");
	  
	  // prints vertical counter and map if less than 5
	  if(height <= 5) {
	  System.out.print(" 1");
	  for(int i=0; i<map.length; i++) {
	      if(i != 0 && i != height-1) {
		  System.out.print(" |");
		  }
	      if(i == height - 1) {
	    	  if(height < 10) {
	    		  System.out.print(" ");
	    	  }
			  System.out.print(height);
		  }
	        for(int j=0; j<map[i].length; j++) {
	            System.out.print(" " + (map[i][j]));
	        }
	        System.out.print("\n");
	    }
	  }
	  
	  // prints vertical counter and map if greater than 5
	  count = 2;
	  if(height > 5) {
		  System.out.print(" 1");
		  for(int i = 0; i<map[0].length; i++) {
		      System.out.print(" " + map[0][i]);
		  }
		  System.out.println();
		  for(int i=1; i<map.length-1; i++) {
			if(count % 5 == 0 && count != height) {
				if(count < 10)
				{
					System.out.print(" ");
				}
	        		System.out.print(count);
		        count++;
	        }
	        	else {
	        		System.out.print(" |");
		        count++;
	        	}
		        for(int j=0; j<map[i].length; j++) {
		            System.out.print(" " + (map[i][j]));
		        }
		        System.out.print("\n");
		    }
		  if(height < 10) {
			  System.out.print(" ");
		  }
		  System.out.print(height);
		  for(int i = 0; i<map[height-1].length ; i++) {
		      System.out.print(" " + map[height-1][i]);
		  }
		  System.out.println();
      }
  }

  /**
   * This method initializes the boolean mines array passed in. A true value for
   * an element in the mines array means that location has a mine, false means
   * the location does not have a mine. The MINE_PROBABILITY is used to determine
   * whether a particular location has a mine. The randGen parameter contains the
   * reference to the instance of Random created in the main method.
   * 
   * Access the elements in the mines array with row then column (e.g., mines[row][col]).
   * 
   * Access the elements in the array solely using the actual size of the mines
   * array passed in, do not use constants. 
   * 
   * A MINE_PROBABILITY of 0.3 indicates that a particular location has a
   * 30% chance of having a mine.  For each location the result of
   *      randGen.nextFloat() < Config.MINE_PROBABILITY 
   * determines whether that location has a mine.
   * 
   * This method does not print out anything.
   *  
   * @param mines  The array of boolean that tracks the locations of the mines.
   * @param randGen The reference to the instance of the Random number generator
   *      created in the main method.
   * @return The number of mines in the mines array.
   */
  public static int placeMines(boolean[][] mines, Random randGen) {
	  int numMines = 0;
	  float num = 0;
	  for(int r=0; r<mines.length; r++) {
	        for(int col=0; col<mines[r].length; col++) {
	        	num = randGen.nextFloat();
	        	if(num < Config.MINE_PROBABILITY) {
	        		mines[r][col] = true;
	        		numMines++;
	        	}
	        	else {
	        		mines[r][col] = false;
	        	}
	        	}
	 } // end of for loop
      return numMines;
  } // end of placeMines class

  /**
   * This method returns the number of mines in the 8 neighboring locations.
   * For locations along an edge of the array, neighboring locations outside of 
   * the mines array do not contain mines. This method does not print out anything.
   * 
   * If the row or col arguments are outside the mines array, then return -1.
   * This method (or any part of this program) should not use exception handling.
   * 
   * @param mines The array showing where the mines are located.
   * @param row The row, 0-based, of a location.
   * @param col The col, 0-based, of a location.
   * @return The number of mines in the 8 surrounding locations or -1 if row or col
   *      are invalid.
   */
  public static int numNearbyMines(boolean [][]mines, int row, int col) {
	  numNearbyMines = 0;
	  
	if(row < 0 || row > mines.length-1 || col < 0 || col > mines[0].length-1) {
		return -1;
	}
	  
	// below
	if(row+1 <= mines.length-1) {
	if(mines[row+1][col] == true){
		numNearbyMines++;
	}
	}
	
	// above
	if(row-1 >= 0) {
	if(mines[row-1][col] == true) {
		  numNearbyMines++;
	}
	}
	
	// right
	if(col+1 <= mines[0].length-1) {
	if(mines[row][col+1] == true) {
		  numNearbyMines++;
	}
	}
	
	// left
	if(col-1 >= 0) {
	if(mines[row][col-1] == true) {
		  numNearbyMines++;
	}
	}
	
	// below and right
	if(col+1 <= mines[0].length-1 && row+1 <= mines.length-1) {
	if(mines[row+1][col+1] == true) {
		  numNearbyMines++;
	}
	}
	
	// bellow and left
	if(row+1 <= mines.length-1 && col-1 >= 0) {
	if(mines[row+1][col-1] == true) {
		  numNearbyMines++;
	}
	}
	
	// above and left
	if(col-1 >= 0 && row-1 >= 0) {
	if(mines[row-1][col-1] == true) {
		  numNearbyMines++;
	}
	}
	
	// above and right
	if(row-1 >= 0 && col+1 <= mines[0].length-1) {
	if(mines[row-1][col+1] == true) {
		  numNearbyMines++;
	}
	}
	
	return numNearbyMines;
  }

  /**
   * This updates the map with each unswept mine shown with the Config.HIDDEN_MINE
   * character. Swept mines will already be mapped and so should not be changed.
   * This method does not print out anything.
   * 
   * @param map  An array containing the map. On return the map shows unswept mines.
   * @param mines An array indicating which locations have mines.  No changes
   *      are made to the mines array.
   */
  public static void showMines(char[][] map, boolean[][] mines) {
	  for(int r=0; r<map.length; r++) {
	        for(int col=0; col<map[r].length; col++) {
	        	if(mines[r][col] == true) {
	        	map[r][col] = Config.HIDDEN_MINE;
	        	}
	        }
	        }
  }

  /**
   * Returns whether all the safe (non-mine) locations have been swept. In 
   * other words, whether all unswept locations have mines. 
   * This method does not print out anything.
   * 
   * @param map The map showing touched locations that is unchanged by this method.
   * @param mines The mines array that is unchanged by this method.
   * @return whether all non-mine locations are swept.
   */
  public static boolean allSafeLocationsSwept(char[][] map, boolean[][] mines) {
	  boolean test = true;
	  for(int r = 0; r < mines.length; r++) {
	        for(int col = 0; col < mines[r].length; col++) {
	        	if(map[r][col] == Config.UNSWEPT && mines[r][col] == false) {
             	test = false;
	        	}
	        }
	  }
    return test;
  }

  /**
   * This method sweeps the specified row and col.
   *   - If the row and col specify a location outside the map array then 
   *     return -3 without changing the map.
   *   - If the location has already been swept then return -2 without changing
   *     the map.
   *   - If there is a mine in the location then the map for the corresponding
   *     location is updated with Config.SWEPT_MINE and return -1.
   *   - If there is not a mine then the number of nearby mines is determined 
   *     by calling the numNearbyMines method. 
   *        - If there are 1 to 8 nearby mines then the map is updated with 
   *          the characters '1'..'8' indicating the number of nearby mines.
   *        - If the location has 0 nearby mines then the map is updated with
   *          the Config.NO_NEARBY_MINE character.
   *        - Return the number of nearbyMines.
   *        
   * @param map The map showing swept locations.
   * @param mines The array showing where the mines are located.
   * @param row The row, 0-based, of a location.
   * @param col The col, 0-based, of a location.
   * @return the number of nearby mines, -1 if the location is a mine, -2 if 
   * the location has already been swept, -3 if the location is off the map.
   */
  public static int sweepLocation(char[][] map, boolean[][] mines, int row, int col) {
      if(row > mines.length-1 || row < 0 || col > mines[0].length-1 || col < 0) {
    	      return -3;
      }
      else if(map[row][col] != Config.UNSWEPT) {
    	      return -2;
      }
      else if(mines[row][col] == true) {
    	      map[row][col] = Config.SWEPT_MINE;
    	      return -1;
      }
      else {
    	  int nearbyMines = numNearbyMines(mines, row, col);
    	      if(nearbyMines >= 1 && nearbyMines <= 8) {
    	    	  map[row][col] = (char)('0' + nearbyMines);
    	      return nearbyMines;
    	      }
          else {
        	  map[row][col] = Config.NO_NEARBY_MINE;
    	      return nearbyMines;
          }
      }
  }

  /**
   * This method iterates through all 8 neighboring locations and calls sweepLocation
   * for each. It does not call sweepLocation for its own location, just the neighboring
   * locations.
   * @param map The map showing touched locations.
   * @param mines The array showing where the mines are located.
   * @param row The row, 0-based, of a location.
   * @param col The col, 0-based, of a location.
   */
  public static void sweepAllNeighbors(char [][]map, boolean[][] mines, int row, int col) {
	  sweepLocation(map, mines, row+1, col);
      sweepLocation(map, mines, row-1, col);
      sweepLocation(map, mines, row, col+1);
      sweepLocation(map, mines, row, col-1);
      sweepLocation(map, mines, row+1, col+1);
      sweepLocation(map, mines, row-1, col-1);
      sweepLocation(map, mines, row+1, col-1);
      sweepLocation(map, mines, row-1, col+1);
  }
	
}
