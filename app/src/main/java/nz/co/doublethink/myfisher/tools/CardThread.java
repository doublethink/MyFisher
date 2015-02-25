package nz.co.doublethink.myfisher.tools;

import android.content.Intent;
import android.widget.Toast;

import java.io.IOException;
import java.math.BigInteger;

import biz.source_code.base64Coder.Base64Coder;
import nz.co.doublethink.myfisher.MainActivity;
import nz.co.doublethink.myfisher.ResultActivity;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Stephen on 19/02/15.
 */
public class CardThread extends Thread {

    private int state = 1;
    private NfcReader nfcReader = null;
    private CardModel cardModel = null;

    public CardThread(int cardState, NfcReader reader, CardModel model) {
        this.state = cardState;
        this.nfcReader = reader;
        this.cardModel = model;
    }

    public void run(){
        switch (getTaskState()) {
            case 1:
                break;
            case 2: //initial read balance
                try {
                    doCardRead();
                    createResultActivity();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3: //topup state
                try {
                    doCardRead();
                    System.out.println("Finished reading");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private byte[] decode(String response) {
        byte[] basedecoded = Base64Coder.decode(response);
        int i = basedecoded.length;
        StringBuilder localStringBuilder = new StringBuilder(i);
        int k;
        for (int j = 0; ; j = k)
        {
            if (j >= i)
                return BinaryTools.decode(localStringBuilder.toString());
            k = j + 1;
            localStringBuilder.append((char)basedecoded[j]);
        }
    }

    private void doCardRead() throws IOException{
        depConnect();
        // check if nfc card is snapper
        cardModel.purseInfo = transceive(ApduDefs.SELECT_SNAPPER_CARD);

        // if card is correct type get summary
        if (cardModel.purseInfo.length > 51 && cardModel.purseInfo[cardModel.purseInfo.length -2] == -112) {
            cardModel.cardType = 13;
            DoReadCardData();
        }
        depClose();
    }

    private void DoReadCardData() throws IOException{
        cardModel.balance = transceive(ApduDefs.CARD_BALANCE);
        cardModel.trans = transceive(ApduDefs.CARD_READ_TRANS);
        cardModel.minus = transceive(ApduDefs.CARD_READ_MINUS);
        byte[] initLoad = new byte[9];
        System.arraycopy(ApduDefs.CARD_INIT_LOAD, 0, initLoad, 0, 5);
        BinaryTools.writeInt(initLoad, 5, 4, 1000);
        cardModel.initData = transceive(initLoad);
        cardModel.writeBalance = transceive(decode("xxxxx"));
    }

    public byte[] transceive(byte[] paramArrayOfByte) throws IOException{
        byte[] arrayOfByte = null;
        if (nfcReader == null)
            throw new IOException("nfcReader is null");

        arrayOfByte = nfcReader.transceive(paramArrayOfByte);

        if (arrayOfByte == null)
            throw new IOException("transceive null response");

        return arrayOfByte;
    }

    private void depClose(){
        try {
            nfcReader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void depConnect() throws IOException {
        if (this.nfcReader == null)
            throw new IOException("nfcReader is null");
         else {
            try {
                this.nfcReader.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createResultActivity(){
        String initData = encode(cardModel.initData, 0, cardModel.initData.length);
        String writeBalance = encode(cardModel.writeBalance, 0, cardModel.writeBalance.length);
        String purseInfo = encode(cardModel.purseInfo, 0, cardModel.purseInfo.length);

        System.out.println("PurseInfo = " + purseInfo+ "\nInitData = " + initData + "\nWriteBalance = " + writeBalance);

        cardModel.mainActivity.startResult("Card Id: " + " " + BinaryTools.prettyBCD(cardModel.purseInfo, 8, 8, 4) +
                "\nBalance: $" + Money.toString(getBalance(cardModel)) + "");
    }

    private int getBalance(CardModel model) {
        int bal = 0;
        byte[] byteBalance = model.balance;
        if (byteBalance != null && model.balance.length >= 4) {
            bal = BinaryTools.readInt(model.balance, 0, 4);
        } else {
            System.out.println("Something wrong with balance");
        }
        return bal;
    }


    private int getTaskState() {
        return this.state;
    }

    public static String encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
        return base64string(BinaryTools.byteToHex(paramArrayOfByte, paramInt1, paramInt2));
    }

    public static String base64string(String paramString)
    {
        try
        {
            byte[] arrayOfByte = paramString.getBytes("UTF-8");
            String str = Base64Coder.encodeLines(arrayOfByte, 0, arrayOfByte.length, arrayOfByte.length << 1, "");
            return str;
        }
        catch (Exception localUnsupportedEncodingException)
        {
        }
        return null;
    }
}
