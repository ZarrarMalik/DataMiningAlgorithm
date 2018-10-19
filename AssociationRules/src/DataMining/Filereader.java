package DataMining;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class Filereader {
	
	 Filereader() {
	
	}
	

	    public static void main(String args[]) throws FileNotFoundException,
	                                IOException {
	    	
	    	
	    int maxSubset = 5;
	    int randomPercent = 80;
	    // int minsup = 8;
	    double new_minsup = 0.01;
	
FileReader fr = new FileReader("C://zarrar/database.dat"); 

BufferedWriter writer = new BufferedWriter(new FileWriter("C://zarrar/sampleresult.dat"));
//writer.write(str);
//FileOutputStream fos = new FileOutputStream("C://zarrar/sampleresult.dat");
//PrintStream printStream = new PrintStream(fos);
//printStream.print("String");
//printStream.close();
BufferedReader bufRead = new BufferedReader(fr);
String line = bufRead.readLine();
ArrayList<String> dbLines = new ArrayList<String>();
HashMap <String, String> ranLines = new HashMap <String, String>();
HashMap <String, String> singleItem = new HashMap <String, String>();
Filereader rSample = new Filereader();



while(line != null)
{
 dbLines.add(line);
 line = bufRead.readLine();
 
} //end of while loop


Random r = new Random();
int lineRan = (dbLines.size() * randomPercent) / 100;
if (lineRan == 0) {lineRan =1;}
lineRan = dbLines.size() - lineRan;
ArrayList<Integer> lIndex = new ArrayList<Integer>();
//     System.out.println(lineRan + " left items");
  for (int k = 0; k < lineRan; k++){
         
         int randomInt = r.nextInt(dbLines.size());

         lIndex.add(randomInt);
         
  }
         Object [] sortVec = lIndex.toArray();
         // Arrays.sort(sortVec);
        
         for (int j =0; j < sortVec.length; j++) {
         
             System.out.print(" Value@index(" + j + ")is=" + sortVec[j]+ "--");
         } 
         
         
         ArrayList rslt = rSample.pickSameIndexes(sortVec, sortVec);
         System.out.println("");
         System.out.println(rslt + " problem same indexes");
         Object [] aR = rslt.toArray();
         
         ArrayList test = rSample.randomLines(dbLines, randomPercent, rslt.size());
         Object [] testVal = test.toArray();
         Arrays.sort(testVal);
         System.out.println(test  + " New Generated Indexes ");
         
         /*
          * Matching the indexes again  
          */
         rslt = rSample.pickSameIndexes(sortVec, testVal);
         
         if (rslt.size() == 0) {
        	 rslt = rSample.pickSameIndexes(testVal, testVal);
         }
          
         HashMap<String,Integer> tt = new HashMap<String, Integer>();
         
         	for (int k = 0; k < lIndex.size(); k++) {
        	
         		tt.put(lIndex.get(k).toString(), Integer.parseInt(lIndex.get(k).toString()));
        	 
         	}
         
while (rslt.size()!=0) {
         		
         		test = rSample.randomLines(dbLines, randomPercent, rslt.size());
                testVal = test.toArray();
                Arrays.sort(testVal);
                System.out.println(test  + " Again creating indexes to add ");
                rslt = rSample.pickSameIndexes(sortVec, testVal);
         		
         	}
         	
for (int u = 0; u < testVal.length; u++) {
		
		lIndex.add(Integer.parseInt(testVal[u].toString()));
		tt.put(testVal[u].toString(), Integer.parseInt(testVal[u].toString()));
	}
	
	System.out.println(tt);
	
	lIndex = new ArrayList<Integer>();
	
	Set indexKeys = tt.keySet();
	Object [] indexKeysVal = indexKeys.toArray();
	
	for (int j =0; j < indexKeysVal.length; j++) {
		
		lIndex.add(Integer.parseInt(indexKeysVal[j].toString()));
		
	}
	   
	for (int rInt = 0 ; rInt < lIndex.size(); rInt++ ) {
	      
        
        
        ranLines.put((String)dbLines.get(rInt),
                       "Random");
         String [] items = ((String)dbLines.get(rInt)).split(" ");


                  for (int j = 0 ; j < items.length; j++) {
                     
                    singleItem.put(items[j], "0");
                  }
        } //end of index for
	
	 // System.out.println(dbLines.size());
    //System.out.println(ranLines);
    //System.out.println(singleItem);
    //fileOutputRandom.writer.newLine();
    //fileOutputRandom.writeString("Printing all Individual Elements picked from Random Lines");
    //fileOutputRandom.writer.newLine();
    //fileOutputRandom.writeString(" " + singleItem + " ");
    //fileOutputRandom.flush();
     //// single items
    Set singleKey = singleItem.keySet();
    Object [] singleKeyValue = singleKey.toArray();
    Arrays.sort(singleKeyValue);

    ///random database lines 
    Set ranKey = ranLines.keySet();
    Object [] ranKeyValue = ranKey.toArray();
    Arrays.sort(ranKeyValue);
	
    HashMap <String, ArrayList> individualElem = new HashMap<String, ArrayList>();
    for (int i = 0; i < singleKeyValue.length; i++) {
        ArrayList indexes = new ArrayList();

   for (int s = 0; s < ranKeyValue.length; s++) {

	String [] aSingleDBLine = ((String)ranKeyValue[s]).split(" ");
    //   System.out.println(ranKeyValue[s]);
	String [] setItem = {(String)singleKeyValue[i]};
     //  System.out.println(setItem[0]);
	boolean exists = rSample.matchTwo(setItem, aSingleDBLine);

		if (exists) {
                 ///  System.out.println("It Matched");
			indexes.add(s + 1);

		}

		if (s == ranKeyValue.length - 1) {
                   
                   double minsupPercent = (double)indexes.size() / (double)dbLines.size();
                   //&& indexes.size() >= minsup) {
			//System.out.println (indexes);
                   if (minsupPercent >= new_minsup) {
			individualElem.put(setItem[0], indexes);
			indexes = new ArrayList();
			setItem = new String[1];
		  }
               }

	} //end of inner for
} //end of outer for
	
    //System.out.println(individualElem);
    //fileOutputRandom.writer.newLine();
    //fileOutputRandom.writeString(" All items matches are displayed here. ");
    //fileOutputRandom.writer.newLine();
    //fileOutputRandom.writeString(" " + individualElem + " ");
    //fileOutputRandom.flush();
    
    
    
    /**
     * Creating F2 subsets
     */
	
    Set f1Item = individualElem.keySet();
    Object [] f1ItemValue = f1Item.toArray();
    Arrays.sort(f1ItemValue);

    HashMap <String, ArrayList> f2Subset = new HashMap <String, ArrayList>();

    for (int j = 0; j < f1ItemValue.length; j++) {

 	   for (int k = 1; k < f1ItemValue.length; k++) {

 	   if (j < k){
 	   		ArrayList ItemIndexA = individualElem.get(f1ItemValue[j]);
	   		ArrayList ItemIndexB = individualElem.get(f1ItemValue[k]);
   			        //System.out.println(f1ItemValue[j] + " " + f1ItemValue[k] );
	   				//System.out.println(ItemIndexA);
	   				//System.out.println(ItemIndexB);
	   		        String f2Key = f1ItemValue[j] + " " + f1ItemValue[k];

   			        ArrayList sameIndexes = rSample.compareTwoIndexes(ItemIndexA, ItemIndexB);
                                double f2Percent = (double)sameIndexes.size() / (double) dbLines.size();
                                
   			        //if (sameIndexes.size() >= minsup) {
                                if (f2Percent >= new_minsup) {
   			        	individualElem.put(f2Key, sameIndexes);
   			        	f2Subset.put(f2Key, sameIndexes);
   			        }
   			        //System.out.println(sameIndexes);
   				}
        } //inner for

		} // outer for
  //System.out.println(individualElementDBIndexes);
    //System.out.println(f2Subset);
    // fileOutputRandom.writer.newLine();
    // fileOutputRandom.writeString(" Printing F2 Subsets ");
    // fileOutputRandom.writer.newLine();
    // fileOutputRandom.writeString(" " + f2Subset + " ");
    // fileOutputRandom.flush();

    Set f2Keys = f2Subset.keySet();
    Object [] f2SetValues = f2Keys.toArray();
    Arrays.sort(f2SetValues);
    while (f2SetValues.length != 0) {

        f2Subset = rSample.makeNSizeSubsets(f2SetValues, f2Subset,
                                 rSample, new_minsup, maxSubset, dbLines.size());

        f2Keys = f2Subset.keySet();
        f2SetValues = f2Keys.toArray();
        Arrays.sort(f2SetValues);

        for (int u = 0; u < f2SetValues.length; u++) {
        individualElem.put((String)f2SetValues[u], f2Subset.get(f2SetValues[u]));
        		}
        //System.out.println(f2Subset);

        }
    // System.out.println(individualElementDBIndexes);
    
    Set allKeys = individualElem.keySet();
    Object [] allKeysValue = allKeys.toArray();
    Arrays.sort(allKeysValue);
    //fileOutputRandom.writer.newLine();
   // fileOutputRandom.writeString("All items and their indexes......");
   //  fileOutputRandom.writer.newLine();
    for (int d = 0; d < allKeysValue.length; d++){

    	//PrintStream prtStream = new PrintStream(fos);
    	//printStream.print("String");
    	//printStream.close();
        writer.write(allKeysValue[d] + " = " +
                individualElem.get((String)allKeysValue[d]));
        writer.newLine();


    } //end of printing
      System.out.println(individualElem);
      writer.flush();
    System.out.println(" FINSIHED RANDOM SAMPLE ");








     /////////// end of main running

    
    
    
    
         	
         }


	    
	

	    public ArrayList pickSameIndexes (Object [] index, Object [] index1) {
		    ArrayList result = new ArrayList();
		    for (int k = 0; k < index.length; k++) {
		   
		          for (int l = 1; l < index1.length; l++) {
		          
		              if (index[k].equals(index1[l]) && k < l) {
		              
		                  result.add(l);
		              }
		          }
		    
		    }
		    
		    return result;
		    
		}
	    
	    
	    public HashMap makeNSizeSubsets (Object[] f2SetValues, HashMap <String, ArrayList>f2Subset,
	    		Filereader rSample, double new_minsup, int maxLength, int dbSize) {

HashMap <String, ArrayList> kSizeSubset = new HashMap<String, ArrayList>();

for (int h = 0; h < f2SetValues.length; h++) {



// System.out.println(f2SetValues[h]);
String [] firstSubset = ((String)f2SetValues[h]).split(" ");
String greatest = firstSubset[firstSubset.length -1];
int insideIndex = h +1;
for (int g = insideIndex; g < f2SetValues.length; g++) {
// System.out.println(f2SetValues[g] + " inner loop ");
String [] secondSubset = ((String)f2SetValues[g]).split(" ");
String lastItem = secondSubset[secondSubset.length-1];

//System.out.print(" COMPARING " + lastItem + " WITH " + greatest + " == ");
//System.out.println(lastItem.compareTo(greatest) + " VALUE RECIEVED");
if (lastItem.compareTo(greatest) >= 1) {
  //System.out.println(" lastItem " + lastItem);
     String newSub = f2SetValues[h] + " " +lastItem;
     //System.out.println(newSub);
    // System.out.println(" Comparing " + f2Subset.get(f2SetValues[h]) + " WITH " + f2Subset.get(f2SetValues[g]));
               String [] maxSub = newSub.split(" ");
               if (maxSub.length <= maxLength) {
     ArrayList matchIndex = rSample.compareTwoIndexes(f2Subset.get(f2SetValues[h]), f2Subset.get(f2SetValues[g]));
    double newMinsip = (double)matchIndex.size() / dbSize;
                 //if (matchIndex.size() >= minsup) {
                if (newMinsip >= new_minsup) {
     kSizeSubset.put(newSub, matchIndex);

	  	  //System.out.println(f2SetValues[h] + " " +lastItem);
	  	  greatest = lastItem;
     }
               } //max subset check
}
} //end of inner
} //outer for loop

return kSizeSubset;

} //end of method
	    
	    
	    
	    public boolean matchTwo (String[] a, String [] b){
	        int match = 0;
	        boolean success = false;
	  String first="";
	  String second="";
	for (int x=0; x<a.length; x++){
		   first = first + a[x];
		}
	for (int y=0; y<b.length; y++){
		   second = second + b[y];
		}

	//System.out.println(first + "-MATCH-" + second);
	      for (int h = 0; h < a.length; h++) {
	        for (int i = 0; i < b.length; i++) {

	            if (((String)a[h].trim()).compareTo((String)b[i].trim()) == 0){
	               match = match + 1;
	                if (match == a.length){
	                   success = true;
	                   break;
	                }

	            }

	        }
	      }

	return success;
	} //end of match two
	    
	    
	    

public ArrayList compareTwoIndexes (ArrayList a, ArrayList b) {

	ArrayList result = new ArrayList();
	   for (int l = 0; l < a.size(); l++) {

		   for (int m =0; m < b.size(); m++) {

			   			if (a.get(l).toString().equalsIgnoreCase(b.get(m).toString())) {
			   				 result.add(a.get(l));
			   			}
		   }

	   }

	   return result;
} //end of method
	    

public ArrayList randomLines (ArrayList dbLines, int randomPercent, int lineRan) {

        Random r = new Random();
         
         
         System.out.println(lineRan + " to generate");
         
         if (lineRan == 0) {lineRan =1;}
          
           ArrayList lIndex = new ArrayList();

         for (int k = 0; k < lineRan; k++){
                
                int randomInt =  1 + (int)(Math.random() *  dbLines.size()-1); 
                        

                lIndex.add(randomInt);
         }
          
           
return lIndex;

 } //end of random ines 
	    
}