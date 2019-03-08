import static org.junit.Assert.*;

import org.junit.Test;

public class BigIntTest {
	BigInt a=new BigInt("12345");
	//BigInt b=new BigInt(67892);

	@Test
	public void testSliceInt() {
		assertEquals("123",a.slice(2).toString());
		assertEquals("12",a.slice(3).toString());
	}

	@Test
	public void testSliceIntInt() {
		BigInt b=new BigInt("12345");
		assertEquals("4",b.slice(1,2).toString());
		assertEquals("12",b.slice(3,5).toString());
		
	}

	@Test
	public void testShift() {
		BigInt c=new BigInt("12345");
		c.shift(2);
		assertEquals("1234500",c.toString());
		BigInt d=new BigInt("12345");
		d.shift(-2);
		assertEquals("123",d.toString());
	}

	@Test
	public void testMultBigInt() {
		BigInt b=new BigInt("123");
		BigInt c=new BigInt("234");
		BigInt d=b.mult(c);
		assertEquals("28782",d.toString());
	}

}
