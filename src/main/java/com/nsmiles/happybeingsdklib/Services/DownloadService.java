package com.nsmiles.happybeingsdklib.Services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.nsmiles.happybeingsdklib.MindGym.AudioDownload;
import com.nsmiles.happybeingsdklib.UI.HomeScreenActivity;
import com.nsmiles.happybeingsdklib.Utils.MySql;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


/**
 * Created by nSmiles on 10/23/2017.
 */

public class DownloadService extends IntentService {


    Context context;
    File check_file_availability;
    private SQLiteDatabase db;
    MySql dbHelper;
    String id, sync_flag, audio_base_url, audio_title, audio_id, audio_sub_url, created_at, updated_at, audio_description, download_status, sd_card_path, audio_number;

    int imageNumber = 0;

    /*Audio Storage File Path*/
    String fileDirPath;
    String storagePath;


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadService(String name) {
        super(name);
    }

    /*Default Constructor, Dont remove otherwise it show error in manifest file*/
    public DownloadService() {
        super("DisplayNotification");
    }

    private int totalFileSize;

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
           // getImageAPI();
            context = getApplicationContext();

            audio_base_url = "https://api.nsmiles.com/v2/G_download/relieve-and-relax-list/";
            fileDirPath = context.getFilesDir().getAbsolutePath() + File.separator + "HappyBeing";
            storagePath = fileDirPath + "/audiofiles/";
        /*Setting relax and coach url*/


        /*Getting Single file and url*/
            if (intent.hasExtra("URL") && (intent.hasExtra("FILE_NAME") && intent.hasExtra("AUDIO_BASE_URL") && intent.hasExtra("AUDIO_ID"))) {
                audio_sub_url = intent.getStringExtra("URL");
                audio_title = intent.getStringExtra("FILE_NAME");
                audio_base_url = intent.getStringExtra("AUDIO_BASE_URL");
                audio_id = intent.getStringExtra("AUDIO_ID");
               // check_file_availability = new File(storagePath + audio_sub_url);
               // if (check_file_availability.exists()) {

                    initDownload();
              //  }
            }
            // Download All Relax Audio Bulk
            if (intent.hasExtra("RELAX")) {
                downloadAllRelaxAudio();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void downloadAllRelaxAudio() {
        try {
            //ResponseBody body;
            dbHelper = new MySql(context, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            Cursor cursor = db.rawQuery("SELECT * FROM relax_audio WHERE DOWNLOAD_STATUS=?", new String[]{"0"});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {

                    id = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("_ID")));
                    audio_number = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_NUMBER"));
                    audio_base_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_BASE_URL"));
                    audio_sub_url = cursor.getString(cursor.getColumnIndexOrThrow("AUDIO_SUB_URL"));
                    download_status = cursor.getString(cursor.getColumnIndexOrThrow("DOWNLOAD_STATUS"));
                    sd_card_path = cursor.getString(cursor.getColumnIndexOrThrow("SD_CARD_PATH"));

                /*    CommonUtils.showLogInforamtion(getClass().getSimpleName(), "AUDIO_NUMBER", audio_number, true);
                    CommonUtils.showLogInforamtion(getClass().getSimpleName(), "AUDIO_BASE_URL", audio_base_url, true);
                    CommonUtils.showLogInforamtion(getClass().getSimpleName(), "AUDIO_SUB_URL", audio_sub_url, true);
                    CommonUtils.showLogInforamtion(getClass().getSimpleName(), "DOWNLOAD STATUS", download_status, true);
                    CommonUtils.showLogInforamtion(getClass().getSimpleName(), "SD CARD PATH", sd_card_path, true);
*/

                    // Check Already the files are available or not in local db;
                    check_file_availability = new File(storagePath + audio_sub_url);
                    if (check_file_availability.exists()) {
                        //  File Already Exist
  //                      CommonUtils.showLogInforamtion(getClass().getSimpleName(), "File Already Exist", audio_base_url + audio_sub_url, true);
                        cv.put("DOWNLOAD_STATUS", "1");
                        cv.put("SD_CARD_PATH", storagePath + audio_sub_url);
                        cv.put("UPDATED_AT", new Date().toString());
                        db.update("relax_audio", cv, "_ID=?",
                                new String[]{id});
                    } /// File Not Available Download it
                    else {

//                        body = request.execute().body();
                        String urlString = audio_base_url+audio_sub_url;
                        URL url = new URL(urlString);
                        URLConnection conexion = url.openConnection();
                        conexion.connect();

                        OutputStream output = null;
                        int count;
                        byte data[] = new byte[1024 * 4];
                        long fileSize = conexion.getContentLength();
                        InputStream bis = new BufferedInputStream(url.openStream(), 1024 * 8);
                        final File dir = new File(storagePath);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        output = new FileOutputStream(dir + "/" + audio_sub_url);


                        long total = 0;
                        long startTime = System.currentTimeMillis();
                        int timeCount = 1;
                        while ((count = bis.read(data)) != -1) {

                            total += count;
                            totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
                            double current = Math.round(total / (Math.pow(1024, 2)));
                            int progress = (int) ((total * 100) / fileSize);
                            long currentTime = System.currentTimeMillis() - startTime;
                            AudioDownload download = new AudioDownload();
                            download.setTotalFileSize(totalFileSize);

                            if (currentTime > 1000 * timeCount) {
                                // NO NEED UPDATE STATUS FOR BULK DOWNLOAD
                                //    download.setCurrentFileSize((int) current);
                                //    download.setProgress(progress);
                                //    sendNotification(download);
                                timeCount++;
                            }


                            output.write(data, 0, count);

                        }
                        // NO NEED UPDATE STATUS FOR BULK DOWNLOAD
                        // onDownloadComplete();
                        cv.put("DOWNLOAD_STATUS", "1");
                        cv.put("SD_CARD_PATH", dir + "/" + audio_sub_url);
                        cv.put("UPDATED_AT", new Date().toString());
                        db.update("relax_audio", cv, "_ID=?",
                                new String[]{id});
                        output.flush();
                        output.close();
                        bis.close();

                    } // do block end

                } // File Not Available Else Block

                while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    // Download Single Audio Instance
    private void initDownload() {
        Log.i("DownloadService", "In init download method");
        String urlString = audio_base_url+audio_sub_url;
        Log.i("DownlaodService", "Url string is "+urlString);
        try {
//            Log.i("DownloadFile", "Audio base url is "+audio_base_url+"audio file is "+audio_sub_url+" request is "+request.execute().body());
            URL url = new URL(urlString);
            URLConnection conexion = url.openConnection();
            conexion.connect();

            downloadFile(conexion.getContentLength(), url.openStream());
            //downloadFile(request.execute().body());


        } catch (Exception e) {

            e.printStackTrace();
        //    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    private void downloadFile(long filesize, InputStream inputStream) throws IOException {

        try {
            dbHelper = new MySql(context, "mydb", null, MySql.version);
            db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            OutputStream output = null;
            int count;
            byte data[] = new byte[1024 * 4];
            long fileSize = filesize;
            InputStream bis = new BufferedInputStream(inputStream, 1024 * 8);
            // File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "file.zip");


            final File dir = new File(storagePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            output = new FileOutputStream(dir + "/" + audio_sub_url);


            long total = 0;
            long startTime = System.currentTimeMillis();
            int timeCount = 1;
            while ((count = bis.read(data)) != -1) {

                total += count;
                totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));
                double current = Math.round(total / (Math.pow(1024, 2)));

                int progress = (int) ((total * 100) / fileSize);

                long currentTime = System.currentTimeMillis() - startTime;

                AudioDownload download = new AudioDownload();
                download.setTotalFileSize(totalFileSize);

                if (currentTime > 1000 * timeCount) {

                    download.setCurrentFileSize((int) current);
                    download.setProgress(progress);
                    sendNotification(download);
                    timeCount++;
                }
                cv.put("DOWNLOAD_STATUS", "1");
                cv.put("SD_CARD_PATH", dir + "/" + audio_sub_url);
                cv.put("UPDATED_AT", new Date().toString());
                db.update("relax_audio", cv, "_ID=?",
                        new String[]{audio_id});
                output.write(data, 0, count);
            }
            onDownloadComplete();
            output.flush();
            output.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void sendIntent(AudioDownload download) {

        try {
            Intent intent = new Intent(HomeScreenActivity.MESSAGE_PROGRESS);
            intent.putExtra("download", download);
            LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(AudioDownload download) {

        try {
            sendIntent(download);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void onDownloadComplete() {
        try {
            AudioDownload download = new AudioDownload();
            download.setProgress(100);
            download.setAudio_number(audio_number);
            sendIntent(download);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}