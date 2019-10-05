package com.bcil.visitorapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.bcil.visitorapp.model.VisitorData;

import java.util.List;


/**
 * Created by Dev6 on 5/5/2018.
 */
@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySingleRecord(VisitorData assetInfo);

    @Query("SELECT * FROM VisitorData")
    List<VisitorData> fetchAllData();

   /* @Query("SELECT * FROM VisitorData WHERE scan =:uscan")
    VisitorData getRfidDataExist(String uscan);


    @Query("DELETE FROM VisitorData WHERE scan =:uscan")
    void deleteRfidData(String uscan);*/


    @Query("DELETE FROM VisitorData")
    void deleteAllDataList();
}
