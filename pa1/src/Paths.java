/*
Name: boen zhang
Assignment: PA 1
Course/Semester: CS 371 - Fall 2017
Instructor: Dr.wolff
Known Bugs: none
Special instructions:none
*/


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

import javax.xml.soap.Node;

import java.util.Queue;

public class Paths {
	
	int[][] m;//store adjacency matrix
	String ew,ns;//store the String of row and col
	
	/**
	 * String dir is a string that store the line of string in road block section
	 * int block is an int to store the third line int from the input file
	 * 
	 */
	Paths(String filename) throws FileNotFoundException{
		Scanner in=new Scanner(new FileReader(filename));
		String dir;
		int block=0;
		int temp=0;
		ew=in.nextLine();
		ns=in.nextLine();
		block=in.nextInt();
		
		m=new int[ew.length()*ns.length()][ew.length()*ns.length()];
		int v=ns.length();
		
		while(temp<ew.length()){
			if(ew.charAt(temp)=='E'){
				for(int i=0;i<v-1;i++)
					m[temp*v+i][temp*v+i+1]=1;
			}
			else if(ew.charAt(temp)=='W'){
				for(int i=0;i<v-1;i++)
					m[temp*v+i+1][temp*v+i]=1;
			}
			else if(ew.charAt(temp)=='T'){
				for(int i=0;i<v-1;i++){
					m[temp*v+i][temp*v+i+1]=1;
					m[temp*v+i+1][temp*v+i]=1;
				}
			}
			else{
				System.out.println("input error");
				System.exit(0);
			}
			temp++;
		}
		temp=0;
		
		while(temp<ns.length()){
			if(ns.charAt(temp)=='S'){
				for(int i=0;i<ew.length()-1;i++)
					m[i*ew.length()+temp][(i+1)*ew.length()+temp]=1;
			}
			else if(ns.charAt(temp)=='N'){
				for(int i=0;i<ew.length()-1;i++)
					m[(i+1)*ew.length()+temp][i*ew.length()+temp]=1;
			}
			else if(ns.charAt(temp)=='T'){
				for(int i=0;i<ew.length()-1;i++){
					m[(i+1)*ew.length()+temp][i*ew.length()+temp]=1;
					m[i*ew.length()+temp][(i+1)*ew.length()+temp]=1;
				}
			}
			else{
				System.out.println("input error");
				System.exit(0);
			}
			temp++;
		}
		int begin=0;
		int finish=0;
		int num=0;
		
		for(int i=0;i<block;i++){
			dir=in.next();
			num=in.nextInt();
			begin=in.nextInt();
			finish=in.nextInt();
			if(dir.equals("EW")){
				for(int j=begin;j<finish;j++){
					m[num*ns.length()+j][num*ns.length()+j+1]=0;
					m[num*ns.length()+j+1][num*ns.length()+j]=0;
				}			
			}
			else if(dir.equals("NS")){
				for(int j=begin;j<finish;j++){
					m[j*ew.length()+num][(j+1)*ew.length()+num]=0;
					m[(j+1)*ew.length()+num][j*ew.length()+num]=0;
				}
			}
			else{
				System.out.println("road block input error");
				System.exit(0);
			}
			
		}
		
			
		
	}//end of Paths
	
	/**
	 * Use the depth-first search algorithm to find out the path exists or not
	 * @param sr the start row
	 * @param sc the start col in order to locate the node
	 * @param er the end row
	 * @param ec the end col in order to locate the node
	 * @return true if the path from the start node to end node exist, false if didn't
	 */
	public boolean pathExists(int sr,int sc,int er,int ec){
		int s=ew.length()*ns.length();
		int v=0;
		boolean result=false;
		int[] visited=new int[s];
		int start=sr*ew.length()+sc;
		int end=er*ew.length()+ec;
		Stack<Integer> st =new Stack<Integer>();
		
		st.push(start);
		
		while(!st.isEmpty()){
			v=st.pop();
			if(visited[v]==0)
				visited[v]=1;
			
			for(int i=0;i<s;i++){
				if((m[v][i]==1)&&(visited[i]==0)){
					st.push(v);
					visited[i]=1;
					v=i;
				}
			}
			
		}//end of while
		//System.out.println(Arrays.toString(visited));
		
		if(visited[end]==1)
			result=true;
		else
			result=false;
			
		return result;
	}
	
	/**
	 * Use the breadth-first search algorithm to determine the shortest distance of two nodes.
	 * @param sr the start row
	 * @param sc the start col in order to locate the node
	 * @param er the end row
	 * @param ec the end col in order to locate the node
	 * @return an integer of the shortest distance from the start node to end node, -1 means the path does not exist
	 */
	public int shortestDistance(int sr,int sc,int er,int ec){
		//ArrayList<Integer> go=new ArrayList<Integer>();
		int result=-1;
		
		int v=0;
		int s=ew.length()*ns.length();
		int[] visited=new int[s];
		Arrays.fill(visited,-1);
		int start=sr*ew.length()+sc;
		int end=er*ew.length()+ec;
		
		LinkedList<Integer> temp=new LinkedList<Integer>();
		temp.add(start);
		
		visited[start]=0;
		
		
		while(!temp.isEmpty()){
			
				
			v=temp.remove();
			
			
			
			//if(visited[v]==0)
				//visited[v]=1;
			for(int i=0;i<s;i++){
				if((m[v][i]==1)&&(visited[i]==-1)){
					temp.add(i);
					
					visited[i]=visited[v]+1;
					
					if(visited[end]!=-1)
						result=visited[end];		
							
				}
				
			}//end of for
			
			
		}//end of while
		
		
		
		
		return result;
	}
	
	
	
	
	
	
	/**
	 * print out the adjacency matrix
	 */
	public void printM(){
		for (int[] x : m)
		{
		   for (int y : x)
		   {
		        System.out.print(y + " ");
		   }
		   System.out.println();
		}
	}
	
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		//Paths a=new Paths("a.txt");
		//a.printM();
		//System.out.println(a.pathExists(0, 0, 0, 2));
		
		//System.out.println(a.shortestDistance(0, 0, 0, 2));
		//System.out.println(a.shortestDistance(0, 0, 1,0));
		//System.out.println(a.shortestDistance(0, 0, 1,0));
		
	}

}
