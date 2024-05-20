package com.lawencon.payroll.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUtil {

	public static void sendFile(String fileBase64, String remoteFile) {
		final String server = "192.168.20.34";
		final int port = 21;
		final String user = "gladosbot1@outlook.com";
		final String pass = "forScienceChell";

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

	public static byte[] downloadFile(String remoteFile) {
		final String server = "192.168.20.34";
		final int port = 21;
		final String user = "gladosbot1@outlook.com";
		final String pass = "forScienceChell";

		byte[] base = null;

		final FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			System.out.println("======> Downloading file");

            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile);
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			inputStream.transferTo(byteArray);
			base = byteArray.toByteArray();
			
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

		return base;
	}

	public static void createDirectory(String userName) {
		final String server = "192.168.20.34";
		final int port = 21;
		final String user = "gladosbot1@outlook.com";
		final String pass = "forScienceChell";

		final FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			System.out.println("======> Create New Directory");

			final String directory = "/" + userName + "/";

            boolean success = ftpClient.makeDirectory(directory);
 
            if (success) {
                System.out.println("Successfully create new directory.");
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