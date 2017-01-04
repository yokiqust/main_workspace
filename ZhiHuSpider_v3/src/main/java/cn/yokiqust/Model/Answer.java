package cn.yokiqust.Model;

public class Answer {
	private int id;
	private Question question;
	private Author author;
	private String count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Answer [question=" + question.getName() + ", author=" + author.getName() + ", count=" + count + "]";
	}

}
