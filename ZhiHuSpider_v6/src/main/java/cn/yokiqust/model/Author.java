package cn.yokiqust.model;

public class Author {
	private String name;
	private String id;
	private String bio;

	public Author() {
	}

	public Author(String name, String id, String bio) {
		this.name = name;
		this.id = id;
		this.bio = bio;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	@Override
	public String toString() {
		return "Author [name=" + name + ", id=" + id + ", bio=" + bio + "]";
	}

}
