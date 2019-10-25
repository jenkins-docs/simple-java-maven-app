package com.mycompany.app;

public class BinarySearch {
    public static int search( int [] collection ,int value) {
        int size= collection.length;
        int lower=0;
        int upper = size - 1;
        int middle = 0;
        
        while (lower <= upper ) {
            middle = ( lower + upper ) / 2;
            System.out.println("Middle="+middle);
            if ( collection[middle] > value ){
                  upper = middle - 1;
            } else if ( collection [middle]  < value ){
                 lower=middle + 1;
            } else // we found the data
                 return middle;
            
        }
        
        return - 1;
      }
    }