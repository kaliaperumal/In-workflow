package com.seqnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SequenceNumber {
	public static void main(String args[]) throws IOException {
		File f1 = new File(".");
		File[] f2 = f1.listFiles();
		for (int i = 0; i < f2.length; i++) {
			if (f2[i].isFile()) {
				String filename = "", line = "";
				filename = f2[i].getName();
				int count = 0;
				if (filename.indexOf("_id") == -1
						&& filename.toLowerCase().endsWith(".xml")) {
					String index = filename.substring(0,
							filename.lastIndexOf(".xml"));
					String out_filename = filename.replaceAll("(.xml|.XML)",
							"_id.xml");
					System.out.println(filename + "\n");

					BufferedReader br = new BufferedReader(new FileReader(
							filename));
					PrintWriter pw = new PrintWriter(new FileWriter(
							out_filename));
					while ((line = br.readLine()) != null) {

						// xlink:href="http://arxiv.org/abs/astro&hyphen;ph/9708014"
						/*
						 * Pattern pat3 = Pattern
						 * .compile("\\s*xlink\\:href=\"([^\"]+?)\""); Matcher
						 * mat3 = pat3.matcher(line); while (mat3.find()) {
						 * String s8 = ""; s8 = mat3.group(); if
						 * (s8.indexOf("<") == -1 || s8.indexOf(">") == -1) { //
						 * System.out.println("-------->"+mat3.group()); line =
						 * line.replace(s8, ""); }
						 * 
						 * }
						 */

						// xlink:href="http://arxiv.org/abs/astro&hyphen;ph/9708014"

						Pattern pat4 = Pattern
								.compile("\\s*xmlns\\:xlink=\"([^\"]+?)\"");
						Matcher mat4 = pat4.matcher(line);
						while (mat4.find()) {
							String s9 = "";
							s9 = mat4.group();
							if (s9.indexOf("<") == -1 || s9.indexOf(">") == -1) {
								// System.out.println("-------->"+mat4.group());
								line = line.replace(s9, "");
							}
						}

						Pattern pat1 = Pattern
								.compile("(\\<[A-z][^>]*?)(\\/\\>|\\>)");
						Matcher mat1 = pat1.matcher(line);
						while (mat1.find()) {

							count++;
							String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "";
							s1 = mat1.group();
							s2 = mat1.group();
							s3 = mat1.group(1);
							s5 = mat1.group(2);

							s3 = s3.replaceAll("(.+)", "$1 name=\"TRS_ID_"
									+ count + "\"");
							s4 = mat1.group(1);
							s1 = s1.replace(s4, s3);
							line = line.replace(s2, s1);
						}

						if (line.indexOf("<ext-link") != -1) {

							line = line
									.replaceAll(
											"(<ext-link.+?\"\\>.+?)(\\&[A-z]{1,7}\\;)(.+?</ext-link>)",
											"$1<span class=\"unicode-char\">$2</span>$3");
						} else {
							// System.out.println("==============>else ::: " +
							// line);
							Pattern pat2 = Pattern
									.compile("(\\&[A-z]{1,7}\\;)");
							Matcher mat2 = pat2.matcher(line);
							if (mat2.find()) {
								String s6 = "", s7 = "";
								s6 = mat2.group();
								s7 = mat2.group();
								if (s6.indexOf("<") == -1
										|| s6.indexOf("<") == -1) {
									s6 = s6.replace(s6,
											"<span class=\"unicode-char\">"
													+ s6 + "</span>");
									line = line.replace(s7, s6);
									// System.out.println("==sss=========>"+mat2.group());
								}

							}
						}
						pw.println(line);
					}
					pw.close();
					br.close();
				}
			}
		}
	}
}