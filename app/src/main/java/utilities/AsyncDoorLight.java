package utilities;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by valen on 06/08/2016.
 */
public class AsyncDoorLight extends AsyncTask<String,String,String>
{
    private String mlink;

    public AsyncDoorLight(String link)
    {
        this.mlink = link;
    }

    @Override
    protected String doInBackground(String... params)
    {
        try
        {
            URL url = new URL(mlink);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(false);

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }
            return sb.toString();

        }
        catch (Exception e)
        {
            return new String("Exception: "+e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("Luz/Door O/Off O/C ",s);
    }
}
