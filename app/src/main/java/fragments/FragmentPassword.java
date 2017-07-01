package fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.valen.proyectobitnext.MainActivity;
import com.example.valen.proyectobitnext.R;

import java.util.ArrayList;

import utilities.Constants;
import utilities.MyAsyncTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPassword extends Fragment
{
    private Button change;
    private EditText password;
    private EditText passwordNew;
    private EditText passwordNewConfirm;
    private String idUser;
    //private SharedPreferences pf;


    public FragmentPassword() {
        // Required empty public constructor
    }

    public void idUsuario(String id)
    {
        this.idUser = id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_password,container,false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Password");

        password = (EditText)v.findViewById(R.id.edPass);
        passwordNew = (EditText)v.findViewById(R.id.edPassNew);
        passwordNewConfirm = (EditText)v.findViewById(R.id.edPassNewConfirm);
        change = (Button)v.findViewById(R.id.btnChangePass);

        change.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String passwordd = String.valueOf(password.getText());
                String passwordNeww = String.valueOf(passwordNew.getText());
                String passwordNewConfirmm = String.valueOf(passwordNewConfirm.getText());

                if(passwordNeww.equals(passwordNewConfirmm))
                {
                    ArrayList<String> datos = new ArrayList<String>();
                    datos.add(passwordd);
                    datos.add(passwordNeww);
                    datos.add(idUser);
                    datos.add("");
                    Toast.makeText((MainActivity)getActivity(),idUser,Toast.LENGTH_SHORT).show();

                    if (passwordd.length() > 0 && passwordNeww.length() > 0 && passwordNewConfirmm.length()>0) {
                        password.setText(null);
                        passwordNew.setText(null);
                        passwordNewConfirm.setText(null);
                        new MyAsyncTask(datos, Constants.CHANGE_PASSWORD_URL,((MainActivity)getActivity())).execute();
                    }
                    else
                    {
                        Toast.makeText((MainActivity)getActivity(),"Debes completar los campos.",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText((MainActivity)getActivity(),"La contraseña no coincide, vuelva a intentarlo.",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }

}
