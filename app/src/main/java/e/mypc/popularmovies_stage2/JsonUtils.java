package e.mypc.popularmovies_stage2;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonUtils {
    public static final String MOV_URL="https://api.themoviedb.org/3/";
    public static final String MOV="movie";
    public static final String API="api_key";
    public static final String KEY="c1b8ef02096fa20303026eab7eda60fa";
    public static final String LOAD="GET";
    public static final int Time=10000;



    public static String loadJsonData(String movies)
    {
        BufferedReader bufferedReader=null;
        String data=null;
        HttpURLConnection httpURLConnection=null;
        try {
            Uri uri=Uri.parse(MOV_URL)
                    .buildUpon().appendPath(MOV).appendPath(movies).appendQueryParameter(API,KEY).build();


            URL url=new URL(uri.toString());
            httpURLConnection=(HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(Time);
            httpURLConnection.setReadTimeout(Time);
            httpURLConnection.setRequestMethod(LOAD);
            httpURLConnection.connect();

            InputStream stream=httpURLConnection.getInputStream();
            StringBuilder stringBuilder=new StringBuilder();
            bufferedReader=new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }
            if (stringBuilder.length()==0)
            {
                return null;
            }
            data=stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null)
            {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null)
            {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return data;
    }
    public static String loadTrailers(String s,int id)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader bufferedReader=null;
        String trailes=null;
        try {
            Uri uri=Uri.parse(MOV_URL)
                    .buildUpon().appendPath(MOV).appendPath(String.valueOf(id)).appendPath(s).appendQueryParameter(API,KEY).build();

            URL url=new URL(uri.toString());
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(LOAD);
            httpURLConnection.connect();

            InputStream inputStream=httpURLConnection.getInputStream();
            StringBuilder stringBuilder=new StringBuilder();
            bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String data;
            while ((data = bufferedReader.readLine())!= null)
            {
                stringBuilder.append(data);
            }
            if (stringBuilder.length() == 0)
            {
                return null;
            }
            trailes=stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null)
            {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null)
            {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return trailes;
    }

    public static String loadreviews(String s,int id)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader bufferedReader=null;
        String reviews=null;
        try {
            Uri uri=Uri.parse(MOV_URL).buildUpon().appendPath(MOV).appendPath(String.valueOf(id)).appendPath(s).appendQueryParameter(API,KEY).build();
            URL url=new URL(uri.toString());
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(LOAD);
            httpURLConnection.connect();

            InputStream inputStream=httpURLConnection.getInputStream();
            StringBuilder stringBuilder=new StringBuilder();
            bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String data;
            while ((data = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(data);
            }
            if (stringBuilder.length() == 0)
            {
                return null;
            }
            reviews=stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null)
            {
                httpURLConnection.disconnect();
            }
            if (bufferedReader != null)
            {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return reviews;
    }

}
