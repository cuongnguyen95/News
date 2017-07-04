package cuongd13.news.Async;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by cuong pc on 7/3/2017.
 */

public class AsyncTaskGetJson extends AsyncTask<String, Integer, String> {
    //    String url;
    StringBuilder dulieu;
    //    public AsyncTaskGetJson(String url){
//    this.url = url;
//    }
    @Override
    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader read = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(read);
            String dong = "";
            dulieu = new StringBuilder();

            while ( (dong = bufferedReader.readLine()) != null ){
                dulieu.append(dong);
            }
            bufferedReader.close();
            read.close();
            inputStream.close();
            httpURLConnection.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dulieu.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
