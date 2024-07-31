import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson7.models.Hero

object HeroDetailsViewModel : ViewModel() {
    val heroToUpdate = MutableLiveData<Hero?>(null)

    fun saveData(hero: Hero? = null) {
        heroToUpdate.value = hero
    }
}
