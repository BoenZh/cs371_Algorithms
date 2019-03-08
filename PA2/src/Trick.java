/*
Name: boen zhang
Assignment: PA 2
Course/Semester: CS 371 - Fall 2017
Instructor: Dr.wolff
Known Bugs: none
Special instructions:none
*/

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Trick {
	int size;
	//int[] num;
	Queue<Integer> myQue=new LinkedList<Integer>();
	
	Trick(String filename) throws FileNotFoundException{
		
		String line;
		String[] temp;
		Card a,b,c,d,e;
		Scanner in=new Scanner(new FileReader(filename));
		size=in.nextInt();
		in.nextLine();
		
		for(int i=0;i<size;i++){
			line=in.nextLine().toString();
			
			temp=line.split(" ");
			
			a=makeCard(temp[0]);
			b=makeCard(temp[1]);
			c=makeCard(temp[2]);
			d=makeCard(temp[3]);
			e=makeCard(temp[4]);
			System.out.println("Problem "+(i+1)+":");
			doWork(a,b,c,d,e);
			System.out.println();
		}
		
		
	}
	/**
	 * do work calls the johnson algorithm and put them into a queue of card, then use this queue to brute force
	 * then print out all valid answers. 
	 * @param a the first card 
	 * @param b the second card
	 * @param c the third card
	 * @param d the fourth card
	 * @param e the fifth card
	 */
	public void doWork(Card a,Card b,Card c,Card d,Card e){
		Queue<Card> Que=new LinkedList<Card>();
		Queue<Card> result=new LinkedList<Card>();
		Card p1,p2,p3,p4,p5;
		int value=0;
		String str="";
		johnson();
		
		
		
		while(!myQue.isEmpty()){
			Que.add(toCard(myQue.remove(),a,b,c,d,e));
		}
		
		while(!Que.isEmpty()){
			p1=Que.remove();
			p2=Que.remove();
			p3=Que.remove();
			p4=Que.remove();
			p5=Que.remove();
			
			
			value=p2.getV()+p3.cardPos(p4, p5)+p3.order(p4, p5);
			if(value>13)
				value=value-13;
			str=p2.getS();
			if(p1.sameThing(value, str)){
				result.add(p1);
				result.add(p2);
				result.add(p3);
				result.add(p4);
				result.add(p5);
				
			}
				
			
		}//end of first while
		
		while(!result.isEmpty()){
			p1=result.remove();
			p2=result.remove();
			p3=result.remove();
			p4=result.remove();
			p5=result.remove();
			System.out.println(p1.toString()+" "+p2.toString()+" "+p3.toString()+" "+p4.toString()+" "+p5.toString());
		}
		
		
		
		
		
	}
	
	public void johnson(){
		
		int[] p=new int[5];
		int[] pi=new int[5];
		int[] dir=new int[5];
		for(int i=0;i<5;i++){
			p[i]=i;
			pi[i]=i;
			dir[i]=-1;
		}
		johnson(0,p,pi,dir);
	}
	
	public void johnson(int n,int[] p,int[] pi, int[] dir){
		if(n>=p.length){
			
			
			for(int j=0;j<p.length;j++){
				myQue.add(p[j]);
			}
			return;
		}
		johnson(n+1,p,pi,dir);
		for(int i=0;i<=n-1;i++){
			int temp=p[pi[n]+dir[n]];
			p[pi[n]]=temp;
			p[pi[n]+dir[n]]=n;
			pi[temp]=pi[n];
			pi[n]=pi[n]+dir[n];
			johnson(n+1,p,pi,dir);
		}
		dir[n]=-dir[n];	
		
	}
	/*
	 * conver this int n to card position
	 */
	public Card toCard(int n,Card a,Card b,Card c,Card d,Card e){
		if(n==0)
			return a;
		else if(n==1)
			return b;
		else if(n==2)
			return c;
		else if(n==3)
			return d;
		else 
			return e;
		
	}
	/**
	 * it read input sting and make it become a card
	 * @param in input string like:10D
	 * @return the card object
	 */
	public Card makeCard(String in){
		Card result;
		if(in.length()==3)
			result=new Card(10,""+in.charAt(2));
		else if(in.charAt(0)=='2')
			result=new Card(2,""+in.charAt(1));
		else if(in.charAt(0)=='3')
			result=new Card(3,""+in.charAt(1));
		else if(in.charAt(0)=='4')
			result=new Card(4,""+in.charAt(1));
		else if(in.charAt(0)=='5')
			result=new Card(5,""+in.charAt(1));
		else if(in.charAt(0)=='6')
			result=new Card(6,""+in.charAt(1));
		else if(in.charAt(0)=='7')
			result=new Card(7,""+in.charAt(1));
		else if(in.charAt(0)=='8')
			result=new Card(8,""+in.charAt(1));
		else if(in.charAt(0)=='9')
			result=new Card(9,""+in.charAt(1));
		else if(in.charAt(0)=='J')
			result=new Card(11,""+in.charAt(1));
		else if(in.charAt(0)=='Q')
			result=new Card(12,""+in.charAt(1));
		else if(in.charAt(0)=='K')
			result=new Card(13,""+in.charAt(1));
		else
			result=new Card(1,""+in.charAt(1));
		
		
		
		
		
		return result;
	}
	
	
	
	
	
	
	
	

	public static void main(String[] args) throws FileNotFoundException {
		Trick a=new Trick("trick.in");
		
		
		
		

	}

}
