package utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by valen on 05/08/2016.
 */
public class Constants
{
    public static final String SECURITY_CODE_URL = "http://192.168.43.190/webservice/controlador/securityCodeVerification.php";
    public static final String CHANGE_CODE_URL = "http://192.168.43.190/login/controlador/login.php";
    public static final String CHANGE_PASSWORD_URL = "http://192.168.43.190/webservice/controlador/changePass.php";
    public static final String SESSION_URL = "http://192.168.43.190/webservice/controlador/login.php";
    public static final String SESSION_OUT_URL = "http://192.168.43.190/webservice/controlador/logout.php";
    public static final String TEMPERATURE_URL = "http://192.168.43.190/webservice/controlador/consultarTem.php";

    public static final String URL_CAM = "http://192.168.43.113/mjpeg.cgi";
    public static final String DOOR_CLOSE = "http://192.168.43.70:8081/DOOR=OPEN";
    public static final String DOOR_OPEN = "http://192.168.43.70:8081/DOOR=CLOSE";
    public static final String LIGHT_ON = "http://192.168.43.70:8081/LED=ON";
    public static final String LIGHT_OFF = "http://192.168.43.70:8081/LED=OFF";

    public static AlertDialog mostrarAlertDialog(Activity activity, String mensaje, String titulo, int icono)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage(mensaje);
        builder1.setIcon(icono);
        builder1.setTitle(titulo);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder1.create();
        return alertDialog;
    }

}
