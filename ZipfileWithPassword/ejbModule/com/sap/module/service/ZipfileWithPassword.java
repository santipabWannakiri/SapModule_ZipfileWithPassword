package com.sap.module.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.net.ftp.FTPSClient;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import com.sap.aii.af.lib.mp.module.ModuleException;
import com.sap.engine.interfaces.messaging.api.auditlog.AuditLogStatus;
import com.sap.module.util.ArchiveDelete;
import com.sap.module.util.AuditLogHelper;
import com.sap.module.util.Logger;
import com.sap.module.util.ParameterID;
import com.sap.module.util.PrefixSuffixTimestamp;

public class ZipfileWithPassword {

	public void compressWithPassword(AuditLogHelper audit,
			ParameterID paramConnection, Logger log, String sourcePath,
			String targetPath, String passwordToZip, String formatFileName,
			FTPSClient ftps, ArrayList<File> listFileSrc) throws ZipException,
			ModuleException, IOException {

		UploadFileToFTPS upload = new UploadFileToFTPS();
		ArchiveDelete archiveDelete = new ArchiveDelete();
		PrefixSuffixTimestamp prefix_suffix_time = new PrefixSuffixTimestamp();
		
		String Soure_preFix_sufFixFileZip = prefix_suffix_time.Soure_preFix_sufFix_timeStamp(formatFileName, audit, paramConnection, log,paramConnection.getFormatTimeStamp());
		String Target_preFix_sufFixFileZip = prefix_suffix_time.Target_preFix_sufFix_timeStamp(formatFileName, audit, paramConnection, log,paramConnection.getFormatTimeStamp());
		
		String srcZip = sourcePath + "/" + Soure_preFix_sufFixFileZip + ".zip";
		audit.addLog(AuditLogStatus.SUCCESS, "Temp zip : " + srcZip);

		ZipFile zipFile = new ZipFile(srcZip);
		// Setting parameters
		ZipParameters zipParameters = new ZipParameters();
		zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
		zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_ULTRA);
		zipParameters.setEncryptFiles(true);
		zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
		zipParameters.setIncludeRootFolder(false);
		// Setting password
		audit.addLog(AuditLogStatus.SUCCESS, "Set password zip file : " + passwordToZip);
		zipParameters.setPassword(passwordToZip);

		audit.addLog(AuditLogStatus.SUCCESS, "Add file to zip...");

		zipFile.addFiles(listFileSrc, zipParameters);
		
		audit.addLog(AuditLogStatus.SUCCESS, "Finish to generate zip file.");
		
		audit.addLog(AuditLogStatus.SUCCESS, "Start transfer file : " + Soure_preFix_sufFixFileZip + ".zip" + " to FTPS");
		
		// Upload Zip File to FTPS
		upload.UploadFTPS(audit, log, ftps, targetPath+ "/" + Target_preFix_sufFixFileZip+".zip", srcZip);
		
		//Archive file from List
		for(int i=0; i<listFileSrc.size(); i++){
			log.add(listFileSrc.get(i).getName() + " in " + Target_preFix_sufFixFileZip+".zip"); //write log list file to zip
			String Soure_preFix_sufFixFile = prefix_suffix_time.Soure_preFix_sufFix_timeStamp(listFileSrc.get(i).getName(), audit, paramConnection, log,paramConnection.getFormatTimeStamp());
			File archivePath = new File(paramConnection.getSrcPath()+ "/archive/"+ Soure_preFix_sufFixFile);
			File srcPath = new File(listFileSrc.get(i).getAbsolutePath());
		archiveDelete.archiveFileToFolder(srcPath, archivePath, Soure_preFix_sufFixFile, audit, log);
		}
		// Archive Zip file
		File archiveZipPath = new File(paramConnection.getSrcPath()+ "/archive/"+ Soure_preFix_sufFixFileZip);
		File srcZipPath = new File(srcZip);
		archiveDelete.archiveFileToFolder(srcZipPath, archiveZipPath, Soure_preFix_sufFixFileZip, audit, log);
	}
}
