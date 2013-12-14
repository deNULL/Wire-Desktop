package ru.denull.wire.lib;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.IGEBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Random;
import java.util.zip.CRC32;

public class CryptoUtils {
    public final static String TAG = "CryptoUtils";

    /* Crypto functions */
    public static int CRC32(byte[] buffer) {
        CRC32 crc = new CRC32();
        crc.update(buffer);
        return (int) crc.getValue();
    }

    public static int CRC32(String str) {
        return (int) CRC32(str.getBytes());
    }

    public static byte[] SHA1(byte[] buf) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        return sha1.digest(buf);
    }

    public static byte[] SHA1(ByteBuffer buf, int offset, int size) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        for (int i = offset; i < offset + size; i++) {
            sha1.update(buf.get(i));
        }
        return sha1.digest();
    }

    public static byte[] RSAEncrypt(byte[] buf, BigInteger modulus, BigInteger pubExp) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(modulus, pubExp);
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
        Cipher rsa = Cipher.getInstance("RSA/ECB/NoPadding");
        rsa.init(Cipher.ENCRYPT_MODE, key);

        return rsa.doFinal(buf);
    }

    public static byte[] AES(boolean encrypt, Object buf, int offset, int size, byte[] key, byte[] iv) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        BufferedBlockCipher cipher = new BufferedBlockCipher(new IGEBlockCipher(new AESEngine()));
        cipher.init(encrypt, new ParametersWithIV(new KeyParameter(key), iv));
        byte[] answer = new byte[cipher.getOutputSize(size)];
        byte[] buffer = null;
        if (buf instanceof byte[]) {
            buffer = (byte[]) buf;
        } else {
            buffer = new byte[((ByteBuffer) buf).capacity()];
            ((ByteBuffer) buf).rewind();
            ((ByteBuffer) buf).get(buffer);
        }
        int len = cipher.processBytes(buffer, offset, size, answer, 0);
        cipher.doFinal(answer, len);

        return answer;
    }

    public static byte[] AESEncrypt(Object buf, int offset, int size, byte[] key, byte[] iv) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        return AES(true, buf, offset, size, key, iv);
    }

    public static byte[] AESDecrypt(Object buf, int offset, int size, byte[] key, byte[] iv) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        return AES(false, buf, offset, size, key, iv);
    }

    /* Misc utils */
    public static byte[] substr(byte[] array, int start, int count) {
        return Arrays.copyOfRange(array, start, start + count);
    }

    public static byte[] concat(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }
        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }
    // returns such a prime number that x is divisible by
    // bad: 1524705608009140637 (shanks), 2291122014370375721

    // 2012708483660954293
    public static BigInteger factor(BigInteger x) {
        //Log.i(TAG, "Factorisation started (" + x + ")...");
        if (x.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) < 0) {
            //Log.i(TAG, "Shanks result: " + factor_shanks(x.longValue()));
            //Log.i(TAG, "Fermat result: " + factor_fermat(x.longValue()));
            //Log.i(TAG, "Pollard result: " + factor_pollard(x.longValue()));

            //return BigInteger.valueOf(factor_shanks(x.longValue()));

            return BigInteger.valueOf(findSmallMultiplierLopatin(x.longValue()));
        }
        Log.w(TAG, "Using long arithmetics");

        long k = 1;
        while (true) {
            BigInteger n = x.multiply(BigInteger.valueOf(k));

            BigInteger sq = sqrt(n);
            BigInteger p0 = sq;
            if (p0.multiply(p0).equals(n)) {
                return p0;
            }


            BigInteger q0 = BigInteger.ONE;
            BigInteger q1 = n.subtract(p0.multiply(p0));

            BigInteger sq_q1 = sqrt(q1);
            while (!sq_q1.multiply(sq_q1).equals(q1)) {
                BigInteger b1 = sq.add(p0).divide(q1);
                BigInteger p1 = b1.multiply(q1).subtract(p0);
                BigInteger q2 = q0.add(b1.multiply(p0.subtract(p1)));

                p0 = p1;
                q0 = q1;
                q1 = q2;
                sq_q1 = sqrt(q1);
            }

            BigInteger b0 = sq.subtract(p0).divide(sq_q1);
            p0 = b0.multiply(sq_q1).add(p0);
            q0 = sq_q1;
            q1 = n.subtract(p0.multiply(p0)).divide(q0);

            while (true) {
                BigInteger b1 = sq.add(p0).divide(q1);
                BigInteger p1 = b1.multiply(q1).subtract(p0);
                BigInteger q2 = q0.add(b1.multiply(p0.subtract(p1)));

                if (p1.equals(p0)) break;
                p0 = p1;
                q0 = q1;
                q1 = q2;
            }

            BigInteger ans = x.gcd(p0);
            if (!ans.equals(BigInteger.ONE) && !ans.equals(x)) return ans;

            k++;
        }
    }

    public static long factor_shanks(long x) {
        long start_time = System.currentTimeMillis();

        if (isPerfectSquare(x)) {
            return (long) Math.sqrt(x);
        }

        long k = 1;
        //if (x % 4 == 1) k = 2;

        while (true) {
            long n = k * x;

			/*long sq = (long) Math.sqrt(n);
            long p0 = sq;
			long q0 = 1;
			long q1 = n - p0 * p0;
	
			while (!isPerfectSquare(q1)) {
				long b1 = (sq + p0) / q1;
				long p1 = (b1 * q1) - p0;
				long q2 = q0 + b1 * (p0 - p1);
				
				p0 = p1; q0 = q1; q1 = q2;
			}

			long sq_q1 = (long) Math.sqrt(q1);
			long b0 = (sq - p0) / sq_q1;
			p0 = (b0 * sq_q1) + p0;
			q0 = sq_q1;
			q1 = (n - p0 * p0) / q0;
			
			while (true) {
				long b1 = (sq + p0) / q1;
				long p1 = (b1 * q1) - p0;
				long q2 = q0 + b1 * (p0 - p1);
				
				if (p0 == p1) break;
				p0 = p1; q0 = q1; q1 = q2;
			}
			
			long ans = gcd(p0, x);*/

            long sq = (long) Math.sqrt(n);
            long P0 = 0;
            long Q0 = 1;
            long r0 = sq;
            long P1 = sq;
            long Q1 = n - r0 * r0;
            long r1 = 2 * r0 / Q1;

            while (!isPerfectSquare(Q1)) {
                long P2 = r1 * Q1 - P1;
                long Q2 = Q0 + (P1 - P2) * r1;
                long r2 = (P2 + sq) / Q2;

                P0 = P1;
                P1 = P2;
                Q0 = Q1;
                Q1 = Q2;
                r1 = r2;
            }

            P0 = -P1;
            Q0 = (long) Math.sqrt(Q1);
            r0 = (P0 + sq) / Q0;
            P1 = r0 * Q0 - P0;
            Q1 = (n - P1 * P1) / Q0;
            r1 = (P1 + sq) / Q1;

            while (P1 != P0) {
                long P2 = r1 * Q1 - P1;
                long Q2 = Q0 + (P1 - P2) * r1;
                long r2 = (P2 + sq) / Q2;

                P0 = P1;
                P1 = P2;
                Q0 = Q1;
                Q1 = Q2;
                r1 = r2;
            }

            long ans = gcd(x, Q0);
            //Log.i(TAG, "P0=" + P0 + ", Q0=" + Q0 + ", Q1=" + Q1);

            if (ans != 1 && ans != x) {
                //Log.d(TAG, "x % 4 = " + (x % 4) + ", used k = " + k);
                Log.i(TAG, "Shanks took " + (System.currentTimeMillis() - start_time) + "ms");
                return ans;
            }

            if (k > 30) {
                return factor_pollard(x);
            }

            k++;
        }
    }

    public static long factor_pollard(long n) {
        long start_time = System.currentTimeMillis();
        long x = 1 + (long) (Math.random() * (n - 3));
        long y = 1, i = 0, stage = 2;

        while (gcd(n, Math.abs(x - y)) == 1) {
            if (i == stage) {
                y = x;
                stage <<= 1;
            }

            BigInteger t = BigInteger.valueOf(x);
            x = t.multiply(t).add(BigInteger.ONE).mod(BigInteger.valueOf(n)).longValue();
            // x = (x * x + 1) % n;
            i++;
        }

        Log.i(TAG, "Pollard took " + (System.currentTimeMillis() - start_time) + "ms");
        return gcd(n, Math.abs(x - y));
    }

    public static long factor_fermat(long n) {
        long start_time = System.currentTimeMillis();
        long x = (long) Math.sqrt(n);
        long y = 0;
        long r = x * x - y * y - n;

        while (true) {
            if (r == 0) {
                Log.i(TAG, "Fermat took " + (System.currentTimeMillis() - start_time) + "ms");
                return (x != y) ? (x - y) : (x + y);
            } else if (r > 0) {
                r -= y + y + 1;
                y++;
            } else {
                r += x + x + 1;
                x++;
            }
        }
    }

    public static BigInteger sqrt(BigInteger x) {
        if (x.compareTo(BigInteger.ZERO) < 0) {
            Log.e(TAG, "Invalig argument for sqrt");
        }

        if (x.equals(BigInteger.ZERO) || x.equals(BigInteger.ONE)) {
            return x;
        }

        BigInteger two = BigInteger.valueOf(2L);
        BigInteger y;

        for (y = x.divide(two); y.compareTo(x.divide(y)) > 0; y = ((x.divide(y)).add(y)).divide(two)) ;

        return y;
    }

    public static long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % t;
            a = t;
        }
        return a;
    }

    private final static boolean isPerfectSquare(long n) {
        if (n < 0)
            return false;

        switch ((int) (n & 0x3F)) {
            case 0x00:
            case 0x01:
            case 0x04:
            case 0x09:
            case 0x10:
            case 0x11:
            case 0x19:
            case 0x21:
            case 0x24:
            case 0x29:
            case 0x31:
            case 0x39:
                long sqrt;
                if (n < 410881L) {
                    //John Carmack hack, converted to Java.
                    // See: http://www.codemaestro.com/reviews/9
                    int i;
                    float x2, y;

                    x2 = n * 0.5F;
                    y = n;
                    i = Float.floatToRawIntBits(y);
                    i = 0x5f3759df - (i >> 1);
                    y = Float.intBitsToFloat(i);
                    y = y * (1.5F - (x2 * y * y));

                    sqrt = (long) (1.0F / y);
                } else {
                    //Carmack hack gives incorrect answer for n >= 410881.
                    sqrt = (long) Math.sqrt(n);
                }
                return sqrt * sqrt == n;

            default:
                return false;
        }
    }

    public static void debug(Object... rest) {
  	/*for (Object o : rest) {
  		if (o instanceof ByteBuffer) {
  			for (int i = 0; i < ((ByteBuffer) o).capacity(); i++) {
          System.out.print(String.format("%2x ", ((ByteBuffer) o).get(i)));
          if (i % 16 == 15) System.out.println();
        }
        System.out.println();
  		} else
  		if (o instanceof byte[]) {
  			for (int i = 0; i < ((byte[]) o).length; i++) {
          System.out.print(String.format("%2x ", ((byte[]) o)[i]));
          if (i % 16 == 15) System.out.println();
        }
        System.out.println();
  		} else
			if (o instanceof long[]) {
  			for (int i = 0; i < ((long[]) o).length; i++) {
          System.out.print(((long[]) o)[i] + " ");
          if (i % 16 == 15) System.out.println();
        }
        System.out.println();
  		} else {
  			System.out.println(o);
  		}
  	}*/
    }

    public static BigInteger unsignedBigInt(byte[] buffer) {
        byte[] buf2 = new byte[buffer.length + 1];
        System.arraycopy(buffer, 0, buf2, 1, buffer.length);
        return new BigInteger(buf2);
    }

    public static long GCD(long a, long b) {
        while (a != 0 && b != 0) {
            while ((b & 1) == 0) {
                b >>= 1;
            }
            while ((a & 1) == 0) {
                a >>= 1;
            }
            if (a > b) {
                a -= b;
            } else {
                b -= a;
            }
        }
        return b == 0 ? a : b;
    }

    public static long findSmallMultiplierLopatin(long what) {
        Random r = new Random();
        long g = 0;
        int it = 0;
        for (int i = 0; i < 3; i++) {
            int q = (r.nextInt(128) & 15) + 17;
            long x = r.nextInt(1000000000) + 1, y = x;
            int lim = 1 << (i + 18);
            for (int j = 1; j < lim; j++) {
                ++it;
                long a = x, b = x, c = q;
                while (b != 0) {
                    if ((b & 1) != 0) {
                        c += a;
                        if (c >= what) {
                            c -= what;
                        }
                    }
                    a += a;
                    if (a >= what) {
                        a -= what;
                    }
                    b >>= 1;
                }
                x = c;
                long z = x < y ? y - x : x - y;
                g = GCD(z, what);
                if (g != 1) {
                    break;
                }
                if ((j & (j - 1)) == 0) {
                    y = x;
                }
            }
            if (g > 1) {
                break;
            }
        }

        long p = what / g;
        return Math.min(p, g);
    }
}
