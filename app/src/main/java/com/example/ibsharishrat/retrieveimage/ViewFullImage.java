package com.example.ibsharishrat.retrieveimage;

/**
 * Created by Ibshar Ishrat on 26/10/2016.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ViewFullImage extends AppCompatActivity
{
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full);
        Intent intent = getIntent();
        int i = intent.getIntExtra(ImageListView.BITMAP_ID,0);

        imageView = (ImageView) findViewById(R.id.imageViewFull);
        imageView.setImageBitmap(GetImages.bitmaps[i]);

    }
}