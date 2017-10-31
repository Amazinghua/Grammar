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
	     * ���ص����ļ�����ʵ�ֶϵ�����. 
	     *  
	     * @param serverPath 
	     *            FtpĿ¼���ļ�·�� 
	     * @param localPath 
	     *            ����Ŀ¼ 
	     * @param fileName        
	     *            ����֮����ļ����� 
	     * @throws IOException 
	     */  
	    public void downloadSingleFile(String serverPath, String localPath, String fileName)  
	            throws Exception {  
	        // ��FTP����  
	        try {  
	            this.openConnect();   
	        } catch (IOException e1) {  
	            e1.printStackTrace();   
	            return;  
	        }  
	  
	        // ���жϷ������ļ��Ƿ����  
	        FTPFile[] files = ftpClient.listFiles(serverPath); 
	        //���������ļ���  
	        File mkFile = new File(localPath);  
	        if (!mkFile.exists()) {  
	            mkFile.mkdirs();  
	        }  
	        localPath = localPath + "/" + fileName;  
	        // �����ж����ص��ļ��Ƿ��ܶϵ�����  
	        long serverSize = files[0].getSize(); 
	        File localFile = new File(localPath);  
	        long localSize = 0;  
	        if (localFile.exists()) {  
	            localSize = localFile.length(); // ��������ļ����ڣ���ȡ�����ļ��ĳ���  
	            if (localSize >= serverSize) {  
	                File file = new File(localPath);  
	                file.delete();  
	            }  
	        }  
	        // ��ʼ׼�������ļ�  
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
	     * ��FTP����. 
	     *  
	     * @throws IOException 
	     */  
	    public void openConnect() throws IOException {  
	        // ����ת��  
	        ftpClient.setControlEncoding("UTF-8");  
	        int reply; // ��������Ӧֵ  
	        // ������������  
	        ftpClient.connect(hostName, serverPort);  
	        // ��ȡ��Ӧֵ  
	        reply = ftpClient.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            // �Ͽ�����  
	            ftpClient.disconnect();  
	            throw new IOException("connect fail: " + reply);  
	        }  
	        // ��¼��������  
	        ftpClient.login(userName, password);  
	        // ��ȡ��Ӧֵ  
	        reply = ftpClient.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            // �Ͽ�����  
	            ftpClient.disconnect();  
	            throw new IOException("connect fail: " + reply);  
	        } else {  
	            // ��ȡ��¼��Ϣ  
	            FTPClientConfig config = new FTPClientConfig(ftpClient  
	                    .getSystemType().split(" ")[0]);  
	            config.setServerLanguageCode("zh");  
	            ftpClient.configure(config);  
	            // ʹ�ñ���ģʽ��ΪĬ��  
	            ftpClient.enterLocalPassiveMode();  
	            // �������ļ�֧��  
	            ftpClient  
	                    .setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);  
	        }  
	    }  
	  
	    /** 
	     * �ر�FTP����. 
	     *  
	     * @throws IOException 
	     */  
	    public void closeConnect() throws IOException {  
	        if (ftpClient != null) {  
	            // �˳�FTP  
	            ftpClient.logout();  
	            // �Ͽ�����  
	            ftpClient.disconnect();  
	        }  
	    }  
	 
	  
	}  

