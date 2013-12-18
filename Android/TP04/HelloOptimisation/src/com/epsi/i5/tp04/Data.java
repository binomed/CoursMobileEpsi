package com.epsi.i5.tp04;

import java.util.ArrayList;
import java.util.List;

public class Data {

	private String title;
	private String subTitle;
	private int image;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}

	public static final List<Data> getAllDatas() {
		List<Data> dataList = new ArrayList<Data>();
		Data dataTmp = null;
		int modulo = 0;
		for (int i = 0; i < 500; i++) {
			dataTmp = new Data();
			dataTmp.title = "Title : " + i;
			dataTmp.subTitle = "Subtile for : " + i;
			modulo = i % 3;
			dataTmp.image = modulo == 0 ? android.R.drawable.ic_btn_speak_now : modulo == 1 ? android.R.drawable.ic_delete : android.R.drawable.ic_input_add;
			dataList.add(dataTmp);
		}

		return dataList;
	}

}
