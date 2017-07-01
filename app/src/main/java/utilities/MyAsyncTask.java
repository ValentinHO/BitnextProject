package utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.valen.proyectobitnext.MainActivity;
import com.example.valen.proyectobitnext.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import fragments.FragmentTemperatura;

/**
 * Created by valen on 05/08/2016.
 */
public class MyAsyncTask extends AsyncTask<String,String,String>
{
    private ArrayList<String> mdatos;
    private String mlink;
    private Activity mactivity;
    String id,nombre,apellidoP,apellidoM,statusS,temp,hum;

    public MyAsyncTask(ArrayList<String> datos,String link,Activity activity)
    {
        this.mdatos = datos;
        this.mlink = link;
        this.mactivity=activity;
    }

    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            Looper.prepare();
            String data = condiciones();
            if(!data.equals("Exception"))
            {
                URL url = new URL(mlink);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write(data);
                wr.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                return sb.toString();
            }
            else{
                return "Problemas al conectar con el Servidor.";
            }
        }

        catch(Exception e)
        {
            return new String("Exception: "+e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        validacion(s);
    }

    private String condiciones()
    {
        String data=null;
        try {
            if (mdatos.size() == 2) {
                String username = mdatos.get(0);
                String password = mdatos.get(1);
                data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                Toast.makeText(mactivity, username, Toast.LENGTH_SHORT).show();
            } else if (mdatos.size() == 1) {
                String code = mdatos.get(0);
                data = URLEncoder.encode("codigo", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8");
            } else if (mdatos.size() == 3) {
                String id = mdatos.get(0);
                data = URLEncoder.encode("ids", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
            } else if (mdatos.size() == 4) {
                String passwCurrent = mdatos.get(0);
                String passwNew = mdatos.get(1);
                String idUser = mdatos.get(2);
                data = URLEncoder.encode("CurrentPass", "UTF-8") + "=" + URLEncoder.encode(passwCurrent, "UTF-8");
                data += "&" + URLEncoder.encode("NewPass", "UTF-8") + "=" + URLEncoder.encode(passwNew, "UTF-8");
                data += "&" + URLEncoder.encode("idUser", "UTF-8") + "=" + URLEncoder.encode(idUser, "UTF-8");
            } else if(mdatos.size() == 5) {
                String vari = mdatos.get(0);
                data = URLEncoder.encode("dato1", "UTF-8") + "=" + URLEncoder.encode(vari, "UTF-8");
            }
            return data;
        }
        catch(Exception ex)
        {
            return "Exception";
        }
    }


    private void validacion(String result)
    {
        if(mdatos.size()==2)//VERIFICACION DE LOGIN
        {
            try
            {
                JSONArray datos = new JSONArray(result);
                if(datos.length() > 0)
                {
                    for(int i=0; i<datos.length(); i++){
                        JSONObject obj = datos.getJSONObject(i);

                        id = obj.getString("idEmple");
                        nombre = obj.getString("nameEmple");
                        apellidoP = obj.getString("firstName");
                        apellidoM = obj.getString("lastName");
                        statusS = obj.getString("statuSesion");
                    }

                    if(id.length()>0)
                    {
                        Log.d("Entra aqui:",nombre+" "+statusS);
                        Intent intent = new Intent(mactivity,MainActivity.class);
                        intent.putExtra("idUser"," "+id);
                        intent.putExtra("nombreUser",nombre+" "+apellidoP+" "+apellidoM);
                        intent.putExtra("estadoSesion","1");
                        mactivity.startActivity(intent);
                        mactivity.finish();
                        statusS = null;
                    }
                    else
                    {
                        Toast.makeText(mactivity.getBaseContext(),"Error al iniciar Activity",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(mactivity.getBaseContext(),"No se recibieron datos",Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception ex)
            {
                Toast.makeText(mactivity,"Usuario o contraseña incorrecta. ",Toast.LENGTH_SHORT).show();
            }
        }
        else if(mdatos.size()==1)//RESULTADO DE VERIFICAR CODIGO
        {
            codeV(result);
        }
        else if(mdatos.size()==3)//CERRAR SESIÓN
        {
            codeV(result);
        }
        else if(mdatos.size()==4)//CAMBIAR CONTRASEÑA
        {
            if(result.length()>3)
            {
                Toast.makeText(mactivity,result,Toast.LENGTH_SHORT).show();
            }
            else
            {
                Constants.mostrarAlertDialog(mactivity,
                        "La Contraseña ha cambiado satisfactoriamente, vuelva a iniciar sesión.","Successfully",
                        R.drawable.ic_launcher).show();
            }
        }

    }

    public void codeV(String response)
    {
        try
        {
            JSONArray datos = new JSONArray(response);
            if(datos.length() > 0)
            {
                for(int i=0; i<datos.length(); i++){
                    JSONObject obj = datos.getJSONObject(i);

                    id = obj.getString("idEmple");
                    nombre = obj.getString("nameEmple");
                    apellidoP = obj.getString("firstName");
                    apellidoM = obj.getString("lastName");
                    statusS = obj.getString("statuSesion");
                }

                if(id.length()>0)
                {
                    Log.d("Entra aqui:",nombre+" "+statusS);
                    Intent intent = new Intent(mactivity,MainActivity.class);
                    intent.putExtra("idUser"," "+id);
                    intent.putExtra("nombreUser",nombre+" "+apellidoP+" "+apellidoM);
                    intent.putExtra("estadoSesion",statusS);
                    mactivity.startActivity(intent);
                    mactivity.finish();

                }
                else
                {
                    Toast.makeText(mactivity.getBaseContext(),"Error al iniciar Activity",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(mactivity.getBaseContext(),"No se recibieron datos",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception ex)
        {
            if(mdatos.size()==1)
            {
                Toast.makeText(mactivity.getBaseContext(),"El código introducido no es correcto, intentalo de nuevo.",Toast.LENGTH_SHORT).show();
                mactivity.finish();
            }
            else if(mdatos.size()==3)
            {
                Toast.makeText(mactivity,"Error al cerrar sesión",Toast.LENGTH_SHORT).show();
            }
            else if(mdatos.size()==2)
            {
                Toast.makeText(mactivity,"Usuario o contraseña incorrecta. ",Toast.LENGTH_SHORT).show();
            }

        }
    }

}
