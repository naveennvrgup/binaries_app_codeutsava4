package binaries.app.codeutsava.restapi.utils;

import android.text.Html;
import android.text.Spanned;

public class Misc {
    public static String getUpperForm(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static Spanned getHTML(String s){
        String[] temp = s.split(":");
        return Html.fromHtml("<b><font color=#000000>" + temp[0] + ":</font></b> " + ((temp.length > 1) ? temp[1] : " "));
    }
}
