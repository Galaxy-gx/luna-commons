package com.luna.baidu.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.luna.common.http.HttpUtils;
import com.luna.common.http.HttpUtilsConstant;
import com.luna.common.utils.text.CharsetKit;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author Luna@win10
 * @date 2020/5/24 21:10
 */
public class BaiduLanguageProcessingApi {

    /**
     * 百度文本纠错
     * 
     * @param text
     * @throws IOException
     */
    public static String correction(String key, String text) {
        String body = "{\"text\": \"" + text + "\"" + "}";
        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiContent.HOST, BaiduApiContent.LANGUAGE_PROCESSING,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON),
            ImmutableMap.of("access_token", key, "charset", CharsetKit.UTF_8),
            body);
        JSONObject response = HttpUtils.getResponse(httpResponse);
        JSONObject jsonObject = JSON.parseObject(response.get("item").toString());
        return jsonObject.get("correct_query").toString();
    }

    /**
     * 文本相似度比较
     *
     * @param text1 文本1
     * @param text2 文本2
     * @param model 默认使用BOW
     * BOW（词包）模型=>基于bag of words的BOW模型，特点是泛化性强，效率高，比较轻量级，适合任务：输入序列的 term “确切匹配”、不关心序列的词序关系，对计算效率有很高要求；
     *
     * GRNN（循环神经网络）模型=>基于recurrent，擅长捕捉短文本“跨片段”的序列片段关系，适合任务：对语义泛化要求很高，对输入语序比较敏感的任务；
     *
     * CNN（卷积神经网络）模型=>模型语义泛化能力介于 BOW/RNN 之间，对序列输入敏感，相较于 GRNN 模型的一个显著优点是计算效率会更高些。
     * @return
     * @throws IOException
     */
    public static String similarityText(String key, String text1, String text2, String model) {
        if (model == null || model.length() == 0) {
            model = "BOW";
        }
        HashMap<String, String> textParam = Maps.newHashMap();
        textParam.put("text_1", text1);
        textParam.put("text_2", text2);
        textParam.put("model", model);
        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiContent.HOST, BaiduApiContent.TEXT_SIMILARITY,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON),
            ImmutableMap.of("access_token", key, "charset", CharsetKit.UTF_8),
            JSON.toJSONString(textParam));
        JSONObject response = HttpUtils.getResponse(httpResponse);
        return response.get("score").toString();
    }

    /**
     * 词语比较
     * 
     * @param word1
     * @param word2
     * @return
     * @throws IOException
     */
    public static String similarityWords(String key, String word1, String word2) {
        HashMap<String, String> wordParam = Maps.newHashMap();
        wordParam.put("word_1", word1);
        wordParam.put("word_2", word2);
        HttpResponse httpResponse = HttpUtils.doPost(BaiduApiContent.HOST, BaiduApiContent.WOEDS_SIMILARITY,
            ImmutableMap.of("Content-Type", HttpUtilsConstant.JSON),
            ImmutableMap.of("access_token", key, "charset", CharsetKit.UTF_8),
            JSON.toJSONString(wordParam));
        JSONObject response = HttpUtils.getResponse(httpResponse);
        return response.get("score").toString();
    }

}
