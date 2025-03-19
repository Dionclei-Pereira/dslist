package me.dionclei.dslist.dto;

public record RequestCreateGame(String title, Integer year, String genre, String platforms, Double score, String imgUrl,
		String shortDescription, String longDescription) {

}
