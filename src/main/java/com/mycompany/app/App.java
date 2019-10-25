package com.mycompany.app;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        //requirement the collection needs to be sorted
        int [] arr1 = new int [] {11, 22, 33, 44, 55, 66, 77};
        System.out.println("Start to serach");
        int result=BinarySearch.search(arr1, 191);
        if (result != -1) {
            System.out.println("Item found at index:"+result);
        }
        else System.out.println("Item not found");
        
        
    }
}
