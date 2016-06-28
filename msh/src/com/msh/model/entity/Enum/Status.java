package com.msh.model.entity.Enum;

/**
 * @author Tang Yong Di
 * @date 2016/3/21
 */
public enum Status {

	DEACTIVATE("停用"),
	ACTIVATED("已启用"),;

	private String label;

	Status(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public String getValue() {
		return name();
	}
}
