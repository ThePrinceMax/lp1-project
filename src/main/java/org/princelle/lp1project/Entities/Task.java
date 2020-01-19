package org.princelle.lp1project.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "table_task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "title", nullable = false)
	private String title;

	public long getId() { return id; }

	public void setId(int id) { this.id = id; }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "cost", nullable = false)
	private Integer cost = 0;

	@Column(name = "proposed", nullable = false)
	private Boolean proposed = false;

	@ManyToOne(targetEntity = Colocation.class)
	@JoinColumn(name = "coloc")
	private Colocation coloc = null;

	@ManyToOne(targetEntity = Person.class)
	@JoinColumn(name = "fromUser")
	private Person fromPerson = null;

	@ManyToOne(targetEntity = Person.class)
	@JoinColumn(name = "toUser")
	private Person toPeople = null;

	private Date finishDate = null;

	private String picture = null;

	private boolean validTo = false;
	private boolean validFrom = false;

	public long getId() { return id; }

	public void setId(int id) { this.id = id; }

	public Colocation getColoc() {
		return coloc;
	}

	public void setColoc(Colocation coloc) {
		this.coloc = coloc;
	}

	public boolean isAchieved() {
		if (this.finishDate != null) {
			return true;
		}
		return false;
	}

	public Boolean getProposed() {
		return proposed;
	}

	public void setProposed(Boolean proposed) {
		this.proposed = proposed;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Person getFromPerson() {
		return fromPerson;
	}

	public Person setFromPerson(Person fromPerson) {
		this.fromPerson = fromPerson;
		return this.fromPerson;
	}

	public Person getToPeople() {
		return toPeople;
	}

	public void setToPeople(Person toPeople) {
		this.toPeople = toPeople;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public Date setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
		return this.finishDate;
	}

	public String getPicture() {
		return picture;
	}

	public String setPicture(String picture) {
		this.picture = picture;
		return this.picture;
	}

	public boolean isValidTo() {
		return validTo;
	}

	public boolean setValidTo(boolean validTo) {
		this.validTo = validTo;
		return this.validTo;
	}

	public boolean isValidFrom() {
		return validFrom;
	}

	public boolean setValidFrom(boolean validFrom) {
		this.validFrom = validFrom;
		return this.validFrom;
	}*/
}