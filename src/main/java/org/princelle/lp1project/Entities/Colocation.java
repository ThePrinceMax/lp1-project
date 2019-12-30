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
		return this.member;
	}

	public List<Person> setPeople(List<Person> people) {
		this.member = people;
		return this.member;
	}

	public List<Person> addPeople(Person person) {
		this.member.add(person);
		return this.member;
	}

	public List<Person> deletePeople(Person person) {
		if (this.member.contains(person)){
			this.member.remove(person);
		}
		return this.member;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
