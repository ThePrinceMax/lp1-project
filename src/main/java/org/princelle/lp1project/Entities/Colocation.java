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

	public long getId() { return id; }

	public void setId(int id) { this.id = id; }

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
