package com.msh.model.entity;

import java.io.Serializable;

/**
 * @author Tang Yong Di
 * @date 2016/3/5
 */
public abstract class AbstractDomain implements Domain, Cloneable {

	private static final long serialVersionUID = 4576000441217442931L;
	protected Integer id;

	protected AbstractDomain() {
	}

	protected AbstractDomain(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof AbstractDomain)) {
			return false;
		}
		AbstractDomain that = (AbstractDomain) o;
		if (!id().equals(that.id())) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return id().hashCode();
	}

	@Override
	public Serializable getId() {
		return id;
	}

	public Integer id() {
		return id;
	}

	public void cleanId() {
		this.id = null;
	}
}
