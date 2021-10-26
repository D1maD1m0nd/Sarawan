package com.example.sarawan.model.datasource

import com.example.sarawan.model.data.DataModel
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class DataSourceLocal @Inject constructor(private val remoteProvider: RoomDataBaseImplementation) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)

    override fun saveData(dataSet: List<DataModel>) = remoteProvider.saveData(dataSet)
}
