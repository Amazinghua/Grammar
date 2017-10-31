package com.DataLayer;
 
	import java.io.File;  
	import java.io.FileOutputStream;  
	import java.io.IOException;  
	import java.io.InputStream;  
	import java.io.OutputStream;  
	import org.apache.commons.net.ftp.FTPClient;  
	import org.apache.commons.net.ftp.FTPClientConfig;  
	import org.apache.commons.net.ftp.FTPFile;  
	import org.apache.commons.net.ftp.FTPReply;  
	  
	public class Ftp {  
	    private String hostName;  
	    private int serverPort;  
	    private String userName;  
	    private String password;  
	    private FTPClient ftpClient;  
	  
	    public Ftp() {  
	        this.hostName = "221.179.63.114";  
	        this.serverPort = 2121;  
	        this.userName = "csyftp";  
	        this.password = "Olpy@$2016";  
	        this.ftpClient = new FTPClient();  
	    }  
	  
	
	    /** 
	     * 下载单个文件，可实现断点下载. 
	     *  
	     * @param serverPath 
	     *            Ftp目录及文件路径 
	     * @param localPath 
	     *            本地目录 
	     * @param fileName        
	     *            下载之后的文件名称 
	     * @throws IOException 
	     */  
	    public void downloadSingleFile(String serverPath, String localPath, String fileName)  
	            throws Exception {  
	        // 打开FTP服务  
	        try {  
	            this.openConnect();   
	        } catch (IOException e1) {  
	            e1.printStackTrace();   
	            return;  
	        }  
	  
	        // 先判断服务器文件是否存在  
	        FTPFile[] files = ftpClient.listFiles(serverPath); 
	        //创建本地文件夹  
	        File mkFile = new File(localPath);  
	        if (!mkFile.exists()) {  
	            mkFile.mkdirs();  
	        }  
	        localPath = localPath + "/" + fileName;  
	        // 接着判断下载的文件是否能断点下载  
	        long serverSize = files[0].getSize(); 
	        File localFile = new File(localPath);  
	        long localSize = 0;  
	        if (localFile.exists()) {  
	            localSize = localFile.length(); // 如果本地文件存在，获取本地文件的长度  
	            if (localSize >= serverSize) {  
	                File file = new File(localPath);  
	                file.delete();  
	            }  
	        }  
	        // 开始准备下载文件  
	        OutputStream out = new FileOutputStream(localFile, true);  
	        ftpClient.setRestartOffset(localSize);  
	        InputStream input = ftpClient.retrieveFileStream(serverPath);  
	        byte[] b = new byte[1024];  
	        int length = input.read(b);  
	        while ((length = input.read(b)) != -1) {  
	            out.write(b, 0, length);   
	        }  
	        out.flush();  
	        out.close();  
	        input.close();  
	        if (ftpClient.completePendingCommand()) {  
	        } else {  
	        }  
	        this.closeConnect();  
	        return;  
	    }  
	    /** 
	     * 打开FTP服务. 
	     *  
	     * @throws IOException 
	     */  
	    public void openConnect() throws IOException {  
	        // 中文转码  
	        ftpClient.setControlEncoding("UTF-8");  
	        int reply; // 服务器响应值  
	        // 连接至服务器  
	        ftpClient.connect(hostName, serverPort);  
	        // 获取响应值  
	        reply = ftpClient.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            // 断开连接  
	            ftpClient.disconnect();  
	            throw new IOException("connect fail: " + reply);  
	        }  
	        // 登录到服务器  
	        ftpClient.login(userName, password);  
	        // 获取响应值  
	        reply = ftpClient.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            // 断开连接  
	            ftpClient.disconnect();  
	            throw new IOException("connect fail: " + reply);  
	        } else {  
	            // 获取登录信息  
	            FTPClientConfig config = new FTPClientConfig(ftpClient  
	                    .getSystemType().split(" ")[0]);  
	            config.setServerLanguageCode("zh");  
	            ftpClient.configure(config);  
	            // 使用被动模式设为默认  
	            ftpClient.enterLocalPassiveMode();  
	            // 二进制文件支持  
	            ftpClient  
	                    .setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);  
	        }  
	    }  
	  
	    /** 
	     * 关闭FTP服务. 
	     *  
	     * @throws IOException 
	     */  
	    public void closeConnect() throws IOException {  
	        if (ftpClient != null) {  
	            // 退出FTP  
	            ftpClient.logout();  
	            // 断开连接  
	            ftpClient.disconnect();  
	        }  
	    }  
	 
	  
	}  

