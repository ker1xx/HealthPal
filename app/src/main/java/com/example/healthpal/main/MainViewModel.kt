package com.example.healthpal.main

import android.content.Context
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.healthpal.localdb.DTO.workout.WorkoutDTO
import com.example.healthpal.localdb.HealthPalDatabase
import com.example.healthpal.localdb.helper.WorkoutEnums.WorkoutIntensityLevelTypeEnum
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import com.google.android.gms.fitness.request.GoalsReadRequest
import com.google.android.gms.fitness.result.DataReadResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.nextUp
import kotlin.random.Random

@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {
    var steps = mutableFloatStateOf(0f)
    var calories = mutableFloatStateOf(0f)
    var isLoadingFinished = mutableStateOf(false)
    var stepsForCertainDates = mutableStateOf<List<Float>>(listOf())
    var caloriesForCertainDates = mutableStateOf<List<Float>>(listOf())


    val fitnessOptions: FitnessOptions by lazy {
        FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.AGGREGATE_DISTANCE_DELTA, FitnessOptions.ACCESS_READ)
            .build()
    }

    private fun getData(dataSet: DataSet) {
        val dataPoints = dataSet.dataPoints
        for (point in dataPoints) {
            Timber.tag("info").i("data manual: + ${point.originalDataSource.streamName}")

            for (field in point.dataType.fields) {
                val value = point.getValue(field).toString().toFloat()
                Timber.tag("info").i("data: $value")

                when (field.name) {
                    Field.FIELD_STEPS.name -> steps.floatValue =
                        value.toString().format("#.##").toFloat()

                    Field.FIELD_CALORIES.name -> calories.floatValue =
                        value.toString().format("#.##").toFloat()
                }
            }
        }
        isLoadingFinished.value = true
    }

    fun requestForHistory(
        context: Context,
        startDateCalendar: Calendar,
        googleAccount: GoogleSignInAccount
    ) {
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        val startTime = startDateCalendar.timeInMillis

        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA)
            .aggregate(DataType.AGGREGATE_STEP_COUNT_DELTA)
            .aggregate(DataType.TYPE_CALORIES_EXPENDED)
            .aggregate(DataType.AGGREGATE_CALORIES_EXPENDED)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build()

        Fitness.getHistoryClient(context, googleAccount)
            .readData(readRequest)
            .addOnSuccessListener { it ->
                onSuccess(it)
            }

    }

    fun onSuccess(o: Any) {
        if (o is DataSet) {
            getData(o)
        } else if (o is DataReadResponse) {
            if (o.buckets.isNotEmpty()) {
                for (bucket in o.buckets) {
                    val stepsDataSet = bucket.getDataSet(DataType.TYPE_STEP_COUNT_DELTA)
                    getDataFromDataReadResponse(stepsDataSet)
                    val caloriesDataSet = bucket.getDataSet(DataType.TYPE_CALORIES_EXPENDED)
                    getDataFromDataReadResponse(caloriesDataSet)
                }
            }
        }
    }

    private fun getDataFromDataReadResponse(dataSet: DataSet?) {
        if (dataSet?.isEmpty == false) {
            val dataPoints = dataSet.dataPoints
            for (dataPoint in dataPoints) {
                for (field in dataPoint.dataType.fields) {
                    val value = dataPoint.getValue(field).toString().format("#.##").toFloat()
                    Timber.tag("info").i("data $value")

                    when (field.name) {
                        Field.FIELD_STEPS.name -> stepsForCertainDates.value +=
                            value

                        Field.FIELD_CALORIES.name -> caloriesForCertainDates.value +=
                            value
                    }
                }
            }
        }
    }

    fun generateExercise(db: HealthPalDatabase): WorkoutDTO {
        val workoutsByIntensity = db
            .workoutDao()
            .getWorkoutsByIntensity(
                WorkoutIntensityLevelTypeEnum
                    .Intermediate
                    .name
            )
        val allWorkouts = db
            .workoutDao()
            .getAllWorkouts()
        if (workoutsByIntensity.size > 0) {
            return allWorkouts[Random.nextInt(
                0,
                workoutsByIntensity.size - 1
            )]
        }
        return allWorkouts[Random.nextInt(
            0,
            allWorkouts.size - 1
        )]
    }
}