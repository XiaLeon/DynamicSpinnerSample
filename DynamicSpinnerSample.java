import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.memorydemo.R;

import java.util.ArrayList;
import java.util.List;

public class DynamicSpinnerSample extends Activity implements View.OnClickListener {
    private static final String TAG = "DynamicSpinnerSample";

    private TextView mTextView;
    private EditText mEditText;
    private Button mBtnAdd;
    private Button mBtnRemove;
    private Spinner mSpinner;
    private ArrayAdapter<String> mAdapter;
    private List<String> mCountries;

    @Override
    protected void onCreate(Bundle onSavedInstance) {
        super.onCreate(onSavedInstance);
        setContentView(R.layout.dynamic_spinner_sample);

        mTextView = findViewById(R.id.textView10);
        mEditText = findViewById(R.id.editTextDynamicSpinner);

        mBtnAdd = findViewById(R.id.buttonAddToSpinner);
        mBtnAdd.setOnClickListener(this);
        mBtnRemove = findViewById(R.id.buttonRemoveFromSpinner);
        mBtnRemove.setOnClickListener(this);
        mSpinner = findViewById(R.id.dynamicSpinner);

        initData();

        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mCountries);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTextView.setText(mCountries.get(position));

                // 换成下面这种写法也可以
                // mTextView.setText(parent.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i(TAG, "onNothingSelected ...");
            }
        });
    }

    private void initData() {
        mCountries = new ArrayList<>(4);
        mCountries.add("北京");
        mCountries.add("上海");
        mCountries.add("广州");
        mCountries.add("深圳");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddToSpinner:
                String newCity = mEditText.getText().toString();

                // 先检查该城市是否已存在于 Spinner 下拉菜单中，如果存在，就不添加
                if (mCountries.contains(newCity)) {
                    return;
                }

                // 用户输入内容的合法性检查
                if (!TextUtils.isEmpty(newCity)) {
                    mAdapter.add(newCity);

                    // 获取该新内容所对应的位置，并将 Spinner 设置为该位置
                    int position = mAdapter.getPosition(newCity);
                    mSpinner.setSelection(position);

                    // 清空用户输入的内容
                    mEditText.setText("");
                }

                break;

            case R.id.buttonRemoveFromSpinner:
                if (mSpinner.getSelectedItem() != null) {
                    mAdapter.remove(mSpinner.getSelectedItem().toString());
                    mEditText.setText("");

                    // 如果 Spinner全部被清空，也给用户显示空内容
                    if (mAdapter.getCount() == 0) {
                        mTextView.setText("");
                    }
                }
                break;

            default:
                break;
        }
    }
}
