package com.nick.svn;

import java.io.File;
import java.net.URL;

import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.util.SVNURLUtil;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNMoveClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import org.tmatesoft.svn.core.wc.admin.SVNAdminClient;

public class SVNUtil {
	

	static String svnRoot = "http://172.19.13.219/svn/LeoProject/NickTest";
	static String username = "acn_nickwen";
	static String password = "Init@0310";

	
	public static void main(String[] args) {
		SVNClientManager clientManager = getClientManager(svnRoot, username, password);
		
		SVNAdminClient adminClient = clientManager.getAdminClient();
		SVNMoveClient moveClient = clientManager.getMoveClient();
		
		try {
			moveClient.doMove(new File("http://172.19.13.219/svn/LeoProject/NickTest/test1"), new File("http://172.19.13.219/svn/LeoProject/NickTest/Test1"));
		} catch (SVNException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String url = "http://172.19.13.219/svn/LeoProject/NickTest/Test";
//		String commitMessage = "test";
//		makeDirectory(clientManager, url, commitMessage);
	}
	
	
	/**
	 * 通过不同的协议初始化版本库
	 */
	public static void setupLibrary() {
		DAVRepositoryFactory.setup();
		SVNRepositoryFactoryImpl.setup();
		FSRepositoryFactory.setup();
	}

	/**
	 * 验证登录svn
	 */
	public static SVNClientManager getClientManager(String svnRoot, String username,
			String password) {
		// 初始化版本库
		setupLibrary();

		// 创建库连接
		SVNRepository repository = null;
		try {
			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(svnRoot));
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
			
			return null;
		}

		// 身份验证
		ISVNAuthenticationManager authManager = SVNWCUtil

		.createDefaultAuthenticationManager(username, password);

		// 创建身份验证管理器
		repository.setAuthenticationManager(authManager);

		DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
		SVNClientManager clientManager = SVNClientManager.newInstance(options,
				authManager);
		return clientManager;
	}
	
	/**
	 * Make directory in svn repository
	 * @param clientManager
	 * @param url 
	 * 			eg: http://svn.ambow.com/wlpt/bsp/trunk 
	 * @param commitMessage
	 * @return
	 * @throws SVNException
	 */
	public static SVNCommitInfo makeDirectory(SVNClientManager clientManager,
			SVNURL url, String commitMessage) {
		try {
			return clientManager.getCommitClient().doMkDir(
					new SVNURL[] { url }, commitMessage);
		} catch (SVNException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
