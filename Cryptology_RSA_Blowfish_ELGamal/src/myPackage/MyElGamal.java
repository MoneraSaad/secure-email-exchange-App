package myPackage;

import java.math.BigInteger;
import java.util.*;

//Implement the ElGamal Digital Signature Scheme.

public class MyElGamal {
	
	BigInteger p,alpha,secretKey,peta,k ,p1,r,s ,temp ,secretkeyR,one,two,three,V1,V2,s2;
	String VerifiedMsg;
	BigInteger[] signArray= new BigInteger[2]; 
	BigInteger m = new BigInteger("0"); //m= message 
	
	
	/**
	 *  Constructor.
	 */
	public MyElGamal() {
	/////initial setup
	initialSetUp();
	
	}
	
	/**
	 * Initial set up 
	 */
	private void initialSetUp() {

		//Alice chooses a large prime p and a primitive root α.	
		p= new BigInteger( 64, 10, new Random()) ;//large prime p
		p1=p.subtract( BigInteger.valueOf( 1 ) ) ;//p1= p-1
		alpha = new BigInteger( 64, 10, new Random() ) ;//primitive root alpha 													
		while( alpha.compareTo( p ) == 0 ) ;
		//chooses a secret key (number): 1 < xA < p-1 
		secretKey = new BigInteger(64, 10, new Random());
		if(p1.subtract(secretKey).compareTo(BigInteger.ZERO)==1); //(p-1)-secretKey > 0  //  1 < xA < p-1 // xA =secretKey
		else {
			secretKey = new BigInteger(64, 10, new Random());
			while(p1.subtract(secretKey).compareTo(BigInteger.ZERO)!=1);
		}
		 
		peta=alpha.modPow(secretKey, p); //peta -> public key //calculates β ≡ α^secretKey (mod p). 
	}

	/**
	 * message signing 
	 */
	public void signthemessage(){
		
		//She selects a secret random integer k such that GCD(k, p – 1) = 1.
		k = new BigInteger(64, 10, new Random());
		if(p1.subtract(k).compareTo(BigInteger.ZERO)==1); //(p-1)-k > 0  //  1 < k < p-1 
		else {
			k = new BigInteger(64, 10, new Random());
			while(p1.subtract(k).compareTo(BigInteger.ZERO)!=1);
		}
		if(p1.gcd(k).compareTo( BigInteger.valueOf( 1 ) )==0);
		else {
			k = new BigInteger(64, 10, new Random());
			while(p1.gcd(k).compareTo( BigInteger.valueOf( 1 ) )!=0);
		}
		//She then computes r ≡ α^k (mod p).
		r= alpha.modPow(k, p); //r -> temporary key
		secretkeyR=secretKey.multiply(r);// secretkeyR = secretKey*r
	    m=new BigInteger(64,10,new Random());//computing the message  m , 0 <= m <= (p-1)
		if(p1.subtract(m).compareTo(BigInteger.ZERO)==1); //(p-1)-m > 0  //  1 < m < p-1 
		else {
			m = new BigInteger(64, 10, new Random());
			while(p1.subtract(m).compareTo(BigInteger.ZERO)!=1);
		}
	    //She then finally computes s ≡ k^-1(m – secretKey*r) (mod p – 1).
	    s=k.modInverse(p1);	//s=k.modInverse(p1) => computing k^-1 = s. 
	    temp=m.subtract(secretkeyR);// temp= m-secretKey*r
	    s2=s.multiply(temp).mod(p1); //s2 ≡ k^-1(m – secretKey*r) (mod p – 1).
		//The signed message is the pair (r, s2).
	    signArray[0]=r;
	    signArray[1]=s2;	
	}
	
	
	/**
	 * The verification process which can be performed by Bob using public information only.
	 */
	public void verification() {
		
		one=peta.modPow(r, p); //one= peta^r (mod p)
		two=r.modPow(s2, p); //two= r^s2 (mod p)
		V1=alpha.modPow(m,p); //v1 ≡ α^m (mod p).
		V2=one.multiply(two).mod(p); //v2 ≡ β^r*r^s (mod p) 
		//The signature is declared valid if and only if v1 = v2.
		if(V1.equals(V2)) {
			setVerifiedMsg("Your Signature is verified ;) , you are Alice!");
		}
		else {
			setVerifiedMsg("can't verify your signature!!!!");
		}
	}
	

	public BigInteger getP() {
		return p;
	}

	public BigInteger getAlpha() {
		return alpha;
	}

	public BigInteger getSecretKey() {
		return secretKey;
	}

	public BigInteger getPeta() {
		return peta;
	}

	public BigInteger getK() {
		return k;
	}

	public BigInteger getR() {
		return r;
	}

	public BigInteger getV1() {
		return V1;
	}

	public BigInteger getV2() {
		return V2;
	}

	public BigInteger getS2() {
		return s2;
	}

	public String getVerifiedMsg() {
		return VerifiedMsg;
	}

	public void setVerifiedMsg(String verifiedMsg) {
		VerifiedMsg = verifiedMsg;
	}

	public BigInteger[] getSignArray() {
		return signArray;
	}

	public BigInteger getM() {
		return m;
	}


}

