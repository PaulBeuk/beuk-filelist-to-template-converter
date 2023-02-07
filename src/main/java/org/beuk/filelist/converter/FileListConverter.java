package org.beuk.filelist.converter;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.commons.configuration.*;
import org.apache.xmlrpc.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import freemarker.template.*;

public class FileListConverter {

	public static String prefix;
	public static String club;

	public static void main(String[] args) throws Exception {

		final FileListConverter converter = new FileListConverter();
		final List<String> lines = converter.readInput();
		final List<FileListItem> list = converter.parseToListItems(lines);
		System.out.println("found editions: " + list.size());
		final LinkedHashMap<Short, LinkedHashMap<Short, LinkedHashMap<Short, List<FileListItem>>>> items = converter.orderEditions(list);
		// converter.printItems(items);
		final String text = converter.toTemplate(items);
		System.out.println("Out:\n" + text);
		// converter.toWordpress("De schakeling", text);
	}

	public String inputFile;

	// private final XMLRPCController xmlrpcController;

	private final PropertiesConfiguration configuration;
	private final String template;

	public FileListConverter() throws ConfigurationException, JsonParseException, JsonMappingException, XmlRpcException, IOException {
		final String propertyFile = System.getProperty("configPropertyFile");
		if (propertyFile == null) {
			System.err.println("configPropertyFile not set use -DconfigPropertyFile=<file>");
			System.exit(1);
		}
		configuration = new PropertiesConfiguration(propertyFile);
		inputFile = configuration.getString("filelist.inputfile");
		club = configuration.getString("filelist.club");
		prefix = configuration.getString("filelist.prefix");
		template = configuration.getString("filelist.template");
		// xmlrpcController = new XMLRPCController(configuration);
	}

	LinkedHashMap<Short, LinkedHashMap<Short, LinkedHashMap<Short, List<FileListItem>>>> orderEditions(List<FileListItem> editions) {

		// (decade, year, month, edition);
		final Map<Short, Map<Short, Map<Short, List<FileListItem>>>> ordered = new HashMap<>();
		for (final FileListItem item : editions) {
			Map<Short, Map<Short, List<FileListItem>>> decadeMap = ordered.get(item.decade);
			if (decadeMap == null)
				decadeMap = new HashMap<>();

			Map<Short, List<FileListItem>> yearMap = decadeMap.get(item.year);
			if (yearMap == null)
				yearMap = new HashMap<>();

			List<FileListItem> monthList = yearMap.get(item.monthNumber);
			if (monthList == null) {
				monthList = new ArrayList<>();
			}
			monthList.add(item);
			yearMap.put(item.monthNumber, monthList);
			decadeMap.put(item.year, yearMap);
			ordered.put(item.decade, decadeMap);
		}

		final List<Short> l = new ArrayList<>();
		l.addAll(ordered.keySet());
		Collections.sort(l);
		final LinkedHashMap<Short, LinkedHashMap<Short, LinkedHashMap<Short, List<FileListItem>>>> linkedHashMap = new LinkedHashMap<>();
		for (final Short dec : l) {
			final LinkedHashMap<Short, LinkedHashMap<Short, List<FileListItem>>> decList = new LinkedHashMap<>();
			final Map<Short, Map<Short, List<FileListItem>>> years = ordered.get(dec);
			final List<Short> y = new ArrayList<>();
			y.addAll(years.keySet());
			Collections.sort(y);
			for (final Short year : y) {
				final LinkedHashMap<Short, List<FileListItem>> yearsList = new LinkedHashMap<>();
				final Map<Short, List<FileListItem>> months = years.get(year);
				final List<Short> m = new ArrayList<>();
				m.addAll(months.keySet());
				Collections.sort(m);
				for (final Short month : m) {
					final List<FileListItem> editionList = months.get(month);
					yearsList.put(month, editionList);
				}
				decList.put(year, yearsList);
			}
			linkedHashMap.put(dec, decList);
		}
		return linkedHashMap;
	}

	List<FileListItem> parseToListItems(List<String> lines) {

		final List<FileListItem> list = new ArrayList<>();
		for (final String line : lines) {
			final FileListItem item = new FileListItem(club, prefix);
			if (item.parse(line)) {
				list.add(item);
			}
		}
		return list;
	}

	void printEditions(List<FileListItem> editions) {

		for (final FileListItem edition : editions) {
			System.out.println(edition.toString());
		}
	}

	List<String> readInput() throws FileNotFoundException, Exception {

		final List<String> list = new ArrayList<>();
		final URL resource = this.getClass().getClassLoader().getResource(inputFile);
		System.out.println("resource: " + resource);
		try (BufferedReader br = new BufferedReader(new FileReader(resource.getPath()))) {
			String line = br.readLine();

			while (line != null) {
				if (!line.trim().isEmpty())
					list.add(line);
				line = br.readLine();
			}
		}
		return list;
	}

	String toTemplate(LinkedHashMap<Short, LinkedHashMap<Short, LinkedHashMap<Short, List<FileListItem>>>> items) throws IOException, TemplateException {

		final TemplateController templateController = new TemplateController();
		final FileListDTO dto = new FileListDTO();
		dto.setItems(items);
		return templateController.processTemplate(dto, template);
	}

	// void toWordpress(String title, String html) throws IOException, XmlRpcException {
	//
	// final int userid = xmlrpcController.getUserByName("beuk");
	// System.out.println("beuk id: " + userid);
	// final String[] cats = {};
	// final XMLPageSummary parentPage = xmlrpcController.getPageByTitle("archief");
	// int parentId = -1;
	// if (parentPage != null) {
	// parentId = parentPage.pageId;
	// }
	// xmlrpcController.savePage(title, title.replace(' ', '-'), html, userid, parentId, cats);
	// }

	private void printItems(LinkedHashMap<Short, LinkedHashMap<Short, LinkedHashMap<Short, List<FileListItem>>>> items) {

		System.out.println("decades: " + items.size());
		for (final Short d : items.keySet()) {
			final LinkedHashMap<Short, LinkedHashMap<Short, List<FileListItem>>> dec = items.get(d);
			for (final Short year : dec.keySet()) {
				final LinkedHashMap<Short, List<FileListItem>> month = dec.get(year);
				for (final Short num : month.keySet()) {
					final List<FileListItem> editions = month.get(num);
					for (final FileListItem item : editions) {
						System.out.println(d + " -> " + year + " -> " + num + " -> " + item.fileURL);
					}
				}
			}
		}
	}

}
