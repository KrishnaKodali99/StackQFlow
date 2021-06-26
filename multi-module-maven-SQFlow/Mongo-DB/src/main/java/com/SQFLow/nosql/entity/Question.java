package com.SQFLow.nosql.entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
	private String QID;
	private String question;
	private int upVotes;
	private int downVotes;
	private String status;
	private String UID;
	private Date timestamp;
	private List<String> AIDList;

}