package com.tekton.searchengine.model.yandex;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Found {

	private String priority;
	private Long value;

	public Found() {
		// TODO Auto-generated constructor stub
	}

	public String getPriority() {
		return priority;
	}

	@XmlAttribute
	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Long getValue() {
		return value;
	}

	@XmlValue
	public void setValue(Long value) {
		this.value = value;
	}

}
