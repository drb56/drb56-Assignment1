/*CS1501 programming assignment 1
    This program does not work. I'm having trouble with the recursive search
        of the dictionaryDLB.
Author: David Bickford
Email: DRB56@pitt.edu
 */

//necessary imports
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author David Bickford
 */
public class MyBoggle
{
    //various global variables
    public static char[][] boggleBoard = new char[4][4];
    public static String[] foundWordsArray = new String[30];
    public static StringBuilder foundWords = new StringBuilder("");
    public static int numberOfWords;
    
    //main method
    public static void main(String[] args) throws IOException
    {
        //main method variables
        char[] board;
        String[] userWords = new String[30];
        String words = "";
	String inputString;
        String treeImplementation = "DLB";
        String boardNumber = "board1.txt";
	StringBuilder sb = new StringBuilder("");
	//for loop to check if the string args are something else
        for(int i=0; i<args.length;i++)
        {
            if(args[i].equals("-b"))
            {
                boardNumber = args[i+1];
            }
            else if(args[i].equals("-d"))
            {
                treeImplementation = args[i+1];
            }
        }
        //setting a new scanner to dictionary and the board input
        Scanner dictionaryScan = new Scanner(new FileInputStream("dictionary.txt"));
        Scanner boardScan = new Scanner(new FileInputStream(boardNumber));
        //creating a new DLB object called dictionary which holds the dictionary
        DLB dictionary = new DLB();
        //while loop to go through the dictionary text file and set it to the 
        //dictionary DLB object
	while (dictionaryScan.hasNext())
	{
            inputString = dictionaryScan.nextLine();
            dictionary.add(inputString);
	}
        //setting the inputString to the board text file
        inputString = boardScan.nextLine();
        board = inputString.toCharArray();
        //calling the addBoard method which populates the char array
        addBoard(board);
        String stringToPass = "";
        //calling the boardCheck method which checks the board for valid words
        boardCheck(dictionary, stringToPass);
        words = foundWords.toString();
        System.out.println("There were " + numberOfWords + " total words:\n\n" + foundWords.toString());
        
        
    }
    /*
     *addBoard method. Populates the boggleBoard array with inputted chars
     *@param: charToAdd - an array which holds all the characters to add
     *@returns: void
    */
    public static void addBoard(char[] charToAdd)
    {
        //populating all the board positions
        boggleBoard[0][0] = charToAdd[0];
        boggleBoard[0][1] = charToAdd[1];
        boggleBoard[0][2] = charToAdd[2];
        boggleBoard[0][3] = charToAdd[3];
        boggleBoard[1][0] = charToAdd[4];
        boggleBoard[1][1] = charToAdd[5];
        boggleBoard[1][2] = charToAdd[6];
        boggleBoard[1][3] = charToAdd[7];
        boggleBoard[2][0] = charToAdd[8];
        boggleBoard[2][1] = charToAdd[9];
        boggleBoard[2][2] = charToAdd[10];
        boggleBoard[2][3] = charToAdd[11];
        boggleBoard[3][0] = charToAdd[12];
        boggleBoard[3][1] = charToAdd[13];
        boggleBoard[3][2] = charToAdd[14];
        boggleBoard[3][3] = charToAdd[15];
        //printing out the boggle board characters
        System.out.println(boggleBoard[0][0] + " " + boggleBoard[0][1] + " " + 
                boggleBoard[0][2] + " " + boggleBoard[0][3]);
        System.out.println(boggleBoard[1][0] + " " + boggleBoard[1][1] + " " + 
                boggleBoard[1][2] + " " + boggleBoard[1][3]);
        System.out.println(boggleBoard[3][1] + " " + boggleBoard[3][2] + " " + 
                boggleBoard[2][0] + " " + boggleBoard[2][1]);
        System.out.println(boggleBoard[3][0] + " " + boggleBoard[3][1] + " " + 
                boggleBoard[3][2] + " " + boggleBoard[3][3]);
    }
    
    /*
     *boardCheck method. Takes the dictionaryDLB and an empty string to pass
     *    to the boardCheck method which is a recursive method.
     *@param: dictionaryDLB and stringToPass
     *@returns: void
    */
    public static void boardCheck(DLB dictionaryDLB, String stringToPass)
    {
        //nested for loop to call the boardCheck recursive method
        for (int row = 0; row < boggleBoard.length; row++) {
            for (int col = 0; col < boggleBoard.length; col++) {
                boardCheck(row, col, 0, 0, dictionaryDLB, stringToPass);
            }
        }
        
    }
    
    /*
     *boardCheck method. A recursive method that takes 6 variables and checks
     *      the dictionaryDLB if it is a valid word.
     *@param: posI, posJ, oldI, oldJ, dictionaryDLB, stringToPass
     *@returns: void
    */
    public static void boardCheck(int posI, int posJ, int oldI, int oldJ,
                                DLB dictionaryDLB, String stringToPass)
    {
        int check;
        //if statement to check if the given positions are within the bounds
        //of the board.
        if(!(posI < 0 || posI >= boggleBoard.length || 
                     posJ < 0 || posJ >= boggleBoard.length))
        {
            //setting a variable to the position at given posI and posJ
            char atPosition = boggleBoard[posI][posJ];
            stringToPass = stringToPass + atPosition;
            //creating a new stringbuilder with the contents of stringToPass
            StringBuilder sb = new StringBuilder(stringToPass);
            //searching the dictionaryDLB for the word
            check = dictionaryDLB.search(sb);
            //if/elseif statemtent to check the returned value from searching
            //the dictionaryDLB
            if(check == 1)
            {
                //recursively searching boardCheck
                boardCheck(posI, posJ+1, posI, posJ, dictionaryDLB, stringToPass);
                boardCheck(posI+1, posJ, posI, posJ, dictionaryDLB, stringToPass);
                boardCheck(posI, posJ-1, posI, posJ, dictionaryDLB, stringToPass);
                boardCheck(posI-1, posJ, posI, posJ, dictionaryDLB, stringToPass);
            }
            else if(check == 2)
            {
                //adding the found word to a foundWords stringbuilder
                String newWord = sb.toString();
                foundWords.append(newWord);
                foundWords.append("\n");
                numberOfWords++;
            }
            else if(check == 3)
            {
                //adding the found word to the foundWords stringbuilder
                String newWord = sb.toString();
                foundWords.append(newWord);
                foundWords.append("\n");
                numberOfWords++;
                //recursively searching boardCheck
                boardCheck(posI, posJ+1, posI, posJ, dictionaryDLB, stringToPass);
                boardCheck(posI+1, posJ, posI, posJ, dictionaryDLB, stringToPass);
                boardCheck(posI, posJ-1, posI, posJ, dictionaryDLB, stringToPass);
                boardCheck(posI-1, posJ, posI, posJ, dictionaryDLB, stringToPass);
            }
        }
    }
}

