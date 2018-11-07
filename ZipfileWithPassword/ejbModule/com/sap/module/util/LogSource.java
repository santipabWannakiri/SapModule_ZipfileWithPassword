package com.sap.module.util;

import java.io.FileWriter;
import java.io.IOException;

import com.sap.aii.af.lib.mp.module.ModuleException;
import com.sap.engine.interfaces.messaging.api.auditlog.AuditLogStatus;

public class LogSource {

	public void WriteLogSrc(String srcLog, String fileName, Logger log ,AuditLogHelper audit) throws IOException,ModuleException {
		
		FileWriter fileWriter;
		
		audit.addLog(AuditLogStatus.SUCCESS,"Create backup Log : "+ srcLog +"/"+ fileName);
		String tmpLogWrite = "\r\n ============== Log File ==============\r\n";
		for (int i = 0; i < log.getLogs().size(); i++) {
			tmpLogWrite += log.getLogs().get(i) + "\r\n";
		}

		try {
			fileWriter = new FileWriter(srcLog +"/"+ fileName);
			fileWriter.write(tmpLogWrite); //content
			fileWriter.close();
		} catch (IOException e) {
			throw new ModuleException(e.getMessage(), e);
		}

	}

}
