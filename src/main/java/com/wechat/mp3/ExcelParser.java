package com.wechat.mp3;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelParser {
	public MediaInfo getMediaInfo(String mediaInfoPath) {
		MediaInfo mediaInfo = null;
		Workbook book = null;
		InputStream is = null;
		try {
			is = new FileInputStream(mediaInfoPath);
			book = Workbook.getWorkbook(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (book != null) {
			Sheet sheet = book.getSheet(0);
			int rows = sheet.getRows();
			int cols = sheet.getColumns();
			if (rows > 1 && cols > 0) {
				List<Sentence> sentenceList = new ArrayList<Sentence>();
				boolean isExpExist = false;

				int page = 0;
				int homework = -1;
				int[] struct = null;
				for (int row = 1; row < rows; row++) {
					String senId = sheet.getCell(0, row).getContents().trim();
					if (senId.toLowerCase().contains("end")) {
						break;
					} else if (!senId.trim().isEmpty()) {
						int id = -1;
						try {
							id = Integer.parseInt(senId);
						} catch (NumberFormatException e) {
						}
						if (id > 0) {
							Sentence sentence = new Sentence();
							sentence.setId(id);
							String wordNumStr = sheet.getCell(2, row)
									.getContents().trim();
							int wordNum = 0;
							if (!wordNumStr.isEmpty()) {
								try {
									wordNum = Integer.parseInt(wordNumStr);
								} catch (NumberFormatException e) {
								}
								if (wordNum > 0) {
									for (int i = row + 1; i <= row + wordNum; i++) {
										String text = sheet.getCell(6, i)
												.getContents().trim();
										String explain = sheet.getCell(9, i)
												.getContents().trim();
										sentence.addWord(text, explain);
									}
								}
							}
							int enStartTime = -1;
							String enStartTimeStr = sheet.getCell(3, row)
									.getContents().trim();
							try {
								enStartTime = Integer.parseInt(enStartTimeStr);
							} catch (NumberFormatException e) {
							}
							sentence.setStartTime(enStartTime);

							int readLength = 0;
							String readLengthStr = sheet.getCell(4, row)
									.getContents().trim();
							try {
								readLength = Integer.parseInt(readLengthStr);
							} catch (NumberFormatException e) {
							}
							sentence.setReadLength(readLength);

							int nextEnStartTime = -1;
							String nextEnStartTimeStr = sheet
									.getCell(3, row + 1 + wordNum)
									.getContents().trim();
							try {
								nextEnStartTime = Integer
										.parseInt(nextEnStartTimeStr);
							} catch (NumberFormatException e) {
							}

							int enLength = 0;
							if (nextEnStartTime > 0
									&& nextEnStartTime > enStartTime) {
								enLength = nextEnStartTime - enStartTime;
							} else {
								enLength = readLength;
							}
							sentence.setLength(enLength);

							String enText = sheet.getCell(6, row).getContents()
									.trim();
							sentence.setEnText(enText.equals("0") ? "" : enText);

							int expStartTime = -1;
							String expStartTimeStr = sheet.getCell(7, row)
									.getContents().trim();
							try {
								expStartTime = Integer
										.parseInt(expStartTimeStr);
							} catch (NumberFormatException e) {
							}
							sentence.setExpStartTime(expStartTime);

							int expLength = 0;
							String expLengthStr = sheet.getCell(8, row)
									.getContents().trim();
							try {
								expLength = Integer.parseInt(expLengthStr);
							} catch (NumberFormatException e) {
							}
							sentence.setExpLength(expLength);

							String expText = sheet.getCell(9, row)
									.getContents().trim();
							sentence.setExpText(expText.equals("0") ? ""
									: expText);

							String pageStr = sheet.getCell(10, row)
									.getContents().trim();
							if (!pageStr.isEmpty()) {
								try {
									page = Integer.parseInt(pageStr);
								} catch (NumberFormatException e) {
								}
							}
							sentence.setPage(page);

							boolean homewordEnd = false;
							String homeworkStr = sheet.getCell(11, row)
									.getContents().toLowerCase().trim();
							if (!homeworkStr.isEmpty()) {
								if (homeworkStr.endsWith("_start")) {
									String startStr = homeworkStr.substring(0,
											homeworkStr.indexOf("_start"));
									try {
										homework = Integer.parseInt(startStr);
									} catch (NumberFormatException e) {
									}
								} else if (homeworkStr.endsWith("end")) {
									homewordEnd = true;
								} else {
									homework = -1;
								}
							}
							sentence.setHomework(homework);
							if (homewordEnd) {
								homework = -1;
							}
							String structStr = sheet.getCell(12, row)
									.getContents().toLowerCase().trim();
							if (!structStr.isEmpty()) {
								String[] tmpArray = structStr.split("-");
								struct = new int[tmpArray.length];
								for (int i = 0; i < tmpArray.length; i++) {
									try {
										int sInt = Integer
												.parseInt(tmpArray[i]);
										struct[i] = sInt;
									} catch (NumberFormatException e) {
									}
								}
							}
							sentence.setStruct(struct);
							sentenceList.add(sentence);
						}

					} else {

					}
				}
				if (!sentenceList.isEmpty()) {
					mediaInfo = new MediaInfo(sentenceList);
				}
			}
		}
		return mediaInfo;
	}

	private static ExcelParser instance;

	public static ExcelParser getInstance() {
		if (instance == null) {
			instance = new ExcelParser();
		}
		return instance;
	}

}
