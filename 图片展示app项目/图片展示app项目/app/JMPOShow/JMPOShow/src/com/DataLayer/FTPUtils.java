package com.DataLayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import android.annotation.SuppressLint;
import android.util.Log;

/** 
 * 
 * ����Android��FTP���������н����Ĺ����� 
 *  
 */  

public class FTPUtils {  
	private String TAG="FTPUtils";
    private FTPClient ftpClient = null;  
    private static FTPUtils ftpUtilsInstance = null;  
    private String FTPUrl;  
    private int FTPPort;  
    private String UserName;  
    private String UserPassword; 
//    private String RemoteDir; 
      
    private FTPUtils()  
    {  
        ftpClient = new FTPClient();  
    }  
    
    /* 
     * �õ������ʵ������Ϊֻ����һ������������������õ���ģʽ�� 
     */  
    public  static FTPUtils getInstance() {  
        if (ftpUtilsInstance == null)  
        {  
            ftpUtilsInstance = new FTPUtils();  
        }  
        return ftpUtilsInstance;  
    }  
      
    /** 
     * ����FTP������ 
     * @param FTPUrl   FTP������ip��ַ 
     * @param FTPPort   FTP�������˿ں� 
     * @param UserName    ��½FTP���������˺� 
     * @param UserPassword    ��½FTP������������ 
     * @return 
     */  
    public boolean initFTPSetting(String FTPUrl, int FTPPort, String UserName, String UserPassword)  
    {     
        this.FTPUrl = FTPUrl;  
        this.FTPPort = FTPPort;  
        this.UserName = UserName;  
        this.UserPassword = UserPassword;            
        int reply;         
        try {  
        	 //1.Ҫ���ӵ�FTP������Url,Port   
            ftpClient.connect(FTPUrl, FTPPort);               
            //2.��½FTP������
            ftpClient.login(UserName, UserPassword);           
          //3.�����ص�ֵ�ǲ���230������ǣ���ʾ��½�ɹ� 
            reply = ftpClient.getReplyCode();           
            if (!FTPReply.isPositiveCompletion(reply))  
            {  
            	  //�Ͽ�  
                ftpClient.disconnect();  
                Log.d(TAG,"�ѶϿ�����");
                return false;  
            }  
            Log.d(TAG,"���ӳɹ�"); 
            return true;            
        } catch (SocketException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return false;  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            return false;  
        }  
    }  
      

    /** 
     * ���ص����ļ� 
     * @param FilePath  Ҫ��ŵ��ļ���·�� 
     * @param FileName   Զ��FTP�������ϵ��Ǹ��ļ������� 
     * @return   trueΪ�ɹ���falseΪʧ�� 
     */  
    @SuppressLint("SdCardPath") 
    public boolean downLoadFile(String LocFilePath,String RemotePath,String RemFileName) {  
          
        if (!ftpClient.isConnected())  
        {  
            if (!initFTPSetting(FTPUrl,  FTPPort,  UserName,  UserPassword))  
            {  
                return false;  
            }  
        }  
           
        try {  
        	// ת��ָ������Ŀ¼
            ftpClient.changeWorkingDirectory(RemotePath);  
              
         // �г���Ŀ¼�������ļ�  
            FTPFile[] files = ftpClient.listFiles();  
              
         // ���������ļ����ҵ�ָ�����ļ�  
            for (FTPFile file : files) {  
                if (file.getName().equals(RemFileName)) {  
					File localFile = new File(RemotePath);                  
                    // �����  
                    OutputStream outputStream = new FileOutputStream(LocFilePath+RemFileName);                   
                    // �����ļ� 
                    ftpClient.retrieveFile(file.getName(), outputStream);                     
                    //�ر��� 
                    outputStream.close();  
                }  
            }              
            //�˳���½FTP���ر�ftpCLient������ 
            ftpClient.logout();  
            ftpClient.disconnect();            
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return true;  
    }  
    
 
}