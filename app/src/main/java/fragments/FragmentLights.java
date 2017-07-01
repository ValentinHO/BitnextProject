package fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.valen.proyectobitnext.MainActivity;
import com.example.valen.proyectobitnext.R;

import java.util.ArrayList;

import utilities.AsyncDoorLight;
import utilities.Constants;
import utilities.MyAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLights extends Fragment
{
    private static boolean pressedlight;
    private ImageButton luz;
    ArrayList<String> datos = new ArrayList<>();
    private AsyncDoorLight asyncDoorLight;

    public FragmentLights() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lights, container, false);

        //**************Obtiene ActionBar para asignarle nuevo titulo*******************************************************
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Lights");

        //**********************Inicializacion de ImageButton Luces*********************************************************************
        luz = (ImageButton)v.findViewById(R.id.lights_button);

        //****************************Restauracion del ImageButton de Luces
        pressedlight = ((MainActivity) getActivity())
                .getSharedPreferences("PreferenciasDrawer", Context.MODE_PRIVATE)
                .getBoolean("fragmentosLights",pressedlight);//Obtiene variable booleana de SharedPreferences del MainActivity

        if(pressedlight)
            luz.setImageResource(R.drawable.encendido);
        else
            luz.setImageResource(R.drawable.apagado);

        //****************************Establece Listener a ImageButton Luz****************************************************
        luz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!pressedlight)
                {
                    luz.setImageResource(R.drawable.encendido);
                    asyncDoorLight = new AsyncDoorLight(Constants.LIGHT_ON);
                    asyncDoorLight.execute();
                    asyncDoorLight = null;
                }
                else
                {
                    luz.setImageResource(R.drawable.apagado);
                    asyncDoorLight = new AsyncDoorLight(Constants.LIGHT_OFF);
                    asyncDoorLight.execute();
                    asyncDoorLight = null;
                }
                pressedlight = !pressedlight;
            }
        });
        return v;
    }

    public boolean getBooleano(){return pressedlight;}

}
