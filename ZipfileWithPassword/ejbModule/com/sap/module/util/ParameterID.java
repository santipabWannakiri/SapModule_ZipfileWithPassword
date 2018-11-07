package com.sap.module.util;

public class ParameterID {

	private String formatTimeStamp;
	private String extensionsWithoutZip;
	private String extensionsToZip;
	private String formatFileName;
	private String passwordToZipfile;
	private String protocol;
	private String server;
	private String username;
	private String password;
	private int portNumber;
	private Boolean appendLog;
	private Boolean Archive;
	private Boolean srcTimestamp;
	private String srcPrefix;
	private String srcSuffix;
	private Boolean tarTimestamp;
	private String tarPrefix;
	private String tarSuffix;
	private String srcPath;
	private String targetPath;
	private String SrcDirLog;
	private String FileNameSrcLog;
	
	public String getFormatTimeStamp() {
		return formatTimeStamp;
	}
	public void setFormatTimeStamp(String formatTimeStamp) {
		this.formatTimeStamp = formatTimeStamp;
	}
	public String getExtensionsWithoutZip() {
		return extensionsWithoutZip;
	}
	public void setExtensionsWithoutZip(String extensionsWithoutZip) {
		this.extensionsWithoutZip = extensionsWithoutZip;
	}
	public String getExtensionsToZip() {
		return extensionsToZip;
	}
	public void setExtensionsToZip(String extensionsToZip) {
		this.extensionsToZip = extensionsToZip;
	}
	public String getFormatFileName() {
		return formatFileName;
	}
	public void setFormatFileName(String formatFileName) {
		this.formatFileName = formatFileName;
	}
	public String getPasswordToZipfile() {
		return passwordToZipfile;
	}
	public void setPasswordToZipfile(String passwordToZipfile) {
		this.passwordToZipfile = passwordToZipfile;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}
	public Boolean getAppendLog() {
		return appendLog;
	}
	public void setAppendLog(Boolean appendLog) {
		this.appendLog = appendLog;
	}
	public Boolean getArchive() {
		return Archive;
	}
	public void setArchive(Boolean archive) {
		Archive = archive;
	}
	public Boolean getSrcTimestamp() {
		return srcTimestamp;
	}
	public void setSrcTimestamp(Boolean srcTimestamp) {
		this.srcTimestamp = srcTimestamp;
	}
	public String getSrcPrefix() {
		return srcPrefix;
	}
	public void setSrcPrefix(String srcPrefix) {
		this.srcPrefix = srcPrefix;
	}
	public String getSrcSuffix() {
		return srcSuffix;
	}
	public void setSrcSuffix(String srcSuffix) {
		this.srcSuffix = srcSuffix;
	}
	public Boolean getTarTimestamp() {
		return tarTimestamp;
	}
	public void setTarTimestamp(Boolean tarTimestamp) {
		this.tarTimestamp = tarTimestamp;
	}
	public String getTarPrefix() {
		return tarPrefix;
	}
	public void setTarPrefix(String tarPrefix) {
		this.tarPrefix = tarPrefix;
	}
	public String getTarSuffix() {
		return tarSuffix;
	}
	public void setTarSuffix(String tarSuffix) {
		this.tarSuffix = tarSuffix;
	}
	public String getSrcPath() {
		return srcPath;
	}
	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	public String getTargetPath() {
		return targetPath;
	}
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	public String getSrcDirLog() {
		return SrcDirLog;
	}
	public void setSrcDirLog(String srcDirLog) {
		SrcDirLog = srcDirLog;
	}
	public String getFileNameSrcLog() {
		return FileNameSrcLog;
	}
	public void setFileNameSrcLog(String fileNameSrcLog) {
		FileNameSrcLog = fileNameSrcLog;
	}
	@Override
	public String toString() {
		return "ParameterID [Archive=" + Archive + ", FileNameSrcLog="
				+ FileNameSrcLog + ", SrcDirLog=" + SrcDirLog + ", appendLog="
				+ appendLog + ", extensionsToZip=" + extensionsToZip
				+ ", extensionsWithoutZip=" + extensionsWithoutZip
				+ ", formatFileName=" + formatFileName + ", formatTimeStamp="
				+ formatTimeStamp + ", password=" + password
				+ ", passwordToZipfile=" + passwordToZipfile + ", portNumber="
				+ portNumber + ", protocol=" + protocol + ", server=" + server
				+ ", srcPath=" + srcPath + ", srcPrefix=" + srcPrefix
				+ ", srcSuffix=" + srcSuffix + ", srcTimestamp=" + srcTimestamp
				+ ", tarPrefix=" + tarPrefix + ", tarSuffix=" + tarSuffix
				+ ", tarTimestamp=" + tarTimestamp + ", targetPath="
				+ targetPath + ", username=" + username + "]";
	}
	
	
	

}
