package com.zlp.test.annotation;

import com.zlp.test.annotation.FruitColor.Color;

public class TestFruit {
	
	@FruitName("Apple")
    private String appleName;
    
    @FruitColor(fruitColor=Color.RED)
    private String appleColor;
    
	public String getAppleName() {
		return appleName;
	}

	public void setAppleName(String appleName) {
		this.appleName = appleName;
	}

	public String getAppleColor() {
		return appleColor;
	}

	public void setAppleColor(String appleColor) {
		this.appleColor = appleColor;
	}

	public static void main(String[] args) {
		TestFruit tf = new TestFruit();
		System.err.println("begin ----->");
		
		System.out.println("appleName = " +tf.getAppleName());
		System.out.println("appleColor = " +tf.getAppleColor());
		
		System.err.println("end ----->");
		
	}
 
}
