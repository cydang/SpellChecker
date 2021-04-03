package cs146F20.dang.project4;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

/**
 * tests the Red Black Tree and WordCheker class
 * @author chloedang
 *
 */
public class RBTTester {
	
	File dictionary;
	File poem;
	WordChecker checker;

	@Test
    //Test the Red Black Tree
	public void test() {
		RedBlackTree<String> rbt = new RedBlackTree<String>();
        rbt.insert("D");
        rbt.insert("B");
        rbt.insert("A");
        rbt.insert("C");
        rbt.insert("F");
        rbt.insert("E");
        rbt.insert("H");
        rbt.insert("G");
        rbt.insert("I");
        rbt.insert("J");
        System.out.print("Test 1 Tree: ");  
		rbt.printTree();
        assertEquals("DBACFEHGIJ", makeString(rbt));
        String str=     "Color: 1, Key:D Parent: \n"+
                        "Color: 1, Key:B Parent: D\n"+
                        "Color: 1, Key:A Parent: B\n"+
                        "Color: 1, Key:C Parent: B\n"+
                        "Color: 1, Key:F Parent: D\n"+
                        "Color: 1, Key:E Parent: F\n"+
                        "Color: 0, Key:H Parent: F\n"+
                        "Color: 1, Key:G Parent: H\n"+
                        "Color: 1, Key:I Parent: H\n"+
                        "Color: 0, Key:J Parent: I\n";
		assertEquals(str, makeStringDetails(rbt));
            
    }
    
	@Test
	public void test2() {
		RedBlackTree<String> rbt = new RedBlackTree<String>();
		rbt.insert("Carat");
		rbt.insert("Wizard");
		rbt.insert("Melody");
		rbt.insert("Orbit");
		rbt.insert("Starlight");
		rbt.insert("Army");
		rbt.insert("Panda");
		rbt.insert("Shawol");
		rbt.insert("Fantasy");
		System.out.print("\nTest 2 Tree: ");  
		rbt.printTree();
		assertEquals("MelodyCaratArmyFantasyStarlightPandaOrbitShawolWizard", 
				makeString(rbt));
		String str=     "Color: 1, Key:Melody Parent: \n"+
						"Color: 1, Key:Carat Parent: Melody\n"+
						"Color: 0, Key:Army Parent: Carat\n"+
						"Color: 0, Key:Fantasy Parent: Carat\n"+
						"Color: 0, Key:Starlight Parent: Melody\n"+
						"Color: 1, Key:Panda Parent: Starlight\n"+
						"Color: 0, Key:Orbit Parent: Panda\n"+
						"Color: 0, Key:Shawol Parent: Panda\n"+
						"Color: 1, Key:Wizard Parent: Starlight\n";
		assertEquals(str, makeStringDetails(rbt));
	}

	/**
	 * tests the word checker
	 */
	@Test
	public void testWordChecker() {
		dictionary = new File("src/dictionary.txt");
		poem = new File("src/poem.txt");
		double start = System.currentTimeMillis();
		WordChecker checker = new WordChecker(dictionary, poem);
		double end =  System.currentTimeMillis();
		
		double time = end - start;
		System.out.println("\nWord Checker Test:");
		System.out.println("Word Checker took: " + time + "ms");
		System.out.println("Number of words not in dictionary: " + checker. getwordsNotInDictionaryCount());
		assertEquals(91, WordChecker. getwordsNotInDictionaryCount());
		
	}
    
	/**
	 * prints the string
	 * @param t
	 * @return
	 */
    public static String makeString(RedBlackTree t)
    {
       class MyVisitor implements RedBlackTree.Visitor {
          String result = "";
          public void visit(RedBlackTree.Node n)
          {
             result = result + n.key;
          }
       };
       MyVisitor v = new MyVisitor();
       t.preOrderVisit(v);
       return v.result;
    }

    /**
     * prints the string details
     * @param t
     * @return
     */
    public static String makeStringDetails(RedBlackTree t) {
    	{
    	       class MyVisitor implements RedBlackTree.Visitor {
    	          String result = "";
    	          public void visit(RedBlackTree.Node n)
    	          {
    	        	  if(!(n.key).equals(""))
    	        	  	result = result + "Color: " + (n.isRed ? "0" : "1") +", Key:" + n.key + " Parent: " + (n.parent != null ? n.parent.key : "") + "\n";
    	          }
    	       };
    	       MyVisitor v = new MyVisitor();
    	       t.preOrderVisit(v);
    	       return v.result;
    	 }
    }
    
 }
