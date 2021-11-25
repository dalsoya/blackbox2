package org.techtown.blackbox2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView m_list;
    ArrayList<String> m_fileList = new ArrayList<>();

    // asdasdasdewsfjpiodsfpiosdjpiofdsf
//    asdsiaopjdpioasjdpiosadjsaopd

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        m_fileList = getVideoList();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, m_fileList);
        m_list = findViewById(R.id.listView);
        m_list.setAdapter(adapter);
        m_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strImg = m_fileList.get(position);
                Toast.makeText(getApplicationContext(), strImg, Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("sdcard/DCIM/Location/");
                Intent intent = new Intent(getApplicationContext(), VideoMapView.class);
                intent.putExtra("uri", uri.toString());
                startActivity(intent);

            }
        });


    }

    private ArrayList<String> getVideoList() {
        ArrayList<String> fileList = new ArrayList<>();
        Uri uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME};

        Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " desc");
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int columnDisplayname = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int lastIndex;
        while (cursor.moveToNext()) {
            String absolutePathOfImage = cursor.getString(columnIndex);
            String nameOfFile = cursor.getString(columnDisplayname);
            lastIndex = absolutePathOfImage.lastIndexOf(nameOfFile);
            lastIndex = lastIndex >= 0 ? lastIndex : nameOfFile.length() - 1;
            if (!TextUtils.isEmpty(absolutePathOfImage)) {
                fileList.add(absolutePathOfImage);
            }
        }
        return fileList;
    }
}


