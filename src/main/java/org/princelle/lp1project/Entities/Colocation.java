package org.princelle.lp1project.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "table_colocation")
public class Colocation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(targetEntity = Person.class)
	@JoinColumn(name = "people")
	private List people;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	public void addPeople(Person person) {
		this.people.add(person);
	}

	public void deletePeople(Person person) {
		this.people.remove(person);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
