package org.princelle.lp1project.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "table_colocation")
public class Colocation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name", nullable = false)
	private String name;

	@OneToMany(targetEntity = Person.class)
	@JoinColumn(name = "member")
	private List member;

	public long getId() { return id; }

	public void setId(int id) { this.id = id; }

	public List<Person> getPeople() {
		return member;
	}

	public void setPeople(List<Person> people) {
		this.member = people;
	}

	public void addPeople(Person person) {
		this.member.add(person);
	}

	public void deletePeople(Person person) {
		this.member.remove(person);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
