package cn.hisdar.alt.androiddevice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.hisdar.lib.log.HLog;

class CommandLineExecResultReadThread implements Runnable {
    private String character = "GB2312";
    private StringBuffer outputBuffer;
    private InputStream inputStream;

    public CommandLineExecResultReadThread(InputStream inputStream, StringBuffer resuletData) {
        this.inputStream = inputStream;
        this.outputBuffer = resuletData;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);//将其设置为守护线程
        thread.start();
    }

    public void run() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream, character));
            String line = null;
            while ((line = br.readLine()) != null) {
                
                if (outputBuffer != null) {
                    outputBuffer.append(line);
                    outputBuffer.append("\n");
                } else {
                	HLog.il(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}