package myPackage;

import java.math.BigInteger ;
import java.util.Random ;
import java.util.Scanner;
import java.io.* ;

public class RSAKeyGeneration
{
	/**
	 * Bit length of each prime number.
	 */
	int primeSize ;

	/**
	* Two distinct large prime numbers p and q.
	*/
	BigInteger p, q ;

	/**
	 * Modulus N.
	 */
	BigInteger N ;

	Random r;

	/**
	 * r = ( p ׳’ג‚¬ג€� 1 ) * ( q ׳’ג‚¬ג€� 1 )
	 */
	BigInteger phi ;

	/**
	 * Public exponent E and Private exponent D
	 */
	BigInteger E, D ;

	String nt,dt,et;
	
	/**
	 * Constructor.
	 *
	 * @param primeSize Bit length of each prime number.
	 */
	public RSAKeyGeneration( int primeSize )
	{

		this.primeSize = primeSize ;

		// Generate two distinct large prime numbers p and q.
		generatePrimeNumbers() ;

		// Generate Public and Private Keys.
		generatePublicPrivateKeys() ;

	}

	/**
	 * Generate two distinct large prime numbers p and q.
	 */
	public void generatePrimeNumbers()
	{
	
		r=new Random();
		p = BigInteger.probablePrime(primeSize, r) ;
		q = BigInteger.probablePrime(primeSize, r);//public BigInteger(int bitLength,int certainty,Random rnd)

	}
	
	/**
	 * Generate Public and Private Keys.
	 */
	public void generatePublicPrivateKeys()
	{
		// N = p * q
		N = p.multiply( q ) ;

		// phi = ( p ׳’ג‚¬ג€� 1 ) * ( q ׳’ג‚¬ג€� 1 )
		phi = p.subtract( BigInteger.ONE ).multiply( q.subtract(BigInteger.ONE ) ) ;//(p-1)(q-1) // num of the numbers that gcd(num,N)=1

		// Choose E, coprime to and less than r
		E=BigInteger.probablePrime(primeSize/2, r);
		if(phi.subtract(E).compareTo(BigInteger.ZERO)==1); //phi-E > 0  //  1 < E < phi
		else {
			E = BigInteger.probablePrime(primeSize/2, r);
			while(phi.subtract(E).compareTo(BigInteger.ZERO)!=1);
		}
		if(phi.gcd(E).compareTo( BigInteger.valueOf( 1 ) )==0);
		else {
			 E = BigInteger.probablePrime(primeSize/2, r);
			while(phi.gcd(E).compareTo( BigInteger.valueOf( 1 ) )!=0);
		}

		// Compute D, the inverse of E mod r
		D = E.modInverse( phi ) ; //private key

	}		

	/**
	 * Get prime number p.
	 *
	 * @return Prime number p.
	 */
	public BigInteger getp()
	{
		return( p ) ;
	}

	/**
	 * Get prime number q.
	 *
	 * @return Prime number q.
	 */
	public BigInteger getq()
	{
		return( q ) ;
	}

	/**
	 * Get r.
	 *
	 * @return r.
	 */
	public BigInteger getr()
	{
		return( phi ) ;
	}

	/**
	 * Get modulus N.
	 *
	 * @return Modulus N.
	 */
	public BigInteger getN()
	{
		return( N ) ;
	}

	/**
	 * Get Public exponent E.
	 *
	 * @return Public exponent E.
	 */
	public BigInteger getE()
	{
		return( E ) ;
	}

	/**
	 * Get Private exponent D.
	 *
	 * @return Private exponent D.
	 */
	public BigInteger getD()
	{
		return( D ) ;
	}

	/**
	 * KeyGeneration Main program for Unit Testing.
	 */
	public static String generateKey() throws IOException
	{
		RSAKeyGeneration akg = new RSAKeyGeneration(33);//primeSize=33 
		BigInteger publicKeyB = akg.getE();
		BigInteger randomNumberB = akg.getN();
		File file=new File("C:\\Users\\User\\eclipse-workspace\\Cryptology_RSA_Blowfish_ELGamal\\src\\myPackage\\plainKey.txt");
		Scanner myObj = new Scanner(file);
		System.out.println("RSA");
		System.out.println("*****************Bob*****************");
		System.out.println("Reading blowfish key session from file");
		String plainKeyString= myObj.nextLine();
		BigInteger plainKeyInt= new BigInteger(plainKeyString,16);
		BigInteger cipherKey=plainKeyInt;
		cipherKey=cipherKey.modPow(publicKeyB, randomNumberB); //c= m^e mod n
		System.out.println("The encrypted key is "+ cipherKey.toString(16));
		System.out.println("Sending encrypted key to Alice");
		System.out.println("*************************************");
		System.out.println("****************Alice****************");
		System.out.println("Alice Received the encrypted key");
		System.out.println("The encrypted key is "+ cipherKey.toString(16));
		BigInteger privateKeyB = akg.getD();
		BigInteger decryptedKeyInt=cipherKey;
		decryptedKeyInt=decryptedKeyInt.modPow(privateKeyB, randomNumberB); //m= c^d mod n
		String decryptedKeyString=decryptedKeyInt.toString(16);
		System.out.println("The decrypted key is "+ decryptedKeyString);
		myObj.close();
		System.out.println("*************************************");
		return decryptedKeyString;
		/*String publicKey = publicKeyB.toString();
		String privateKey = privateKeyB.toString();
		String randomNumber = randomNumberB.toString();
		System.out.println("Public Key (E,N): "+publicKey+","+randomNumber);
		System.out.println("Private Key (D,N): "+privateKey+","+randomNumber);
		 */

	}

}