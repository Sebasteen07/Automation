package com.intuit.ihg.common.entities;

/**
 * Created by IntelliJ IDEA.
 * User: vvalsan
 * Date: 3/26/13
 * Time: 4:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class AskAQuestion {

    private String staff;
    private String subject;
    private String body;
    private String questionId;
    private String question;
    private String doctor;
    private String location;
    
    
	public AskAQuestion() {
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    
    public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public AskAQuestion(String subj, String bdy){
       this(null,subj,bdy);
    }
    public AskAQuestion(String staffName, String subj, String bdy){
        setStaff(staffName);
        setSubject(subj);
        setBody(bdy);
    }
}
