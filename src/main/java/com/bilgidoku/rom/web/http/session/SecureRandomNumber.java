package com.bilgidoku.rom.web.http.session;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureRandomNumber {
	
	private static final String SHA1_PRNG = "SHA1PRNG";
	
	private static final int DEFAULT_RANDOM_SIZE = 1024;
	
	private static final char HEX_DIGIT [] = {
          '0', '1', '2', '3', '4', '5', '6', '7',
          '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
	
	// This isn't thread safe but we probably don't really care
	// since all we're doing is reading a bunch of random numbers
	// out of the generator.
	private final SecureRandom sRandom__;
	
	public SecureRandomNumber(){
		try {
	    	sRandom__ = SecureRandom.getInstance( SHA1_PRNG );
	    } catch ( NoSuchAlgorithmException e ) {
	        throw new Error(e);
	    }
	}
	
	public String generate(){
		return toHex(getNextSecureRandom());
	}

	/**
	 * Get the number of next random bits in this SecureRandom
	 * generators' sequence.
	 * @param size how many random bits you want
	 * @return
	 * @throws IllegalArgumentException if the arg isn't divisible by eight
	 */
	private  byte [] getNextSecureRandom () {
		final int bits=DEFAULT_RANDOM_SIZE ;
		
		// Make sure the number of bits we're asking for is at least
		// divisible by 8.
//		if ( (bits % 8) != 0 ) {
//		   throw new IllegalArgumentException("Size is not divisible " +
//                                                           "by 8!");
//		}
		
		// Usually 64-bits of randomness, 8 bytes
	    final byte [] bytes = new byte[ bits / 8 ];
	    
	    // Get the next 64 random bits. Forces SecureRandom
	    // to seed itself before returning the bytes.
	    sRandom__.nextBytes(bytes);
	    
	    return bytes;
		
	}
	
    /**
     * Convert a byte array into its hex String
     * equivalent.
     * @param bytes
     * @return
     */
    public static String toHex ( byte [] bytes ) {
        
        if ( bytes == null ) {
            return null;
        }
        
        StringBuilder buffer = new StringBuilder(bytes.length*2);
        for ( byte thisByte : bytes ) {
            buffer.append(byteToHex(thisByte));
        }
        
        return buffer.toString();
        
    }
    
    /**
     * Convert a single byte into its hex String
     * equivalent.
     * @param b
     * @return
     */
    private static String byteToHex ( byte b ) {
        char [] array = { HEX_DIGIT[(b >> 4) & 0x0f],
                            HEX_DIGIT[b & 0x0f] };
        return new String(array);
    }
	
//    /**
//     * An example showing how to use SecureRandomNumber.
//     * @param args
//     */
//	public static void main ( String [] args ) {
//				
//		for ( int i = 0; i < 10; i++ ) {
//			
//			// Get 64-bits of secure random goodness.
//			final byte [] randBytes =
//				SecureRandomNumber.
//					getNextSecureRandom(
//							DEFAULT_RANDOM_SIZE );
//			
//			// Convert it to something pretty.
//			syso(
//				SecureRandomNumber.toHex( randBytes ) );
//			
//		}
//		
//	}
	
}