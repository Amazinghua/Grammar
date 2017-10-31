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
 * 用于Android和FTP服务器进行交互的工具类 
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
     * 得到类对象实例（因为只能有一个这样的类对象，所以用单例模式） 
     */  
    public  static FTPUtils getInstance() {  
        if (ftpUtilsInstance == null)  
        {  
            ftpUtilsInstance = new FTPUtils();  
        }  
        return ftpUtilsInstance;  
    }  
      
    /** 
     * 设置FTP服务器 
     * @param FTPUrl   FTP服务器ip地址 
     * @param FTPPort   FTP服务器端口号 
     * @param UserName    登陆FTP服务器的账号 
     * @param UserPassword    登陆FTP服务器的密码 
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
        	 //1.要连接的FTP服务器Url,Port   
            ftpClient.connect(FTPUrl, FTPPort);               
            //2.登陆FTP服务器
            ftpClient.login(UserName, UserPassword);           
          //3.看返回的值是不是230，如果是，表示登陆成功 
            reply = ftpClient.getReplyCode();           
            if (!FTPReply.isPositiveCompletion(reply))  
            {  
            	  //断开  
                ftpClient.disconnect();  
                Log.d(TAG,"已断开连接");
                return false;  
            }  
            Log.d(TAG,"连接成功"); 
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
     * 下载单个文件 
     * @param FilePath  要存放的文件的路径 
     * @param FileName   远程FTP服务器上的那个文件的名字 
     * @return   true为成功，false为失败 
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
        	// 转到指定下载目录
            ftpClient.changeWorkingDirectory(RemotePath);  
              
         // 列出该目录下所有文件  
            FTPFile[] files = ftpClient.listFiles();  
              
         // 遍历所有文件，找到指定的文件  
            for (FTPFile file : files) {  
                if (file.getName().equals(RemFileName)) {  
					File localFile = new File(RemotePath);                  
                    // 输出流  
                    OutputStream outputStream = new FileOutputStream(LocFilePath+RemFileName);                   
                    // 下载文件 
                    ftpClient.retrieveFile(file.getName(), outputStream);                     
                    //关闭流 
                    outputStream.close();  
                }  
            }              
            //退出登陆FTP，关闭ftpCLient的连接 
            ftpClient.logout();  
            ftpClient.disconnect();            
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return true;  
    }  
    
 
}