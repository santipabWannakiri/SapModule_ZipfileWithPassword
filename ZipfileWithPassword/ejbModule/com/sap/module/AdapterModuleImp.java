package com.sap.module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import javax.ejb.Local;
import javax.ejb.LocalHome;
import javax.ejb.Remote;
import javax.ejb.RemoteHome;
import javax.ejb.Stateless;


import org.apache.commons.net.ftp.FTPSClient;


import com.sap.aii.af.lib.mp.module.ModuleException;
import com.sap.aii.af.lib.mp.module.ModuleHome;
import com.sap.aii.af.lib.mp.module.ModuleLocal;
import com.sap.aii.af.lib.mp.module.ModuleLocalHome;
import com.sap.aii.af.lib.mp.module.ModuleRemote;
import com.sap.engine.interfaces.messaging.api.XMLPayload;
import com.sap.engine.interfaces.messaging.api.auditlog.AuditLogStatus;

import com.sap.module.connection.Connection;
import com.sap.module.service.UploadFileToFTPS;
import com.sap.module.service.ZipfileWithPassword;
import com.sap.module.util.CreateDirectory;
import com.sap.module.util.GetFileandExtensions;
import com.sap.module.util.Logger;
import com.sap.module.util.ParameterID;



@Stateless(name = "ZipfileWithPassword")
@Local(value = { ModuleLocal.class })
@Remote(value = { ModuleRemote.class })
@LocalHome(value = ModuleLocalHome.class)
@RemoteHome(value = ModuleHome.class)
public class AdapterModuleImp extends AbstractAdapterModule{

	protected  byte[] b_content;
	private Connection connectionFTPS;
	private CreateDirectory directory;
	private ZipfileWithPassword zipfileWithPassword;
	private GetFileandExtensions fileandExtensions;
	private UploadFileToFTPS upload;
	private ParameterID paramConnection = new ParameterID();

	@Override
	protected void processModule() throws ModuleException {
		
		paramConnection.setFormatTimeStamp(this.param.getParameter("formatTimeStamp", "yyyyMMdd-HHmmss", true));
		paramConnection.setExtensionsWithoutZip(this.param.getParameter("extensionsWithoutZip", "", true));
		paramConnection.setExtensionsToZip(this.param.getParameter("extensionsToZip", "", true));
		paramConnection.setFormatFileName(this.param.getParameter("formatFileName", "PI_ZIPFILE", true));
		paramConnection.setPasswordToZipfile(this.param.getParameter("passwordToZip", "", true));
		paramConnection.setSrcPath(this.param.getParameter("srcPath", "", true));
		paramConnection.setTargetPath(this.param.getParameter("tarPath", "", true));
		paramConnection.setProtocol(this.param.getParameter("protocol", "ftps", true));
		paramConnection.setServer(this.param.getParameter("receiverServer", "", true));
		paramConnection.setUsername(this.param.getParameter("username", "", true));
		paramConnection.setPassword(this.param.getParameter("pwdPassword", "", true));
		paramConnection.setPortNumber(this.param.getIntParameter("port", 0, true));
		paramConnection.setAppendLog(this.param.getBoolParameter("appendLog", "Y", true));
		paramConnection.setArchive(this.param.getBoolParameter("archive", "Y", true));
		paramConnection.setSrcTimestamp(this.param.getBoolParameter("srcTimestamp", "N", true));
		paramConnection.setSrcPrefix(this.param.getParameter("srcPrefix", "", true));
		paramConnection.setSrcSuffix(this.param.getParameter("srcSuffix", "", true));
		paramConnection.setTarTimestamp(this.param.getBoolParameter("tarTimestamp", "N", true));
		paramConnection.setTarPrefix(this.param.getParameter("tarPrefix", "", true));
		paramConnection.setTarSuffix(this.param.getParameter("tarSuffix", "", true));

//		audit.addLog(AuditLogStatus.SUCCESS,"== Adapter mode == \n Archive mode : " + paramConnection.getArchive() + "\n Delete mode : " + paramConnection.getDelete() + "\n appendLog mode : " + paramConnection.getAppendLog());
//		log.add("== Adapter mode == \n Archive mode : " + paramConnection.getArchive() + "\n Delete mode : " + paramConnection.getDelete() + "\n appendLog mode : " + paramConnection.getAppendLog());
//		audit.addLog(AuditLogStatus.SUCCESS,"== Source mode == \n TimeStamp : " + paramConnection.getSrcTimestamp() + "\n srcPrefix : " + paramConnection.getSrcPrefix() + "\n Suffix : " + paramConnection.getSrcSuffix());
//		log.add("== Source mode == \n TimeStamp : " + paramConnection.getSrcTimestamp() + "\n srcPrefix : " + paramConnection.getSrcPrefix() + "\n Suffix : " + paramConnection.getSrcSuffix());
//		audit.addLog(AuditLogStatus.SUCCESS,"== Target mode == \n TimeStamp : " + paramConnection.getTarTimestamp()+ "\n srcPrefix : " + paramConnection.getTarPrefix() + "\n Suffix : "+ paramConnection.getTarSuffix());
//		log.add("== Target mode == \n TimeStamp : " + paramConnection.getTarTimestamp()+ "\n srcPrefix : " + paramConnection.getTarPrefix() + "\n Suffix : "+ paramConnection.getTarSuffix());
//		// Print action
		audit.addLog(AuditLogStatus.SUCCESS, "== Adapter mode == \n passwordToZip : " + paramConnection.getPasswordToZipfile() + "\n srcPath : " + paramConnection.getSrcPath() + "\n tarPath : " + paramConnection.getTargetPath());
		audit.addLog(AuditLogStatus.SUCCESS, "Archive : " + paramConnection.getArchive());
		
		
	}
	
	
	@Override
	protected void ZipPasswordProcess() throws ModuleException, IOException {
		zipfileWithPassword = new ZipfileWithPassword();
		connectionFTPS  = new Connection();
		 directory = new CreateDirectory();
		 fileandExtensions = new GetFileandExtensions();
		 upload = new UploadFileToFTPS();
		Boolean IsDirectory = false;
		 
		String extensionsToZip [] = fileandExtensions.SplitExtensions(paramConnection.getExtensionsToZip());
		String extensionsWithoutZip [] = fileandExtensions.SplitExtensions(paramConnection.getExtensionsWithoutZip());
		
		ArrayList<File> listFileToZipSrc = fileandExtensions.GetListfileFromSrc(paramConnection.getSrcPath(), extensionsToZip);
		ArrayList<File> listFileWithoutZipSrc = fileandExtensions.GetListfileFromSrc(paramConnection.getSrcPath(), extensionsWithoutZip);
		
		FTPSClient ftps = connectionFTPS.LoginFTPS( paramConnection.getServer(), paramConnection.getPortNumber(), paramConnection.getUsername(), paramConnection.getPassword(), audit, log);
		IsDirectory = directory.CreateDirectorysFTPS(paramConnection.getSrcPath(), paramConnection.getTargetPath(), ftps, paramConnection.getArchive() ,audit,log);
		//Check file in folder for bypass case don't have file
		if(!listFileWithoutZipSrc.isEmpty()){
			//Upload file to ftps without zip
		upload.UploadFTPS(audit, log, ftps, paramConnection.getTargetPath(), listFileWithoutZipSrc,paramConnection );
		}else if(!listFileToZipSrc.isEmpty()){
		if(IsDirectory == true){
			try {
				audit.addLog(AuditLogStatus.SUCCESS,  "Access to zip module");
				zipfileWithPassword.compressWithPassword(audit, paramConnection, log, paramConnection.getSrcPath(), paramConnection.getTargetPath(),paramConnection.getPasswordToZipfile(),paramConnection.getFormatFileName(),ftps,listFileToZipSrc);
				audit.addLog(AuditLogStatus.SUCCESS,"Transfer done.");
			} catch (Exception e) {
				audit.addLog(AuditLogStatus.ERROR,  e.getMessage());
				connectionFTPS.LogoutFTPS(ftps, audit, log);
				throw new ModuleException(e.getMessage(), e);
			}finally{
				connectionFTPS.LogoutFTPS(ftps, audit, log);
			}
		}else{
			audit.addLog(AuditLogStatus.ERROR,  "Error Please check fuction create directory!!");
			connectionFTPS.LogoutFTPS(ftps, audit, log);
			throw new ModuleException("Error Please check fuction create directory!!");
		}
	 }else{
		 connectionFTPS.LogoutFTPS(ftps, audit, log);
		audit.addLog(AuditLogStatus.WARNING,  "Don't have file in folder");
	 }
	}
	

	@Override
	protected void WriteLog(Logger log,XMLPayload payload) throws ModuleException {
		audit.addLog(AuditLogStatus.WARNING  , "log size"+ log.getLogs().size());
		log.add( "write log size"+ log.getLogs().size());
		if (paramConnection.getAppendLog() == true) {
			try {
				String strOutput = new String();
				String tmpLogWrite = "\r\n ============== Log File ==============\r\n";
				for (int i = 0; i < log.getLogs().size(); i++) {
					tmpLogWrite += log.getLogs().get(i) + "\r\n";
				}
				strOutput = strOutput + tmpLogWrite;
				payload.setContent(strOutput.getBytes("UTF-8"));
			} catch (Exception e) {
				audit.addLog(AuditLogStatus.ERROR  , "Error "+ e.getMessage());
				log.add("Error " + e.getMessage());
				throw new ModuleException(e.getMessage(), e);
			}
		}
		log.clearData();
		
	}
	
}
