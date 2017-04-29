package com.smartd90photoprint;

public class PrintParam {

    private String ftphost;
    private String ftpport;
    private String ftpuser;
    private String ftppass;
    private String filename;
    private String hotfolder;

    public PrintParam(String ftphost,
                      String ftpport,
                      String ftpuser,
                      String ftppass,
                      String filename,
                      String hotfolder )
    {
        this.ftphost = ftphost;
        this.ftpport = ftpport;
        this.ftpuser = ftpuser;
        this.ftppass = ftppass;
        this.filename = filename;
        this.hotfolder = hotfolder;
    }

    public String getFtphost() {
        return ftphost;
    }

    public void setFtphost( String ftphost )
    {
        this.ftphost = ftphost;
    }

    public String getFtpport() {
        return ftpport;
    }

    public void setFtpport( String ftpport )
    {
        this.ftpport = ftpport;
    }

    public String getFtpuser() {
        return ftpuser;
    }

    public void setFtpuser( String ftpuser )
    {
        this.ftpuser = ftpuser;
    }

    public String getFtppass() {
        return ftppass;
    }

    public void setFtppass( String ftppass )
    {
        this.ftppass = ftppass;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename( String filename )
    {
        this.filename = filename;
    }

    public String getHotfolder() {
        return hotfolder;
    }

    public void setHotfolder( String hotfolder )
    {
        this.hotfolder = hotfolder;
    }

}

