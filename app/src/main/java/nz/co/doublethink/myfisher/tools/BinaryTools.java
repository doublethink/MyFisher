package nz.co.doublethink.myfisher.tools;

/**
 * Created by Stephen on 23/02/15.
 */
public class BinaryTools {

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static byte[] concat(final byte[]... arrays) {
        int length = 0;

        for (final byte[] array : arrays) {
            length += array.length;
        }

        final byte[] result = new byte[length];
        int pos = 0;

        for (final byte[] array : arrays) {
            System.arraycopy(array, 0, result, pos, array.length);
            pos += array.length;
        }

        return result;
    }

    public static int readInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
        return (int)readLong(paramArrayOfByte, paramInt1, paramInt2);
    }

    public static long readLong(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
        long l = 0L;
        int i = paramInt2;
        int m;
        for (int j = paramInt1; ; j = m)
        {
            int k = i - 1;
            if (i == 0)
                return l;
            m = j + 1;
            int n = 0xFF & paramArrayOfByte[j];
            l = l << 8 | n;
            i = k;
        }
    }

    public static String prettyBCD(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    {
        int i = paramInt3 >> 1;
        StringBuilder localStringBuilder = new StringBuilder();
        while (true)
        {
            if (paramInt2 <= i)
            {
                if (paramInt2 > 0)
                {
                    if (localStringBuilder.length() != 0)
                        localStringBuilder.append(' ');
                    localStringBuilder.append(encode(paramArrayOfByte, paramInt1, paramInt2));
                }
                return localStringBuilder.toString();
            }
            if (localStringBuilder.length() != 0)
                localStringBuilder.append(' ');
            localStringBuilder.append(encode(paramArrayOfByte, paramInt1, i));
            paramInt1 += i;
            paramInt2 -= i;
        }
    }

    public static String encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
        StringBuffer localStringBuffer = new StringBuffer(paramInt2 * 2);
        int i = paramInt2;
        int m;
        for (int j = paramInt1; ; j = m)
        {
            int k = i - 1;
            if (i == 0)
                return localStringBuffer.toString();
            m = j + 1;
            int n = paramArrayOfByte[j];
            append(localStringBuffer, (byte)(0xF & n >> 4));
            append(localStringBuffer, (byte)(n & 0xF));
            i = k;
        }
    }

    public static String byteToHex(byte[] bytes, int paramInt1, int paramInt2)
    {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = paramInt1; j < paramInt2; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    static void append(StringBuffer paramStringBuffer, byte paramByte)
    {
        if (paramByte > 9);
        for (int i = paramByte + 48; ; i = paramByte + 48)
        {
            paramStringBuffer.append((char)i);
            return;
        }
    }

    public static byte[] decode(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static void writeInt(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
    {
        paramInt1 += paramInt2;
        for (int i = paramInt2; ; i = paramInt2) {
            paramInt2 = i + -1;
            if (i == 0)
                return;
            paramInt1 += -1;
            int j = (byte) paramInt3;
            paramArrayOfByte[paramInt1] = (byte)j;
            paramInt3 >>= 8;
        }
    }
}
