#sql("getDetailBymemberId")
SELECT
	q.id questionId,
	q.member_id memberId,
	q.text qusetionText,
	rela.id relaId,
	a.id answerId,
	a.text answerText
FROM
	train_question q
LEFT JOIN train_question_answer_rela rela ON  rela. STATUS = 1
AND q.id = rela.question_id
LEFT JOIN train_answer a ON rela.answer_id = a.id
AND a. STATUS = 1
WHERE
	member_id = #para(memberId)
AND
q.status = 1
ORDER BY q.id
#end


#sql("getAnswerByKnowledgeIds")
SELECT
ar.knowledge_id knowledgeId,
ar.id knowledgeAnswerRelaId,
a.id,
IFNULL(a.text,"") text,
a.type,
IFNULL(a.content,"") content
FROM
train_knowledge_answer_rela ar LEFT JOIN train_answer_item a ON ar.answer_item_id = a.id
WHERE
ar.knowledge_id in(
#for(t : knowledgeIds)
  #(for.index > 0?",":"")
  '#(t)'
#end
)
AND
ar.status = 1 and a.status=1
#end


#sql("getQuestionByKnowledgeIds")
SELECT
qr.knowledge_id knowledgeId,
qr.id knowledgeQuestionRelaId,
q.id,
q.text
FROM
train_knowledge_question_rela qr LEFT JOIN train_question_item q ON qr.question_item_id = q.id
WHERE
qr.knowledge_id in(
#for(t : knowledgeIds)
  #(for.index > 0?",":"")
  '#(t)'
#end
)
AND
qr.status = 1 AND q.`status`=1
#end