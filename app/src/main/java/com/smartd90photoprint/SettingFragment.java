package com.smartd90photoprint;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.IdRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SettingFragment extends Fragment

{
    private EditText edftphost, edftpport, edftpuser, edftppass;
    private EditText hotfolder;
    private LinearLayout ftpset;
    private ImageView  saveset;
    private FtpItem ftpitem = new FtpItem("","","","","");
    private LinearLayout linehotfolder;
    private Vibrator vibro;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle data = getArguments();
            ftpitem.setFtphost(data.getString("FTPHOST"));
            ftpitem.setFtpport(data.getString("FTPPORT"));
            ftpitem.setFtpuser(data.getString("FTPUSER"));
            ftpitem.setFtppass(data.getString("FTPPASS"));
            ftpitem.setHotfolder(data.getString("HOTFOLDER"));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_setting, container, false);

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vibro = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        saveset = (ImageView)view.findViewById(R.id.saveset);
        saveset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftpitem.setFtphost(edftphost.getText().toString());
                ftpitem.setFtpport(edftpport.getText().toString());
                ftpitem.setFtpuser(edftpuser.getText().toString());
                ftpitem.setFtppass(edftppass.getText().toString());
                ftpitem.setHotfolder(hotfolder.getText().toString());
                ((MainActivity)getActivity()).savesettings(ftpitem);
                vibro.vibrate(35);
                Toast.makeText(getActivity(), getActivity().getString(R.string.saving),
                        Toast.LENGTH_LONG).show();
            }
        });
        linehotfolder= (LinearLayout)view.findViewById(R.id.linehotfolder);
        edftphost = (EditText)view.findViewById(R.id.ftphost);
        edftphost.setText(ftpitem.getFtphost());
        edftpport = (EditText)view.findViewById(R.id.ftpport);;
        edftpport.setText(ftpitem.getFtpport());
        edftpuser  = (EditText)view.findViewById(R.id.ftpuser);;
        edftpuser.setText(ftpitem.getFtpuser());
        edftppass  = (EditText)view.findViewById(R.id.ftppass);
        edftppass.setText(ftpitem.getFtppass());
        hotfolder = (EditText)view.findViewById(R.id.hotfolder);
        hotfolder.setText(ftpitem.getHotfolder());
        ftpset = (LinearLayout)view.findViewById(R.id.ftpset) ;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}

