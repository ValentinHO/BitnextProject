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
public class FragmentDoors extends Fragment
{

    private static boolean presseddoor;
    private ImageButton puerta;
    ArrayList<String> datos = new ArrayList<>();
    private AsyncDoorLight asyncDoorLight;

    public FragmentDoors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //***********************Seteo de interfaz xml al fragment**********
        View v = inflater.inflate(R.layout.fragment_doors,container,false);
        //*********Obteniendo ActionBar para establecerle nuevo Titulo****************************************************************
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Doors");

        //************Inicializacion del ImageButton**********************************************************************************
        puerta = (ImageButton)v.findViewById(R.id.doors_button);


        //********************Restauracion de estado de imagen de la Puerta**************************************************************
        presseddoor = ((MainActivity) getActivity())
                .getSharedPreferences("PreferenciasDrawer", Context.MODE_PRIVATE)
                .getBoolean("fragmentosDoors",presseddoor);//Obtiene objeto booleano SharedPreferences del MainActivity

        if(presseddoor)
            puerta.setImageResource(R.drawable.open);
        else
            puerta.setImageResource(R.drawable.close);


        //****************************Establece un Listener al ImageButton de la puerta********************************************************
        puerta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(!presseddoor)
                {
                    puerta.setImageResource(R.drawable.open);
                    asyncDoorLight = new AsyncDoorLight(Constants.DOOR_OPEN);
                    asyncDoorLight.execute();
                    asyncDoorLight = null;
                }
                else
                {
                    puerta.setImageResource(R.drawable.close);
                    asyncDoorLight = new AsyncDoorLight(Constants.DOOR_CLOSE);
                    asyncDoorLight.execute();
                    asyncDoorLight = null;
                }


                presseddoor = !presseddoor;
            }
        });
        return v;
    }

    public boolean getBooleano(){return presseddoor;}

}
