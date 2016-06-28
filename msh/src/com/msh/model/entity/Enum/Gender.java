package com.msh.model.entity.Enum;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
public enum Gender {
	WOMAN("女"),
	MAN("男");

	private String label;

	Gender(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return name();
	}
}
