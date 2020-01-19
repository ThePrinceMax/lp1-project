package org.princelle.lp1project.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "table_service")
public class Service {
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

	@OneToOne(targetEntity = AchievedService.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "achieved")
	private AchievedService achieved;

	@ManyToOne(targetEntity = Colocation.class)
	@JoinColumn(name = "coloc", nullable = false)
	private Colocation coloc;

	@ManyToOne(targetEntity = Person.class)
	@JoinColumn(name = "fromPerson", nullable = false)
	private Person fromPerson;

	@ManyToMany(targetEntity = Person.class)
	@JoinColumn(name = "toPeople")
	private List<Person> toPeople;

	public Service(String title, String description, Integer cost, Boolean proposed, Colocation coloc, Person fromPerson) {
		this.title = title;
		this.description = description;
		this.cost = cost;
		this.proposed = proposed;
		this.coloc = coloc;
		this.fromPerson = fromPerson;
		this.achieved = null;
		this.toPeople = null;
	}

	public long getId() { return id; }

	public void setId(int id) { this.id = id; }

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

	public boolean isAchieved() {
		if (this.achieved != null) {
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

	public List<Person> getToPeople() {
		return toPeople;
	}

	public List<Person> setToPeople(List<Person> toPeople) {
		this.toPeople = toPeople;
		return this.toPeople;
	}
}