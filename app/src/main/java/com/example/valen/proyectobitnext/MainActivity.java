package com.example.valen.proyectobitnext;

import android.content.Context;
import android.content.SharedPreferences;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fragments.FragmentCam;
import fragments.FragmentDoors;
import fragments.FragmentHome;
import fragments.FragmentLights;
import fragments.FragmentPassword;
import fragments.FragmentSession;
import fragments.FragmentTemperatura;
import utilities.Constants;
import utilities.MyAsyncTask;

public class MainActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FragmentHome fragmentHome;
    private FragmentDoors fragmentDoors;
    private FragmentLights fragmentLights;
    private FragmentSession fragmentSession;
    private FragmentCam fragmentCam;
    private FragmentPassword fragmentPassword;
    private FragmentTemperatura fragmentTemperatura;
    private SharedPreferences pf;
    private String sesion,nombreUser,id;
    private char sesiones,idUser;
    private TextView tvUsuario;
    private MyAsyncTask myAsyncTask;
    private boolean ini=false;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pf = getSharedPreferences("PreferenciasDrawer", Context.MODE_PRIVATE);
        inicializarToolbar();

        sesion = getIntent().getStringExtra("estadoSesion");
        nombreUser = getIntent().getStringExtra("nombreUser");
        id = getIntent().getStringExtra("idUser");
        idUser = id.charAt(1);
        sesiones = sesion.charAt(0);
        //Toast.makeText(this,String.valueOf(sesiones),Toast.LENGTH_SHORT).show();
        inicializarComponentes();

    }

    private void inicializarToolbar()
    {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void inicializarComponentes()
    {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        if(navigationView != null)
        {

            if(sesiones == '0')//Si hay sesion cerrada.
            {

                navigationView.getMenu().findItem(R.id.nav_doors).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_lights).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_session).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_cam).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_claveSesion).setVisible(false);
                //navigationView.getMenu().findItem(R.id.nav_tempe).setVisible(false);
                setupDrawerContent(navigationView);
            }
            else if(sesiones == '1')//Si hay sesion abierta
            {

                navigationView.getMenu().findItem(R.id.nav_Iniciar_Sesion).setVisible(false);
                setupDrawerContent(navigationView);
                //Asignar nombre de usuario al header del navigation drawer
                View header = navigationView.getHeaderView(0);
                tvUsuario = (TextView)header.findViewById(R.id.tvUserSession);
                tvUsuario.setText(nombreUser);

            }
        }

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        cargarFragment(getFragmentHome());
    }

    private void setupDrawerContent(NavigationView navigationView)
    {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item)
                    {
                        drawerLayout.closeDrawers();

                        switch (item.getItemId())
                        {
                            case R.id.nav_home:
                                item.setChecked(true);
                                if(getSupportActionBar().getTitle() != "Home")
                                    cargarFragment(getFragmentHome());
                                break;

                            case R.id.nav_doors:
                                item.setChecked(true);
                                if(getSupportActionBar().getTitle() != "Doors")
                                    cargarFragment(getFragmentDoor());
                                pf.edit().putBoolean("fragmentosDoors",fragmentDoors.getBooleano());
                                pf.edit().commit();
                                break;

                            case R.id.nav_lights:
                                item.setChecked(true);
                                if(getSupportActionBar().getTitle() != "Lights")
                                    cargarFragment(getFragmentLight());
                                pf.edit().putBoolean("fragmentosLights",fragmentLights.getBooleano());
                                pf.edit().commit();
                                break;
                            case R.id.nav_cam:
                                item.setChecked(true);
                                if(getSupportActionBar().getTitle() != "Security Cam")
                                    cargarFragment(getFragmentCam());
                                break;
                            case R.id.nav_claveSesion:
                                item.setChecked(true);
                                if(getSupportActionBar().getTitle() != "Password")
                                    cargarFragment(getFragmentPassword());
                                fragmentPassword.idUsuario(String.valueOf(idUser));
                                break;

                            case R.id.nav_session:
                                item.setChecked(true);
                                ArrayList<String> datos = new ArrayList<String>();
                                datos.add(String.valueOf(idUser));datos.add("1");datos.add("2");
                                myAsyncTask = new MyAsyncTask(datos, Constants.SESSION_OUT_URL,MainActivity.this);
                                myAsyncTask.execute();
                                myAsyncTask = null;
                                break;
                            case R.id.nav_Iniciar_Sesion:
                                item.setChecked(true);
                                if(getSupportActionBar().getTitle() != "Login")
                                    cargarFragment(getFragmentSession());
                                break;
                            /*case R.id.nav_tempe:
                                item.setChecked(true);
                                if (getSupportActionBar().getTitle() != "Temperatura y Humedad")
                                    cargarFragment(getFragmentTemperatura());*/
                        }
                        return true;
                    }
                }
        );
    }

    private FragmentHome getFragmentHome()
    {
        if(fragmentHome == null)fragmentHome = new FragmentHome();
        return fragmentHome;
    }
    //**************************************************************************************************
    private FragmentDoors getFragmentDoor()
    {
        if(fragmentDoors == null)fragmentDoors = new FragmentDoors();
        return fragmentDoors;
    }
    //**************************************************************************************************
    private FragmentLights getFragmentLight()
    {
        if(fragmentLights == null)fragmentLights = new FragmentLights();
        return fragmentLights;
    }
    //**************************************************************************************************
    private FragmentSession getFragmentSession()
    {
        if(fragmentSession == null)fragmentSession = new FragmentSession();
        return fragmentSession;
    }
    //**************************************************************************************************
    private FragmentCam getFragmentCam()
    {
        if(fragmentCam == null)fragmentCam = new FragmentCam();
        return fragmentCam;
    }
    //**************************************************************************************************
    private FragmentPassword getFragmentPassword()
    {
        if(fragmentPassword == null)fragmentPassword = new FragmentPassword();
        return fragmentPassword;
    }
    //**************************************************************************************************
    /*private FragmentTemperatura getFragmentTemperatura()
    {
        if(fragmentTemperatura == null)fragmentTemperatura = new FragmentTemperatura();
        return fragmentTemperatura;
    }*/
    //**************************************************************************************************

    private void cargarFragment(Fragment fragmento)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_content,fragmento);
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.maindos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.salir:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

}
