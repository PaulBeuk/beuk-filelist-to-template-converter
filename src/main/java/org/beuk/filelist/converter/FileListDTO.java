package org.beuk.filelist.converter;

import java.util.*;

public class FileListDTO {

	private String name;
	private LinkedHashMap<Short, LinkedHashMap<Short, LinkedHashMap<Short, List<FileListItem>>>> items;

	public LinkedHashMap<Short, LinkedHashMap<Short, LinkedHashMap<Short, List<FileListItem>>>> getItems() {

		return items;
	}

	public String getName() {

		return name;
	}

	public void setItems(LinkedHashMap<Short, LinkedHashMap<Short, LinkedHashMap<Short, List<FileListItem>>>> items) {

		this.items = items;
	}

	public void setName(String name) {

		this.name = name;
	}
}
