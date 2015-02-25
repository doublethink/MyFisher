package nz.co.doublethink.myfisher.tools;

import android.nfc.tech.IsoDep;

import java.io.IOException;

/**
 * Created by Stephen on 19/02/15.
 */
public class NfcReader {

    IsoDep dep;

    public NfcReader(IsoDep isoDep) {
        this.dep = isoDep;
    }

    public void connect() throws IOException{
        this.dep.connect();
    }

    public void close() throws IOException{

        if (dep != null)
            dep.close();
        dep = null;
    }

    public byte[] transceive(byte[] paramArrayOfByte) throws IOException {
        byte[] arrayOfByte = null;
        if (dep == null)
            throw new IOException("isoDep is null");

        arrayOfByte = dep.transceive(paramArrayOfByte);

        if ((arrayOfByte != null) && (arrayOfByte.length > 2) && (arrayOfByte[(arrayOfByte.length) -1] == -112))
        {
            byte[][] arrayOfByte1 = new byte[2][];
            arrayOfByte1[0] = arrayOfByte;
            arrayOfByte1[1] = new byte[1];
            arrayOfByte = BinaryTools.concat(arrayOfByte1);
        }
        return arrayOfByte;
    }
}
