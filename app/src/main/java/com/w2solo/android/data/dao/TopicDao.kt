package com.w2solo.android.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.w2solo.android.data.entity.Topic

@Dao
interface TopicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topic: Topic)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<Topic>)

    fun listHomeTopics(): List<Topic>

    @Delete
    fun delete(topic: Topic)

    @Delete
    fun deleteAll(topics: List<Topic>)
}