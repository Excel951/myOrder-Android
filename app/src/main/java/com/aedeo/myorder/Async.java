package com.aedeo.myorder;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Async extends AsyncTask<String, Integer, String> {
    ArrayList<HashMap<String,String>> itemList=new ArrayList<>();
    private TextView outputTextView;
    private ListView lv;
    public Async (TextView outputTextView, ListView lv) {
        this.outputTextView=outputTextView;
        this.lv=lv;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data="";
        try {
            data =downloadUrl("http://192.168.1.11/project/mobile/m10/read_allorder.php");
            JSONObject jsonObject=new JSONObject(data);
            JSONArray orders=jsonObject.getJSONArray("orders");
            for (int i = 0; i < orders.length(); i++) {
                JSONObject item=orders.getJSONObject(i);

                String id=item.getString("id");
                String nama=item.getString("item");

                HashMap<String, String> barang=new HashMap<>();
                barang.put("id",id);
                barang.put("item",nama);
                itemList.add(barang);
            }
        } catch (Exception e) {
            Log.d("Background Task  : ", "doInBackground: " + e);
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result) {
//        outputTextView.setText(result);
        super.onPostExecute(result);
        outputTextView.setText("View All Order...");
        ListAdapter adapter=new SimpleAdapter(outputTextView.getContext(), itemList, R.layout.item, new String[]{"id","item"}, new int[]{R.id.id_item,R.id.nama});
        lv.setAdapter(adapter);
    }

    private String downloadUrl(String stringurl) throws IOException {
        String data="";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(stringurl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            data = stringBuffer.toString();
            bufferedReader.close();
        } catch (Exception e) {
            Log.d("Download URL", "downloadUrl: " + e);
        } finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
