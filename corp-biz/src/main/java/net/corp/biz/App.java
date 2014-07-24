package net.corp.biz;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int i = 123;
        int j = 31488;
        int lenD = Integer.toBinaryString(j).length();
        int lenN = Integer.toBinaryString(i).length();
        int leftD = Integer.highestOneBit(j);
        int factor = Integer.toBinaryString(j - leftD).length() - 2;
        leftD = Integer.highestOneBit(j/factor);
        
        if (i > j || j != leftD) {
        	System.out.println("Impossible");
        }
        else {
        	System.out.println(lenD - lenN);
        }
        
    }
}
