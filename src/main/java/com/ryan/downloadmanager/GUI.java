package com.ryan.downloadmanager;
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

/**
 * This example, like all Swing examples, exists in a package:
 * in this case, the "start" package.
 * If you are using an IDE, such as NetBeans, this should work 
 * seamlessly.  If you are compiling and running the examples
 * from the command-line, this may be confusing if you aren't
 * used to using named packages.  In most cases,
 * the quick and dirty solution is to delete or comment out
 * the "package" line from all the source files and the code
 * should work as expected.  For an explanation of how to
 * use the Swing examples as-is from the command line, see
 * http://docs.oracle.com/javase/javatutorials/tutorial/uiswing/start/compile.html#package
 */

/*
 * HelloWorldSwing.java requires no other files. 
 */
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private void initUI() {
        //Create and set up the window.
        JPanel panel = new JPanel();
        
        
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel.setLayout(new GridLayout(4,1,5,5));
        
        JLabel pem = new JLabel("PEM(make sure this file exist):");
        final JTextField pemField = new JTextField();
        pemField.setText("/Users/ryan/nk.pem");
        
        JLabel label = new JLabel("URL:");
        final JTextField area = new JTextField();
        
        JLabel fieldLabel = new JLabel("Dest File Name:");
        final JTextField field = new JTextField(30);
        JButton exit = new JButton("Exit");
        exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
        	
        });
        
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        		Runnable r = (new Runnable(){
        			@Override
        			public void run(){
        				String sourceUrl = area.getText();
        				String destFileName = field.getText();
        				String pemFile = pemField.getText();
        				if(sourceUrl.equals("") || destFileName.equals("")){
        					System.out.println("Nothing to download.");
        					return;
        				}
        				System.out.println("Starting download task...");
        				PublicKeyAuthentication.startDownloadProcess(pemFile, sourceUrl, destFileName);
        			}
        		});
        		Thread t = new Thread(r);
        		t.start();
        	}
        });
        panel.add(pem);
        panel.add(pemField);
        panel.add(label);
        panel.add(area);
        panel.add(fieldLabel);
        panel.add(field);
        panel.add(exit);
        panel.add(start);
        this.add(panel);
        setSize(500,500);
        //Display the window.
        pack();
    }
    public GUI(){
    	initUI();
    }
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI gui = new GUI();
                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui.setVisible(true);
            }
        });
    }
}