package com.memoko.integrated.imageanalysis.vo;

public class InputFoodVO {
	String eng_name;
	String kor_name;
	public String getEng_name() {
		return eng_name;
	}
	public void setEng_name(String eng_name) {
		this.eng_name = eng_name;
	}
	public String getKor_name() {
		return kor_name;
	}
	public void setKor_name(String kor_name) {
		this.kor_name = kor_name;
	}
	public InputFoodVO(String eng_name, String kor_name) {
		super();
		this.eng_name = eng_name;
		this.kor_name = kor_name;
	}
	
	
}
