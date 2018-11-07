package com.sap.module.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPSClient;

import com.sap.aii.af.lib.mp.module.ModuleException;
import com.sap.engine.interfaces.messaging.api.auditlog.AuditLogStatus;
import com.sap.module.util.ArchiveDelete;
import com.sap.module.util.AuditLogHelper;
import com.sap.module.util.Logger;
import com.sap.module.util.ParameterID;
import com.sap.module.util.PrefixSuffixTimestamp;

public class UploadFileToFTPS {
	private PrefixSuffixTimestamp prefix_suffix_time = new PrefixSuffixTimestamp();
	
	public Boolean UploadFTPS(AuditLogHelper audit, Logger log,
			FTPSClient ftps, String destinationPath, String srcPathAndName)
			throws ModuleException {

		try {
			InputStream input = new FileInputStream(new File(srcPathAndName));
			ftps.storeFile(destinationPath, input);
			input.close();
		} catch (IOException e) {
			audit.addLog(AuditLogStatus.ERROR, e.getMessage());
			throw new ModuleException(e.getMessage());
		}

		return true;

	}

	public Boolean UploadFTPS(AuditLogHelper audit, Logger log,
			FTPSClient ftps, String destinationPath,
			ArrayList<File> listFileWithoutZipSrc,ParameterID paramConnection) throws ModuleException {
		
		ArchiveDelete archiveDelete = new ArchiveDelete();
		InputStream input = null;
		audit.addLog(AuditLogStatus.SUCCESS, "Upload file without zip");
		
		log.add(" --------- List file without zip ---------"); //write log list file move to destination
		try {
			for (int i = 0; i < listFileWithoutZipSrc.size(); i++) {
				log.add(listFileWithoutZipSrc.get(i).getName()); //write log list file move to destination
				String Soure_preFix_sufFixFile = prefix_suffix_time.Soure_preFix_sufFix_timeStamp(listFileWithoutZipSrc.get(i).getName(), audit, paramConnection, log,paramConnection.getFormatTimeStamp());
				//String Target_preFix_sufFixFile = prefix_suffix_time.Target_preFix_sufFix_timeStamp(listFileWithoutZipSrc.get(i).getName(), audit, paramConnection, log,paramConnection.getFormatTimeStamp());
				
				input = new FileInputStream(new File(listFileWithoutZipSrc.get(i).getAbsolutePath()));
				//ftps.storeFile(destinationPath+ "/"+Target_preFix_sufFixFile, input);
				ftps.storeFile(destinationPath+ "/"+listFileWithoutZipSrc.get(i).getName(), input);
				
				File archivePath = new File(paramConnection.getSrcPath()+ "/archive/"+ Soure_preFix_sufFixFile);
				File srcPath = new File(listFileWithoutZipSrc.get(i).getAbsolutePath());
				archiveDelete.archiveFileToFolder(srcPath, archivePath, Soure_preFix_sufFixFile, audit, log);
			}
			input.close();
		} catch (IOException e) {
			audit.addLog(AuditLogStatus.ERROR, e.getMessage());
			throw new ModuleException(e.getMessage());
		}

		return true;

	}

}
