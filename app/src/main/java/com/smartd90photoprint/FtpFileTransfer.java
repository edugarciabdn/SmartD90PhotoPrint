package com.smartd90photoprint;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FtpFileTransfer {

    private Context context;
    private PrintParam printparam;
    private String msg;

    public FtpFileTransfer(Context context, PrintParam printparam ) {
        this.context=context;
        new wFtp().execute();
        this.printparam = printparam;
        msg = context.getString(R.string.checkwifi);
    }

    private class wFtp extends AsyncTask<Void,Integer,Boolean> {

        public ProgressDialog dialog;
        private boolean mDone = false;

        //Before start to upload the file creating a dialog
        @Override
        protected void onPreExecute()
        {
            dialog = new ProgressDialog(context);
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
            dialog.setMessage(context.getString(R.string.sendingimage));
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate();
            dialog.setProgress(values[0]);
        }

        protected  Boolean doInBackground(Void... params) {
            FTPClient ftpClient = new FTPClient();
            try {
                ftpClient.connect(printparam.getFtphost(), Integer.parseInt(printparam.getFtpport()));
                ftpClient.login(printparam.getFtpuser(), printparam.getFtppass());
                ftpClient.enterLocalPassiveMode();
                ftpClient.changeWorkingDirectory(printparam.getHotfolder());
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.setConnectTimeout(3000);
                File fileSource = new File(printparam.getFilename());
                FileInputStream fis = new FileInputStream(fileSource);
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                OutputStream ftpoutputStream = ftpClient.storeFileStream("SPP_"+timeStamp+".png");
                byte[] bytesIn = new byte[4096];
                int read = 0;
                int progress=0;
                int bytesRead = fis.read(bytesIn, 0, 512);
                while (bytesRead > 0)
                {
                    progress+=bytesRead;
                    ftpoutputStream.write(bytesIn, 0, bytesRead);
                    publishProgress((int)((progress*100)/(int)fileSource.length()));
                    bytesRead = fis.read(bytesIn, 0, 512);
                }
                fis.close();
                ftpoutputStream.close();
            }catch (Exception e) {
                return false;
            }
            finally {
                try {
                    if (ftpClient.isConnected()) {
                        ftpClient.logout();
                        ftpClient.disconnect();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            return true;
        }

        protected void onPostExecute(Boolean done) {
            dialog.dismiss();
            if (!done)
                Toast.makeText(context,msg,
                        Toast.LENGTH_LONG).show();
        }
    }

}

