package com.example.SentenceComplexity;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class SentenceComplexityController {
    private StanfordCoreNLP pipeline;

    @Autowired
    public SentenceComplexityController(StanfordCoreNLP pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("/complexity")
    public String getComplexity(@RequestParam("sentence") String sentence) {

        Annotation document = new Annotation(sentence);
        pipeline.annotate(document);
        CoreMap sentenceMap = document.get(CoreAnnotations.SentencesAnnotation.class).get(0);
        int numWords = sentenceMap.get(CoreAnnotations.TokensAnnotation.class).size();

        double percentage = (double) numWords / 20 * 100;
        String complexity;
        if (percentage <= 50.0) {
            complexity = "Simple";
        } else if (percentage <= 75.0) {
            complexity = "Moderate";
        } else {
            complexity = "Complex";
        }

        String result = "The complexity of the sentence is " + complexity;
        result += " (" + String.format("%.1f", percentage) + "%)";
        System.out.println(result);

        return result;
    }

}




