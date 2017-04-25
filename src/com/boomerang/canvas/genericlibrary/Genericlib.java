package com.boomerang.canvas.genericlibrary;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.search.FlagTerm;

import org.json.JSONArray;
import org.json.JSONObject;
import com.boomerang.canvas.testbase.Testbase;

public class Genericlib extends Testbase{
	ReadOutputdata RTC = new ReadOutputdata();
	 DecimalFormat format = new DecimalFormat();
		
	//################################ Generic methods ####################################
		public double getresponsesintdata(String filename,String metrix,String value){
			double REVENUE = 0;
			JSONObject repo2 = null;
			String jsonFile = Readjson.readFileAsString(workspace+filename);
			RTC.dataparser(jsonFile);
			JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
			JSONArray repo = temp1.getJSONArray("data");
			for(int n = 0; n < repo.length(); n++)
			{
			    JSONObject r = repo.getJSONObject(n);
			    
			    try{
				    boolean repo1 = r.isNull(metrix);
				    if(!repo1){
			    repo2 = r.getJSONObject(metrix);
			    }
				    else{
				    	repo2 = null;
				    }
			    boolean b=repo2.isNull(value);
			    if(!b){
				REVENUE=repo2.getDouble(value);
			    }
			    else{
			    	REVENUE=(Double) null;
			    }
				    }
			    catch(Exception e){
			    	e.getMessage();
			    }
			}
			return REVENUE;
		}
		
		public String getresponsesStringdata(String filename,String metrix,String value){
			String REVENUE = "";
			String jsonFile = Readjson.readFileAsString(workspace+filename);
			RTC.dataparser(jsonFile);
			JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
			JSONArray repo = temp1.getJSONArray("data");
			for(int n = 0; n < repo.length(); n++)
			{
			    JSONObject r = repo.getJSONObject(n);
			    JSONObject repo2 = null;
			    try{
				    boolean repo1 = r.isNull(metrix);
				    if(!repo1){
				    	repo2 = r.getJSONObject(metrix);
				    }
				    else{
				    	repo2=null;
				    }
			    boolean b=repo2.isNull(value);
				    if(!b){
					REVENUE=repo2.getString(value);
				    }
				    else{
				    	REVENUE=null;
				    }
				    }catch(Exception e){
				    	e.getMessage();
				    }
			}
			return REVENUE;
		}
	

		public String truncate(Double l) throws Exception{
			
			Double toBeTruncated = new Double(l);
		    Double resrevenue1=new BigDecimal(toBeTruncated ).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	        String formattedvalue = format.format(resrevenue1);
	        return formattedvalue;
		}
		
		public String conversion(Double value1) throws Exception{
			String resrevenue1;
			if(value1>=0 && value1<1000){
				resrevenue1=truncate(value1);
			}
			else if(value1>=1000 && value1<1000000){
				resrevenue1=truncate(value1/1000) + "K";
				}
				else if(value1>=1000000 && value1<1000000000){
					resrevenue1=truncate(value1/1000000) + "M";
				}
				else{
					resrevenue1=truncate(value1/1000000000) + "B";
				}
			return resrevenue1;
		}
		public String conversioninpercent(Double value1) throws Exception{
			String resrevenue1;
			if(value1>=0 && value1<1000){
				resrevenue1=truncate(value1)+ "%";
			}
			else if(value1>=1000 && value1<1000000){
				resrevenue1=truncate(value1/1000) + "%";
				}
				else if(value1>=1000000 && value1<1000000000){
					resrevenue1=truncate(value1/1000000) + "%";
				}
				else{
					resrevenue1=truncate(value1/1000000000) + "%";
				}
			return resrevenue1;
		}
		
		public final static int READ_ALL = -1;
		  
		  
		  
		  /**
		   * This method will return all the recent mails in the user defined folder.
		   * 
		   * @param username
		   * @param password
		   * @param count
		   * @param folder
		   */
		  public List<Message> readMails(final String username, final String password, int count,
		      String folder) {
		    List<Message> messages = new ArrayList<Message>();
		    Properties properties = null;
		    Session session = null;
		    Store store = null;
		    Folder inbox = null;
		    properties = new Properties();
		    properties.setProperty("mail.host", "imap.gmail.com");
		    properties.setProperty("mail.port", "995");
		    properties.setProperty("mail.transport.protocol", "imaps");
		    session = Session.getInstance(properties, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		      }
		    });
		    try {
		      store = session.getStore("imaps");
		      store.connect();
		      inbox = store.getFolder(folder);
		      inbox.open(Folder.READ_ONLY);
		      Message messagesArr[] = inbox.search(new FlagTerm(new Flags(Flag.RECENT), false));;
		      //log.info("Number of mails = " + messagesArr.length);
		      if (count == -1) {
		        count = messagesArr.length;
		      }
		      for (int i = messagesArr.length - 1; i >= messagesArr.length - count; i--) {
		        messages.add(messagesArr[i]);
		      }
		      // inbox.close(true);
		      // store.close();
		    } catch (NoSuchProviderException e) {
		      e.printStackTrace();
		    } catch (MessagingException e) {
		      e.printStackTrace();
		    }
		    return messages;
		  }
		  
		  /**
		   * This method will fetch all the mails received after the given date in INBOX
		   * 
		   * @param userName
		   * @param password
		   * @param date // * @return
		   */
		  public List<Message> readInboxMails(final String userName, final String password, Date date) {
		    return readMails(userName, password, date, "INBOX");
		  }
		  
		  
		  /**
		   * This method will fetch all the mails received after the given date i mentioned folder
		   * 
		   * @param username
		   * @param password
		   * @param date
		   * @param folder
		   * @return
		   */
		  public List<Message> readMails(final String username, final String password, Date date,
		      String folder) {
		    List<Message> messages = new ArrayList<Message>();
		    Properties properties = null;
		    Session session = null;
		    Store store = null;
		    Folder inbox = null;
		    properties = new Properties();
		    properties.setProperty("mail.host", "imap.gmail.com");
		    properties.setProperty("mail.port", "995");
		    properties.setProperty("mail.transport.protocol", "imaps");
		    session = Session.getInstance(properties, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		      }
		    });
		    try {
		      store = session.getStore("imaps");
		      store.connect();
		      inbox = store.getFolder(folder);
		      inbox.open(Folder.READ_ONLY);
		      Message messagesArr[] = inbox.search(new FlagTerm(new Flags(Flag.RECENT), false));;
		     // log.info("Number of mails = " + messagesArr.length);
		      for (int i = messagesArr.length - 1; i >= 0; i--) {
		        Date d = messagesArr[i].getSentDate();
		        if (d.after(date)) {
		          messages.add(messagesArr[i]);
		        } else {
		          break;
		        }
		      }
		      // inbox.close(true);
		      // store.close();
		    } catch (NoSuchProviderException e) {
		      e.printStackTrace();
		    } catch (MessagingException e) {
		      e.printStackTrace();
		    }
		    return messages;
		  }
		  
		  public String processContent(Message message) throws IOException, MessagingException {
		    Object content = message.getContent();
		    if (content instanceof String) {
		      return content.toString();
		    } else if (content instanceof InputStream) {
		      InputStream inStream = (InputStream) content;
		      int ch;
		      String c = "";
		      while ((ch = inStream.read()) != -1) {
		        c += (char) ch;
		        return c;
		      }
		    } else {
		      Multipart multiPart = (Multipart) content;
		      return procesMultiPart(multiPart);
		    }
		    return null;
		  }
		  
		  private String procesMultiPart(Multipart multiPart) throws MessagingException, IOException {
		    String t = "";
		    if (multiPart == null) {
		      return "";
		    }
		    int multiPartCount = multiPart.getCount();
		    for (int i = 0; i < multiPartCount; i++) {
		      BodyPart bodyPart = multiPart.getBodyPart(i);
		      Object o = bodyPart.getContent();;
		      if (o instanceof String) {
		        t += o.toString() + procesMultiPart(null);
		      } else if (o instanceof Multipart) {
		        procesMultiPart((Multipart) o);
		      }
		    }
		    return t;
		  }
		  
		  public void deleteMessages(String userName, String password, String subjectToDelete) {
			  List<Message> messages = new ArrayList<Message>();
			    Properties properties = null;
			    Session session = null;
			    Store store = null;
			    Folder inbox = null;
		  
			  properties = new Properties();
			    properties.setProperty("mail.host", "imap.gmail.com");
			    properties.setProperty("mail.port", "995");
			    properties.setProperty("mail.transport.protocol", "imaps");
			    session = Session.getInstance(properties, new javax.mail.Authenticator() {
			      protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(userName, password);
			      }
			    });
			    try {
			      store = session.getStore("imaps");
			      store.connect();

		     // Session session = Session.getDefaultInstance(properties);

		      //try {
		          // connects to the message store
		        

		          // opens the inbox folder
		          Folder folderInbox = store.getFolder("INBOX");
		          folderInbox.open(Folder.READ_WRITE);

		          // fetches new messages from server
		          Message[] arrayMessages = folderInbox.getMessages();

		          for (int i = 0; i < arrayMessages.length; i++) {
		              Message message = arrayMessages[i];
		              String subject = message.getSubject();
		              if (subject.contains(subjectToDelete)) {
		                  message.setFlag(Flags.Flag.DELETED, true);
		                  System.out.println("Marked DELETE for message: " + subject);
		              }

		          }

		          // expunges the folder to remove messages which are marked deleted
		          boolean expunge = true;
		          folderInbox.close(expunge);

		          // another way:
		          //folderInbox.expunge();
		          //folderInbox.close(false);

		          // disconnect
		          store.close();
		      } catch (NoSuchProviderException ex) {
		          System.out.println("No provider.");
		          ex.printStackTrace();
		      } catch (MessagingException ex) {
		          System.out.println("Could not connect to the message store.");
		          ex.printStackTrace();
		      }
		  }

}
