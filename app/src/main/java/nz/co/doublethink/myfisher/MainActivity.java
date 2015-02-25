package nz.co.doublethink.myfisher;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import nz.co.doublethink.myfisher.tools.CardModel;
import nz.co.doublethink.myfisher.tools.CardThread;
import nz.co.doublethink.myfisher.tools.NfcReader;
import nz.co.doublethink.myfisher.tools.Toolbox;


public class MainActivity extends Activity {

    public final static String EXTRA_MESSAGE = "nz.co.doublethink.myfisher.MESSAGE";
    private NfcAdapter nfcAdapter;
    private TextView textview;
    private CardModel cardModel;
    private NfcReader nfcReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textview = (TextView) findViewById(R.id.nfc_check);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // check if phone is nfc capable
        if (nfcAdapter == null) {
            Toast.makeText(this, "This device does not support NFC", Toast.LENGTH_LONG).show();
            finish();
        }

        if (!nfcAdapter.isEnabled()) {
            textview.setText(R.string.enable_nfc);
        } else {
            handleIntent(getIntent());
        }
    }

    private void handleIntent(Intent intent) {
        if (intent.getAction().equals(nfcAdapter.ACTION_TECH_DISCOVERED)){
            IsoDep isoDep = Toolbox.getIsoDep(intent);

            if(isoDep != null){
                nfcReader = new NfcReader(isoDep);
                cardModel = new CardModel(this);
                new CardThread(2, nfcReader, cardModel).start();
            } else {
                setTextview("Something went wrong with IsoDep");
            }
        } else {
            textview.setText(R.string.place_tag);
        }
    }

    public void setTextview(String text) {textview.setText(text);}

    public void startResult(String result) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(EXTRA_MESSAGE, result);
        startActivity(intent);
    }
}
