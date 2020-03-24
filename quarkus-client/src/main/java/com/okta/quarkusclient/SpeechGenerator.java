package com.okta.quarkusclient;

import io.reactivex.Flowable;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class SpeechGenerator {

    private static final Logger LOG = Logger.getLogger(SpeechGenerator.class);
    private Integer index = 0;
    private String[] speech = {"Interwoven", "as", "is", "the", "love", "of", "liberty", "with", "every", "ligament", "of", "your", "hearts,",
            "no", "recommendation", "of", "mine", "is", "necessary", "to", "fortify", "or", "confirm", "the", "attachment.", "The", "unity", "of",
            "government", "which", "constitutes", "you", "one", "people", "is", "also", "now", "dear", "to", "you.", "It", "is", "justly", "so,", "for",
            "it", "is", "a", "main", "pillar", "in", "the", "edifice", "of", "your", "real", "independence,", "the", "support", "of", "your",
            "tranquillity", "at", "home,", "your", "peace", "abroad,", "of", "your", "safety,", "of", "your", "prosperity,", "of", "that", "very", "liberty",
            "which", "you", "so", "highly", "prize.", "But", "as", "it", "is", "easy", "to", "foresee", "that", "from", "different", "causes", "and", "from",
            "different", "quarters", "much", "pains", "will", "be", "taken,", "many", "artifices", "employed,", "to", "weaken", "in", "your", "minds", "the",
            "conviction", "of", "this", "truth,", "as", "this", "is", "the", "point", "in", "your", "political", "fortress", "against", "which", "the",
            "batteries", "of", "internal", "and", "external", "enemies", "will", "be", "most", "constantly", "and", "actively", "(though", "often", "covertly",
            "and", "insidiously)", "directed,", "it", "is", "of", "infinite", "moment", "that", "you", "should", "properly", "estimate", "the", "immense",
            "value", "of", "your", "national", "union", "to", "your", "collective", "and", "individual", "happiness;", "that", "you", "should", "cherish", "a",
            "cordial,", "habitual,", "and", "immovable", "attachment", "to", "it;", "accustoming", "yourselves", "to", "think", "and", "speak", "of", "it", "as",
            "of", "the", "palladium", "of", "your", "political", "safety", "and", "prosperity;", "watching", "for", "its", "preservation", "with", "jealous",
            "anxiety;", "discountenancing", "whatever", "may", "suggest", "even", "a", "suspicion", "that", "it", "can", "in", "any", "event", "be", "abandoned,",
            "and", "indignantly", "frowning", "upon", "the", "first", "dawning", "of", "every", "attempt", "to", "alienate", "any", "portion", "of", "our", "country",
            "from", "the", "rest", "or", "to", "enfeeble", "the", "sacred", "ties", "which", "now", "link", "together", "the", "various", "parts."
    };

    @Outgoing("generated-word")
    public Flowable<String> generate() {
        return Flowable.interval(500, TimeUnit.MILLISECONDS).map(tick -> {
            String nextWord = speech[index];
            index += 1;
            if (index == speech.length) index = 0;
            LOG.info("Next word = " + nextWord);
            return nextWord;
        });
    }
}
