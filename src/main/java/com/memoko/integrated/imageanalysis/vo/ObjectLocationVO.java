package com.memoko.integrated.imageanalysis.vo;



public class ObjectLocationVO {
	int x1;
	int x2;
	int y1;
	int y3;
	int width;
	int height;
	String value;
	
	//double a�� x1, double b�� x2, double c�� y1, double d�� y3 
	public ObjectLocationVO(Double x1, Double x2, Double y1, Double y3, int width, int height) {
		this.x1 = (int) Math.round(width * x1);
		this.x2 = (int) Math.round(width * x2);
		this.y1 = (int) Math.round(height * y1);
		this.y3 = (int) Math.round(height * y3);
		this.width = width;
		this.height = height;
		this.value="";
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY3() {
		return y3;
	}

	public void setY3(int y3) {
		this.y3 = y3;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "ObjectLocationVO [x1=" + x1 + ", x2=" + x2 + ", y1=" + y1 + ", y3=" + y3 + ", value=" + value + "]";
	}

}
