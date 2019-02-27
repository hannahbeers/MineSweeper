/**
 * This file contains testing methods for the MineSweeper project.
 * These methods are intended to serve several objectives:
 * 1) provide an example of a way to incrementally test your code
 * 2) provide example method calls for the MineSweeper methods
 * 3) provide examples of creating, accessing and modifying arrays
 *    
 * Toward these objectives, the expectation is that part of the 
 * grade for the MineSweeper project is to write some tests and
 * write header comments summarizing the tests that have been written. 
 * Specific places are noted with FIXME but add any other comments 
 * you feel would be useful.
 * 
 * Some of the provided comments within this file explain
 * Java code as they are intended to help you learn Java.  However,
 * your comments and comments in professional code, should
 * summarize the purpose of the code, not explain the meaning
 * of the specific Java constructs.
 *    
 */

import java.util.Random;
import java.util.Scanner;


/**
 * This class contains a few methods for testing methods in the MineSweeper
 * class as they are developed. These methods are all private as they are only
 * intended for use within this class.
 * 
 * @author Jim Williams
 * @author Hannah Beers
 *
 */
public class TestMineSweeper {

    /**
     * This is the main method that runs the various tests. Uncomment the tests
     * when you are ready for them to run.
     * 
     * @param args  (unused)
     */
    public static void main(String [] args) {

        // Milestone 1
        //testing the main loop, promptUser and simplePrintMap, since they have
        //a variety of output, is probably easiest using a tool such as diffchecker.com
        //and comparing to the examples provided.
        //testEraseMap();
        
        // Milestone 2
        //testPlaceMines();
        //testNumNearbyMines(); 
        //testShowMines(); 
        //testAllSafeLocationsSwept(); 
        
        // Milestone 3
        testSweepLocation();
        //testSweepAllNeighbors();
        //testing printMap, due to printed output is probably easiest using a 
        //tool such as diffchecker.com and comparing to the examples provided.
    }
    
    /**
     * This is intended to run some tests on the eraseMap method. 
     * Checks if all characters in the array are the Config.UNSWEPT character.
     * 
     * @param answer - boolean that is true if test passed, false if not
     * @param map - example array to check if test works
     */
    private static void testEraseMap() {
        //Review the eraseMap method header carefully and write
        //tests to check whether the method is working correctly.
    	boolean answer = false;
    	char[][] map = {{'*','4','@'},{'.','.','k'}};
    	MineSweeper.eraseMap(map);
    	for(int i=0; i<map.length; i++) {
	        for(int j=0; j<map[i].length; j++) {
	            if (map[i][j] == Config.UNSWEPT) {
	            	answer = true;
	            }
	        }
	    }
    	if(answer == false) {
    		System.out.print("testEraseMap: failed");
    	}
    	else {
    		System.out.print("testEraseMap: passed");
    	}
    }      
    
    /*
     * Calculate the number of elements in array1 with different values from 
     * those in array2
     */
    private static int getDiffCells(boolean[][] array1, boolean[][] array2) {
        int counter = 0;
        for(int i = 0 ; i < array1.length; i++){
            for(int j = 0 ; j < array1[i].length; j++){
                if(array1[i][j] != array2[i][j]) 
                    counter++;
            }
        }
        return counter;
    }    
    
    /**
     * This runs some tests on the placeMines method. 
     * Tests if example array of boolean mines expectedMap matches the array 
     * the placeMines method produces.
     */
    private static void testPlaceMines() {
        boolean error = false;
        
        //These expected values were generated with a Random instance set with
        //seed of 123 and MINE_PROBABILITY is 0.2.
        boolean [][] expectedMap = new boolean[][]{
            {false,false,false,false,false},
            {false,false,false,false,false},
            {false,false,false,true,true},
            {false,false,false,false,false},
            {false,false,true,false,false}};
        int expectedNumMines = 3;
            
        Random studentRandGen = new Random( 123);
        boolean [][] studentMap = new boolean[5][5];
        int studentNumMines = MineSweeper.placeMines( studentMap, studentRandGen);
        
        if ( studentNumMines != expectedNumMines) {
            error = true;
            System.out.println("testPlaceMines 1: expectedNumMines=" + expectedNumMines + " studentNumMines=" + studentNumMines);
        }
        int diffCells = getDiffCells( expectedMap, studentMap);
        if ( diffCells != 0) {
            error = true;
            System.out.println("testPlaceMines 2: mine map differs.");
        }

        if (error) {
            System.out.println("testPlaceMines: failed");
        } else {
            System.out.println("testPlaceMines: passed");
        }        
        
    }
    
    /**
     * This runs some tests on the numNearbyMines method. 
     * Using the mines array, this method iterates through each element in the array to
     * see if it matches the corresponding array passed into the numNearbyMines method.
     */
    private static void testNumNearbyMines() {
        boolean error = false;

        boolean [][] mines = new boolean[][]{
            {false,false,true,false,false},
            {false,false,false,false,false},
            {false,true,false,true,true},
            {false,false,false,false,false},
            {false,false,true,false,false}};
        int numNearbyMines = MineSweeper.numNearbyMines( mines, 1, 1);
        
        if ( numNearbyMines != 2) {
            error = true;
            System.out.println("testNumNearbyMines 1: numNearbyMines=" + numNearbyMines + " expected mines=2");
        }
        
       numNearbyMines = MineSweeper.numNearbyMines( mines, 2, 1);
        
        if ( numNearbyMines != 0) {
            error = true;
            System.out.println("testNumNearbyMines 2: numNearbyMines=" + numNearbyMines + " expected mines=0");
        }        

        if (error) {
            System.out.println("testNumNearbyMines: failed");
        } else {
            System.out.println("testNumNearbyMines: passed");
        }
    }
    
    /**
     * This runs some tests on the showMines method. 
     * Uses the mines boolean array to check if a mine is counted when the
     * element is true and not counted when it is false in the showMines method.
     */
    
    private static void testShowMines() {
        boolean error = false;
        

        boolean [][] mines = new boolean[][]{
            {false,false,true,false,false},
            {false,false,false,false,false},
            {false,true,false,false,false},
            {false,false,false,false,false},
            {false,false,true,false,false}};
            
        char [][] map = new char[mines.length][mines[0].length];
        map[0][2] = Config.UNSWEPT;
        map[2][1] = Config.UNSWEPT;
        map[4][2] = Config.UNSWEPT;
        
        MineSweeper.showMines( map, mines);
        if ( !(map[0][2] == Config.HIDDEN_MINE && map[2][1] == Config.HIDDEN_MINE && map[4][2] == Config.HIDDEN_MINE)) {
            error = true;
            System.out.println("testShowMines 1: a mine not mapped");
        }
        if ( map[0][0] == Config.HIDDEN_MINE || map[0][4] == Config.HIDDEN_MINE || map[4][4] == Config.HIDDEN_MINE) {
            error = true;
            System.out.println("testShowMines 2: unexpected showing of mine.");
        }
        
        // Can you think of other tests that would make sure your method works correctly?
        // if so, add them.

        if (error) {
            System.out.println("testShowMines: failed");
        } else {
            System.out.println("testShowMines: passed");
        }        
    }    
    
    /**
     * This is intended to run some tests on the allSafeLocationsSwept method.
     * Detects if all remaining UNSWEPT characters are true, meaning
     * a mine is at that spot.
     * 
     * @param answer - boolean that is true if test passed, false if not
     * @param map - sample boolean map used to test if method works
     */
    private static void testAllSafeLocationsSwept() {
        //Review the allSafeLocationsSwept method header carefully and write
        //tests to check whether the method is working correctly.
     boolean answer = true;
    	 boolean [][] mines = new boolean[][]{
             {true,false,false},
             {false,true,false},
             {false,false,true}};
    	 
    	 char [][] map = new char[][]{
             {'.','-','-'},
             {'-','.','-'},
             {'-','-','.'}};
             
     MineSweeper.allSafeLocationsSwept(map, mines);
     
     for(int i=0; i<map.length; i++) {
    	      for(int j=0; j<map[i].length; j++) {
    	           if ((map[i][j] == Config.UNSWEPT && mines[i][j] == false) || ((map[i][j] != Config.UNSWEPT && mines[i][j] == true))) {
    	        	   answer = false;
    	            }
    	        }
    	    }
     if(answer == true) {
        System.out.println("testAllSafeLocationsSwept: passed");
     }
     else {
         System.out.println("testAllSafeLocationsSwept: failed");
     }
    }      

    
    /**
     * This is intended to run some tests on the sweepLocation method. 
     * Tests different possible return statements within sweepLocation
     * method and if all tests pass, the method works.
     * 
     * @param test1 - checks if return statement is -3
     * @param test2 - checks if return statement is -2
     * @param test3 - checks if return statement is -1
     */
    private static void testSweepLocation() {
        //Review the sweepLocation method header carefully and write
        //tests to check whether the method is working correctly.
    	boolean test1 = false;
    	boolean test2 = false;
    	boolean test3 = false;
       	
    	boolean [][] mines = new boolean[][]{
            {true,false,false},
            {false,true,false},
            {false,false,true}};
   	 
   	 char [][] map = new char[][]{
            {'.','.','.'},
            {'.',' ','.'},
            {'.','.','.'}};
            
    	if(MineSweeper.sweepLocation(map, mines, 10, 10) == -3) {
    		test1 = true;
    	}
    if(MineSweeper.sweepLocation(map, mines, 1, 1) == -2) {
     	test2 = true;
    }
    if(MineSweeper.sweepLocation(map, mines, 2, 2) == -1) {
    	    test3 = true;
    }
    	
    if(test1 == true && test2 == true && test3 == true) {
        System.out.println("testSweepAllNeighbors: passed");
    }
    else{
        System.out.println("testSweepAllNeighbors: failed");
    }
    }      
    
    /**
     * This is intended to run some tests on the sweepAllNeighbors method. 
     * Checks if all 8 surrounding spots in the array map are checked
     * for a mine.
     * 
     * @param answer - boolean that is true if test passed, false if not
     * @param temp - int used to store sweepAllNeighbors value
     */
    private static void testSweepAllNeighbors() {
        //Review the sweepAllNeighbors method header carefully and write
        //tests to check whether the method is working correctly.
    	boolean answer = false;
    	
   	 boolean [][] mines = new boolean[][]{
         {true,false,false},
         {false,true,false},
         {false,false,true}};
	 
	 char [][] map = new char[][]{
         {'.','.','.'},
         {'.','.','.'},
         {'.','.','.'}};
    
    MineSweeper.sweepAllNeighbors(map, mines, 2, 3);
    	if(MineSweeper.numNearbyMines == 2) {
    		answer = true;
    	}
    	if(answer == true) {
        System.out.println("testSweepAllNeighbors: passed");
    	}
    	else {
        System.out.println("testSweepAllNeighbors: failed");
    	}
    }      
}

