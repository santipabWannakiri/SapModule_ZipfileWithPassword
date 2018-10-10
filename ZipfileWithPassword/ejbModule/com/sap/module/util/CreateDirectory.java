package com.sap.module.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPSClient;

import com.sap.aii.af.lib.mp.module.ModuleException;
import com.sap.engine.interfaces.messaging.api.auditlog.AuditLogStatus;
import com.sap.module.util.AuditLogHelper;
import com.sap.module.util.Logger;

public class CreateDirectory {

	
	public  boolean CreateDirectorysFTPS(String tempSrcPath, String tempTargetPath ,FTPSClient ftps , Boolean Archive ,AuditLogHelper audit, Logger log)throws ModuleException {
		try {
			// ==================== Create Directory ====================
			audit.addLog(AuditLogStatus.SUCCESS,"Sorce path AL11 :" + tempSrcPath);
			audit.addLog(AuditLogStatus.SUCCESS,"Create Directory : FTPS target path :" + tempTargetPath);

			
			// Create archive folder
			if (Archive == true) {
				File files = new File(tempSrcPath + "/archive");
				if (!files.exists()) {
					if (files.mkdirs()) {
						audit.addLog(AuditLogStatus.SUCCESS,"Create Directory : Archive folder : "+ tempSrcPath + "/archive");
					} else {
						audit.addLog(AuditLogStatus.SUCCESS,"Failed to create archive folder!");
					}
				} else {
					audit.addLog(AuditLogStatus.SUCCESS,"The directory archive exists!!");
				}
			}
			// Create FTPS target path
			String[] directories = tempTargetPath.split("/");
			ftps.changeWorkingDirectory("/");
			boolean dirExists = true;
			for (String dir : directories) {
				if (!dir.isEmpty()) {
					if (dirExists) {
						dirExists = ftps.changeWorkingDirectory(dir);
					}
					if (!dirExists) {
						if (!ftps.makeDirectory(dir)) {
							audit.addLog(AuditLogStatus.WARNING,"Unable to create remote directory '" + dir+ "'.  error='"+ ftps.getReplyString() + "'");
						}
						if (!ftps.changeWorkingDirectory(dir)) {
							audit.addLog(AuditLogStatus.WARNING,"Unable to change into newly created remote directory '"+ dir + "'.  error='"+ ftps.getReplyString() + "'");
						}
					}
				}
			}
		} catch (IOException e) {
			audit.addLog(AuditLogStatus.ERROR,e.getMessage());
			throw new ModuleException(e.getMessage(), e);
		}
		return true;

	}

}
