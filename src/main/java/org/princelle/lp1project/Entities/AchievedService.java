package org.princelle.lp1project.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "table_achievedservice")
public class AchievedService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private Date date;

	private String picture;

	private boolean validTo;
	private boolean validFrom;

	public long getId() { return id; }

	public void setId(int id) { this.id = id; }

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isPicture() {
		if(this.picture == null || this.picture == "")
			return true;
		return false;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isValidTo() {
		return this.validTo;
	}

	public boolean getValidTo() {
		return this.validTo;
	}

	public void setValidTo(boolean validTo) {
		this.validTo = validTo;
	}

	public boolean isValidFrom() {
		return this.validFrom;
	}

	public boolean getValidFrom() {
		return this.validFrom;
	}

	public void setValidFrom(boolean validFrom) {
		this.validFrom = validFrom;
	}
}