package choehaualen.breath.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import choehaualen.breath.data.local.UserDao
import choehaualen.breath.data.local.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(
    private val dao: UserDao
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user = _user.asStateFlow()

    fun setNameText(newName: String) = _name.update { newName }

    fun onNext() {
        viewModelScope.launch {
            val userEntity = UserEntity(name = name.value)
            val id = withContext(Dispatchers.IO) { dao.insertUser(userEntity) }
            val entity = withContext(Dispatchers.IO) { dao.getUserById(id) }
            _user.update { entity }
        }
    }

    fun onFlush() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { dao.flushDatabase() }
                .also { withContext(Dispatchers.Default) { _user.update { null } } }
        }
    }

}