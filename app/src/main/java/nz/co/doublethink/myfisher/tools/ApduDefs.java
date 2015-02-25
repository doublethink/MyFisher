package nz.co.doublethink.myfisher.tools;

/**
 * Created by Stephen on 23/02/15.
 */
public class ApduDefs
{
    public static final byte[] AID_SNAPPER;
    public static final byte[] CARD_BALANCE;
    public static final byte[] CARD_INIT_LOAD;
    public static final byte[] CARD_READ_MARKET;
    public static final byte[] CARD_READ_MINUS;
    public static final byte[] CARD_READ_PURSE_INFO;
    public static final byte[] CARD_READ_TRANS;
    public static final byte[] GET_RESPONSE;
    public static final byte[] SELECT_SNAPPER_CARD;

    static
    {
        byte[] arrayOfByte1 = new byte[7];
        arrayOfByte1[0] = -44;
        arrayOfByte1[1] = 16;
        arrayOfByte1[4] = 3;
        arrayOfByte1[6] = 1;
        AID_SNAPPER = arrayOfByte1;
        byte[] arrayOfByte2 = new byte[12];
        arrayOfByte2[1] = -92;
        arrayOfByte2[2] = 4;
        arrayOfByte2[4] = 7;
        arrayOfByte2[5] = -44;
        arrayOfByte2[6] = 16;
        arrayOfByte2[9] = 3;
        arrayOfByte2[11] = 1;
        SELECT_SNAPPER_CARD = arrayOfByte2;
        byte[] arrayOfByte3 = new byte[5];
        arrayOfByte3[0] = -112;
        arrayOfByte3[1] = 76;
        arrayOfByte3[4] = 4;
        CARD_BALANCE = arrayOfByte3;
        byte[] arrayOfByte4 = new byte[5];
        arrayOfByte4[0] = -112;
        arrayOfByte4[1] = 120;
        arrayOfByte4[4] = 16;
        CARD_READ_MARKET = arrayOfByte4;
        byte[] arrayOfByte5 = new byte[5];
        arrayOfByte5[0] = -112;
        arrayOfByte5[1] = 64;
        arrayOfByte5[4] = 4;
        CARD_INIT_LOAD = arrayOfByte5;
        byte[] arrayOfByte6 = new byte[5];
        arrayOfByte6[1] = -80;
        arrayOfByte6[2] = -122;
        arrayOfByte6[4] = 66;
        CARD_READ_MINUS = arrayOfByte6;
        byte[] arrayOfByte7 = new byte[5];
        arrayOfByte7[1] = -78;
        arrayOfByte7[2] = 1;
        arrayOfByte7[3] = 20;
        arrayOfByte7[4] = 51;
        CARD_READ_PURSE_INFO = arrayOfByte7;
        byte[] arrayOfByte8 = new byte[5];
        arrayOfByte8[1] = -78;
        arrayOfByte8[2] = 1;
        arrayOfByte8[3] = 28;
        arrayOfByte8[4] = 52;
        CARD_READ_TRANS = arrayOfByte8;
        byte[] arrayOfByte9 = new byte[5];
        arrayOfByte9[1] = -64;
        GET_RESPONSE = arrayOfByte9;
    }
}