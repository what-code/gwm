package com.b5m.web.core;

import java.io.Serializable;

public class B5MCondition implements Serializable {

	private static final long serialVersionUID = 6002925860782430682L;

	private String name;
	private String operation;
	private Object value;

	public B5MCondition(String name, String operation, String value) {
		this.name = name;
		this.operation = operation;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
