package com.Transformation;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XSLTConversion 
{
   public void xslTransformation(String xslt,String XML, String outXML, JTextField textField_3) throws IOException
   {
	  //System.out.println("Output to====xslt=========>"+xslt);
	  //System.out.println("Output to====XML=========>"+XML);
	  //System.out.println("Output to====outXML=========>"+outXML);
	   	
	      String line="";
	      int count=0;
	      String refile=XML.replace(".xml","_out.xml");
		  BufferedReader br=new BufferedReader(new FileReader(XML));
		  PrintWriter pw=new PrintWriter(new FileWriter(refile));
		  while((line=br.readLine())!=null)
		  {			  
			  //xlink:href="http://arxiv.org/abs/astro&hyphen;ph/9708014"					  
			  Pattern pat3=Pattern.compile("\\s*xlink\\:href=\"([^\"]+?)\"");
			  Matcher mat3=pat3.matcher(line);
			  while(mat3.find())
			  {
				  String s8="";
				  s8=mat3.group();						  
				  if(s8.indexOf("<")==-1 || s8.indexOf(">")==-1)
				  {
					  //System.out.println("-------->"+mat3.group());  
					  line=line.replace(s8, "");
				  }  
				  
			  }					  
			  
			  Pattern pat4=Pattern.compile("\\s*xmlns\\:xlink=\"([^\"]+?)\"");
			  Matcher mat4=pat4.matcher(line);
			  while(mat4.find())
			  {
				  String s9="";
				  s9=mat4.group();						  
				  if(s9.indexOf("<")==-1 || s9.indexOf(">")==-1)
				  {
					  //System.out.println("-------->"+mat4.group());  
					  line=line.replace(s9, "");
				  }						  
			  }	  
			  
			  Pattern pat1=Pattern.compile("(\\<[A-z][^>]*?)(\\/\\>|\\>)");
			  Matcher mat1=pat1.matcher(line);
			  while(mat1.find())
			  {
				  count++;
				  String s1="",s2="",s3="",s4="",s5="";
				  s1=mat1.group();
				  s2=mat1.group();
				  s3=mat1.group(1);
				  s5=mat1.group(2);
				  s3=s3.replaceAll("(.+)", "$1 name=\"OPT_ID_"+count+"\"");
				  s4=mat1.group(1);
				  s1=s1.replace(s4, s3);
				  line=line.replace(s2, s1);
				  //System.out.println("===========>"+s1 +"------>"+s3);
			  }
			  
			  Pattern pat2=Pattern.compile("(\\&[A-z]{1,7}\\;)");
			  Matcher mat2=pat2.matcher(line);
			  if(mat2.find())
			  {
				  String s6="",s7="";						  
				  s6=mat2.group();
				  s7=mat2.group();
				  s6=s6.replace(s6,"<span class=\"unicode-char\">"+s6+"</span>");
				  line=line.replace(s7, s6);						  						  
				  //System.out.println("===========>"+mat2.group());
			  }
			  pw.println(line);										  
		  }
		  pw.close();
		  br.close();	
	     
		  
		 try {
	         File sf = new File(refile); // source file
	         File rf = new File(outXML); // result file
	         File tf = new File(xslt); // template file
	         TransformerFactory f = TransformerFactory.newInstance();
	         Transformer t = f.newTransformer(new StreamSource(tf));
	         Source s = new StreamSource(sf);
	         Result r = new StreamResult(rf);
	         t.transform(s,r);
	         textField_3.setText("HTML Transformation Completed.......");
	      } catch (TransformerConfigurationException e) 
	      {	    	 
	         System.out.println(e.toString());         
	         textField_3.setText("Transformation Exception "+e);
	         File delete_file=new File(refile);
			 if(delete_file.exists())
			 {
				 delete_file.delete();
			 }
	      } catch (TransformerException e) {
	    
	         System.out.println(e.toString());
	         textField_3.setText("Transformation Exception "+e);
	         
	         File delete_file=new File(refile);
			 if(delete_file.exists())
			 {
				 delete_file.delete();
			 }
	      }
		  
		 
		 File delete_file=new File(refile);
		 if(delete_file.exists())
		 {
			 delete_file.delete();
		 }
		
	   
   }
}