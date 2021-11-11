package py.com.frontendtp2.ui.fichaClinica;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FichaClinicaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FichaClinicaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is ficha clinica fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}