package com.nick.svn;

import java.io.File;
import java.util.List;
import java.util.Map;

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

	static String svnRoot = "http://172.19.13.219/svn/LeoProject/NickTest";
	static String name = "acn_nickwen";
	static String password = "Init@0310";
	
	public static void main(String[] args) {
		try {
			moveFile();
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	static private SVNClientManager clientManager;

	private static SVNClientManager getSVNClientManager() {

		if (clientManager == null) {
			synchronized (SVNUtil.class) {
				
				SVNRepositoryFactoryImpl.setup();

				ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
				
				clientManager = SVNClientManager.newInstance((DefaultSVNOptions) options, name, password);
			}
		}

		return clientManager;
	}
	
	public static void moveFile() throws SVNException {
		
		SVNClientManager svnClientManager = getSVNClientManager();
		

		
		SVNCopySource svnCopySource1 = null;
		
		svnCopySource1 = new SVNCopySource(SVNRevision.HEAD,SVNRevision.HEAD,SVNURL.parseURIEncoded("http://172.19.13.219/svn/LeoProject/NickTest/testmove1"));

		
		SVNCopySource[] sources = new SVNCopySource[] {svnCopySource1};
		SVNURL dst = null;
		
		dst = SVNURL.parseURIEncoded("http://172.19.13.219/svn/LeoProject/NickTest/testmove2");
	
		Map<SvnTarget, List<SVNExternal>> revisionProperties;
		
		
		
		SVNCopyClient copyClient = svnClientManager.getCopyClient();
		
		
		boolean isMove = true;
		boolean makeParents  = false;
		boolean failWhenDstExists = false;
		String commitMessage = "tst";
		SVNCommitInfo info = copyClient.doCopy(sources, dst, isMove,  makeParents, failWhenDstExists, commitMessage, new SVNProperties());
		
		
		System.out.println(info.toString());
		
		
		
		
		
		
	}

}
