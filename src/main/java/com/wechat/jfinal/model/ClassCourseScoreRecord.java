package com.wechat.jfinal.model;

import com.wechat.jfinal.model.base.BaseClassCourseScoreRecord;

import java.util.List;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class ClassCourseScoreRecord extends BaseClassCourseScoreRecord<ClassCourseScoreRecord> {
	public static final ClassCourseScoreRecord dao = new ClassCourseScoreRecord().dao();

    public ClassCourseScoreRecord findLowWords(Integer recordId) {
        return findFirst("SELECT a.wordList FROM `class_course_score_record` a WHERE  a.id=?",recordId);
    }
}