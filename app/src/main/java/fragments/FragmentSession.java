package fragments;


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
public class FragmentSession extends Fragment
{
    private Button login;
    private EditText username;
    private EditText password;


    public FragmentSession() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_session,container,false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Login");
        username = (EditText)v.findViewById(R.id.Edusernamesession);
        password = (EditText)v.findViewById(R.id.Edpasswordsession);
        login = (Button)v.findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final String usuario = String.valueOf(username.getText());
                final String passwordd = String.valueOf(password.getText());
                //Toast.makeText((MainActivity)getActivity(),usuario+" "+passwordd,Toast.LENGTH_SHORT).show();
                ArrayList<String> datos = new ArrayList<>();
                datos.add(usuario);
                datos.add(passwordd);

                if(usuario.length()>0 && passwordd.length()>0)
                {
                    new MyAsyncTask(datos, Constants.SESSION_URL,((MainActivity)getActivity())).execute();
                }
                else
                {
                    Toast.makeText((MainActivity)getActivity(),"Debes completar los campos",Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }

}
