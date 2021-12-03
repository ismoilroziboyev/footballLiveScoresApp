package uz.ismoilroziboyev.footballlivescores.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import uz.ismoilroziboyev.footballlivescores.database.AppDatabase
import uz.ismoilroziboyev.footballlivescores.utils.NetworkHelper
import java.lang.Exception

class ViewModelFactory(val appDatabase: AppDatabase,val networkHelper: NetworkHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(uz.ismoilroziboyev.footballlivescores.viewmodels.ViewModel::class.java)) {
            return uz.ismoilroziboyev.footballlivescores.viewmodels.ViewModel(appDatabase,networkHelper) as T
        }

        throw Exception("Error")
    }
}