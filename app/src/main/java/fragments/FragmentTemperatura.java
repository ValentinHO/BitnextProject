package fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
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

import utilities.Constants;
import utilities.MyAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTemperatura extends Fragment
{
    private TextView tvtemperatura;
    private TextView tvhumedad;
    private TextView tvalerta;
    private Button btnActualizar;
    private String humedad = "0.00";
    private String temperatura = "0.00";
    String temp,hum;


    public FragmentTemperatura() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_temperatura,container,false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Temperatura y Humedad");

        tvtemperatura = (TextView)v.findViewById(R.id.tvtemperaturaNum);
        tvhumedad = (TextView)v.findViewById(R.id.tvhumedadNum);
        tvalerta = (TextView)v.findViewById(R.id.tvAlerta);
        btnActualizar = (Button)v.findViewById(R.id.btntemperatura);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> datos = new ArrayList<String>();
                datos.add("1");
                new MyAsyncTaskTemp(datos, Constants.TEMPERATURE_URL,((MainActivity)getActivity())).execute();
            }
        });

        return v;
    }

    private class MyAsyncTaskTemp extends AsyncTask<String,String,String>
    {
        Activity mactivity;
        ArrayList<String> mdatos;
        String mlink;
        public MyAsyncTaskTemp(ArrayList<String> datos, String link, Activity activity)
        {
            this.mdatos = datos;
            this.mlink = link;
            this.mactivity = activity;
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                String data = null;
                String vari = mdatos.get(0);
                data = URLEncoder.encode("dato1", "UTF-8") + "=" + URLEncoder.encode(vari, "UTF-8");

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
            }catch(Exception e)
            {
                return new String(e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            codeV2(s);
        }
    }

    public void codeV2(String response)
    {
        try
        {
            JSONArray datos = new JSONArray(response);
            if(datos.length() > 0)
            {
                for(int i=0; i<datos.length(); i++){
                    JSONObject obj = datos.getJSONObject(i);

                    humedad = obj.getString("Humedad");
                    temperatura = obj.getString("Temperatura");
                }

                if(temperatura.length()>0)
                {
                    tvtemperatura.setText(temperatura+" °C");
                    tvhumedad.setText(humedad+"%");

                    Float hu = Float.parseFloat(humedad);
                    Float te = Float.parseFloat(temperatura);

                    if(te > 25 && hu > 55)
                    {
                        tvalerta.setText("¡Atención! Temperatura por arriba de los 25°C y humedad mayor al 55% permitido.");
                        tvalerta.setTextColor(Color.RED);
                    }else if(te< 20 && hu< 40)
                    {
                        tvalerta.setText("¡Atención! Temperatura por debajo de los 20°C y humedad menor al 40% permitido.");
                        tvalerta.setTextColor(Color.RED);
                    }else if(te> 25 && (hu< 55 || hu> 40))
                    {
                        tvalerta.setText("¡Atención! Temperatura por arriba de los 25°C.");
                        tvalerta.setTextColor(Color.RED);
                    }else if(hu> 55 && (te< 25 || te> 20))
                    {
                        tvalerta.setText("¡Atención! Humedad mayor al 55% permitido.");
                        tvalerta.setTextColor(Color.RED);
                    }else if(te< 20 && (hu< 55 || hu> 40))
                    {
                        tvalerta.setText("¡Atención! Temperatura por debajo de los 20°C.");
                        tvalerta.setTextColor(Color.RED);
                    }else if(hu < 40 && (te< 25 || te> 20))
                    {
                        tvalerta.setText("¡Atención! Humedad menor al 40% permitido.");
                        tvalerta.setTextColor(Color.RED);
                    }else
                    {
                        tvalerta.setText("Todo funciona con normalidad.");
                        tvalerta.setTextColor(Color.GREEN);
                    }
                }
                else
                {
                    Toast.makeText((MainActivity)getActivity(),"Error al iniciar Activity",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText((MainActivity)getActivity(),"No se recibieron datos",Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception ex)
        {
            Toast.makeText((MainActivity)getActivity(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }


}
