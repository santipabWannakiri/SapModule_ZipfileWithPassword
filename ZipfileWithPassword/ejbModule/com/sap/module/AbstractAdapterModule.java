package com.sap.module;


import java.io.IOException;

import com.sap.aii.af.lib.mp.module.Module;
import com.sap.aii.af.lib.mp.module.ModuleContext;
import com.sap.aii.af.lib.mp.module.ModuleData;
import com.sap.aii.af.lib.mp.module.ModuleException;
import com.sap.engine.interfaces.messaging.api.Message;
import com.sap.engine.interfaces.messaging.api.XMLPayload;
import com.sap.engine.interfaces.messaging.api.auditlog.AuditLogStatus;
import com.sap.module.util.AuditLogHelper;
import com.sap.module.util.Logger;
import com.sap.module.util.ParameterHelper;


public abstract class AbstractAdapterModule implements Module{
	
	protected Message msg;
	protected  static AuditLogHelper audit;
	protected ParameterHelper param;
	protected Logger log;
	protected XMLPayload payload;
	
	@Override
	public ModuleData process(ModuleContext moduleContext,
			ModuleData inputModuleData) throws ModuleException {
		
		msg = (Message) inputModuleData.getPrincipalData();
		payload = msg.getDocument();
		audit = new AuditLogHelper(msg);
		param = new ParameterHelper(moduleContext, audit);
		log = new Logger(moduleContext, audit);
		audit.addLog(AuditLogStatus.SUCCESS,"Start Module create instant. ");
		processModule();
		try {
			audit.addLog(AuditLogStatus.SUCCESS,"Access ZippasswordProcess");
			ZipPasswordProcess();
		} catch (IOException e) {
			audit.addLog(AuditLogStatus.ERROR,e.getMessage());
		}
		WriteLog(log,payload);	

		inputModuleData.setPrincipalData(msg);	
		return inputModuleData;
	}
	
	abstract protected void processModule() throws ModuleException;
	abstract protected void ZipPasswordProcess() throws ModuleException,IOException;
	abstract protected void WriteLog(Logger log,XMLPayload payload) throws ModuleException;
}
