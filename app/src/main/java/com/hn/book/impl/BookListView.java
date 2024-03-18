package com.hn.book.impl;

import com.hn.book.bean.HnBook;
import com.hn.book.model.BookDetailResultModel;
import com.hn.book.model.BookListModel;
import com.hn.book.param.DeleteListParam;
import com.hn.book.param.DeleteParam;
import com.hn.book.param.QueryListParam;
import com.yangna.lbdsp.common.base.BaseModel;
import com.yangna.lbdsp.mall.model.ProductDetailResultModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BookListView {

    public void getBookDetailById(BookDetailResultModel model);

    public void addBook(BaseModel model);//

    public void deleteBook(BaseModel model);//

    public void deleteBookList(BaseModel model);//

    public void getBookList(BookListModel.BookModel bookModel);//

    public void updateBook(BaseModel model);//

}
