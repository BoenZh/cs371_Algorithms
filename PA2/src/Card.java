
public class Card {

	int value;
	String suit;
	
	public Card(int v,String s){
		value=v;
		suit=s;
	}
	
	public int getV(){
		return value;
	}
	
	public String getS(){
		return suit;
	}
	
	public boolean sameThing(int v,String s){
		if(value==v&&suit.equals(s))
			return true;
		else 
			return false;
	}
	
	public Card smallCard(Card b){
		if(this.getV()>b.getV())
			return b;
		else if(this.getV()<b.getV())
			return this;
		else if(this.getS().compareTo(b.getS())<0)
			return this;
		else
			return b;
	}
	
	public int cardPos(Card b,Card c){
		Card temp;
		temp=this.smallCard(b);
		temp=temp.smallCard(c);
		if(temp.sameThing(this.getV(), this.getS()))
			return 1;
		else if(temp.sameThing(b.getV(), b.getS()))
			return 2;
		else
			return 3;
		
	}
	
	public boolean inOrder(Card b){
		Card temp;
		temp=this.smallCard(b);
		if(temp.sameThing(this.getV(), this.getS()))
			return true;
		else
			return false;
		
	}
	
	public int order( Card b,Card c){
		int temp=this.cardPos(b, c);
		if(temp==1){
			if(b.inOrder(c))
				return 0;
			else
				return 3;
		}
		else if(temp==2){
			if(this.inOrder(c))
				return 0;
			else
				return 3;
		}
		else{
			if(this.inOrder(b))
				return 0;
			else 
				return 3;
		}
		
		
	}
	
	
	public String toString(){
		String result = "";
		if(this.getV()==1)
			result+="A";
		else if(this.getV()==11)
			result+="J";
		else if(this.getV()==12)
			result+="Q";
		else if(this.getV()==13)
			result+="K";
		else 
			result+=this.getV();
		
		result +=this.getS();
		
		
		return result;
	}
	

}
