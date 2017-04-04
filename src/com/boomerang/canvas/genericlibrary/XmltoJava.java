package com.boomerang.canvas.genericlibrary;

import java.util.Arrays;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "XmltoJava")
public class XmltoJava extends GenericClass {
     
	private Suite[] suite;
	private String name;

	@XmlElement
	public Suite[] getSuite() {
		return suite;
	}

	public void setSuite(Suite[] suite) {
		this.suite = suite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlRootElement
	public static class Suite {
		private Parameter[] parameter;

		private String suitename;
		private String classname;

		@XmlAttribute
		public String getClassname() {
			return classname;
		}

		public void setClassname(String classname) {
			this.classname = classname;
		}

		@XmlAttribute
		public String getSuitename() {
			return suitename;
		}

		public void setSuitename(String suitename) {
			this.suitename = suitename;
		}

		@XmlElement
		public Parameter[] getParameter() {
			return parameter;
		}

		public void setParameter(Parameter[] parameter) {
			this.parameter = parameter;
		}

		@Override
		public String toString() {
			return "Suite [parameter=" + Arrays.toString(parameter) + ", suitename=" + suitename + "]";
		}

	}

	@XmlRootElement
	public static class Parameter {

		private String testcase;
		private String username;
		private String password;
		private String actualerrormessage;
		private String text;
		
		@XmlAttribute
		public String getText() {
			return text;
		}
		
		public void setText(String text) {
			this.text = text;
		}
		@Override
		public String toString() {
			return "Parameter [testcase=" + testcase + ", username=" + username + ", password=" + password
					+ ", actualerrormessage=" + actualerrormessage + ", text=" + text + ", startdate=" + startdate
					+ ", enddate=" + enddate + "]";
		}
		private String startdate;
		
		@XmlAttribute
		public String getStartdate() {
			return startdate;
		}
		public void setStartdate(String startdate) {
			this.startdate = startdate;
		}
		private String enddate;
		@XmlAttribute
		public String getEnddate() {
			return enddate;
		}
		public void setEnddate(String enddate) {
			this.enddate = enddate;
		}
		@XmlAttribute
		public String getActualerrormessage() {
			return actualerrormessage;
		}
		public void setActualerrormessage(String actualerrormessage) {
			this.actualerrormessage = actualerrormessage;
		}
		
		@XmlAttribute
		public String getTestcase() {
			return testcase;
		}
		public void setTestcase(String testcase) {
			this.testcase = testcase;
		}
		@XmlAttribute
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		@XmlAttribute
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}

	@Override
	public String toString() {
		return "XmltoJava [suite=" + Arrays.toString(suite) + ", name=" + name + "]";
	}

}