package com.memoko.integrated.member.service;

public class CreateRandomPw {

	
	public static String getRandomPw() {
		
		String pw = "";
		
		for(int i=0; i<8; i++) {
			  int rndVal = (int)(Math.random() * 62);
			  if(rndVal < 10) {
			    pw += rndVal;
			  } else if(rndVal > 35) {
			    pw += (char)(rndVal + 61);
			  } else {
			    pw += (char)(rndVal + 55);
			  }
			}


		return pw;
	}
}
