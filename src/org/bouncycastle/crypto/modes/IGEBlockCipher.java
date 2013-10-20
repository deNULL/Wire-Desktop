package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class IGEBlockCipher implements BlockCipher {
  private byte[] x0, y0;
  private byte[] xPrev;
  private byte[] yPrev;

  private int blockSize;
  private BlockCipher cipher = null;
  private boolean encrypting;

  /**
   * Basic constructor.
   * 
   * @param cipher the block cipher to be used as the basis of chaining.
   */
  public IGEBlockCipher(BlockCipher cipher) {
    this.cipher = cipher;
    this.blockSize = cipher.getBlockSize();

    this.x0 = new byte[blockSize];
    this.y0 = new byte[blockSize];
    this.xPrev = new byte[blockSize];
    this.yPrev = new byte[blockSize];
  }

  /**
   * return the underlying block cipher that we are wrapping.
   * 
   * @return the underlying block cipher that we are wrapping.
   */
  public BlockCipher getUnderlyingCipher() {
    return cipher;
  }

  /**
   * Initialise the cipher and, possibly, the initialisation vector (IV). If an
   * IV isn't passed as part of the parameter, the IV will be all zeros.
   * 
   * @param encrypting if true the cipher is initialised for encryption, if false for decryption.
   * @param params the key and other data required by the cipher.
   * @exception IllegalArgumentException if the params argument is inappropriate.
   */
  public void init(boolean encrypting, CipherParameters params)
      throws IllegalArgumentException {
    boolean oldEncrypting = this.encrypting;

    this.encrypting = encrypting;

    if (params instanceof ParametersWithIV) {
      ParametersWithIV ivParam = (ParametersWithIV) params;
      byte[] iv = ivParam.getIV();

      if (iv.length != blockSize * 2) {
        throw new IllegalArgumentException(
            "initialisation vector must be twice as long as block size");
      }

      System.arraycopy(iv, 0, x0, 0, x0.length);
      System.arraycopy(iv, x0.length, y0, 0, y0.length);

      reset();

      // if null it's an IV changed only.
      if (ivParam.getParameters() != null) {
        cipher.init(encrypting, ivParam.getParameters());
      } else if (oldEncrypting != encrypting) {
        throw new IllegalArgumentException(
            "cannot change encrypting state without providing key.");
      }
    } else {
      reset();

      // if it's null, key is to be reused.
      if (params != null) {
        cipher.init(encrypting, params);
      } else if (oldEncrypting != encrypting) {
        throw new IllegalArgumentException(
            "cannot change encrypting state without providing key.");
      }
    }
  }

  /**
   * return the algorithm name and mode.
   * 
   * @return the name of the underlying algorithm followed by "/IGE".
   */
  public String getAlgorithmName() {
    return cipher.getAlgorithmName() + "/IGE";
  }

  /**
   * return the block size of the underlying cipher.
   * 
   * @return the block size of the underlying cipher.
   */
  public int getBlockSize() {
    return cipher.getBlockSize();
  }

  /**
   * Process one block of input from the array in and write it to the out array.
   * 
   * @param in the array containing the input data.
   * @param inOff offset into the in array the data starts at.
   * @param out the array the output data will be copied into.
   * @param outOff the offset into the out array the output will start at.
   * @exception DataLengthException if there isn't enough data in in, or space in out.
   * @exception IllegalStateException if the cipher isn't initialised.
   * @return the number of bytes processed and produced.
   */
  public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
      throws DataLengthException, IllegalStateException {
    return (encrypting) ? encryptBlock(in, inOff, out, outOff) : decryptBlock(
        in, inOff, out, outOff);
  }

  /**
   * reset the chaining vector back to the IV and reset the underlying cipher.
   */
  public void reset() {
    System.arraycopy(x0, 0, xPrev, 0, x0.length);
    System.arraycopy(y0, 0, yPrev, 0, y0.length);

    cipher.reset();
  }

  /**
   * Do the appropriate chaining step for IGE mode encryption.
   * 
   * @param in the array containing the data to be encrypted.
   * @param inOff offset into the in array the data starts at.
   * @param out the array the encrypted data will be copied into.
   * @param outOff the offset into the out array the output will start at.
   * @exception DataLengthException if there isn't enough data in in, or space in out.
   * @exception IllegalStateException if the cipher isn't initialised.
   * @return the number of bytes processed and produced.
   */
  private int encryptBlock(byte[] in, int inOff, byte[] out, int outOff)
      throws DataLengthException, IllegalStateException {
    if ((inOff + blockSize) > in.length) {
      throw new DataLengthException("input buffer too short");
    }

    for (int i = 0; i < blockSize; i++) {
      xPrev[i] ^= in[inOff + i];
    }

    int length = cipher.processBlock(xPrev, 0, out, outOff);
    
    for (int i = 0; i < blockSize; i++) {
      out[outOff + i] ^= yPrev[i];
    }

    System.arraycopy(in, inOff, yPrev, 0, yPrev.length);
    System.arraycopy(out, outOff, xPrev, 0, xPrev.length);

    return length;
  }

  /**
   * Do the appropriate chaining step for IGE mode decryption.
   * 
   * @param in the array containing the data to be decrypted.
   * @param inOff offset into the in array the data starts at.
   * @param out the array the decrypted data will be copied into.
   * @param outOff the offset into the out array the output will start at.
   * @exception DataLengthException if there isn't enough data in in, or space in out.
   * @exception IllegalStateException if the cipher isn't initialised.
   * @return the number of bytes processed and produced.
   */
  private int decryptBlock(byte[] in, int inOff, byte[] out, int outOff)
      throws DataLengthException, IllegalStateException {
    if ((inOff + blockSize) > in.length) {
      throw new DataLengthException("input buffer too short");
    }

    for (int i = 0; i < blockSize; i++) {
      yPrev[i] ^= in[inOff + i];
    }

    int length = cipher.processBlock(yPrev, 0, out, outOff);

    for (int i = 0; i < blockSize; i++) {
      out[outOff + i] ^= xPrev[i];
    }

    System.arraycopy(out, outOff, yPrev, 0, yPrev.length);
    System.arraycopy(in, inOff, xPrev, 0, xPrev.length);

    return length;
  }
}
