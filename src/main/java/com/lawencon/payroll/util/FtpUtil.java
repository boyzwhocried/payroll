package com.lawencon.payroll.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.File;
import java.util.Base64;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUtil {

	public static void sendFile(String fileBase64, String remoteFile) {
		final String server = "192.168.20.74";
		final int port = 21;
		final String user = "User1";
		final String pass = "123";

		final FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			System.out.println("======> Uploading file");
			final OutputStream outputStream = ftpClient.storeFileStream(remoteFile);
			final byte[] data = Base64.getDecoder().decode(fileBase64);

			outputStream.write(data);
			outputStream.close();

			final boolean isCompleted = ftpClient.completePendingCommand();
			if (isCompleted) {
				System.out.println("======> Upload successfully");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void downloadFile(String remoteFile, String downlaodFile) {
		final String server = "192.168.20.74";
		final int port = 21;
		final String user = "User1";
		final String pass = "123";

		final FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			System.out.println("======> Downloading file");

            File downloadFile1 = new File(downlaodFile);
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(remoteFile, outputStream1);
            outputStream1.close();
 
            if (success) {
                System.out.println("Download successfully.");
            }

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}