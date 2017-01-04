package cn.yokiqust.model;

public class Question {
	private String name;
	private int follow_num;
	private int id;
	private int answer_num;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFollow_num() {
		return follow_num;
	}

	public void setFollow_num(int follow_num) {
		this.follow_num = follow_num;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAnswer_num() {
		return answer_num;
	}

	public void setAnswer_num(int answer_num) {
		this.answer_num = answer_num;
	}

	@Override
	public String toString() {
		return "Question [name=" + name + ", follow_num=" + follow_num + ", id=" + id + ", answer_num=" + answer_num
				+ "]";
	}

}
