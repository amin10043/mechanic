package com.project.mechanic.entity;

public class LikeInPost {

	int Id;
	int PostId;
	int UserId;
	int IsLike;
	String Date;
	String ModifyDate;

	public LikeInPost(int Id, int PostId, int UserId, int IsLike, String Date,
			String ModifyDate) {
		this.Id = Id;
		this.PostId = PostId;
		this.UserId = UserId;
		this.IsLike = IsLike;
		this.Date = Date;
		this.ModifyDate = ModifyDate;

	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getPostId() {
		return PostId;
	}

	public void setPostId(int postId) {
		PostId = postId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getIsLike() {
		return IsLike;
	}

	public void setIsLike(int isLike) {
		IsLike = isLike;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getModifyDate() {
		return ModifyDate;
	}

	public void setModifyDate(String modifyDate) {
		ModifyDate = modifyDate;
	}

}