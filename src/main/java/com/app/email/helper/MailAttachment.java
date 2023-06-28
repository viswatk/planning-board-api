package com.app.email.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class MailAttachment {

	private String fileName;
	private byte[] inputStreamSource;

	public MailAttachment(String fileName, byte[] inputStreamSource) {
		super();
		this.fileName = fileName;
		this.inputStreamSource = inputStreamSource;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getInputStreamSource() {
		return inputStreamSource;
	}

	public void setInputStreamSource(byte[] inputStreamSource) {
		this.inputStreamSource = inputStreamSource;
	}

}
