package org.beuk.filelist.converter;

import java.util.*;

public class FileListDTOOld {

	public static class DecadeDTO {

		public static class YearDTO {

			public static class MonthDTO {

				public static List<MonthDTO> fromMap(LinkedHashMap<Integer, List<FileListItem>> months) {

					final List<MonthDTO> list = new ArrayList<>();
					for (final Integer month : months.keySet()) {
						final List<FileListItem> editionList = months.get(month);
						for (final FileListItem edition : editionList) {
							final MonthDTO dto = new MonthDTO();
							dto.month = month;
							dto.edition = edition;
							list.add(dto);
						}
					}
					return list;
				}

				public Integer month;
				public FileListItem edition;
			}

			public static List<YearDTO> fromMap(LinkedHashMap<Integer, LinkedHashMap<Integer, List<FileListItem>>> years) {

				final List<YearDTO> list = new ArrayList<>();
				for (final Integer year : years.keySet()) {
					final YearDTO dto = new YearDTO();
					dto.year = year;
					dto.months = MonthDTO.fromMap(years.get(year));
					list.add(dto);
				}
				return list;
			}

			public Integer year;
			public List<MonthDTO> months;
		}

		public static List<DecadeDTO> fromMap(Map<Integer, LinkedHashMap<Integer, LinkedHashMap<Integer, List<FileListItem>>>> decades) {

			final List<DecadeDTO> list = new ArrayList<>();
			for (final Integer decade : decades.keySet()) {
				final DecadeDTO dto = new DecadeDTO();
				dto.decade = decade;
				dto.years = YearDTO.fromMap(decades.get(decade));
				list.add(dto);
			}
			return list;
		}

		public int decade;
		public List<YearDTO> years;
	}

	public static FileListDTOOld toDTO(Map<Integer, LinkedHashMap<Integer, LinkedHashMap<Integer, List<FileListItem>>>> ordered) {

		final FileListDTOOld dto = new FileListDTOOld();
		dto.decades = DecadeDTO.fromMap(ordered);
		return dto;
	}

	public List<DecadeDTO> decades;
}
