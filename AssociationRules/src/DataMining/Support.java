package DataMining;

import java.util.ArrayList;

public class Support {
	
	Support(){
		
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

	
	
}
