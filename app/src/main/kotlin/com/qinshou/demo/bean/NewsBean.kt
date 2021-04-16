package com.qinshou.demo.bean

/**
 * Author: QinHao
 * Email:qinhao@jeejio.com
 * Date: 2021/4/16 10:59
 * Description:类描述
 */
class NewsBean {
    var id: Int? = null
    var href: String? = null
    var imgSmall: String? = null
    var imgBig: String? = null
    var titleChinese: String? = null
    var titleEnglish: String? = null
    var publishTime: Long? = null
    var introduction: String? = null
    var sourceChinese: String? = null
    var sourceEnglish: String? = null
    var translatorAndEditor: String? = null
    var htmlContent: String? = null

    override fun toString(): String {
        return "NewsBean{" +
                "id=" + id +
                ", href='" + href + '\'' +
                ", imgSmall='" + imgSmall + '\'' +
                ", imgBig='" + imgBig + '\'' +
                ", titleChinese='" + titleChinese + '\'' +
                ", titleEnglish='" + titleEnglish + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", introduction='" + introduction + '\'' +
                ", sourceChinese='" + sourceChinese + '\'' +
                ", sourceEnglish='" + sourceEnglish + '\'' +
                ", translatorAndEditor='" + translatorAndEditor + '\'' +
                ", htmlContent='" + htmlContent + '\'' +
                '}'
    }
}