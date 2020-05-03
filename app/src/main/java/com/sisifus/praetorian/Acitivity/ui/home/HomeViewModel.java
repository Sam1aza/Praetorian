package com.sisifus.praetorian.Acitivity.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Manhã" +
                "\t\t\t\t\n"+
                "\tCarbamazepina\t200\tMlg\t12H\n" +
                "\tRisperidona\t1\tMlg\t12H\n" +
                "\tBrilinta\t90\tMlg\t12H\n" +
                "\tLosartana (Aradois)\t25\tMlg\t12H\n" +
                "\tConcor\t2.5\tMlg\t24H\n" +
                "\tZimpas (Rosuvastatina)\t10\tMlg\t24H\n" +
                "\t\t\t\t\n" +
                "Tarde" +
                "\t\t\t\t\n"+
                "\tStanglit\t30\tMlg\t24H\n" +
                "\tAAS\t100\tMlg\t24H\n" +
                "\t\t\t\t\n" +
                "Noite" +
                "\t\t\t\t\n"+
                "\tCarbamazepina\t200\tMlg\t12H\n" +
                "\tRisperidona\t1\tMlg\t12H\n" +
                "\tBrilinta\t90\tMlg\t12H\n" +
                "\tLosartana (Aradois)\t25\tMlg\t12H\n");
    }

    public LiveData<String> getText() {
        return mText;
    }
}