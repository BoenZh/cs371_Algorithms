/**
 * name: boen zhang
 * PA 5
 * CS 371 fall 2017
 * 12/8/2017
 * known bug:none
 * 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/*
for(int i=0;i<q;i++){
	for(int j=0;j<q;j++)
		System.out.print(roadMap[i][j]+" ");
	System.out.println();
}
*/

/*
for(int i=0;i<q;i++){
	for(int j=0;j<q;j++)
		System.out.print(myMap[i][j]+" ");
	System.out.println();
}
*/

public class main {

	public static int min(ArrayList<Integer> a) {
		int hi = Integer.MAX_VALUE;
		for (int i = 0; i < a.size(); i++) {
			if (a.get(i) < hi)
				hi = a.get(i);
		}

		return a.indexOf(hi);
	}
	
	public static boolean checkTree(String a,String b,ArrayList<String> input){
		ArrayList<String> temp=new ArrayList<String>();
		boolean result=false;
		for(int i=0;i<input.size();i++){
			temp.add(input.get(i));
		}
		while(!temp.isEmpty()){
			String a1=temp.remove(0);
			String a2=temp.remove(0);
			if(a1.equals(a)&&a2.equals(b))
				result=true;
			if(a1.equals(b)&&a2.equals(a))
				result=true;
			
		}
		
		return result;
	}

	public static void main(String[] args) throws FileNotFoundException {
		String yolo;
		String[] hi;
		
		
		Scanner in = new Scanner(new FileReader("detours.in"));
		int q = in.nextInt();
		in.nextLine();
		ArrayList<String> city = new ArrayList<String>();
		for (int i = 0; i < q; i++) {
			city.add(in.nextLine());
		}

		int[][] roadMap = new int[q][q];

		while (true) {
			yolo = in.nextLine();
			if (yolo.equals("."))
				break;

			hi = yolo.split("\\s*,\\s*");
			int dis = 0;
			dis = Integer.parseInt(hi[2]);
			int l = -1;
			int r = -1;
			l = city.indexOf(hi[0]);
			r = city.indexOf(hi[1]);
			roadMap[l][r] = dis;
			roadMap[r][l] = dis;

		}
		int lol = Integer.MAX_VALUE;

		for (int i = 0; i < q; i++) {
			for (int j = 0; j < q; j++)
				if (roadMap[i][j] == 0)
					roadMap[i][j] = Integer.MAX_VALUE;
		}
		int[][] myMap = new int[q][q];
		ArrayList<Integer> dist = new ArrayList<Integer>();
		ArrayList<String> distID = new ArrayList<String>();
		ArrayList<String> inTree=new ArrayList<String>();
		ArrayList<String> parent = new ArrayList<String>();
		ArrayList<Integer> result=new ArrayList<Integer>();

		while (in.hasNext()) {
			
			for (int i = 0; i < q; i++) {
				for (int j = 0; j < q; j++) {
					myMap[i][j] = roadMap[i][j];
				}
			} // set up local map for input line
			

			yolo = in.nextLine();
			hi = yolo.split("\\s*,\\s*");
			String l = hi[0];
			String r = hi[1];

			boolean check = false;

			for (int i = 0; i < q; i++) {
				if (l.equals(city.get(i)))
					check = true;
			}
			if (check == false) {
				System.out.println(l + " is not a recognized city");
				continue;
			}
			check = false;
			for (int i = 0; i < q; i++) {
				if (r.equals(city.get(i)))
					check = true;
			}
			if (check == false) {
				System.out.println(r + " is not a recognized city");
				continue;
			}

			myMap[city.indexOf(l)][city.indexOf(r)] = Integer.MAX_VALUE;
			myMap[city.indexOf(r)][city.indexOf(l)] = Integer.MAX_VALUE;
			
			
			

			int left = city.indexOf(l);
			int right = city.indexOf(r);

			
			for(int i=0;i<q;i++){
				
				parent.add("");
				result.add(0);
			}
			//inTree.set(i, 1);
			
			for (int i = 0; i < q; i++) {
				if (myMap[left][i] != lol) {
					dist.add(myMap[left][i]);
					distID.add(city.get(i));
					inTree.add(l);
					inTree.add(city.get(i));
					parent.set(i, l);
					result.set(i, myMap[left][i]);
					
				}
			}
			//System.out.println(inTree.toString());
			
			
			 
			
			
			while(!dist.isEmpty()){
				
				int index=min(dist);
				int currentDis=0;
				String currentID=distID.remove(index);
				currentDis=dist.remove(index);
				for(int i=0;i<q;i++){
					if(myMap[city.indexOf(currentID)][i]!=lol){
						
						int totalDis=0;
						totalDis=currentDis+myMap[city.indexOf(currentID)][i];
						int checkIndex=-1;
						if(distID.contains(city.get(i)) && !checkTree(city.get(i),currentID,inTree)){
							
							  checkIndex=distID.indexOf(city.get(i));
							 // System.out.println(myMap[city.indexOf(currentID)][i]+"hi"+checkIndex+" "+i);
							  if(dist.get(checkIndex)>totalDis){
								  
								  

							    distID.remove(checkIndex);
							    dist.remove(checkIndex);
							    distID.add(city.get(i));
							    dist.add(totalDis);
							    inTree.add(city.get(i));
							    inTree.add(currentID);
							    parent.set(checkIndex, currentID);
							    result.set(checkIndex, totalDis);
							    checkIndex=-1;
							    
							  }
						}
						else if(checkTree(city.get(i),currentID,inTree)){
							
						}
						else{
							distID.add(city.get(i));
							dist.add(totalDis);
							
							parent.set(i, currentID);
							result.set(i, totalDis);
							inTree.add(currentID);
							inTree.add(city.get(i));
							
							
						}
						
						
						
					}
				}//end of for loop
				
			}
			//System.out.println(parent.toString());
			//System.out.println(inTree.toString());
			
			//System.out.println(result.toString());
			//System.out.println();
			int tempIndex=city.indexOf(r);
			
			if(parent.get(tempIndex).equals(""))
				System.out.println("There is no road directly from "+l+" to "+r);
			else{
				int myResult=result.get(tempIndex);
			
			
			System.out.print(r);
			
			while(!city.get(tempIndex).equals(l)){
				System.out.print(" <--> "+parent.get(tempIndex));
				tempIndex=city.indexOf(parent.get(tempIndex));
			}
			System.out.println();
			System.out.println("Total distance: "+myResult+" miles");
			System.out.println();
			}
			
			dist.clear();
			distID.clear();
			inTree.clear();
			parent.clear();
			result.clear();
			
			
			
			
			
			
			
			

		}//end of while

	}// end of main

}
