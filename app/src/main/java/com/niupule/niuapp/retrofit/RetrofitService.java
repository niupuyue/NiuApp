package com.niupule.niuapp.retrofit;

import com.niupule.niuapp.data.detail.ArticleData;
import com.niupule.niuapp.data.detail.HotKeyDetailData;
import com.niupule.niuapp.data.detail.HotKeysData;
import com.niupule.niuapp.data.detail.LoginData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2018/9/3
 * Time: 20:45
 * Desc:
 * Version:
 */
public interface RetrofitService {

    @FormUrlEncoded
    @POST(Api.LOGIN)
    Observable<LoginData> login(@Field("username") String username,@Field("password") String password);

    @FormUrlEncoded
    @POST(Api.REGISTER)
    Observable<LoginData> register(@Field("username") String username,@Field("password") String password,@Field("repassword") String repassword);

    @GET(Api.HOT_KEY)
    Observable<HotKeysData> getHotkeys();

    @GET(Api.ARTICLE_LIST+"{page}/json")
    Observable<ArticleData> getArticles(@Path("page") int page);

    @GET(Api.QUERY_ARTICLES+"{page}/json")
    Observable<ArticleData> queryArticle(@Path("page") int page, @Query("k") String k);

    @GET(Api.ARTICLE_LIST+"{page}/json")
    Observable<ArticleData> getArticleFromCateg(@Path("page") int page,@Query("cid") int cid);

}
