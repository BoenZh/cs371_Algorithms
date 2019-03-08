/**
 * name: boen zhang	
 * assignment: pa4
 * Course/Semester: CS 371 - Fall 2017
 * Instructor:Dr.Wolff
 * Sources consulted: none
 * Known Bugs: none
 * Special instructions: none
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		
		job temp=new job();
		Scanner in=new Scanner(new FileReader("jobs.in"));
		int q=in.nextInt();
		
		
		for(int z=0;z<q;z++){
			
			
		int size=0;
		int time=0;
		size=in.nextInt();
		ArrayList<job> list=new ArrayList<job>();
		
		for(int i=0;i<size;i++){
			int a=0;
			double b=0;
			a=in.nextInt();
			b=in.nextDouble();
			temp=new job(a,b);
			list.add(temp);
		}
		time=in.nextInt();
		//System.out.println(size+" "+t);
		
		double[][] table=new double[size+1][time+1];
		
		for(int i=0;i<size+1;i++){
			for(int j=0;j<time+1;j++){
				
					table[i][j]=0;
			}
		}//fill table with 0
		
		
		
		for(int i=0;i<size+1;i++){
			
			for(int j=0;j<time+1;j++){
			
				if(i==0||j==0){
					continue;
				}
				if(j-list.get(i-1).getW()<0){
					table[i][j]=table[i-1][j];
					
				}
				else{
					table[i][j]=Math.max(table[i-1][j], list.get(i-1).getPay()+table[i-1][j-list.get(i-1).getW()]);
					
				}
				
			}
		}
		
		//System.out.println(Arrays.deepToString(table).replace("], ", "]\n"));
		double max=table[size][time];
		int check=time-1;
		boolean stop=false;
		while(stop==false){
			if(table[size][time]==table[size][check])
				check--;
			else
				stop=true;
			
		}//total time
		check++;
		System.out.print("Problem "+(z+1)+": "+check+" seconds scheduled for $");
		System.out.printf("%.2f", table[size][time]);
		System.out.println();
		}// end of for z loop

	}

}
