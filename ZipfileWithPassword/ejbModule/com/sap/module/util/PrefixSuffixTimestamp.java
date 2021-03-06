package com.sap.module.util;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;

import com.sap.aii.af.lib.mp.module.ModuleException;
import com.sap.engine.interfaces.messaging.api.auditlog.AuditLogStatus;



public class PrefixSuffixTimestamp{
	
	
	public  String Soure_preFix_sufFix_timeStamp(String fileName,AuditLogHelper audit,ParameterID paramConnection,Logger log,String formatTimeStamp)throws IOException, ModuleException {
		String timeStamp = new SimpleDateFormat(formatTimeStamp).format(new Date());
		String basename = FilenameUtils.getBaseName(fileName);
		String extension = FilenameUtils.getExtension(fileName);
		String name = "";
		
		if (!paramConnection.getSrcPrefix().equalsIgnoreCase("") && paramConnection.getSrcSuffix().equalsIgnoreCase("") && paramConnection.getSrcTimestamp().equals(false)) {
			name = paramConnection.getSrcPrefix() + fileName;
			audit.addLog(AuditLogStatus.SUCCESS,"Add source Prefix : "+name);
		} else if (!paramConnection.getSrcSuffix().equalsIgnoreCase("") && paramConnection.getSrcPrefix().equalsIgnoreCase("") && paramConnection.getSrcTimestamp().equals(false)) {
			name = basename + paramConnection.getSrcSuffix() + "." + extension;
			audit.addLog(AuditLogStatus.SUCCESS,"Add soruce Suffix : "+name);
		} else if (paramConnection.getSrcTimestamp().equals(true) && paramConnection.getSrcPrefix().equalsIgnoreCase("") && paramConnection.getSrcSuffix().equalsIgnoreCase("")) {
			name =  timeStamp + "_" + fileName ;
			audit.addLog(AuditLogStatus.SUCCESS,"Add soruce Timestamp : "+name);
		} else if (paramConnection.getSrcPrefix().equalsIgnoreCase("") && paramConnection.getSrcSuffix().equalsIgnoreCase("") && paramConnection.getSrcTimestamp().equals(false)) {
			name = fileName;
			audit.addLog(AuditLogStatus.SUCCESS,"Do nothing source file name : "+name);
		}
		else{
			audit.addLog(AuditLogStatus.ERROR,"Source mode invalid!!");
		throw new ModuleException(new Exception("Target mode invalid!!"));
		}
		return name;
	}

	public  String Target_preFix_sufFix_timeStamp(String fileName, AuditLogHelper audit,ParameterID paramConnection,Logger log,String formatTimeStamp)throws IOException, ModuleException {
		String timeStamp = new SimpleDateFormat(formatTimeStamp).format(new Date());

		String basename = FilenameUtils.getBaseName(fileName);
		String extension = FilenameUtils.getExtension(fileName);
		String name = "";
		
		if (!paramConnection.getTarPrefix().equalsIgnoreCase("") && paramConnection.getTarSuffix().equalsIgnoreCase("") && paramConnection.getTarTimestamp().equals(false) ){
			name = paramConnection.getTarPrefix() + fileName;
			audit.addLog(AuditLogStatus.SUCCESS,"Add target Prefix : "+name);
		} else if (!paramConnection.getTarSuffix().equalsIgnoreCase("") && paramConnection.getTarPrefix().equalsIgnoreCase("") && paramConnection.getTarTimestamp().equals(false) ) {
			name = basename + paramConnection.getTarSuffix()  + extension;
			audit.addLog(AuditLogStatus.SUCCESS,"Add target Suffix : "+name);
		} else if (paramConnection.getTarTimestamp().equals(true) && paramConnection.getTarPrefix().equalsIgnoreCase("") && paramConnection.getTarSuffix().equalsIgnoreCase("") ) {
			name =  timeStamp + "_" + fileName ;
			audit.addLog(AuditLogStatus.SUCCESS,"Add target Timestamp : "+name);
		} else if (paramConnection.getTarPrefix().equalsIgnoreCase("") && paramConnection.getTarSuffix().equalsIgnoreCase("") && paramConnection.getTarTimestamp().equals(false) ) {
			name = fileName;
			audit.addLog(AuditLogStatus.SUCCESS,"Do nothing target file name : "+name);
		}
		else{
			audit.addLog(AuditLogStatus.ERROR,"Target mode invalid!!");
			 throw new ModuleException(new Exception("Target mode invalid!!"));
		}
		return name;
	}

}


