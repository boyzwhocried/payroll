package com.lawencon.payroll.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.util.Base64;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FtpUtil {

	public static void sendFile(String fileBase64, String remoteFile) {
		final String server = "192.168.20.74";
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

			final boolean deleted = ftpClient.deleteFile(remoteFile);
			if (deleted) {
				System.out.println("Deleted");
			} else {
				System.out.println("It's not.");
			}

			final boolean isDeleted = ftpClient.completePendingCommand();
			if (isDeleted) {
				System.out.println("======> Delete successfully");
			}

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
		final String server = "192.168.20.74";
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
            // boolean success = ftpClient.retrieveFile(remoteFile, outputStream1);
            // outputStream1.close();
			
			
            // if (success) {
            //     System.out.println("Download successfully.");
            // }
			
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
		final String server = "192.168.20.74";
		final int port = 21;
		final String user = "gladosbot1@outlook.com";
		final String pass = "forScienceChell";

		final FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			System.out.println("======> Downloading file");

			final String directory = "/user-" + userName;

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