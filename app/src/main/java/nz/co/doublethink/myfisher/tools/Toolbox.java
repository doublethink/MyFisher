package nz.co.doublethink.myfisher.tools;

import android.content.Intent;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Parcelable;

/**
 * Created by root on 19/02/15.
 */
public class Toolbox {

    public static IsoDep getIsoDep(Intent intent) {
        IsoDep isoDep = null;
        // Will not bother parsing tag to see if compatible with app
        Parcelable parcelable = intent.getParcelableExtra("android.nfc.extra.TAG");
        if (parcelable != null) {
            Tag nfcTag = (Tag)parcelable;
            if (nfcTag != null) {
                isoDep = IsoDep.get(nfcTag);
            }
        }
        return isoDep;
    }
}
