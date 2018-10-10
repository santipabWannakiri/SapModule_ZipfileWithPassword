package com.sap.module.util;

import java.io.File;
import java.util.ArrayList;

public class GetFileandExtensions {

	public ArrayList<File> GetListfileFromSrc(String src, String[] extensions) {
		ArrayList<File> listFile = new ArrayList();
		File folder = new File(src);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				for (int j = 0; j < extensions.length; j++) {
					if (listOfFiles[i].getName().endsWith(extensions[j])) {
						listFile.add(listOfFiles[i]);
					}
				}
			}

		}
		return listFile;
	}

	public String[] SplitExtensions(String extension) {
		String[] splitArray = extension.split(",");
		return splitArray;
	}

}
