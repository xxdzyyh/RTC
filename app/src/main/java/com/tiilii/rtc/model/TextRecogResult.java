package com.tiilii.rtc.model;

import java.util.List;

/**
 * @author fox
 * @since 2018/03/19
 */

public class TextRecogResult {

    private String log_id;
    private int direction;
    private int words_result_num;
    private List<Word> words_result;


    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<Word> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<Word> words_result) {
        this.words_result = words_result;
    }

    public class Word {
        private String words;

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }
    }
}
