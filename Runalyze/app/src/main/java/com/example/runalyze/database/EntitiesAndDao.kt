package com.example.runalyze.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.TypeConverters
import java.sql.Date

@Entity
data class Goal(
    @PrimaryKey(autoGenerate = true)
    val goalId: Long,
    @TypeConverters(Converters::class)
    val startDate:Long?,
    @TypeConverters(Converters::class)
    val endDate:Long?,
    val repeatedDays: String,
    val reminderTime: String?,
    val targetDistanceInKm: Double,
    val targetSpeedInKmh: Double,
    val targetHeartRateInBpm: Int
)

@Entity
data class TrainingDetail(
    @PrimaryKey(autoGenerate = true)
    val trainingDetailId: Long,
    @TypeConverters(Converters::class)
    val trainingDateTime: Long,
    val durationInMinutes: Int,
    val distance: Double,
    val averageSpeed: Double,
    val heartRate: Int?,
    val elevation: Double?,
    val caloriesInKcal: Int?,
    val rating: Int?,
    val note: String?
)

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGoal(goal: Goal): Long
}

@Dao
interface TrainingDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrainingDetail(detail: TrainingDetail): Long

    @Query("SELECT COUNT(*) FROM TrainingDetail")
    fun getTrainingDetailCount(): LiveData<Int>

    @Query("SELECT * FROM TrainingDetail")
    fun getTrainingDetails(): LiveData<List<TrainingDetail>>
}