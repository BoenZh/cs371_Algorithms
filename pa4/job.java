
public class job {
	
	private int w;
	private double pay;
	
	public job(){
		w=0;
		pay=0;
		
	}
	public job(int weight,double p){
		w=weight;
		pay=p;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public double getPay() {
		return pay;
	}
	public void setPay(double pay) {
		this.pay = pay;
	}

}
