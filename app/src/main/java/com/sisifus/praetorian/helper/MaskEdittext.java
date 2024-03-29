package com.sisifus.praetorian.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskEdittext {

    public static final String FORMAT_DATE = "##/##/####";
    public static final String INSULINA = "###";
    public static final String PRESSAO1 = "###";
    public static final String PRESSAO2 = "###";





    public static TextWatcher mask(final EditText editText, final String mask){
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                final String str = MaskEdittext.unMask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for(final char m: mask.toCharArray()){
                    if (m!= '#' && str.length()>old.length()){
                        mascara+=m;
                        continue;
                    }
                    try{
                        mascara+= str.charAt(i);
                    }catch (final Exception e){
                        break;
                    }
                    i++;
                }

                isUpdating =true;
                editText.setText(mask);
                editText.setSelection(mask.length());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };

    }

    public static String unMask(final String s){
        return s.replace("[.]","")
                .replaceAll("[-]","")
                .replaceAll("[/]","")
                .replaceAll("[(]","")
                .replaceAll("[ ]","")
                .replaceAll("[)]","")
                .replaceAll("[h]","")
                .replaceAll("[m]","");
    }




}
