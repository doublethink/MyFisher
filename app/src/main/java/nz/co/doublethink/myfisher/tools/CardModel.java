package nz.co.doublethink.myfisher.tools;

import nz.co.doublethink.myfisher.MainActivity;

/**
 * Created by Stephen on 23/02/15.
 */
public class CardModel {

    static final byte[] nullBytes = null;
    public MainActivity mainActivity = null;
    public byte[] purseInfo = nullBytes;
    public int cardType = 0;
    public byte[] balance = nullBytes;
    public byte[] trans = nullBytes;
    public byte[][] market = new byte[16][];
    public byte[] minus = nullBytes;
    public byte[] initData = nullBytes;
    public byte[] writeBalance = nullBytes;


    public CardModel(MainActivity activity) {
        this.mainActivity = activity;
    }
}
