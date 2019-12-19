package org.princelle.lp1project.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "table_service")
public class Service {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "cost", nullable = false)
	private Integer cost;

	@Column(name = "proposed", nullable = false)
	private Boolean proposed;

	@OneToOne(targetEntity = AchievedService.class)
	@JoinColumn(name = "achieved", nullable = false)
	private AchievedService achieved;

	@ManyToOne(targetEntity = Colocation.class)
	@JoinColumn(name = "coloc", nullable = false)
	private Colocation coloc;

	@ManyToOne(targetEntity = Person.class)
	@JoinColumn(name = "fromUser", nullable = false)
	private Person fromPerson;

	@ManyToOne(targetEntity = Person.class)
	@JoinColumn(name = "toUser")
	private List toPeople;

	public Colocation getColoc() {
		return coloc;
	}

	public void setColoc(Colocation coloc) {
		this.coloc = coloc;
	}

	public AchievedService getAchieved() {
		return achieved;
	}

	public void setAchieved(AchievedService achieved) {
		this.achieved = achieved;
	}

	public Boolean getProposed() {
		return proposed;
	}

	public void setProposed(Boolean proposed) {
		this.proposed = proposed;
	}

	public Person getFromPerson() {
		return fromPerson;
	}

	public void setFromPerson(Person fromPerson) {
		this.fromPerson = fromPerson;
	}

	public List<Person> getToPeople() {
		return toPeople;
	}

	public void setToPeople(ArrayList<Person> toPeople) {
		this.toPeople = toPeople;
	}

	public void addToUsers(Person toUsers) {
		this.toPeople.add(toUsers);
	}

	public void setToUsers(Person toUsers) {
		this.toPeople.remove(toUsers);
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
}