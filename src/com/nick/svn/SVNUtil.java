package com.nick.svn;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.internal.wc.SVNExternal;
import org.tmatesoft.svn.core.wc.ISVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCopyClient;
import org.tmatesoft.svn.core.wc.SVNCopySource;
import org.tmatesoft.svn.core.wc.SVNMoveClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc.admin.SVNLookClient;
import org.tmatesoft.svn.core.wc2.SvnTarget;

public  class SVNUtil {
	
	
	private static String username = null;
	private static String password = null;
	static {
		try {
			Properties prop = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream("svnconfig.properties")) ;
			prop.load(in);
			
			username = (String) prop.get("username");
			password = (String) prop.get("password");
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		String resourcePath = "";
		String targetPath = "";
		try {
			moveFile(resourcePath, targetPath);
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	static private SVNClientManager clientManager;

	/**
	 * 
	 * @return
	 */
	private static SVNClientManager getSVNClientManager() {

		if (clientManager == null) {
			synchronized (SVNUtil.class) {
				
				SVNRepositoryFactoryImpl.setup();

				ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
				
				clientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, username, password);
			}
		}

		return clientManager;
	}
	
	/**
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @throws SVNException
	 */
	public static void moveFile(String sourcePath, String targetPath) throws SVNException {
		
		SVNClientManager svnClientManager = getSVNClientManager();
		
		SVNCopySource svnCopySource = null;
		
		svnCopySource = new SVNCopySource(SVNRevision.HEAD,SVNRevision.HEAD,SVNURL.parseURIEncoded(sourcePath));

		
		SVNCopySource[] sources = new SVNCopySource[] {svnCopySource};
		
		SVNURL dst = SVNURL.parseURIEncoded(targetPath);
	
		SVNCopyClient copyClient = svnClientManager.getCopyClient();
		
		
		boolean isMove = true;
		boolean makeParents  = false;
		boolean failWhenDstExists = false;
		String commitMessage = "archive files";
		SVNCommitInfo info = copyClient.doCopy(sources, dst, isMove,  makeParents, failWhenDstExists, commitMessage, new SVNProperties());
		
		
		System.out.println(info.toString());
		
		
		
		
		
		
	}

}
