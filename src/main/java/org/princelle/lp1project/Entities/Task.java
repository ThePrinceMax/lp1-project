package org.princelle.lp1project.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "table_taches")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
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
	private Person toPerson = null;

	@Column(name = "finishDate")
	private Date finishDate = null;

	@Column(name = "picture")
	private String picture = null;

	@Column(name = "validTo")
	private Boolean validTo = false;

	@Column(name = "validFrom")
	private Boolean validFrom = false;

	public long getId() { return id; }

	public void setId(int id) { this.id = id; }

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

	public Boolean getProposed() {
		return proposed;
	}

	public void setProposed(Boolean proposed) {
		this.proposed = proposed;
	}

	public Colocation getColoc() {
		return coloc;
	}

	public void setColoc(Colocation coloc) {
		this.coloc = coloc;
	}

	public Person getFromPerson() {
		return fromPerson;
	}

	public void setFromPerson(Person fromPerson) {
		this.fromPerson = fromPerson;
	}

	public Person getToPerson() {
		return toPerson;
	}

	public void setToPerson(Person toPerson) {
		this.toPerson = toPerson;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Boolean getValidTo() {
		return validTo;
	}

	public void setValidTo(boolean validTo) {
		this.validTo = validTo;
	}

	public Boolean getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(boolean validFrom) {
		this.validFrom = validFrom;
	}
}
