package nz.co.doublethink.myfisher.tools;

/**
 * Created by root on 23/02/15.
 */
public class Money {
    public static String toString(int paramInt) {

        int i = paramInt / 100;
        int j = paramInt % 100;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(i);
        arrayOfObject[1] = Integer.valueOf(j);
        return String.format("%d.%02d", arrayOfObject);
    }
}
