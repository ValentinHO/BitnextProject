package com.example.valen.proyectobitnext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import utilities.Constants;
import utilities.MyAsyncTask;

public class SecurityCode extends AppCompatActivity
{
    private AlertDialog.Builder alert;
    private EditText edit;
    private SharedPreferences pf;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_code);
        Log.d("TOKEN ID: ",""+ FirebaseInstanceId.getInstance().getToken());
        pf = getSharedPreferences("PreferenciasDrawer", Context.MODE_PRIVATE);
        alert = new AlertDialog.Builder(this);
        edit = new EditText(this);
        construirAlert();
        opcionesAlert();
        alert.show();
    }


    private void construirAlert()
    {

        alert.setTitle("Confirmación");
        alert.setMessage("Introduce el código de seguridad:");

        edit.setHint("******");
        edit.setWidth(450);
        edit.setBackgroundResource(R.drawable.shapemain);
        edit.setTransformationMethod(new PasswordTransformationMethod());

        RelativeLayout lay = new RelativeLayout(this);
        lay.addView(edit);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) edit.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        edit.setLayoutParams(lp);
        alert.setView(lay);
    }

    private void opcionesAlert()
    {
        alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                 String a = edit.getText().toString();
                pf.edit().putString("codigoS",a);
                pf.edit().commit();
                //enviarDatos(a);
                ArrayList<String> x = new ArrayList<String>();
                x.add(a);
                new MyAsyncTask(x, Constants.SECURITY_CODE_URL,SecurityCode.this).execute();
            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                SecurityCode.this.finish();
            }
        });
    }

}
