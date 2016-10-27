package com.example.ibsharishrat.retrieveimage;

/**
 * Created by Ibshar Ishrat on 26/10/2016.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageListView extends AppCompatActivity implements AdapterView.OnItemClickListener
{

    private ListView listView;

    public static final String GET_IMAGE_URL="http://192.168.8.101/getAllImages.php";

    public static GetImages getImages;
    static String rspStr;

    public static final String BITMAP_ID = "BITMAP_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        getURLs();
    }

    private void getImages(){
        class GetImages extends AsyncTask<Void,Void,Void>
        {
            ProgressDialog loading;
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(ImageListView.this,"Downloading images...","Please wait...",false,false);
            }

            @Override
            protected Void doInBackground(Void... voids)
            {
                try
                {
                    getImages.getAllImages();

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void v)
            {
                super.onPostExecute(v);
                loading.dismiss();
                //Toast.makeText(ImageListView.this,"Success",Toast.LENGTH_LONG).show();
                ActivityList customList = new ActivityList(ImageListView.this, com.example.ibsharishrat.retrieveimage.GetImages.imageURLs,com.example.ibsharishrat.retrieveimage.GetImages.bitmaps);
                listView.setAdapter(customList);
            }
        }
        GetImages getImages = new GetImages();
        getImages.execute();
    }

    private void getURLs()
    {
        class GetURLs extends AsyncTask<String,String,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                loading = ProgressDialog.show(ImageListView.this,"Loading...","Please Wait...",true,true);
            }



            @Override
            protected String doInBackground(String... strings)
            {
                BufferedReader bufferedReader = null;
                try
                {
                    URL url = new URL(strings[0]);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null)
                    {
                        sb.append(json + "\n");
                    }

                    rspStr = sb.toString();

                }
                catch(Exception e)
                {
                    Log.e("Buffer Error", "Error converting result " + e.toString());
                    return null;
                }
                return rspStr;
            }
            @Override
            protected void onPostExecute(String s)
            {
                s = rspStr;
                super.onPostExecute(s);
                loading.dismiss();
                getImages = new GetImages(s);
                getImages();
            }

        }
        GetURLs gu = new GetURLs();
        gu.execute(GET_IMAGE_URL);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        Intent intent = new Intent(this, ViewFullImage.class);
        intent.putExtra(BITMAP_ID,i);
        startActivity(intent);
    }
}
