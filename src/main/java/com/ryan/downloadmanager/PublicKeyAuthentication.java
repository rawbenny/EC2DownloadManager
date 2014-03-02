package com.ryan.downloadmanager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class PublicKeyAuthentication {
	public static void startDownloadProcess(String pemFile, String sourceUrl,
			String destFileName) {

		String hostname = "ec2-54-201-88-26.us-west-2.compute.amazonaws.com";
		String username = "ec2-user";
		if (pemFile.equals("")) {
			pemFile = "/Users/ryan/nk.pem";
		}
		File keyfile = new File(pemFile); // or "~/.ssh/id_dsa"
		try {
			/* Create a connection instance */

			Connection conn = new Connection(hostname);

			/* Now connect */

			conn.connect();

			/* Authenticate */

			boolean isAuthenticated = conn.authenticateWithPublicKey(username,
					keyfile, "");

			if (isAuthenticated == false)
				throw new IOException("Authentication failed.");

			/* Create a session */

			Session sess = conn.openSession();
			String command = "cd /var/www/public/js/;wget -O " + destFileName
					+ " -bqc \"" + sourceUrl + "\"";
			System.out.println("Executing command:" + command);
			sess.execCommand("command");
			InputStream stdout = new StreamGobbler(sess.getStdout());
			BufferedReader br = new BufferedReader(
					new InputStreamReader(stdout));

			System.out.println("Information from the remote host:");

			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				System.out.println(line);
			}

			/* Close this session */
			br.close();
			sess.close();

			/* Close the connection */

			conn.close();

		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(2);
		}
	}
}
