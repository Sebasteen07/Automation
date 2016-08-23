package com.medfusion.product.forms.pojo;

import java.util.HashSet;
import java.util.Set;

public class SelectQuestion extends Question {
	private Set<SelectableAnswer> answers = new HashSet<SelectableAnswer>();

	public SelectQuestion(String questionTitle, Set<SelectableAnswer> answers) {
		super(questionTitle);
		this.answers = answers;
	}

	public Set<SelectableAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<SelectableAnswer> answers) {
		this.answers = answers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SelectQuestion other = (SelectQuestion) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		return true;
	}

}
