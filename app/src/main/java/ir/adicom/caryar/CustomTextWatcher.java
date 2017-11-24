package ir.adicom.caryar;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;

/**
 * Created by adicom on 11/17/17.
 */

public class CustomTextWatcher implements TextWatcher {
    private EditText editText;

    public CustomTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        editText.removeTextChangedListener(this);
        try {
            String givenstring = s.toString();
            Long longval;
            if (givenstring.contains(",")) {
                givenstring = givenstring.replaceAll(",", "");
            }
            longval = Long.parseLong(givenstring);
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedString = formatter.format(longval);
            editText.setText(formattedString);
            editText.setSelection(editText.getText().length());
            // to place the cursor at the end of text
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        editText.addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
