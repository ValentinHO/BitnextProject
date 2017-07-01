package fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.HttpAuthHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.valen.proyectobitnext.MainActivity;
import com.example.valen.proyectobitnext.R;

import java.util.Timer;

import utilities.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCam extends Fragment
{
    private WebView webView;

    public FragmentCam() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //***********************Seteo de interfaz xml al fragment**********
        View v = inflater.inflate(R.layout.fragment_cam,container,false);
        //*********Obteniendo ActionBar para establecerle nuevo Titulo****************************************************************
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Security Cam");

        webView = (WebView) v.findViewById(R.id.webview);
        webView.loadUrl(Constants.URL_CAM);
        webView.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        return v;
    }

    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm)
        {
            handler.proceed("admin", "BitnextCam");
        }
    }

}
