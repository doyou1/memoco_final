package com.memoko.integrated.recipe.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.memoko.integrated.member.dao.MemberDAO;
import com.memoko.integrated.recipe.vo.MongoRecipeVO;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Repository
public class MongoDBDAO {
	
	
	//스트링아무거나 넣어봐
	public ArrayList<MongoRecipeVO> findRecipeList(String[] food) throws Exception
	{
		MongoClient mongoclient = new MongoClient();
		
		// 데이터베이스 접근
		MongoDatabase db = mongoclient.getDatabase("test");
		// 컬렉션 접근
		MongoCollection<Document> test = db.getCollection("test");
		
		//메인쿼리
		ArrayList<BasicDBObject> coll5 = new ArrayList<BasicDBObject>();
//		coll5.add(new BasicDBObject("ingrd.0",Pattern.compile(food[0])));
		
		for(int i =0; i<food.length; i++) {
			coll5.add(new BasicDBObject("ingrd",Pattern.compile(food[i])));		
		}
		BasicDBObject query5 = new BasicDBObject();
		query5.put("$and", coll5);
		
		ArrayList<Document> list5 = new ArrayList<Document>();
		MongoCursor<Document> cursor5 = test.find(query5).iterator();
		while(cursor5.hasNext())
		{
			list5.add(cursor5.next());
		}
		
		//list5.get(0).get(key)
	
		System.out.println("---------------document를 반환하여 리스트화 끝-------------------");
		ArrayList<MongoRecipeVO> ringtest = new ArrayList<MongoRecipeVO>();
		for (int j = 0; j < list5.size(); j++) 
		{
			MongoRecipeVO result = new MongoRecipeVO();
			result.setListname((String)list5.get(j).get("name"));
			result.setListingrd(list5.get(j).get("ingrd", ArrayList.class));
			result.setListamount(list5.get(j).get("amount", ArrayList.class));
			result.setListcontent(list5.get(j).get("content", ArrayList.class));
			result.setListphoto(list5.get(j).get("photo", ArrayList.class));
			result.setListurl((String)list5.get(j).get("url"));
			result.setListnum((String)list5.get(j).get("num"));
			
			ringtest.add(result);
		}
		
		System.out.println("------------document를 변환하여 vo객체리스트로 -----------------");
		
		Collections.shuffle(ringtest);
		
		ArrayList<MongoRecipeVO> printlist = new ArrayList<MongoRecipeVO>();
		
		for(MongoRecipeVO vo : ringtest) {
			printlist.add(vo);
		}	
		System.out.println("printlist.size() : " + printlist.size());
		mongoclient.close();
		return printlist;
	}
	
	public ArrayList<MongoRecipeVO> getMongoRecipesByFoods(String mainFood
			,ArrayList<String> subFood)
	{
		MongoClient mongoclient = new MongoClient();
		
		// 데이터베이스 접근
		MongoDatabase db = mongoclient.getDatabase("test");
		// 컬렉션 접근
		MongoCollection<Document> test = db.getCollection("test");
		
		//메인쿼리
		ArrayList<BasicDBObject> coll5 = new ArrayList<BasicDBObject>();
		coll5.add(new BasicDBObject("ingrd.0",Pattern.compile(mainFood)));
//		coll5.add(new BasicDBObject("ingrd",Pattern.compile(mainFood)));
		
		for(int i =0; i<subFood.size(); i++) {
			coll5.add(new BasicDBObject("ingrd",Pattern.compile(subFood.get(i))));		
		}
		BasicDBObject query5 = new BasicDBObject();
		query5.put("$and", coll5);
		
		ArrayList<Document> list5 = new ArrayList<Document>();
		MongoCursor<Document> cursor5 = test.find(query5).iterator();
		while(cursor5.hasNext())
		{
			list5.add(cursor5.next());
		}
		
		
		System.out.println("---------------document를 반환하여 리스트화 끝-------------------");
		ArrayList<MongoRecipeVO> ringtest = new ArrayList<MongoRecipeVO>();
		for (int j = 0; j < list5.size(); j++) 
		{
			MongoRecipeVO result = new MongoRecipeVO();
			result.setListname((String)list5.get(j).get("name"));
			result.setListingrd(list5.get(j).get("ingrd", ArrayList.class));
			result.setListamount(list5.get(j).get("amount", ArrayList.class));
			result.setListcontent(list5.get(j).get("content", ArrayList.class));
			result.setListphoto(list5.get(j).get("photo", ArrayList.class));
			result.setListurl((String)list5.get(j).get("url"));
			result.setListnum((String)list5.get(j).get("num"));
			
			ringtest.add(result);
		}
		
		System.out.println("------------document를 변환하여 vo객체리스트로 -----------------");
		
		Collections.shuffle(ringtest);
		
		ArrayList<MongoRecipeVO> printlist = new ArrayList<MongoRecipeVO>();
		
		for(MongoRecipeVO vo : ringtest) {
			printlist.add(vo);
		}	
		System.out.println("printlist.size() : " + printlist.size());
		mongoclient.close();
		return printlist;
	}
	
	public MongoRecipeVO readByNum(String listnum){
		
		MongoClient mongoclient = new MongoClient();
		// 데이터베이스 접근
		MongoDatabase db = mongoclient.getDatabase("test");
		// 컬렉션 접근
		MongoCollection<Document> test = db.getCollection("test");
		//"1500" = int가 아니고 스트링형식으로 입력
		BasicDBObject query = new BasicDBObject();
		query.put("num", listnum);
		MongoCursor<Document> cursor = test.find(query).iterator();
		ArrayList<Document> list = new ArrayList<Document>();
		if(cursor.hasNext())
		{
			list.add(cursor.next());
		}
		System.out.println(query);
		//System.out.println(list);
		//Document를 vo객체로
		MongoRecipeVO volist = new MongoRecipeVO();
		volist.setListname((String)list.get(0).get("name"));
		volist.setListingrd(list.get(0).get("ingrd", ArrayList.class));
		volist.setListamount(list.get(0).get("amount", ArrayList.class));
		volist.setListcontent(list.get(0).get("content", ArrayList.class));
		volist.setListphoto(list.get(0).get("photo", ArrayList.class));
		volist.setListurl((String)list.get(0).get("url"));
		volist.setListnum((String)list.get(0).get("num"));
		System.out.println(volist.getListname());
		mongoclient.close();
		return volist;
	}
	
	
	//스트링아무거나 넣어봐
	public ArrayList<MongoRecipeVO> getDefaultRecipeListForMode2()
	{
		MongoClient mongoclient = new MongoClient();
		
		// 데이터베이스 접근
		MongoDatabase db = mongoclient.getDatabase("test");
		// 컬렉션 접근
		MongoCollection<Document> test = db.getCollection("test");
		
		//메인쿼리
		ArrayList<BasicDBObject> coll5 = new ArrayList<BasicDBObject>();
		coll5.add(new BasicDBObject("ingrd",Pattern.compile("돼지고기")));		
		coll5.add(new BasicDBObject("ingrd",Pattern.compile("가지")));		

		BasicDBObject query5 = new BasicDBObject();
		query5.put("$or", coll5);
		
		ArrayList<Document> list5 = new ArrayList<Document>();
		MongoCursor<Document> cursor5 = test.find(query5).iterator();
		while(cursor5.hasNext())
		{
			list5.add(cursor5.next());
		}
		
		//list5.get(0).get(key)
		
		System.out.println("---------------document를 반환하여 리스트화 끝-------------------");
		ArrayList<MongoRecipeVO> ringtest = new ArrayList<MongoRecipeVO>();
		for (int j = 0; j < list5.size(); j++) 
		{
			MongoRecipeVO result = new MongoRecipeVO();
			result.setListname((String)list5.get(j).get("name"));
			result.setListingrd(list5.get(j).get("ingrd", ArrayList.class));
			result.setListamount(list5.get(j).get("amount", ArrayList.class));
			result.setListcontent(list5.get(j).get("content", ArrayList.class));
			result.setListphoto(list5.get(j).get("photo", ArrayList.class));
			result.setListurl((String)list5.get(j).get("url"));
			result.setListnum((String)list5.get(j).get("num"));
			ringtest.add(result);
		}
		
		System.out.println("------------document를 변환하여 vo객체리스트로 -----------------");
		
		Collections.shuffle(ringtest);
	
		mongoclient.close();
		return ringtest;
	}

	public ArrayList<MongoRecipeVO> findRecipeByTitle(String searchText){
		
		MongoClient mongoclient = new MongoClient();
		// 데이터베이스 접근
		MongoDatabase db = mongoclient.getDatabase("test");
		// 컬렉션 접근
		MongoCollection<Document> test = db.getCollection("test");

	
		//몽고디비 메인쿼리(name에서 searchtext가 들어간것을 찾으라)
		BasicDBObject query = new BasicDBObject();
		//요리이름 칼럼에서 검색어로 find
		query.put("name",Pattern.compile(searchText));
		
		ArrayList<Document> list = new ArrayList<Document>();
		MongoCursor<Document> cursor = test.find(query).iterator();
		
		while(cursor.hasNext())
		{
			list.add(cursor.next());
		}

		System.out.println("---------------document를 반환하여 리스트화 끝-------------------");
		ArrayList<MongoRecipeVO> mongo_list = new ArrayList<MongoRecipeVO>();
		for (int j = 0; j < list.size(); j++) 
		{
			MongoRecipeVO result = new MongoRecipeVO();
			result.setListname((String)list.get(j).get("name"));
			result.setListingrd(list.get(j).get("ingrd", ArrayList.class));
			result.setListamount(list.get(j).get("amount", ArrayList.class));
			result.setListcontent(list.get(j).get("content", ArrayList.class));
			result.setListphoto(list.get(j).get("photo", ArrayList.class));
			result.setListurl((String)list.get(j).get("url"));
			result.setListnum((String)list.get(j).get("num"));
			mongo_list.add(result);
		}
		mongoclient.close();
		return mongo_list;
	}

	public ArrayList<MongoRecipeVO> getMongoRecipesByMainFood(String searchText){
		
		MongoClient mongoclient = new MongoClient();
		// 데이터베이스 접근
		MongoDatabase db = mongoclient.getDatabase("test");
		// 컬렉션 접근
		MongoCollection<Document> test = db.getCollection("test");
		
		
		//메인쿼리
		BasicDBObject query = new BasicDBObject();
		query.put("ingrd.0",Pattern.compile(searchText));
//		query.put("ingrd",Pattern.compile(searchText));
		
		ArrayList<Document> list = new ArrayList<Document>();
		MongoCursor<Document> cursor = test.find(query).iterator();
		while(cursor.hasNext())
		{
			list.add(cursor.next());
		}

		System.out.println("---------------document를 반환하여 리스트화 끝-------------------");
		ArrayList<MongoRecipeVO> mongo_list = new ArrayList<MongoRecipeVO>();
		
		System.out.println("list.size() : " + list.size());
		
		
		for (int j = 0; j < list.size(); j++) 
		{
				MongoRecipeVO result = new MongoRecipeVO();
				result.setListname((String)list.get(j).get("name"));
				result.setListingrd(list.get(j).get("ingrd", ArrayList.class));
				result.setListamount(list.get(j).get("amount", ArrayList.class));
				result.setListcontent(list.get(j).get("content", ArrayList.class));
				result.setListphoto(list.get(j).get("photo", ArrayList.class));
				result.setListurl((String)list.get(j).get("url"));
				result.setListnum((String)list.get(j).get("num"));
				mongo_list.add(result);
		}
		System.out.println("mongo_list.size() : "  + mongo_list.size());
		mongoclient.close();
		return mongo_list;
		
	}

	public MongoRecipeVO getMongoRecipeByRecipeNum(int recipe_num) {
		
		
		MongoClient mongoclient = new MongoClient();
		// 데이터베이스 접근
		MongoDatabase db = mongoclient.getDatabase("test");
		// 컬렉션 접근
		MongoCollection<Document> test = db.getCollection("test");
		//"1500" = int가 아니고 스트링형식으로 입력
		BasicDBObject query = new BasicDBObject();
		query.put("num", String.valueOf(recipe_num));
		MongoCursor<Document> cursor = test.find(query).iterator();
		ArrayList<Document> list = new ArrayList<Document>();
		if(cursor.hasNext())
		{
			list.add(cursor.next());
		}
		System.out.println(list);
		MongoRecipeVO mongoRecipe = null;
		if(!(list.isEmpty())) {
			mongoRecipe = new MongoRecipeVO();
			mongoRecipe.setListname((String)list.get(0).get("name"));
			mongoRecipe.setListingrd(list.get(0).get("ingrd", ArrayList.class));
			mongoRecipe.setListamount(list.get(0).get("amount", ArrayList.class));
			mongoRecipe.setListcontent(list.get(0).get("content", ArrayList.class));
			mongoRecipe.setListphoto(list.get(0).get("photo", ArrayList.class));
			mongoRecipe.setListurl((String)list.get(0).get("url"));
			mongoRecipe.setListnum((String)list.get(0).get("num"));			
		}

		mongoclient.close();
		
		return mongoRecipe;
		
	}

	public ArrayList<MongoRecipeVO> getMongoRecipesByRecipeNums(ArrayList<Integer> recipeNums) {
		
		if(recipeNums.size() > 0) {
			MongoClient mongoclient = new MongoClient();
			// 데이터베이스 접근
			MongoDatabase db = mongoclient.getDatabase("test");
			// 컬렉션 접근
			MongoCollection<Document> test = db.getCollection("test");	
			//메인쿼리
			ArrayList<BasicDBObject> coll5 = new ArrayList<BasicDBObject>();

			for(int recipe_num : recipeNums) {
				coll5.add(new BasicDBObject("num",String.valueOf(recipe_num)));				
			}
			BasicDBObject query5 = new BasicDBObject();
			query5.put("$or", coll5);

			MongoCursor<Document> cursor = test.find(query5).iterator();
			ArrayList<Document> list = new ArrayList<Document>();
			if(cursor.hasNext())
			{
				list.add(cursor.next());
			}

			ArrayList<MongoRecipeVO> mongo_list = new ArrayList<MongoRecipeVO>();
			for(int i=0; i<list.size();i++) {
				MongoRecipeVO mongoRecipe = new MongoRecipeVO();
				mongoRecipe.setListname((String)list.get(i).get("name"));
				mongoRecipe.setListingrd(list.get(i).get("ingrd", ArrayList.class));
				mongoRecipe.setListamount(list.get(i).get("amount", ArrayList.class));
				mongoRecipe.setListcontent(list.get(i).get("content", ArrayList.class));
				mongoRecipe.setListphoto(list.get(i).get("photo", ArrayList.class));
				mongoRecipe.setListurl((String)list.get(i).get("url"));
				mongoRecipe.setListnum((String)list.get(i).get("num"));
				
				mongo_list.add(mongoRecipe);
			}
			

			mongoclient.close();
			
			return mongo_list;
			
		}
		
		return null;
		
	}

	public ArrayList<MongoRecipeVO> getMongoRecipesByTitle(String searchText) {
		MongoClient mongoclient = new MongoClient();
		// 데이터베이스 접근
		MongoDatabase db = mongoclient.getDatabase("test");
		// 컬렉션 접근
		MongoCollection<Document> test = db.getCollection("test");

	
		//몽고디비 메인쿼리(name에서 searchtext가 들어간것을 찾으라)
		BasicDBObject query = new BasicDBObject();
		//요리이름 칼럼에서 검색어로 find
		query.put("name",Pattern.compile(searchText));
		
		ArrayList<Document> list = new ArrayList<Document>();
		MongoCursor<Document> cursor = test.find(query).iterator();
		
		while(cursor.hasNext())
		{
			list.add(cursor.next());
		}

		System.out.println("---------------document를 반환하여 리스트화 끝-------------------");
		ArrayList<MongoRecipeVO> mongos = new ArrayList<MongoRecipeVO>();
		for (int j = 0; j < list.size(); j++) 
		{
			MongoRecipeVO result = new MongoRecipeVO();
			result.setListname((String)list.get(j).get("name"));
			result.setListingrd(list.get(j).get("ingrd", ArrayList.class));
			result.setListamount(list.get(j).get("amount", ArrayList.class));
			result.setListcontent(list.get(j).get("content", ArrayList.class));
			result.setListphoto(list.get(j).get("photo", ArrayList.class));
			result.setListurl((String)list.get(j).get("url"));
			result.setListnum((String)list.get(j).get("num"));
			mongos.add(result);
		}
		mongoclient.close();
		return mongos;

	}
	
		   
	
}
