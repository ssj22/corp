package net.corp.core;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    
	 public static void main(String[] args) {
         List<Integer> list = new ArrayList<Integer>();
         list.add(0);list.add(1);list.add(4);list.add(3);list.add(2);

         Iterator<Integer> itr = list.iterator();

         for (int i = 0; i < 6; i++) {
             System.out.println("i = " + i);
             //System.out.println("itr = " + itr.hasNext());
             while (itr.hasNext()) {
                 int val = itr.next();
                 System.out.println("itr.next = " + val);
                 if (i == val) {
                     System.out.println(" Found it ");
                 }
             }
         }

	    }
	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	String str = "Hello/World";
        String[] arr = str.split("/");
        System.out.println(arr[arr.length-1]);
    	assertTrue( true );
    }
}
