/*DLB data structure, this works correctly.
 */



/*
 * @author drbickford1
 */
public class DLB implements DictionaryInterface
{
    //public methods
    protected Node root;
    protected Node currNode;
    protected Node nodeBefore;
    protected int numberOfEntries;
    //DLB constructor creating a new node with null values
    public DLB()
    {
        //creating a new root
        root = new Node(' ', null, null);
    }
    /*
     *add method. takes a string value and adds it to the DLB
    */
    public boolean add(String s) 
    {
        //variables used by this method
        char[] input = s.toCharArray();
        boolean added = false;
        currNode = root;
        Node newNode;
        //if/else statement to check if the node under the root is null. if it
        //is null it will create a new node and set currNode to that node
        if(root.getDownNode() == null)
        {
            //for loop to create new nodes for the first word added
            for(int i=0; i<input.length; i++)
            {
                //creates a new node with the input and sets it to the new node
                newNode = new Node(input[0], null, null);
                currNode.setDownNode(newNode);
                currNode = currNode.getDownNode();
            }
            //creates a new node with a null value and sets it to the bottom
            //of the last node
            newNode = new Node('\0', null, null);
            currNode.setDownNode(newNode);
            currNode = currNode.getDownNode();
            added = true;
        }
        //if it is not the first words added it will drop to here
        else
        {
            //setting currNode to the node under the root
            currNode = currNode.getDownNode();
            //for loop to iterate through the length of the word
            for(int i=0; i<input.length; i++)
            {
                //if/elseif to go through and check certain values to dertermine
                //if it needs to be added or not
                if(currNode.getData() == input[0] && currNode.getDownNode() != null)
                {
                    //setting the currNode to the node underneith
                    currNode = currNode.getDownNode();
                }
                else if(currNode.getData() != input[0] && currNode.getRightNode() != null)
                {
                    //while loop to go through and get the node to the right
                    while(currNode.getData() != input[0] && currNode.getRightNode() != null)
                    {
                        currNode = currNode.getRightNode();
                    }
                    if(currNode.getData() != input[0] && currNode.getRightNode() == null)
                    {
                        //creating a new node with the input and setting 
                        //currNode to that node
                        newNode = new Node(input[0], null, null);
                        numberOfEntries++;
                        currNode.setRightNode(newNode);
                        currNode = currNode.getRightNode();
                        //for loop to iterate through and add a new word
                        for(int j=i+1; j<input.length-i-1; j++)
                        {
                            newNode = new Node(input[j], null, null);
                            currNode.setDownNode(newNode);
                            currNode = currNode.getDownNode();
                            numberOfEntries++;
                        }
                        //creating a null node and setting added to true
                        newNode = new Node('\0', null, null);
                        currNode.setDownNode(newNode);
                        currNode = currNode.getDownNode();
                        added = true;
                        break;
                    }
                    else if(currNode.getData() == input[0])
                    {
                        //getting the down node if the input is equal
                        currNode = currNode.getDownNode();
                    }
                }
                else if((currNode.getData() != input[0] && currNode.getRightNode() == null))
                {
                    //creating a new node with the data at input 0
                    newNode = new Node(input[0], null, null);
                    currNode.setRightNode(newNode);
                    currNode = currNode.getRightNode();
                    numberOfEntries++;
                    //for loop to create a new node for the word
                    for(int j=i+1; j<input.length-i-1; j++)
                    {
                        newNode = new Node(input[j], null, null);
                        currNode.setDownNode(newNode);
                        currNode = currNode.getDownNode();
                        numberOfEntries++;
                    }
                    //creating a new null node for the bottom of the word
                    newNode = new Node('\0', null, null);
                    currNode.setDownNode(newNode);
                    currNode = currNode.getDownNode();
                    added = true;
                    break;
                }
            }
        }
        //returning added
        return added;
    }

    /* search method. Takes a stringbuilder and searches the tree for that word
     */
    public int search(StringBuilder s) 
    {
        //variables used by this method
        String searchString = s.toString();
        String newString = searchString.toLowerCase();
        int type = 0;
        char[] searchArray = newString.toCharArray();
        currNode = root;
        currNode = currNode.getDownNode();
        
        //for loop to iterate through the word and check if it's in the DLB
        for(int i=0; i<searchArray.length; i++)
        {
            //if/elseif to check for certain values
            if(currNode.getData() == searchArray[i] && currNode.getDownNode().getData() != '\0' && i < searchArray.length)
            {
                //getting the down node
                currNode = currNode.getDownNode();
                type = 1;
            }
            else if(currNode.getData() == searchArray[i] && currNode.getDownNode().getData() == '\0' && i < searchArray.length)
            {
                //setting type to 2 and breaking the loop
                type = 2;
                break;
            }
            else if(currNode.getData() != searchArray[i] && currNode.getData() != '\0')
            {
                //while loop to iterate over the searchArray and get the next 
                //node if it isn't the right character
                while(currNode.getData() != searchArray[i])
                {
                    if(currNode.getRightNode() == null && currNode.getData() != searchArray[i])
                    {
                        //if it in't found it breaks the loop
                        type = 0;
                        break;
                    }
                    else
                    {
                        //getting the right node
                        currNode = currNode.getRightNode();
                        type = 1;
                    }
                }
                if(currNode.getDownNode() != null)
                {
                    //sets to the down node
                    currNode = currNode.getDownNode();
                }
                else
                {
                    //setting type to 1 and breaking the loop
                    type = 1;
                    break;
                }
            }
            else if(currNode.getData() == searchArray[i] && currNode.getDownNode().getData() != '\0' && i < searchArray.length-1)
            {
                //setting type to 3 and breaking the loop
                type = 3;
                break;
            }
        }
        //returnign type
        return type;
    }
    
    
    /*
     *Node class. creates new nodes with given data and set values.
    */
    protected class Node
    {
      //global values
      protected char data; // entry in bag
      protected Node down; // link to next node
      protected Node right;
      //Node constructor.
      protected Node(char dataPortion)
      {
         this(dataPortion, null, null);
      } // end constructor
      //Node constructor setting the data and nodes to valid areas
      protected Node(char dataPortion, Node downNode, Node rightNode)
      {
         data = dataPortion;
         down = downNode;
         right = rightNode;
      } // end constructor
      //returns the data that the node holds
      protected char getData()
      {
         return data;
      } // end getData
      //setting the data the node holds
      protected void setData(char newData)
      {
         data = newData;
      } // end setData
      //returning the right node
      protected Node getRightNode()
      {
         return right;
      } // end getNextNode
      //returning the down node
      protected Node getDownNode()
      {
         return down;
      }
      //setting the right node
      protected void setRightNode(Node nextRightNode)
      {
         right = nextRightNode;
      } // end setNextNode
      //setting the down node
      protected void setDownNode(Node nextDownNode)
      {
         down = nextDownNode;
      }
   } // end Node
    
}
