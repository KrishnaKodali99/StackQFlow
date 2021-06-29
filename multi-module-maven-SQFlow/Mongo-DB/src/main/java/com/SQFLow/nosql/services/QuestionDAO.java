package com.SQFLow.nosql.services;

import com.SQFLow.nosql.contracts.IQuestionDAO;
import com.SQFLow.nosql.entity.Question;
import com.SQFLow.nosql.util.MongoUtil;
import com.mongodb.CommandResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class QuestionDAO implements IQuestionDAO {
	
	private MongoClient mongoClient;
	private MongoCollection<Question> qCollection;

	public QuestionDAO() {
		mongoClient = MongoUtil.mongoUtil();
		qCollection = MongoUtil.getCollectionFromDB("teamDB", "questions", Question.class);
	}
	
	@Override
	public void insertOne(Question question) {
		long count = qCollection.count();
		question.setQID("Q" + (count+1));
		try {
			qCollection.insertOne(question);
			System.out.println("Inserted");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Question findById(String qid) {
		return (Question) qCollection.find(eq("qID", qid)).first();
	}
	
	@Override
	public Iterable<Question> findQuestion(String uid) {
		return qCollection.find(eq("uID", uid)).into(new ArrayList<Question>());
	}
	
	@Override
	public Iterable<Question> findAll() {
		return qCollection.find().into(new ArrayList<Question>());
	}

	@Override
	public void addUpVote(String qid) {
		qCollection.updateMany(eq("qID", qid), Updates.set("upVotes", findById(qid).getUpVotes()+1))
		.getModifiedCount();
	}

	@Override
	public void addDownVote(String qid) {
		qCollection.updateMany(eq("qID", qid), Updates.set("downVotes", findById(qid).getDownVotes()+1))
		.getModifiedCount();
	}
	
	@Override
	public void addAnswer(String qid, String aid) {
		qCollection.updateOne(eq("qID", qid), Updates.addToSet("aIDList", aid));
	}
	
}
