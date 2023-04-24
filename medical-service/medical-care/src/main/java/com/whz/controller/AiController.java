package com.whz.controller;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.whz.entity.Disease;
import com.whz.entity.Drugs;
import com.whz.entity.Forum;
import com.whz.entity.Symptom;
import com.whz.mapper.DiseaseMapper;
import com.whz.mapper.DrugsMapper;
import com.whz.mapper.ForumMapper;
import com.whz.mapper.SymptomMapper;
import com.whz.pojo.IntelligentResults;
import com.whz.strings.WordType;
import com.whz.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 文辉正
 * @since 2023/4/22 20:23
 */
@Slf4j
@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    Segment segment;

    @Resource
    SymptomMapper symptomMapper;

    @Resource
    DiseaseMapper diseaseMapper;

    @Resource
    DrugsMapper drugsMapper;

    @Resource
    ForumMapper forumMapper;

    @PostMapping("/search")
    public R search(@RequestBody Map<String, String> map){
        Set<IntelligentResults> results = new HashSet<>();
        Set<String> keyWord = new HashSet<>();
        //进行普通分词
        List<Term> terms = segment.seg(map.get("data"));
        terms.forEach(term -> {
            log.debug("分词：{} ## 词性：{}",term.word,term.nature);
            if(term.nature.toString().equals(WordType.drugs)){
                keyWord.add(term.word);
                setDrugs(term.word,results);
                setForum(term.word,results);
            }else if(term.nature.toString().equals(WordType.symptom)){
                keyWord.add(term.word);
                setSymptoms(term.word,results);
                setForum(term.word,results);
            }else if(term.nature.toString().equals(WordType.disease)){
                keyWord.add(term.word);
                setDiseases(term.word,results);
                setForum(term.word,results);
            }else if(term.nature.firstChar() == 'a' || term.nature.firstChar() == 'n'){
                //a 形容词 n 名词
                keyWord.add(term.word);
                setDrugs(term.word,results);
                setSymptoms(term.word,results);
                setDiseases(term.word,results);
                setForum(term.word,results);
            }
        });
        List<String> data = HanLP.extractKeyword(map.get("data"), 5);//再获取5个关键词
        log.debug("关键词：{}",data.toString());
        data.removeAll(keyWord); //移除上面使用过的
        data.forEach(k ->{
            setDrugs(k,results);
            setSymptoms(k,results);
            setDiseases(k,results);
            setForum(k,results);
        });

        return R.ok().put(results);
    }

    private void setDrugs(String word,Set<IntelligentResults> results) {
        List<Drugs> drugs = drugsMapper.selectListByName(word);
        drugs.forEach(drug -> {
            results.add(IntelligentResults.builder()
                    .type(WordType.drugs)
                    .data(drug).build());
        });
    }

    private void setSymptoms(String word,Set<IntelligentResults> results) {
        Set<Symptom> symptoms = symptomMapper.selectByName(word);
        symptoms.forEach(symptom -> {
            results.add(IntelligentResults.builder()
                    .type(WordType.symptom)
                    .data(symptom).build());
        });
    }

    private void setDiseases(String word,Set<IntelligentResults> results) {
        Set<Disease> diseases = diseaseMapper.getSearchByName(word);
        diseases.forEach(disease -> {
            results.add(IntelligentResults.builder()
                    .type(WordType.disease)
                    .data(disease).build());
        });
    }

    private void setForum(String word,Set<IntelligentResults> results) {
        Set<Forum> forums = forumMapper.selectByTitle(word);
        forums.forEach(forum -> {
            results.add(IntelligentResults.builder()
                    .type(WordType.forum)
                    .data(forum).build());
        });
    }


}
