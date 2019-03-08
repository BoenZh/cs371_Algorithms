/*
 * name: boen zhang
 * pa3
 * 11/10/2017
 * Fall
 * known bug: mult function's concept should be correct, but has NullPointerException when run it. dont know how to clear this exception
 */

import java.util.LinkedList;
import java.util.ListIterator;

public class BigInt implements Comparable<BigInt> {

    /**
     * The list of digits (base 10).  The digits are stored in the order of least significant to
     * most significant.  Leading zeros are allowed, but may be removed by any method.
     */
    private LinkedList<Integer> digits;

    /**
     * Creates a BigInt from a String representation of an integer.  The String must only contain
     * the characters '0' through '9'.  If any other characters are found, an IllegalArgumentException
     * is thrown.
     *
     * @param s the String representing the value of this integer.
     * @throws IllegalArgumentException when the String does not contain digits.
     */
    public BigInt(String s ) {
        digits = new LinkedList<Integer>();

        for( int i = s.length() - 1; i >= 0; i-- ) {
            char c = s.charAt(i);
            if( Character.isDigit(c) ) {
                digits.add(c - '0');
            } else {
                throw new IllegalArgumentException("String must contain digits only.");
            }
        }
        removeLeadingZeros();
    }

    /**
     * Constructs a BigInt from a primitive integer.  The value provided must not be negative.
     *
     * @param v the value for this BigInt
     * @throws IllegalArgumentException if v is negative
     */
    public BigInt(int v ) {
        digits = new LinkedList<Integer>();
        if(v < 0)
            throw new IllegalArgumentException("Negative numbers are not supported.");
        if( v == 0 ) {
            digits.add(0);
        }
        while( v > 0 ) {
            digits.add(v % 10);
            v = v / 10;
        }
    }

    /**
     * Returns a new BigInt containing the sum of this and rhs.  this is not modified.
     *
     * @param rhs a BigInt
     * @return a new BigInt containing the sum of this and rhs.
     */
    public BigInt add(BigInt rhs) {
        BigInt result = new BigInt(0);
        result.digits.clear();

        ListIterator<Integer> leftIter = digits.listIterator();
        ListIterator<Integer> rightIter = rhs.digits.listIterator();
        int carry = 0;
        while( leftIter.hasNext() || rightIter.hasNext() ) {
            int sum = carry;
            if(leftIter.hasNext()) sum += leftIter.next();
            if(rightIter.hasNext()) sum += rightIter.next();

            carry = sum / 10;
            result.digits.add(sum % 10);
        }
        if( carry > 0 ) result.digits.add(carry);
        if( result.size() == 0 ) result.digits.add(0);
        return result;
    }

    /**
     * Returns a new BigInt containing the difference: this - rhs.  this is not modified.
     * A negative result is not allowed, so it must be true that this >= rhs, otherwise an
     * IllegalArgumentException is thrown.
     *
     * @param rhs the right hand side of the operation, must be less than or equal to this.
     * @return the result of this - rhs
     * @throws IllegalArgumentException if this < rhs
     */
    public BigInt sub(BigInt rhs) {
        if( this.compareTo(rhs) < 0 )
            throw new IllegalArgumentException("Negative numbers are not supported");

        BigInt result = new BigInt(0);
        result.digits.clear();
        ListIterator<Integer> leftIter = this.digits.listIterator();
        ListIterator<Integer> rightIter = rhs.digits.listIterator();

        int borrow = 0;
        while(rightIter.hasNext() || leftIter.hasNext()) {
            int leftDigit = leftIter.next() + borrow;
            int rightDigit = 0;
            if( rightIter.hasNext() ) rightDigit = rightIter.next();
            int diff = leftDigit - rightDigit;
            if( diff < 0 ) {
                borrow = -1;
                diff += 10;
            } else {
                borrow = 0;
            }
            result.digits.add(diff);
        }

        result.removeLeadingZeros();
        return result;
    }

    /**
     * Returns the number of digits in this BigInt.
     * @return the number of digits of this
     */
    public int size() { return digits.size(); }

    /**
     * @return a String representation of this BigInt.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListIterator<Integer> iter = digits.listIterator(digits.size());
        while( iter.hasPrevious() ) {
            sb.append(iter.previous());
        }
        return sb.toString();
    }

    /**
     * Compares this to o and returns a negative value when this < o, a positive value when this > 0
     * and zero when they are equal.
     *
     * @param o BigInt to compare with this
     * @return negative, positive or zero value
     */
    @Override
    public int compareTo(BigInt o) {
        this.removeLeadingZeros();
        o.removeLeadingZeros();

        if(size() < o.size() )  return -1;
        else if (size() > o.size() ) return 1;

        ListIterator<Integer> leftIter = this.digits.listIterator(this.digits.size());
        ListIterator<Integer> rightIter = o.digits.listIterator(o.digits.size());

        while( leftIter.hasPrevious() && rightIter.hasPrevious() ) {
            int left = leftIter.previous();
            int right = rightIter.previous();
            if( left > right ) return 1;
            else if( left < right ) return -1;
        }

        return 0;
    }

    /**
     * Removes any leading zeros in this number.  If all digits are zero (or there are no digits), it is
     * replaced with a single digit 0.
     */
    private void removeLeadingZeros() {
        ListIterator<Integer> iter = digits.listIterator(digits.size());
        while( iter.hasPrevious() && iter.previous() == 0 ) iter.remove();
        if( size() == 0 ) digits.add(0);
    }

    /**
     * Returns a new BigInt that contains the digits of this BigInt between start and
     * the end of the number.  This method does not modify this BigInt.
     *
     * Indexes of digits are numbered starting at zero
     * (least significant) and increase towards the most significant digit.  In
     * other words, the indexes increase from right to left in the String representation
     * of the number.
     *
     * Example:
     *    new BigInt("12345").slice(2) --> 123
     *
     * @param start the first index of the slice
     * @return a new BigInt that contains the digits of this BigInt between start and the end of the number.
     * @throws IllegalArgumentException when start is not a valid index
     */
    public BigInt slice(int start) {
    	BigInt result=new BigInt(0);
    	result.digits.clear();
    	ListIterator<Integer> iter = this.digits.listIterator();
    	while(iter.hasNext()){
    		int a=iter.next();
    		result.digits.add(a);
    	}

        if(start>this.size()||start<=0)
        	throw new IllegalArgumentException("not a valid index");
        else if(start==this.size())
        	result=new BigInt(0);
        else{
        	//result=this;
        	result.shift(-start);
        }

        return result;
    }

    /**
     * Returns a new BigInt that contains the digits of this BigInt between start and
     * end - 1.  This method does not modify this BigInt.
     *
     * Indexes of digits are numbered starting at zero
     * (least significant) and increase towards the most significant digit.  In
     * other words, the indexes increase from right to left in the String representation
     * of the number.
     *
     * Examples:
     *   new BigInt("12345").slice(1,2) --> 4
     *   new BigInt("12345").slice(3,5) --> 12
     *
     * @param start the starting index of the slice.  Valid values are between 0 and size() - 1.
     * @param end one plus the ending index of the slice.  Valid values are between 0 and size().
     * @return a new BigInt that contains the digits of this between start and end-1.
     * @throws IllegalArgumentException when start and end do not represent a valid range (e.g. end <= start, etc.)
     */
    public BigInt slice(int start, int end) {
    	BigInt temp=new BigInt(0);
    	BigInt result = new BigInt(0);
    	temp.digits.clear();
    	result.digits.clear();
    	ListIterator<Integer> iter = this.digits.listIterator();
    	while(iter.hasNext()){
    		int a=iter.next();
    		temp.digits.add(a);
    	}

        if(start>this.size()||start<0||end<=start||end>this.size())
        	throw new IllegalArgumentException("start and end do not represent a valid range");
        else{
        	temp.shift(-start);
        	for(int i=0;i<end-start;i++){
        		result.digits.add(temp.digits.remove());
        	}
        	
        }
        

        return result;
    }

    /**
     * Shifts (left) the digits of this BigInt by the amount provided.  This is equivalent to multiplying or
     * dividing by 10.  If v is positive, the number is shifted to the left, otherwise, it is shifted to the right.
     * This method modifies this BigInt.  If it is a right shift and the value of v is greater than size(), the
     * result is 0.
     *
     * Examples:
     *   new BigInt("12345").shift(2) --> 1234500
     *   new BigInt("12345").shift(-2) --> 123
     *   new BigInt("12345").shift(-6) --> 0
     *
     * @param v the number of digits to shift
     */
    public void shift(int v) {

        if(v>=0){
        	for(int i=0;i<v;i++){
        		digits.addFirst(0);
        	}
        }
        else if(-v>this.size()){
        	this.digits.clear();
        	this.digits.add(0);
        	/*
        	ListIterator<Integer> iter = this.digits.listIterator();
        	while(iter.hasNext()){
        		int nothing=iter.next();
        		System.out.println("hi");
        		this.digits.remove();
        	}
        	this.digits.add(0);
        	*/
        }
        else{
    	for(int i=0;i>v;i--){
    		digits.remove();
    	}
        }
    	
    	
    	

    }

    /**
     * Returns a new BigInt that is the product of this * rhs using the algorithm described in Section 5.4.
     * Does not modify this.
     *
     * @param rhs the right hand side of the multiplication operation.
     * @return the product of this and rhs
     */
    public BigInt mult(BigInt rhs ) {
    	int s=0;
    	if(rhs.digits.size()>this.digits.size())
    		s=rhs.digits.size();
    	else
    		s=this.digits.size();
    	
    	if(s%2!=0)
    		s++;
    	for(int i=this.digits.size();i<s;i++)
    		this.digits.add(0);
    	//System.out.println(digits.size());
    	for(int i=rhs.digits.size();i<s;i++)
    		rhs.digits.add(0);
    	//System.out.println(s);
    	BigInt result=mult(this,rhs,s);
    	this.removeLeadingZeros();
    	rhs.removeLeadingZeros();
    	result.removeLeadingZeros();



        

        return result;
    }
    
    public BigInt mult(BigInt lhs,BigInt rhs,int n){
    	
    	BigInt l=new BigInt(0);
    	BigInt r=new BigInt(0);
    	l.digits.clear();
    	r.digits.clear();
    	ListIterator<Integer> iterL = lhs.digits.listIterator();
    	//System.out.println(iterL.next());
    	ListIterator<Integer> iterR = rhs.digits.listIterator();
    	while(iterL.hasNext()){
    		int a=iterL.next();
    		l.digits.add(a);
    	}
    	while(iterR.hasNext()){
    		int b=iterR.next();
    		r.digits.add(b);
    	}
    	
    	
    	if(n==1){
    		int z=l.digits.peek()*r.digits.peek();
    		System.out.println(z);
    		BigInt b=new BigInt(z);
    		
    		return b;
    	}
    	else{
    		
    		BigInt lright = new BigInt(0),lleft=new BigInt(0),rright=new BigInt(0),rleft=new BigInt(0);
    		lright.digits.clear();
    		lleft.digits.clear();
    		rright.digits.clear();
    		rleft.digits.clear();
    		for(int i=0;i<l.digits.size()/2;i++)
    			lright.digits.add(l.digits.remove());
    		for(int i=0;i<l.digits.size();i++)
    			lleft.digits.add(l.digits.remove());
    		for(int i=0;i<r.digits.size()/2;i++)
    			rright.digits.add(r.digits.remove());
    		for(int i=0;i<r.digits.size();i++)
    			lleft.digits.add(r.digits.remove());
    		
    		//System.out.println(lleft.digits.size());
    		
    		
    		BigInt c2=mult(lleft,rleft,lleft.digits.size());
    		BigInt c0=mult(lright,rright,rright.digits.size());
    		BigInt temp1=lleft.add(lright);
    		BigInt temp2=rleft.add(rright);
    		BigInt c1=mult(temp1,temp2,temp1.digits.size()).sub(c2.add(c0));
    		c2.shift(n);
    		c1.shift(n/2);
    		return c2.add(c1.add(c0));
    		
    	}
    		
    	
    	
    	
    	
    }


}
